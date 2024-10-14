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
            return new FirestationDTO();  // Retourner un objet vide en cas d'erreur
        }
        
        logger.debug("Found {} persons, {} firestations, and {} medical records", persons.size(), firestations.size(), medicalRecords.size());

	    // Créer une Map des adresses associées à la station demandée
	    Set<String> addressesForStation = firestations.stream()
	        .filter(firestation -> firestation.getStation() == stationNumber)
	        .map(Firestation::getAddress)
	        .collect(Collectors.toSet());

	    logger.debug("Addresses associated with station {}: {}", stationNumber, addressesForStation);
	    
	    // Créer une Map pour rechercher les dossiers médicaux par nom complet
	    Map<String, MedicalRecord> medicalRecordByName = medicalRecords.stream()
	        .collect(Collectors.toMap(
	            record -> record.getFirstName() + " " + record.getLastName(),
	            record -> record
	        ));

	    FirestationDTO firestationDTO = new FirestationDTO();
	    List<PersonDTO> personsDTO = new ArrayList<>();
	    int countAdult = 0;
	    int countChild = 0;

	    // Parcourir les personnes une seule fois et vérifier si leur adresse est associée à la station
	    for (Person person : persons) {
	        if (addressesForStation.contains(person.getAddress())) {
	        	logger.debug("Processing person: {} {}", person.getFirstName(), person.getLastName());
	        	
	            // Créer un DTO pour chaque personne
	            PersonDTO personDTO = mapPersonToDTO(person);
	            personsDTO.add(personDTO);

	            // Rechercher le dossier médical correspondant
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
	
	// Helper method to map Person to PersonDTO
	private PersonDTO mapPersonToDTO(Person person) {
	    PersonDTO personDTO = new PersonDTO();
	    personDTO.setAddress(person.getAddress());
	    personDTO.setFirstName(person.getFirstName());
	    personDTO.setLastName(person.getLastName());
	    personDTO.setPhone(person.getPhone());
	    return personDTO;
	}

	public ChildAlertDTO getChildPerAddress(String address) {
        logger.info("Fetching children for address: {}", address);

        List<Person> persons;
        List<MedicalRecord> medicalRecords;

        try {
            persons = personService.getPerson();
            medicalRecords = medicalRecordService.getMedicalRecord();
        } catch (Exception e) {
            logger.error("Error fetching data from services", e);
            return new ChildAlertDTO();  // Retourner un objet vide en cas d'erreur
        }

        logger.debug("Found {} persons and {} medical records", persons.size(), medicalRecords.size());

        // Créer une Map pour rechercher les dossiers médicaux par nom complet
        Map<String, MedicalRecord> medicalRecordByName = medicalRecords.stream()
            .collect(Collectors.toMap(
                record -> record.getFirstName() + " " + record.getLastName(),
                record -> record
            ));

        // Filtrer les personnes qui habitent à l'adresse donnée
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
        
        return childAlertDTO;
    }

	public PhoneAlertDTO getPhonePerStation(int firestationNumber) {
        logger.info("Fetching phone numbers for firestation number: {}", firestationNumber);

        List<Person> persons;
        List<Firestation> firestations;

        try {
            persons = personService.getPerson();
            firestations = firestationService.getFirestation();
        } catch (Exception e) {
            logger.error("Error fetching data from services", e);
            return new PhoneAlertDTO(); // Retourner un objet vide en cas d'erreur
        }

        logger.debug("Found {} persons and {} firestations", persons.size(), firestations.size());

        // Créer un Set des adresses associées à la caserne de pompiers
        Set<String> addressesForStation = firestations.stream()
            .filter(firestation -> firestation.getStation() == firestationNumber)
            .map(Firestation::getAddress)
            .collect(Collectors.toSet());

        logger.debug("Addresses associated with firestation number {}: {}", firestationNumber, addressesForStation);

        // Filtrer les personnes vivant aux adresses associées à la station
        List<String> phoneNumbers = persons.stream()
            .filter(person -> addressesForStation.contains(person.getAddress()))
            .map(Person::getPhone)
            .distinct() // Éviter les doublons de numéros de téléphone
            .collect(Collectors.toList());

        logger.debug("Found {} phone numbers for firestation number {}", phoneNumbers.size(), firestationNumber);

        PhoneAlertDTO phoneAlertDTO = new PhoneAlertDTO();
        phoneAlertDTO.setPhone(phoneNumbers);

        logger.info("Returning {} phone numbers for firestation number {}", phoneNumbers.size(), firestationNumber);
        return phoneAlertDTO;
    }

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
            return new FireDTO();  // Retourner un objet vide en cas d'erreur
        }

        logger.debug("Found {} persons, {} firestations, and {} medical records", 
                     persons.size(), firestations.size(), medicalRecords.size());

        // Chercher la caserne associée à l'adresse
        Firestation firestationForAddress = firestations.stream()
            .filter(firestation -> firestation.getAddress().equals(address))
            .findFirst()
            .orElse(null);

        if (firestationForAddress == null) {
            return new FireDTO(); // Aucun résultat si l'adresse n'est pas couverte par une caserne
        }

        logger.debug("Firestation for address {}: Station number {}", address, firestationForAddress.getStation());

        // Créer une Map des dossiers médicaux par nom complet
        Map<String, MedicalRecord> medicalRecordByName = medicalRecords.stream()
            .collect(Collectors.toMap(
                record -> record.getFirstName() + " " + record.getLastName(),
                record -> record
            ));

        // Filtrer les personnes vivant à l'adresse donnée
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
            return new ArrayList<>();  // Retourner une liste vide en cas d'erreur
        }

        logger.debug("Found {} persons, {} firestations, and {} medical records", 
                     persons.size(), firestations.size(), medicalRecords.size());

        // Créer une map pour associer les adresses aux stations
        Map<String, Integer> addressToStationMap = firestations.stream()
            .filter(firestation -> Arrays.asList(stationNumbers).contains(firestation.getStation()))
            .collect(Collectors.toMap(Firestation::getAddress, Firestation::getStation));

        logger.debug("Mapped addresses to stations: {}", addressToStationMap);

        // Créer une map pour associer les noms complets aux dossiers médicaux
        Map<String, MedicalRecord> medicalRecordByName = medicalRecords.stream()
            .collect(Collectors.toMap(
                record -> record.getFirstName() + " " + record.getLastName(),
                record -> record
            ));

        // Regrouper les personnes par adresse
        Map<String, List<Person>> personsByAddress = persons.stream()
            .filter(person -> addressToStationMap.containsKey(person.getAddress()))
            .collect(Collectors.groupingBy(Person::getAddress));

        logger.debug("Grouped persons by address: {}", personsByAddress);

        // Construire la réponse finale avec les FloodDTO
        List<FloodDTO> floodList = new ArrayList<>();
        for (Integer stationNumber : stationNumbers) {
            FloodDTO floodDTO = new FloodDTO();
            List<HomeDTO> homes = new ArrayList<>();

            // Filtrer les adresses liées à la station actuelle
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

	public List<PersonInfoLastNameDTO> getInfoPerPerson(String lastName) {
        logger.info("Fetching person info for last name: {}", lastName);

        List<Person> persons;
        List<MedicalRecord> medicalRecords;

        try {
            persons = personService.getPerson();
            medicalRecords = medicalRecordService.getMedicalRecord();
        } catch (Exception e) {
            logger.error("Error fetching data from services", e);
            return new ArrayList<>();  // Retourner une liste vide en cas d'erreur
        }

        logger.debug("Found {} persons and {} medical records", persons.size(), medicalRecords.size());

        // Créer une map pour associer les noms complets aux dossiers médicaux
        Map<String, MedicalRecord> medicalRecordByName = medicalRecords.stream()
            .collect(Collectors.toMap(
                record -> record.getFirstName() + " " + record.getLastName(),
                record -> record
            ));

        // Filtrer les personnes par nom de famille et créer la liste des DTO
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
            .filter(Objects::nonNull)  // Filtrer les résultats nulls
            .collect(Collectors.toList());

        logger.info("Returning info for {} persons with last name: {}", personsInfo.size(), lastName);

        return personsInfo;
    }
	
	public List<String> getEmail(String city) {
        logger.info("Fetching emails for city: {}", city);

        List<Person> persons;

        try {
            persons = personService.getPerson();
        } catch (Exception e) {
            logger.error("Error fetching data from person service", e);
            return List.of();  // Retourner une liste vide en cas d'erreur
        }

        logger.debug("Found {} persons in total", persons.size());

        // Utiliser les streams pour filtrer et collecter les emails
        List<String> emails = persons.stream()
            .filter(person -> city.equals(person.getCity()))
            .map(Person::getEmail)
            .distinct()  // Évite les doublons
            .collect(Collectors.toList());

        logger.info("Found {} unique emails for city: {}", emails.size(), city);

        return emails;
    }
	
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
            return 0;  // Retourner 0 si une erreur survient
        }
    }
}
