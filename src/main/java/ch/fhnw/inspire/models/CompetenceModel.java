package ch.fhnw.inspire.models;

public class CompetenceModel {

	/**
	 * reflects uri
	 */
	private String competenceURI;
	
	/**
	 * reflects rdfs:label
	 */
	private String competenceType;
	private String itSolution;

	public CompetenceModel(String competenceURI, String competenceType) {
		this.competenceURI = competenceURI;
		this.competenceType = competenceType;
	}

	public String getCompetenceURI() {
		return competenceURI;
	}

	public void setCompetenceURI(String competenceURI) {
		this.competenceURI = competenceURI;
	}

	public String getCompetenceType() {
		return competenceType;
	}

	public void setCompetenceType(String competenceType) {
		this.competenceType = competenceType;
	}

	public String getItSolution() {
		return itSolution;
	}

	public void setItSolution(String itSolution) {
		this.itSolution = itSolution;
	}

}
