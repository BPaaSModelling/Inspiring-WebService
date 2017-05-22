package ch.fhnw.inspire.models;

public class ITSolutionModel {

	private String itSolutionURI;
	private String itSolutionLabel;

	public ITSolutionModel(String itSolutionURI, String itSolutionLabel) {
		this.itSolutionURI = itSolutionURI;
		this.itSolutionLabel = itSolutionLabel;
	}

	public String getItSolutionURI() {
		return itSolutionURI;
	}

	public void setItSolutionURI(String itSolutionURI) {
		this.itSolutionURI = itSolutionURI;
	}

	public String getItSolutionLabel() {
		return itSolutionLabel;
	}

	public void setItSolutionLabel(String itSolutionLabel) {
		this.itSolutionLabel = itSolutionLabel;
	}
}
