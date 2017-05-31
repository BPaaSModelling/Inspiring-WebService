package ch.fhnw.inspire.util;

import java.text.MessageFormat;
import java.util.Set;

import ch.fhnw.inspire.models.AnswerModel;
import ch.fhnw.inspire.models.CompetenceModel;
import ch.fhnw.inspire.models.CompetenceTypeModel;
import ch.fhnw.inspire.persistence.GlobalVariables;

public class AdminEndpointUtils {

	public static void generateAndSetAnswersURIs(Set<AnswerModel> answerList) {
		for(AnswerModel answer: answerList){
			answer.setAnswerURI(GlobalVariables.getRandomIDWithPrefix("answer"));
		}
	}

}
