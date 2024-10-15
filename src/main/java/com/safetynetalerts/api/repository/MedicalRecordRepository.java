package com.safetynetalerts.api.repository;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynetalerts.api.model.Data;
import com.safetynetalerts.api.model.MedicalRecord;
import com.safetynetalerts.api.service.DataService;

/**
 * Repository class for managing {@link MedicalRecord} data.
 * Provides methods to retrieve, save, update, and delete medical records,
 * as well as writing the data to a JSON file.
 */
@Repository
public class MedicalRecordRepository {
	
	@Autowired
	private Data data;
	@Autowired
	private DataService dataService;
	private static final Logger logger = LogManager.getLogger("MedicalRecordRepository");

	/**
     * Retrieves the list of medical records from the data source.
     *
     * @return a list of {@link MedicalRecord}, or {@code null} if an error occurs
     */
	public List<MedicalRecord> getMedicalRecord() {
	    logger.info("Fetching list of medical records.");
	    try {
	        List<MedicalRecord> medicalRecords = data.getMedicalRecords();
	        logger.debug("Retrieved {} medical records", medicalRecords.size());
	        return medicalRecords;
	    } catch (Exception e) {
	        logger.error("Error fetching medical records.", e);
	    }
	    return null;
	}

	/**
     * Saves a new medical record to the data source and writes the updated data to JSON.
     *
     * @param medicalRecord the {@link MedicalRecord} to save
     */
	public void saveMedicalRecord(MedicalRecord medicalRecord) {
	    logger.info("Attempting to save medical record: {}", medicalRecord);
	    try {
	        data.getMedicalRecords().add(medicalRecord);
	        writeJSON();
	        logger.info("Successfully saved medical record: {}", medicalRecord);
	    } catch (Exception e) {
	        logger.error("Error saving medical record: {}", medicalRecord, e);
	    }
	}

	/**
     * Updates an existing medical record at the specified index and writes the updated data to JSON.
     *
     * @param index the index of the medical record to update
     * @param medicalRecord the updated {@link MedicalRecord} object
     */
	public void updateMedicalRecord(int index, MedicalRecord medicalRecord) {
	    logger.info("Attempting to update medical record at index {}: {}", index, medicalRecord);
	    try {
	        data.getMedicalRecords().set(index, medicalRecord);
	        writeJSON();
	        logger.info("Successfully updated medical record at index {}.", index);
	    } catch (Exception e) {
	        logger.error("Error updating medical record at index {}: {}", index, medicalRecord, e);
	    }
	}

	/**
     * Deletes the specified medical record from the data source and writes the updated data to JSON.
     *
     * @param medicalRecord the {@link MedicalRecord} to delete
     */
	public void deleteMedicalRecord(MedicalRecord medicalRecord) {
	    logger.info("Attempting to delete medical record: {}", medicalRecord);
	    try {
	    	data.getMedicalRecords().remove(medicalRecord);
	        writeJSON();
	        logger.info("Successfully deleted medical record: {}", medicalRecord);
	    } catch (Exception e) {
	        logger.error("Error deleting medical record: {}", medicalRecord, e);
	    }
	}
	
	/**
     * Writes the current medical records data to a JSON file.
     */
	public void writeJSON() {
	    logger.info("Writing medical records data to JSON.");
	    try {
	        dataService.writeJSON();
	        logger.info("Successfully wrote medical records data to JSON.");
	    } catch (Exception e) {
	        logger.error("Error writing medical records data to JSON.", e);
	    }
	}
}
