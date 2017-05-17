package ch.fhnw.inspire.models;

public class QuestionModel {
	
	private String questionLabel;
	private String questionType;
	private String testVariable;
	private String questionURI;
	
	public QuestionModel(){
		
	}

	public QuestionModel(String questionURI, String questionLabel) {
		this.questionLabel = questionLabel;
		this.questionURI = questionURI;
	}

	public String getQuestionLabel() {
		return questionLabel;
	}

	public void setQuestionLabel(String questionLabel) {
		this.questionLabel = questionLabel;
	}
	
	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public void setTestVariable(String testVariable) {
		this.testVariable = testVariable;
	}
	
	public String getTestVariable() {
		return this.testVariable;
	}

}
