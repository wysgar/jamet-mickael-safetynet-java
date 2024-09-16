package com.safetynetalerts.api.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.api.model.DTO.ChildAlertDTO;
import com.safetynetalerts.api.model.DTO.FireDTO;
import com.safetynetalerts.api.model.DTO.FloodDTO;
import com.safetynetalerts.api.model.DTO.PersonInfoLastNameDTO;
import com.safetynetalerts.api.model.DTO.PhoneAlertDTO;
import com.safetynetalerts.api.service.AlertService;

@RestController
public class AlertController {
	
	@Autowired
	private AlertService alertService;
	
	@GetMapping("/childAlert")
	private ChildAlertDTO getChildPerAddress(@RequestParam String address) throws ParseException {
		return alertService.getChildPerAddress(address);
	}
	
	@GetMapping("/phoneAlert")
	private PhoneAlertDTO getPhonePerStation(@RequestParam Integer firestationNumber) {
		return alertService.getPhonePerStation(firestationNumber);
	}
	
	@GetMapping("/fire")
	private FireDTO getPersonPerAddress(@RequestParam String address) throws ParseException {
		return alertService.getPersonPerAddress(address);
	}
	
	@GetMapping("/flood/stations")
	private List<FloodDTO> getHomePerStation(@RequestParam Integer[] stations) throws ParseException {
		return alertService.getHomePerStation(stations);
	}
	
	@GetMapping("/personInfolastName")
	private List<PersonInfoLastNameDTO> getInfoPerPerson(@RequestParam String lastName) throws ParseException {
		return alertService.getInfoPerPerson(lastName);
	}
	
	@GetMapping("/communityEmail")
	private List<String> getEmail(@RequestParam String city) {
		return alertService.getEmail(city);
	}
}
