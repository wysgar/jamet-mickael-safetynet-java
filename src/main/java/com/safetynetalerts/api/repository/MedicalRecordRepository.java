package com.safetynetalerts.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynetalerts.api.model.Data;
import com.safetynetalerts.api.model.MedicalRecord;

@Repository
public class MedicalRecordRepository {
	
	@Autowired
	private Data data;

	public List<MedicalRecord> getMedicalRecord() {
		return data.getMedicalRecords();
	}

	public void saveMedicalRecord(MedicalRecord medicalRecord) {
		data.getMedicalRecords().add(medicalRecord);
	}

	public void updateMedicalRecord(MedicalRecord oldMedicalRecord, MedicalRecord newMedicalRecord) {
		data.getMedicalRecords().remove(oldMedicalRecord);
		data.getMedicalRecords().add(newMedicalRecord);
	}

	public void deleteMedicalRecord(MedicalRecord medicalRecord) {
		data.getMedicalRecords().remove(medicalRecord);
	}
}
