package com.safetynetalerts.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.api.model.Firestation;
import com.safetynetalerts.api.repository.FirestationRepository;


@Service
public class FirestationService {
	
	@Autowired
	private FirestationRepository firestationRepository;
	
	public List<Firestation> getFirestation() {
		return firestationRepository.getFirestation();
	}
	
	public void saveFirestation(Firestation firestation) {
		firestationRepository.saveFirestation(firestation);
	}
	
	public void updateFirestation(Firestation firestation) {
		Firestation oldFirestation = new Firestation();
		firestationRepository.updateFirestation(oldFirestation, firestation);
	}
	
	public void deleteFirestation(Firestation firestation) {
		firestationRepository.deleteFirestation(firestation);
	}
}
