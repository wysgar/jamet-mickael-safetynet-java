package com.safetynetalerts.api.model.DTO;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing flood-related information for a specific fire station.
 * 
 * This class encapsulates a list of homes affected by flooding, along with the 
 * fire station number responsible for responding to emergencies in those areas.
 */
public class FloodDTO {
	private List<HomeDTO> homes;
	private int stationNumber;
	
	public List<HomeDTO> getHomes() {
		return homes;
	}
	public void setHomes(List<HomeDTO> home) {
		this.homes = home;
	}
	
	public int getStationNumber() {
		return stationNumber;
	}
	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}
}
