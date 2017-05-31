package ch.fhnw.inspire.models;

public class AnswerModel {
	
	private String answerLabel;
	private String answerURI;
	
	public AnswerModel(String answerURI, String answerLabel) {
		this.answerURI = answerURI;
		this.answerLabel = answerLabel;
		System.out.println("Answer Model: " +answerURI +"::" +answerLabel);
	}


	public void setAnswerURI(String answerURI) {
		this.answerURI = answerURI;
	}


	public String getAnswerURI() {
		return answerURI;
	}

	public String getAnswerLabel() {
		return answerLabel;
	}

}
