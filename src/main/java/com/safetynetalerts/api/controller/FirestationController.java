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

import com.safetynetalerts.api.model.Firestation;
import com.safetynetalerts.api.service.FirestationService;


@RestController
@RequestMapping("/firestation")
public class FirestationController {
	
	@Autowired
	private FirestationService firestationService;
	
	@GetMapping
	public List<Firestation> getFirestation() {
		return firestationService.getFirestation();
	}
	
	@PostMapping
	public void createFirestation(@RequestBody Firestation firestation) {
		firestationService.saveFirestation(firestation);
	}
	
	@PutMapping
	public void updateFirestation(@RequestBody Firestation firestation) {
		firestationService.updateFirestation(firestation);
	}
	
	@DeleteMapping
	public void deleteFirestation(@RequestBody Firestation firestation) {
		firestationService.deleteFirestation(firestation);
	}
}
