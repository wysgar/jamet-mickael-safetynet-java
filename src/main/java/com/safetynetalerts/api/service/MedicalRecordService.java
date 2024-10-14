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

@Service
public class MedicalRecordService {
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;
	private static final Logger logger = LogManager.getLogger("MedicalRecordService");

	public List<MedicalRecord> getMedicalRecord() {
	    logger.info("Fetching all medical records from the repository");

	    // Récupérer tous les dossiers médicaux
	    List<MedicalRecord> medicalRecords = medicalRecordRepository.getMedicalRecord();

	    logger.debug("Found {} medical records in the repository", medicalRecords.size());
	    return medicalRecords;
	}

	public void saveMedicalRecord(MedicalRecord medicalRecord) {
	    logger.info("Attempting to save medical record for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());

	    // Vérifier si le dossier médical existe déjà dans le repository
	    if (!medicalRecordRepository.getMedicalRecord().contains(medicalRecord)) {
	        logger.debug("Medical record does not exist, saving medical record for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());

	        // Sauvegarder le dossier médical
	        medicalRecordRepository.saveMedicalRecord(medicalRecord);
	        logger.info("Medical record saved successfully for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
	    } else {
	        logger.error("Medical record already exists for: {} {}. Save aborted.", medicalRecord.getFirstName(), medicalRecord.getLastName());
	    }
	}

	public void updateMedicalRecord(MedicalRecord medicalRecord) {
	    logger.info("Updating medical record for {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());

	    // Récupérer la liste des dossiers médicaux une seule fois
	    List<MedicalRecord> medicalRecords = medicalRecordRepository.getMedicalRecord();

	    // Rechercher l'index du dossier médical via stream()
	    OptionalInt indexOpt = IntStream.range(0, medicalRecords.size())
	        .filter(i -> medicalRecords.get(i).getFirstName().equals(medicalRecord.getFirstName())
	                  && medicalRecords.get(i).getLastName().equals(medicalRecord.getLastName()))
	        .findFirst();

	    // Vérifier si l'index a été trouvé
	    if (indexOpt.isPresent()) {
	        int index = indexOpt.getAsInt();
	        logger.debug("Medical record found at index {} for {} {}", index, medicalRecord.getFirstName(), medicalRecord.getLastName());

	        // Mise à jour du dossier médical
	        medicalRecordRepository.updateMedicalRecord(index, medicalRecord);
	        logger.info("Medical record updated successfully for {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
	    } else {
	        logger.error("Medical record for {} {} does not exist. Update aborted.", medicalRecord.getFirstName(), medicalRecord.getLastName());
	    }
	}

	public void deleteMedicalRecord(MedicalRecord medicalRecord) {
	    logger.info("Attempting to delete medical record for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());

	    // Vérifier si le dossier médical existe dans le repository
	    if (medicalRecordRepository.getMedicalRecord().contains(medicalRecord)) {
	        logger.debug("Medical record found, deleting record for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());

	        // Supprimer le dossier médical
	        medicalRecordRepository.deleteMedicalRecord(medicalRecord);
	        logger.info("Medical record deleted successfully for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
	    } else {
	        logger.error("Medical record not found for: {} {}. Delete aborted.", medicalRecord.getFirstName(), medicalRecord.getLastName());
	    }
	}
}
