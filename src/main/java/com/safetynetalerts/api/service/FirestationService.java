package com.safetynetalerts.api.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.safetynetalerts.api.model.Firestation;
import com.safetynetalerts.api.repository.FirestationRepository;


@Service
public class FirestationService {
	@Autowired
	private FirestationRepository firestationRepository;
	
	public List<Firestation> getFirestation() {
		// TODO Auto-generated method stub
		return firestationRepository.getFirestation();
	}
	
	public void saveFirestation(Firestation firestation) throws FileNotFoundException, IOException {
		firestationRepository.saveFirestation(firestation);
	}
	
	public void updateFirestation(Firestation oldFirestation, Firestation newFirestation) throws StreamReadException, DatabindException, IOException {
		firestationRepository.updateFirestation(oldFirestation, newFirestation);
	}
	
	public void deleteFirestation(Firestation firestation) throws StreamReadException, DatabindException, IOException {
		firestationRepository.deleteFirestation(firestation);
	}
	
	public void readJSON() throws StreamReadException, DatabindException, IOException {
		firestationRepository.readJSON();
	}
}
