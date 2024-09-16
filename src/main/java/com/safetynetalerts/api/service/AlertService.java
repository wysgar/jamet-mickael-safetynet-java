package com.safetynetalerts.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.api.model.Firestation;
import com.safetynetalerts.api.model.MedicalRecord;
import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.model.DTO.ChildAlertDTO;
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
	
	public FirestationDTO getPersonPerStation(int stationNumber) throws ParseException {
		
		List<Person> persons = personService.getPerson();
		List<Firestation> firestations = firestationService.getFirestation();
		List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecord();
		List<PersonDTO> personsDTO = new ArrayList<PersonDTO>();
		
		FirestationDTO firestationDTO = new FirestationDTO();
		
		
		int countAdult = 0;
		int countChild = 0;
		
		
		for (Firestation firestation : firestations) {
			if (firestation.getStation() == stationNumber) {
				for (Person person : persons) {
					if (person.getAddress().equals(firestation.getAddress())) {
						PersonDTO personDTO = new PersonDTO();
						personDTO.setAddress(person.getAddress());
						personDTO.setFirstName(person.getFirstName());
						personDTO.setLastName(person.getLastName());
						personDTO.setPhone(person.getPhone());
						
						personsDTO.add(personDTO);
						
						for (MedicalRecord medicalRecord : medicalRecords) {
							if(medicalRecord.getFirstName().equals(personDTO.getFirstName())  && medicalRecord.getLastName().equals(personDTO.getLastName())) {
								double age = calculateAge(medicalRecord);
								if (age < 18)
									countChild = countChild + 1;
								else
									countAdult = countAdult + 1;
							}
						}
					}
				}
			}
		}
		
		firestationDTO.setPersons(personsDTO);
		firestationDTO.setCountAdult(countAdult);
		firestationDTO.setCountChild(countChild);
		
		return firestationDTO;
	}

	public ChildAlertDTO getChildPerAddress(String address) throws ParseException {

		List<Person> persons = personService.getPerson();
		List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecord();
		List<Person> family = new ArrayList<Person>();
		
		ChildAlertDTO childAlertDTO = new ChildAlertDTO();
		
		for (Person person : persons) {
			if (person.getAddress().equals(address)) {
				for (MedicalRecord medicalRecord : medicalRecords) {
					if(medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
						double age = calculateAge(medicalRecord);
						if (age < 18) {
							childAlertDTO.setAge((int) age);
							childAlertDTO.setFirstName(person.getFirstName());
							childAlertDTO.setLastName(person.getLastName());
							
							for (Person familyMember : persons) {
								if (familyMember.getAddress().equals(address) && familyMember.getFirstName().equals(person.getFirstName()) != true) {
									family.add(familyMember);
								}
							}
						}
					}
				}
			}
		}
		
		childAlertDTO.setFamily(family);
		
		return childAlertDTO;
	}

	public PhoneAlertDTO getPhonePerStation(int firestationNumber) {
		
		List<Person> persons = personService.getPerson();
		List<Firestation> firestations = firestationService.getFirestation();
		List<String> phone = new ArrayList<String>();
		
		PhoneAlertDTO phoneAlertDTO = new PhoneAlertDTO();
		
		for (Firestation firestation : firestations) {
			if (firestation.getStation() == firestationNumber) {
				for (Person person : persons) {
					if (person.getAddress().equals(firestation.getAddress())) {
						phone.add(person.getPhone());
					}
				}
			}
		}
		
		phoneAlertDTO.setPhone(phone);
		
		return phoneAlertDTO;
	}

	public FireDTO getPersonPerAddress(String address) throws ParseException {
		
		List<Person> persons = personService.getPerson();
		List<Firestation> firestations = firestationService.getFirestation();
		List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecord();
		List<PersonMedicalRecordDTO> list = new ArrayList<PersonMedicalRecordDTO>();
		
		FireDTO fireDTO = new FireDTO();
		
		for (Person person : persons) {
			if (person.getAddress().equals(address)) {
				PersonMedicalRecordDTO personMedicalRecordDTO = new PersonMedicalRecordDTO();
				personMedicalRecordDTO.setLastName(person.getLastName());
				personMedicalRecordDTO.setPhone(person.getPhone());
				
				for (MedicalRecord medicalRecord : medicalRecords) {
					if(medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
						personMedicalRecordDTO.setAge((int) calculateAge(medicalRecord));
						personMedicalRecordDTO.setAllergie(medicalRecord.getAllergies());
						personMedicalRecordDTO.setMedication(medicalRecord.getMedications());
						
						list.add(personMedicalRecordDTO);
					}
				}
				
				for (Firestation firestation : firestations) {
					if (firestation.getAddress().equals(person.getAddress())) {
						fireDTO.setStation(firestation.getStation());
					}
				}
			}
		}
		fireDTO.setPersons(list);
		
		return fireDTO;
	}

	public List<FloodDTO> getHomePerStation(Integer[] stationNumbers) throws ParseException {
		
		List<Person> persons = personService.getPerson();
		List<Firestation> firestations = firestationService.getFirestation();
		List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecord();
		List<FloodDTO> flood = new ArrayList<FloodDTO>();
		
		
		
		for (int stationNumber : stationNumbers) {
			FloodDTO floodDTO = new FloodDTO();
			List<HomeDTO> home = new ArrayList<HomeDTO>();
			for (Firestation firestation : firestations) {
				if (firestation.getStation() == stationNumber) {
					HomeDTO homeDTO = new HomeDTO();
					List<PersonMedicalRecordDTO> family = new ArrayList<PersonMedicalRecordDTO>();
					
					for (Person person : persons) {
						if (person.getAddress().equals(firestation.getAddress())) {
							PersonMedicalRecordDTO personMedicalRecordDTO = new PersonMedicalRecordDTO();
							personMedicalRecordDTO.setLastName(person.getLastName());
							personMedicalRecordDTO.setPhone(person.getPhone());
							
							for (MedicalRecord medicalRecord : medicalRecords) {
								if(medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
									personMedicalRecordDTO.setAge((int) calculateAge(medicalRecord));
									personMedicalRecordDTO.setMedication(medicalRecord.getMedications());
									personMedicalRecordDTO.setAllergie(medicalRecord.getAllergies());
									
									family.add(personMedicalRecordDTO);
								}
							}
						}
					}
					homeDTO.setHome(family);
					home.add(homeDTO);
				}
			}
			floodDTO.setHome(home);
			floodDTO.setStationNumber(stationNumber);
			flood.add(floodDTO);
		}
		
		return flood;
	}

	public List<PersonInfoLastNameDTO> getInfoPerPerson(String lastName) throws ParseException {
		
		List<Person> persons = personService.getPerson();
		List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecord();
		List<PersonInfoLastNameDTO> personsInfo = new ArrayList<PersonInfoLastNameDTO>();
		
		for (Person person : persons) {
			if (person.getLastName().equals(lastName)) {
				PersonInfoLastNameDTO personInfoLastNameDTO = new PersonInfoLastNameDTO();
				personInfoLastNameDTO.setLastName(person.getLastName());
				personInfoLastNameDTO.setAddress(person.getAddress());
				personInfoLastNameDTO.setEmail(person.getEmail());
				for (MedicalRecord medicalRecord : medicalRecords) {
					if(medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
						personInfoLastNameDTO.setAge((int) calculateAge(medicalRecord));
						personInfoLastNameDTO.setMedication(medicalRecord.getMedications());
						personInfoLastNameDTO.setAllergie(medicalRecord.getAllergies());
						
						personsInfo.add(personInfoLastNameDTO);
					}
				}
			}
		}
		
		return personsInfo;
	}

	public List<String> getEmail(String city) {
		
		List<Person> persons = personService.getPerson();
		List<String> email = new ArrayList<String>();
		
		for (Person person : persons) {
			if (person.getCity().equals(city)) {
				email.add(person.getEmail());
			}
		}
		
		return email;
	}

	private double calculateAge(MedicalRecord medicalRecord) throws ParseException {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date birthday = formatter.parse(medicalRecord.getBirthdate());
		double age = (date.getTime() - birthday.getTime()) / 60000 / 60 / 24 / 365;
		return age;
	}
}
