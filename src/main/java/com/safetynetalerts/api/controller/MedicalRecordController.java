package com.safetynetalerts.api.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.api.model.MedicalRecord;
import com.safetynetalerts.api.service.MedicalRecordService;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {
	
	@Autowired
	private MedicalRecordService medicalRecordService;
	private static final Logger logger = LogManager.getLogger("MedicalRecordController");
	
	@PostMapping
	public void createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
	    logger.info("Received request to create medical record: {}", medicalRecord);
	    medicalRecordService.saveMedicalRecord(medicalRecord);
	    logger.debug("Medical record created: {}", medicalRecord);
	}

	@PutMapping
	public void updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
	    logger.info("Received request to update medical record: {}", medicalRecord);
	    medicalRecordService.updateMedicalRecord(medicalRecord);
	    logger.debug("Medical record updated: {}", medicalRecord);
	}

	@DeleteMapping
	public void deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
	    logger.info("Received request to delete medical record: {}", medicalRecord);
	    medicalRecordService.deleteMedicalRecord(medicalRecord);
	    logger.debug("Medical record deleted: {}", medicalRecord);
	}
}
