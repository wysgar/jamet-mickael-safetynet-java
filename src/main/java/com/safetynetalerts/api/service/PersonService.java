package com.safetynetalerts.api.service;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	private static final Logger logger = LogManager.getLogger("PersonService");

	public List<Person> getPerson() {
	    logger.info("Fetching all persons from the repository");

	    // Récupérer toutes les personnes
	    List<Person> persons = personRepository.getPerson();

	    logger.debug("Found {} persons in the repository", persons.size());
	    return persons;
	}

	public void savePerson(Person person) {
	    logger.info("Attempting to save person: {} {}", person.getFirstName(), person.getLastName());

	    // Vérifier si la personne existe déjà dans le repository
	    if (!personRepository.getPerson().contains(person)) {
	        logger.debug("Person does not exist, saving person: {} {}", person.getFirstName(), person.getLastName());

	        // Sauvegarder la personne
	        personRepository.savePerson(person);
	        logger.info("Person saved successfully: {} {}", person.getFirstName(), person.getLastName());
	    } else {
	        logger.error("Person already exists: {} {}. Save aborted.", person.getFirstName(), person.getLastName());
	    }
	}

	public void updatePerson(Person person) {
	    logger.info("Updating person: {} {}", person.getFirstName(), person.getLastName());

	    // Récupérer la liste des personnes une seule fois
	    List<Person> persons = personRepository.getPerson();

	    // Rechercher l'index de la personne à mettre à jour via IntStream.range()
	    OptionalInt indexOpt = IntStream.range(0, persons.size())
	        .filter(i -> persons.get(i).getFirstName().equals(person.getFirstName())
	                  && persons.get(i).getLastName().equals(person.getLastName()))
	        .findFirst();

	    if (indexOpt.isPresent()) {
	        int index = indexOpt.getAsInt();
	        logger.debug("Person found at index {} for {} {}", index, person.getFirstName(), person.getLastName());

	        // Mise à jour de la personne
	        personRepository.updatePerson(index, person);
	        logger.info("Person updated successfully: {} {}", person.getFirstName(), person.getLastName());
	    } else {
	        logger.error("Person {} {} does not exist. Update aborted.", person.getFirstName(), person.getLastName());
	    }
	}

	public void deletePerson(Person person) {
	    logger.info("Attempting to delete person: {} {}", person.getFirstName(), person.getLastName());

	    // Vérifier si la personne existe dans le repository
	    if (personRepository.getPerson().contains(person)) {
	        logger.debug("Person found, deleting person: {} {}", person.getFirstName(), person.getLastName());

	        // Supprimer la personne
	        personRepository.deletePerson(person);
	        logger.info("Person deleted successfully: {} {}", person.getFirstName(), person.getLastName());
	    } else {
	        logger.error("Person not found: {} {}. Delete aborted.", person.getFirstName(), person.getLastName());
	    }
	}
}
