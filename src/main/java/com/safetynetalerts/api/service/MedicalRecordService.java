package com.safetynetalerts.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.api.model.MedicalRecord;
import com.safetynetalerts.api.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	public List<MedicalRecord> getMedicalRecord() {
		return medicalRecordRepository.getMedicalRecord();
	}

	public void saveMedicalRecord(MedicalRecord medicalRecord) {
		medicalRecordRepository.saveMedicalRecord(medicalRecord);
	}

	public void updateMedicalRecord(MedicalRecord oldMedicalRecord, MedicalRecord newMedicalRecord) {
		medicalRecordRepository.updateMedicalRecord(oldMedicalRecord, newMedicalRecord);
	}

	public void deleteMedicalRecord(MedicalRecord medicalRecord) {
		medicalRecordRepository.deleteMedicalRecord(medicalRecord);
	}

}
