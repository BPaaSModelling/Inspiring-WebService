package ch.fhnw.inspire.models;

public class QuestionTypeModel {
	
	
	private String questionTypeLabel;
	private String questionTypeURI;
	
	public QuestionTypeModel(String questionTypeURI, String questionTypeLabel){
		this.questionTypeURI = questionTypeURI;
		this.questionTypeLabel = questionTypeLabel;
	}
	

}
