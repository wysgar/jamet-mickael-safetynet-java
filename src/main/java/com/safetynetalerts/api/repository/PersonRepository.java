package com.safetynetalerts.api.repository;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynetalerts.api.model.Data;
import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.service.DataService;

/**
 * Repository class for managing {@link Person} data.
 * Provides methods to retrieve, save, update, and delete person records,
 * as well as writing the data to a JSON file.
 */
@Repository
public class PersonRepository {

	@Autowired
	private Data data;
	@Autowired
	private DataService dataService;
	private static final Logger logger = LogManager.getLogger("PersonRepository");
	
	/**
     * Retrieves the list of persons from the data source.
     *
     * @return a list of {@link Person}, or {@code null} if an error occurs
     */
	public List<Person> getPerson() {
	    logger.info("Fetching list of persons.");
	    try {
	        List<Person> persons = data.getPersons();
	        logger.debug("Retrieved {} persons", persons.size());
	        return persons;
	    } catch (Exception e) {
	        logger.error("Error fetching persons.", e);
	    }
	    return null;
	}

	/**
     * Saves a new person to the data source and writes the updated data to JSON.
     *
     * @param person the {@link Person} to save
     */
	public void savePerson(Person person) {
	    logger.info("Attempting to save person: {}", person);
	    try {
	        data.getPersons().add(person);
	        writeJSON();
	        logger.info("Successfully saved person: {}", person);
	    } catch (Exception e) {
	        logger.error("Error saving person: {}", person, e);
	    }
	}
	
	/**
     * Updates an existing person at the specified index and writes the updated data to JSON.
     *
     * @param index the index of the person to update
     * @param person the updated {@link Person} object
     */
	public void updatePerson(int index, Person person) {
	    logger.info("Attempting to update person at index {}: {}", index, person);
	    try {
	        data.getPersons().set(index, person);
	        writeJSON();
	        logger.info("Successfully updated person at index {}.", index);
	    } catch (Exception e) {
	        logger.error("Error updating person at index {}: {}", index, person, e);
	    }
	}

	/**
     * Deletes the specified person from the data source and writes the updated data to JSON.
     *
     * @param person the {@link Person} to delete
     */
	public void deletePerson(Person person) {
	    logger.info("Attempting to delete person: {}", person);
	    try {
	    	data.getPersons().remove(person);
	    	writeJSON();
	    	logger.info("Successfully deleted person: {}", person);
	    } catch (Exception e) {
	        logger.error("Error deleting person: {}", person, e);
	    }
	}
	
	/**
     * Writes the current person records data to a JSON file.
     */
	public void writeJSON() {
	    logger.info("Writing persons data to JSON.");
	    try {
	        dataService.writeJSON();
	        logger.info("Successfully wrote persons data to JSON.");
	    } catch (Exception e) {
	        logger.error("Error writing persons data to JSON.", e);
	    }
	}
}
