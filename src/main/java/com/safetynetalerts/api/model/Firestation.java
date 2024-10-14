package com.safetynetalerts.api.model;

import java.util.Objects;

public class Firestation {
	
	private String address;
	private int station;
	
	public String getAddress() {
		return address;
	}
	public int getStation() {
		return station;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	public void setStation(int station) {
		this.station = station;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(address, station);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Firestation other = (Firestation) obj;
		return Objects.equals(address, other.address) && station == other.station;
	}
}
