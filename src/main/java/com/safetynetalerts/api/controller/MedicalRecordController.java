package com.safetynetalerts.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping
	public List<MedicalRecord> getMedicalRecord() {
		return medicalRecordService.getMedicalRecord();
	}
	
	@PostMapping
	public void createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		medicalRecordService.saveMedicalRecord(medicalRecord);
	}
	
	@PutMapping
	public void updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		medicalRecordService.updateMedicalRecord(medicalRecord, medicalRecord);
	}
	
	@DeleteMapping
	public void deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		medicalRecordService.deleteMedicalRecord(medicalRecord);
	}
}
