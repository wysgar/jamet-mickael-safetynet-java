package com.safetynetalerts.api.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.api.model.Firestation;
import com.safetynetalerts.api.model.MedicalRecord;
import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.model.DTO.ChildAlertDTO;
import com.safetynetalerts.api.model.DTO.ChildDTO;
import com.safetynetalerts.api.model.DTO.FireDTO;
import com.safetynetalerts.api.model.DTO.FirestationDTO;
import com.safetynetalerts.api.model.DTO.FloodDTO;
import com.safetynetalerts.api.model.DTO.HomeDTO;
import com.safetynetalerts.api.model.DTO.PersonDTO;
import com.safetynetalerts.api.model.DTO.PersonInfoLastNameDTO;
import com.safetynetalerts.api.model.DTO.PersonMedicalRecordDTO;
import com.safetynetalerts.api.model.DTO.PhoneAlertDTO;

/**
 * Service providing various alerting functionalities for the SafetyNet Alerts API.
 * It includes retrieving person data based on firestation number, address, city, 
 * and other criteria, as well as calculating relevant information like age.
 * 
 * This service interacts with PersonService, FirestationService, and MedicalRecordService to 
 * gather the necessary data and compute results, often returning DTO objects to represent 
 * the processed data.
 */
@Service
public class AlertService {
	
	@Autowired
	private PersonService personService;
	@Autowired
	private FirestationService firestationService;
	@Autowired
	private MedicalRecordService medicalRecordService;
	private static final Logger logger = LogManager.getLogger("AlertService");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	/**
     * Get a list of persons covered by a specific firestation.
     * 
     * @param stationNumber The firestation number to retrieve persons for.
     * @return A FirestationDTO containing the persons and a count of adults and children.
     */
	public FirestationDTO getPersonPerStation(int stationNumber) {
		logger.info("Fetching persons for station number: {}", stationNumber);
		
		List<Person> persons;
        List<Firestation> firestations;
        List<MedicalRecord> medicalRecords;

        try {
            persons = personService.getPerson();
            firestations = firestationService.getFirestation();
            medicalRecords = medicalRecordService.getMedicalRecord();
        } catch (Exception e) {
            logger.error("Error fetching data from services", e);
            return new FirestationDTO();  // Return an empty object on error
        }
        
        logger.debug("Found {} persons, {} firestations, and {} medical records", persons.size(), firestations.size(), medicalRecords.size());

	    // Create a Map of addresses associated with the requested station
	    Set<String> addressesForStation = firestations.stream()
	        .filter(firestation -> firestation.getStation() == stationNumber)
	        .map(Firestation::getAddress)
	        .collect(Collectors.toSet());

	    logger.debug("Addresses associated with station {}: {}", stationNumber, addressesForStation);
	    
	    // Create a Map to search medical records by full name
	    Map<String, MedicalRecord> medicalRecordByName = medicalRecords.stream()
	        .collect(Collectors.toMap(
	            record -> record.getFirstName() + " " + record.getLastName(),
	            record -> record
	        ));

	    FirestationDTO firestationDTO = new FirestationDTO();
	    List<PersonDTO> personsDTO = new ArrayList<>();
	    int countAdult = 0;
	    int countChild = 0;

	    // Browse people once and check if their address is associated with the station
	    for (Person person : persons) {
	        if (addressesForStation.contains(person.getAddress())) {
	        	logger.debug("Processing person: {} {}", person.getFirstName(), person.getLastName());
	        	
	            // Create a DTO for each person
	            PersonDTO personDTO = mapPersonToDTO(person);
	            personsDTO.add(personDTO);

	            // Search for the corresponding medical record
	            MedicalRecord medicalRecord = medicalRecordByName.get(
	                person.getFirstName() + " " + person.getLastName());

	            if (medicalRecord != null) {
	                double age = calculateAge(medicalRecord);
	                logger.debug("Person age: {} ({} {})", age, person.getFirstName(), person.getLastName());
	                if (age < 18) {
	                    countChild++;
	                } else {
	                    countAdult++;
	                }
	            }
	        }
	    }

	    firestationDTO.setPersons(personsDTO);
	    firestationDTO.setCountAdult(countAdult);
	    firestationDTO.setCountChild(countChild);

	    logger.info("Returning {} persons ({} adults, {} children) for station number {}", personsDTO.size(), countAdult, countChild, stationNumber);
	    
	    return firestationDTO;
	}
	
	/**
	 * Converts a Person object into a PersonDTO object.
	 *
	 * This method maps the attributes of a given {@link Person} object to a 
	 * {@link PersonDTO} object. Specifically, it transfers the first name, 
	 * last name, address, and phone number from the person entity to the DTO.
	 *
	 * @param person the {@link Person} object to be converted into a {@link PersonDTO}
	 * @return the {@link PersonDTO} object containing the mapped fields from the person entity
	 */
	private PersonDTO mapPersonToDTO(Person person) {
	    PersonDTO personDTO = new PersonDTO();
	    personDTO.setAddress(person.getAddress());
	    personDTO.setFirstName(person.getFirstName());
	    personDTO.setLastName(person.getLastName());
	    personDTO.setPhone(person.getPhone());
	    return personDTO;
	}

	/**
     * Get children living at a specific address.
     * 
     * @param address The address to check for children.
     * @return A ChildAlertDTO containing children and family members living at the address.
     */
	public ChildAlertDTO getChildPerAddress(String address) {
        logger.info("Fetching children for address: {}", address);

        List<Person> persons;
        List<MedicalRecord> medicalRecords;

        try {
            persons = personService.getPerson();
            medicalRecords = medicalRecordService.getMedicalRecord();
        } catch (Exception e) {
            logger.error("Error fetching data from services", e);
            return new ChildAlertDTO();  // Return an empty object on error
        }

        logger.debug("Found {} persons and {} medical records", persons.size(), medicalRecords.size());

        // Create a Map to search medical records by full name
        Map<String, MedicalRecord> medicalRecordByName = medicalRecords.stream()
            .collect(Collectors.toMap(
                record -> record.getFirstName() + " " + record.getLastName(),
                record -> record
            ));

        // Filter people who live at the given address
        List<Person> personsAtAddress = persons.stream()
            .filter(person -> person.getAddress().equals(address))
            .collect(Collectors.toList());

        logger.debug("Found {} persons at address {}", personsAtAddress.size(), address);

        ChildAlertDTO childAlertDTO = new ChildAlertDTO();
        List<ChildDTO> childs = new ArrayList<>();
        List<Person> family = new ArrayList<>();

        for (Person person : personsAtAddress) {
            String personFullName = person.getFirstName() + " " + person.getLastName();
            MedicalRecord medicalRecord = medicalRecordByName.get(personFullName);
            
            if (medicalRecord != null) {
                ChildDTO childDTO = new ChildDTO();
                double age = calculateAge(medicalRecord);
                logger.debug("Person {} age: {}", personFullName, age);

                if (age < 18) {
                    logger.info("Child found: {} {}", person.getFirstName(), person.getLastName());

                    childDTO.setAge((int) age);
                    childDTO.setFirstName(person.getFirstName());
                    childDTO.setLastName(person.getLastName());

                    childs.add(childDTO);
                }
                else {
                	family.add(person);
                }
            }
        }
        
        childAlertDTO.setChilds(childs);
        childAlertDTO.setFamily(family);
        
        logger.info("Returning child alert info for address {} with {} children and {} family members", address, childAlertDTO.getChilds().size(), childAlertDTO.getFamily().size());
        
        if(childAlertDTO.getChilds().isEmpty()) {
        	ChildAlertDTO temp = new ChildAlertDTO();
        	temp.setChilds(new ArrayList<>());
        	temp.setFamily(new ArrayList<>());
        	return temp;
        }
        else {
        	return childAlertDTO;
        }
    }

	/**
     * Get phone numbers of persons covered by a specific firestation.
     * 
     * @param firestationNumber The firestation number to retrieve phone numbers for.
     * @return A PhoneAlertDTO containing the phone numbers of people covered by the firestation.
     */
	public PhoneAlertDTO getPhonePerStation(int firestationNumber) {
        logger.info("Fetching phone numbers for firestation number: {}", firestationNumber);

        List<Person> persons;
        List<Firestation> firestations;

        try {
            persons = personService.getPerson();
            firestations = firestationService.getFirestation();
        } catch (Exception e) {
            logger.error("Error fetching data from services", e);
            return new PhoneAlertDTO(); // Return an empty object on error
        }

        logger.debug("Found {} persons and {} firestations", persons.size(), firestations.size());

        // Create a Set of addresses associated with the fire station
        Set<String> addressesForStation = firestations.stream()
            .filter(firestation -> firestation.getStation() == firestationNumber)
            .map(Firestation::getAddress)
            .collect(Collectors.toSet());

        logger.debug("Addresses associated with firestation number {}: {}", firestationNumber, addressesForStation);

        // Filter people living at addresses associated with the station
        List<String> phoneNumbers = persons.stream()
            .filter(person -> addressesForStation.contains(person.getAddress()))
            .map(Person::getPhone)
            .distinct() // Avoid duplicate phone numbers
            .collect(Collectors.toList());

        logger.debug("Found {} phone numbers for firestation number {}", phoneNumbers.size(), firestationNumber);

        PhoneAlertDTO phoneAlertDTO = new PhoneAlertDTO();
        phoneAlertDTO.setPhone(phoneNumbers);

        logger.info("Returning {} phone numbers for firestation number {}", phoneNumbers.size(), firestationNumber);
        return phoneAlertDTO;
    }

	/**
     * Get persons and associated medical information for a specific address.
     * 
     * @param address The address to check for persons.
     * @return A FireDTO containing persons' medical information and the firestation number.
     */
	public FireDTO getPersonPerAddress(String address) {
        logger.info("Fetching persons and firestation information for address: {}", address);

        List<Person> persons;
        List<Firestation> firestations;
        List<MedicalRecord> medicalRecords;

        try {
            persons = personService.getPerson();
            firestations = firestationService.getFirestation();
            medicalRecords = medicalRecordService.getMedicalRecord();
        } catch (Exception e) {
            logger.error("Error fetching data from services", e);
            return new FireDTO();  // Return an empty object on error
        }

        logger.debug("Found {} persons, {} firestations, and {} medical records", 
                     persons.size(), firestations.size(), medicalRecords.size());

        // Find the station associated with the address
        Firestation firestationForAddress = firestations.stream()
            .filter(firestation -> firestation.getAddress().equals(address))
            .findFirst()
            .orElse(null);

        if (firestationForAddress == null) {
            return new FireDTO(); // No results if the address is not covered by a barracks
        }

        logger.debug("Firestation for address {}: Station number {}", address, firestationForAddress.getStation());

        // Create a Map of medical records by full name
        Map<String, MedicalRecord> medicalRecordByName = medicalRecords.stream()
            .collect(Collectors.toMap(
                record -> record.getFirstName() + " " + record.getLastName(),
                record -> record
            ));

        // Filter people living at the given address
        List<PersonMedicalRecordDTO> personMedicalRecordDTOs = persons.stream()
            .filter(person -> person.getAddress().equals(address))
            .map(person -> {
                String fullName = person.getFirstName() + " " + person.getLastName();
                MedicalRecord medicalRecord = medicalRecordByName.get(fullName);
                
                if (medicalRecord == null) {
                    return null;
                }

                PersonMedicalRecordDTO dto = new PersonMedicalRecordDTO();
                dto.setLastName(person.getLastName());
                dto.setPhone(person.getPhone());
                dto.setAge((int) calculateAge(medicalRecord));
                dto.setAllergie(medicalRecord.getAllergies());
                dto.setMedication(medicalRecord.getMedications());

                logger.debug("Person {} has been processed with age: {}, allergies: {}, medications: {}", 
                             fullName, dto.getAge(), dto.getAllergie(), dto.getMedication());

                return dto;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        FireDTO fireDTO = new FireDTO();
        fireDTO.setStation(firestationForAddress.getStation());
        fireDTO.setPersons(personMedicalRecordDTOs);

        logger.info("Returning fire information for address {} with {} persons", address, personMedicalRecordDTOs.size());
        return fireDTO;
    }

	/**
     * Get homes covered by specific firestations.
     * 
     * @param stationNumbers An array of firestation numbers to retrieve homes for.
     * @return A list of FloodDTO objects, each representing a home and its residents' medical information.
     */
	public List<FloodDTO> getHomePerStation(Integer[] stationNumbers) {
        logger.info("Fetching homes for stations: {}", Arrays.toString(stationNumbers));

        List<Person> persons;
        List<Firestation> firestations;
        List<MedicalRecord> medicalRecords;

        try {
            persons = personService.getPerson();
            firestations = firestationService.getFirestation();
            medicalRecords = medicalRecordService.getMedicalRecord();
        } catch (Exception e) {
            logger.error("Error fetching data from services", e);
            return new ArrayList<>();  // Return an empty list on error
        }

        logger.debug("Found {} persons, {} firestations, and {} medical records", 
                     persons.size(), firestations.size(), medicalRecords.size());

        // Create a map to associate addresses with stations
        Map<String, Integer> addressToStationMap = firestations.stream()
            .filter(firestation -> Arrays.asList(stationNumbers).contains(firestation.getStation()))
            .collect(Collectors.toMap(Firestation::getAddress, Firestation::getStation));

        logger.debug("Mapped addresses to stations: {}", addressToStationMap);

        // Create a map to associate full names with medical records
        Map<String, MedicalRecord> medicalRecordByName = medicalRecords.stream()
            .collect(Collectors.toMap(
                record -> record.getFirstName() + " " + record.getLastName(),
                record -> record
            ));

        // Group people by address
        Map<String, List<Person>> personsByAddress = persons.stream()
            .filter(person -> addressToStationMap.containsKey(person.getAddress()))
            .collect(Collectors.groupingBy(Person::getAddress));

        logger.debug("Grouped persons by address: {}", personsByAddress);

        // Building the final response with FloodDTOs
        List<FloodDTO> floodList = new ArrayList<>();
        for (Integer stationNumber : stationNumbers) {
            FloodDTO floodDTO = new FloodDTO();
            List<HomeDTO> homes = new ArrayList<>();

            // Filter addresses linked to the current station
            List<String> addressesForStation = addressToStationMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(stationNumber))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

            for (String address : addressesForStation) {
                List<Person> personsAtAddress = personsByAddress.get(address);
                if (personsAtAddress != null) {
                    HomeDTO homeDTO = new HomeDTO();
                    List<PersonMedicalRecordDTO> family = new ArrayList<>();

                    for (Person person : personsAtAddress) {
                        String fullName = person.getFirstName() + " " + person.getLastName();
                        MedicalRecord medicalRecord = medicalRecordByName.get(fullName);

                        if (medicalRecord != null) {
                            PersonMedicalRecordDTO personDTO = new PersonMedicalRecordDTO();
                            personDTO.setLastName(person.getLastName());
                            personDTO.setPhone(person.getPhone());
                            personDTO.setAge((int) calculateAge(medicalRecord));
                            personDTO.setMedication(medicalRecord.getMedications());
                            personDTO.setAllergie(medicalRecord.getAllergies());
                            family.add(personDTO);

                            logger.debug("Added person {} with age: {} to family at address: {}", 
                                         fullName, personDTO.getAge(), address);
                        }
                    }
                    homeDTO.setHome(family);
                    homes.add(homeDTO);
                }
            }

            floodDTO.setHomes(homes);
            floodDTO.setStationNumber(stationNumber);
            floodList.add(floodDTO);

            logger.info("Processed station number {} with {} homes", stationNumber, homes.size());
        }

        return floodList;
    }

	/**
     * Get medical and contact information for all persons with a specific last name.
     * 
     * @param lastName The last name to search for.
     * @return A list of PersonInfoLastNameDTO objects with information on persons with the given last name.
     */
	public List<PersonInfoLastNameDTO> getInfoPerPerson(String lastName) {
        logger.info("Fetching person info for last name: {}", lastName);

        List<Person> persons;
        List<MedicalRecord> medicalRecords;

        try {
            persons = personService.getPerson();
            medicalRecords = medicalRecordService.getMedicalRecord();
        } catch (Exception e) {
            logger.error("Error fetching data from services", e);
            return new ArrayList<>();  // Return an empty list on error
        }

        logger.debug("Found {} persons and {} medical records", persons.size(), medicalRecords.size());

        // Create a map to associate full names with medical records
        Map<String, MedicalRecord> medicalRecordByName = medicalRecords.stream()
            .collect(Collectors.toMap(
                record -> record.getFirstName() + " " + record.getLastName(),
                record -> record
            ));

        // Filter people by last name and create DTO list
        List<PersonInfoLastNameDTO> personsInfo = persons.stream()
            .filter(person -> person.getLastName().equals(lastName))
            .map(person -> {
                String fullName = person.getFirstName() + " " + person.getLastName();
                MedicalRecord medicalRecord = medicalRecordByName.get(fullName);

                if (medicalRecord != null) {
                    PersonInfoLastNameDTO dto = new PersonInfoLastNameDTO();
                    dto.setLastName(person.getLastName());
                    dto.setAddress(person.getAddress());
                    dto.setEmail(person.getEmail());
                    dto.setAge((int) calculateAge(medicalRecord));
                    dto.setMedication(medicalRecord.getMedications());
                    dto.setAllergie(medicalRecord.getAllergies());

                    logger.debug("Processed person {} with age: {}, medications: {}, allergies: {}", 
                                 fullName, dto.getAge(), dto.getMedication(), dto.getAllergie());

                    return dto;
                }
                else {
                    return null;
                }
            })
            .filter(Objects::nonNull)  // Filter null results
            .collect(Collectors.toList());

        logger.info("Returning info for {} persons with last name: {}", personsInfo.size(), lastName);

        return personsInfo;
    }
	
	/**
     * Get emails of persons living in a specific city.
     * 
     * @param city The city to retrieve emails for.
     * @return A list of unique email addresses for persons living in the given city.
     */
	public List<String> getEmail(String city) {
        logger.info("Fetching emails for city: {}", city);

        List<Person> persons;

        try {
            persons = personService.getPerson();
        } catch (Exception e) {
            logger.error("Error fetching data from person service", e);
            return List.of();  // Return an empty list on error
        }

        logger.debug("Found {} persons in total", persons.size());

        // Use streams to filter and collect emails
        List<String> emails = persons.stream()
            .filter(person -> city.equals(person.getCity()))
            .map(Person::getEmail)
            .distinct()  // Avoid duplicates
            .collect(Collectors.toList());

        logger.info("Found {} unique emails for city: {}", emails.size(), city);

        return emails;
    }
	
	/**
     * Calculate the age of a person based on their birthdate.
     * 
     * @param medicalRecord The medical record containing the person's birthdate.
     * @return The age of the person.
     */
	public double calculateAge(MedicalRecord medicalRecord) {
        try {
            LocalDate today = LocalDate.now();
            LocalDate birthdate = LocalDate.parse(medicalRecord.getBirthdate(), DATE_FORMATTER);

            long age = ChronoUnit.YEARS.between(birthdate, today);
            logger.debug("Calculated age for {} {}: {}", medicalRecord.getFirstName(), medicalRecord.getLastName(), age);

            return age;
        } catch (Exception e) {
            logger.error("Error parsing birthdate for {} {}: {}", 
                         medicalRecord.getFirstName(), medicalRecord.getLastName(), medicalRecord.getBirthdate(), e);
            return 0;  // Return 0 if an error occurs
        }
    }
}
