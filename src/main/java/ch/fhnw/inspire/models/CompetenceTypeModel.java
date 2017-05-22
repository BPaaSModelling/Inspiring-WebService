package ch.fhnw.inspire.models;

public class CompetenceTypeModel {

	/**
	 * reflects uri
	 */
	private String competenceURI;
	
	/**
	 * reflects rdfs:label
	 */
	private String competenceType;
	private String itSolution;

	public CompetenceTypeModel(String competenceURI, String competenceType) {
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
