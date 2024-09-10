package com.safetynetalerts.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynetalerts.api.model.Data;
import com.safetynetalerts.api.model.Firestation;

@Repository
public class FirestationRepository {
	@Autowired
	private Data data;

	public List<Firestation> getFirestation() {
		return data.getFirestations();
	}

	public void saveFirestation(Firestation firestation) {
		data.getFirestations().add(firestation);
	}

	public void updateFirestation(Firestation oldFirestation, Firestation updateFirestation) {
		data.getFirestations().remove(oldFirestation);
		data.getFirestations().add(updateFirestation);
	}

	public void deleteFirestation(Firestation firestation) {
		if (data.getFirestations().contains(firestation))
			data.getFirestations().remove(firestation);
	}
}
