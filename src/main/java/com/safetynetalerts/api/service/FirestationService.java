package com.safetynetalerts.api.service;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.api.model.Firestation;
import com.safetynetalerts.api.repository.FirestationRepository;


@Service
public class FirestationService {
	
	@Autowired
	private FirestationRepository firestationRepository;
	private static final Logger logger = LogManager.getLogger("FirestationService");
	
	public List<Firestation> getFirestation() {
	    logger.info("Fetching all firestations from the repository");

	    // Récupérer toutes les casernes de pompiers
	    List<Firestation> firestations = firestationRepository.getFirestation();

	    logger.debug("Found {} firestations in the repository", firestations.size());
	    return firestations;
	}
	
	public void saveFirestation(Firestation firestation) {
	    logger.info("Attempting to save firestation at address: {}", firestation.getAddress());

	    // Vérifier si la caserne de pompiers existe déjà dans le repository
	    if (!firestationRepository.getFirestation().contains(firestation)) {
	        logger.debug("Firestation does not exist, saving firestation at address: {}", firestation.getAddress());

	        // Sauvegarder la caserne de pompiers
	        firestationRepository.saveFirestation(firestation);
	        logger.info("Firestation saved successfully at address: {}", firestation.getAddress());
	    } else {
	        logger.error("Firestation already exists at address: {}. Save aborted.", firestation.getAddress());
	    }
	}
	
	public void updateFirestation(Firestation firestation) {
	    logger.info("Updating firestation at address: {}", firestation.getAddress());

	    // Récupérer la liste des stations de pompiers une seule fois
	    List<Firestation> firestations = firestationRepository.getFirestation();

	    // Rechercher l'index de la station à mettre à jour via IntStream.range()
	    OptionalInt indexOpt = IntStream.range(0, firestations.size())
	        .filter(i -> firestations.get(i).getAddress().equals(firestation.getAddress()))
	        .findFirst();

	    if (indexOpt.isPresent()) {
	        int index = indexOpt.getAsInt(); // Obtenir la valeur de l'index
	        logger.debug("Firestation found at index {} for address {}", index, firestation.getAddress());

	        logger.debug("Calling updateFirestation method in FirestationRepository");
	        firestationRepository.updateFirestation(index, firestation);
	        
	        logger.info("Firestation updated successfully at address {}", firestation.getAddress());
	    } else {
	        logger.error("Firestation at address {} does not exist. Update aborted.", firestation.getAddress());
	    }
	}
	
	public void deleteFirestation(Firestation firestation) {
	    logger.info("Attempting to delete firestation at address: {}", firestation.getAddress());

	    // Vérifier si la caserne de pompiers existe dans le repository
	    if (firestationRepository.getFirestation().contains(firestation)) {
	        logger.debug("Firestation found, deleting firestation at address: {}", firestation.getAddress());

	        // Supprimer la caserne de pompiers
	        firestationRepository.deleteFirestation(firestation);
	        logger.info("Firestation deleted successfully at address: {}", firestation.getAddress());
	    } else {
	        logger.warn("Firestation not found at address: {}. Delete aborted.", firestation.getAddress());
	    }
	}
}
