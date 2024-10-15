package com.safetynetalerts.api.repository;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynetalerts.api.model.Data;
import com.safetynetalerts.api.model.MedicalRecord;
import com.safetynetalerts.api.service.DataService;

@Repository
public class MedicalRecordRepository {
	
	@Autowired
	private Data data;
	@Autowired
	private DataService dataService;
	private static final Logger logger = LogManager.getLogger("MedicalRecordRepository");

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
