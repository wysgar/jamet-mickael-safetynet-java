package com.safetynetalerts.api.model.DTO;

import java.util.List;

public class FloodDTO {
	private List<HomeDTO> homes;
	private int stationNumber;
	
	public List<HomeDTO> getHome() {
		return homes;
	}
	public void setHome(List<HomeDTO> home) {
		this.homes = home;
	}
	
	public int getStationNumber() {
		return stationNumber;
	}
	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}
}
