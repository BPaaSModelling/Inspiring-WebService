package ch.fhnw.inspire.models;

import java.util.HashSet;
import java.util.Set;

public class QuestionModel {
	
	private String questionLabel;
	private String questionType;
	private String questionURI;
	private Set<AnswerModel> answerList;
	

	public QuestionModel(String questionURI, String questionLabel, String questionType) {
		this.questionLabel = questionLabel;
		this.questionURI = questionURI;
		this.questionType = questionType;
		answerList = new HashSet<AnswerModel>();
		System.out.println("questionModel: " +questionURI +"::" +questionLabel +"::" +questionType +"::" );
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

	public void setQuestionURI(String questionURI) {
		this.questionURI = questionURI;
	}

	public Set<AnswerModel> getAnswerList() {
		return this.answerList;
	}
	
	public void setAnswerList(Set<AnswerModel> answerList) {
		this.answerList = answerList;
	}

	public void addAnswer(AnswerModel answer) {
		this.answerList.add(answer);
	}

}
