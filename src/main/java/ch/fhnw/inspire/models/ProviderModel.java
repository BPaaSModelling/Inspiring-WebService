package ch.fhnw.inspire.models;

import java.util.ArrayList;

public class ProviderModel {
	
	private String providerURI;
	private String providerName;
	private ArrayList<CompetenceModel> competence;
	
	public ProviderModel(){
		competence = new ArrayList<CompetenceModel>();
	}

	public String getProviderName() {
		return this.providerName;
	}

	public ArrayList<CompetenceModel> getCompetenceList() {
		return competence;
	}
	

}
