package com.safetynetalerts.api.service;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.api.model.MedicalRecord;
import com.safetynetalerts.api.repository.MedicalRecordRepository;

/**
 * Service responsible for handling operations related to medical records.
 * Provides methods to retrieve, save, update, and delete medical records.
 */
@Service
public class MedicalRecordService {
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;
	private static final Logger logger = LogManager.getLogger("MedicalRecordService");

	/**
     * Retrieves all medical records from the repository.
     * 
     * @return a list of all medical records
     */
	public List<MedicalRecord> getMedicalRecord() {
	    logger.info("Fetching all medical records from the repository");

	    // Retrieve all medical records
	    List<MedicalRecord> medicalRecords = medicalRecordRepository.getMedicalRecord();

	    logger.debug("Found {} medical records in the repository", medicalRecords.size());
	    return medicalRecords;
	}

	/**
     * Saves a new medical record to the repository.
     * If the medical record already exists, the save operation is aborted.
     * 
     * @param medicalRecord the medical record to save
     */
	public void saveMedicalRecord(MedicalRecord medicalRecord) {
	    logger.info("Attempting to save medical record for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());

	    // Check if the medical record already exists
	    if (!medicalRecordRepository.getMedicalRecord().contains(medicalRecord)) {
	        logger.debug("Medical record does not exist, saving medical record for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());

	        // Save the medical record
	        medicalRecordRepository.saveMedicalRecord(medicalRecord);
	        logger.info("Medical record saved successfully for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
	    } else {
	        logger.error("Medical record already exists for: {} {}. Save aborted.", medicalRecord.getFirstName(), medicalRecord.getLastName());
	    }
	}

	/**
     * Updates an existing medical record in the repository based on the person's first and last names.
     * If the medical record does not exist, the update operation is aborted.
     * 
     * @param medicalRecord the medical record to update
     */
	public void updateMedicalRecord(MedicalRecord medicalRecord) {
	    logger.info("Updating medical record for {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());

	    // Retrieve the list of medical records once
	    List<MedicalRecord> medicalRecords = medicalRecordRepository.getMedicalRecord();

	    // Find the index of the medical record using stream()
	    OptionalInt indexOpt = IntStream.range(0, medicalRecords.size())
	        .filter(i -> medicalRecords.get(i).getFirstName().equals(medicalRecord.getFirstName())
	                  && medicalRecords.get(i).getLastName().equals(medicalRecord.getLastName()))
	        .findFirst();

	    // Check if the index was found
	    if (indexOpt.isPresent()) {
	        int index = indexOpt.getAsInt();
	        logger.debug("Medical record found at index {} for {} {}", index, medicalRecord.getFirstName(), medicalRecord.getLastName());

	        // Update the medical record
	        medicalRecordRepository.updateMedicalRecord(index, medicalRecord);
	        logger.info("Medical record updated successfully for {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
	    } else {
	        logger.error("Medical record for {} {} does not exist. Update aborted.", medicalRecord.getFirstName(), medicalRecord.getLastName());
	    }
	}

	/**
     * Deletes an existing medical record from the repository.
     * If the medical record does not exist, the delete operation is aborted.
     * 
     * @param medicalRecord the medical record to delete
     */
	public void deleteMedicalRecord(MedicalRecord medicalRecord) {
	    logger.info("Attempting to delete medical record for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());

	    // Check if the medical record exists in the repository
	    if (medicalRecordRepository.getMedicalRecord().contains(medicalRecord)) {
	        logger.debug("Medical record found, deleting record for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());

	        // Delete the medical record
	        medicalRecordRepository.deleteMedicalRecord(medicalRecord);
	        logger.info("Medical record deleted successfully for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
	    } else {
	        logger.error("Medical record not found for: {} {}. Delete aborted.", medicalRecord.getFirstName(), medicalRecord.getLastName());
	    }
	}
}
