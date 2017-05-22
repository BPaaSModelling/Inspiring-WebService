package ch.fhnw.inspire.models;

import java.util.ArrayList;

public class CompetenceModel {

	/**
	 * reflects uri
	 */
	private String competenceURI;
	
	/**
	 * reflects rdfs:label
	 */
	private String competenceLabel;
	private ITSolutionModel itSolution;
	private ArrayList<CompetenceTypeModel> competenceList;

	public CompetenceModel() {
		this.competenceList = new ArrayList<CompetenceTypeModel>();
	}

	public String getCompetenceURI() {
		return competenceURI;
	}

	public void setCompetenceURI(String competenceURI) {
		this.competenceURI = competenceURI;
	}

	public ITSolutionModel getItSolution() {
		return itSolution;
	}

	public void setItSolution(ITSolutionModel itSolution) {
		this.itSolution = itSolution;
	}

	public ArrayList<CompetenceTypeModel> getCompetenceList() {
		return competenceList;
	}

}
