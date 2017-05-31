package ch.fhnw.inspire;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import com.google.gson.Gson;
import ch.fhnw.inspire.models.AnswerModel;
import ch.fhnw.inspire.models.CompetenceModel;
import ch.fhnw.inspire.models.CompetenceTypeModel;
import ch.fhnw.inspire.models.ITSolutionModel;
import ch.fhnw.inspire.models.ProviderModel;
import ch.fhnw.inspire.models.QuestionModel;
import ch.fhnw.inspire.models.QuestionTypeModel;
import ch.fhnw.inspire.ontology.OntologyManager;
import ch.fhnw.inspire.persistence.GlobalVariables;
import ch.fhnw.inspire.util.AdminEndpointUtils;

@Path("/admin")
public class AdminEndpoint {

	private Gson gson = new Gson();
	private OntologyManager ontologyManger = OntologyManager.getInstance();

	@POST
	@Path("/question/add")
	public Response addQuestion(String json) {

		System.out.println("/question/add received: " + json + "\n\n");

		try {
			QuestionModel question = gson.fromJson(json, QuestionModel.class);
			
			AdminEndpointUtils.generateAndSetAnswersURIs(question.getAnswerList());
			String questionURI = GlobalVariables.getRandomIDWithPrefix("question");

			
			ParameterizedSparqlString insertQuery = new ParameterizedSparqlString();
			insertQuery.append("INSERT DATA { ");
			
			for (AnswerModel answer : question.getAnswerList()) {

				insertQuery.append(MessageFormat.format("inspire_data:{0} rdf:type inspire:Answer ;", answer.getAnswerURI()));
				insertQuery.append(MessageFormat.format("rdfs:label \"{0}\" ;", answer.getAnswerLabel()));

				insertQuery.append(".");
			}
			
			insertQuery.append(MessageFormat.format("inspire_data:{0} rdf:type inspire:Question ;", questionURI));
			insertQuery.append(MessageFormat.format("rdfs:label \"{0}\" ;", question.getQuestionLabel()));
			insertQuery.append(MessageFormat.format("inspire:QuestionHasQuestionType <{0}> ;", question.getQuestionType()));
			
			for (AnswerModel answer : question.getAnswerList()) {
				insertQuery.append(MessageFormat.format("inspire:QuestionHasAnswer inspire_data:{0} ;", answer.getAnswerURI()));
			}
			insertQuery.append(".");
			insertQuery.append("}");
			ontologyManger.performUpdateQuery(insertQuery);

			String toBeSent = gson.toJson(question);

			return Response.status(Status.OK).entity(toBeSent).build();

		} catch (com.google.gson.JsonSyntaxException e) {
			System.err.println("could not convert");

			return Response.status(Status.OK).entity("sorry, no chance").build();
		}
	}

	@DELETE
	@Path("/question/delete")
	public Response deleteQuestion(@QueryParam("questionURI") String questionURI) {

		System.out.println("/question/delete received: " + questionURI + "\n\n");

		try {
			ParameterizedSparqlString updateQuery = new ParameterizedSparqlString();
			updateQuery.append("DELETE { ");
			updateQuery.append(MessageFormat.format("<{0}> ?x ?y .", questionURI));
			updateQuery.append("} WHERE {");
			updateQuery.append(MessageFormat.format("<{0}> ?x ?y .", questionURI));
			updateQuery.append("}");

			ontologyManger.performUpdateQuery(updateQuery);

			return Response.status(Status.OK).entity("{}").build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.OK).entity("error occured, check server").build();
		}
	}
	
	
	@PUT
	@Path("/question/update")
	public Response updateQuestion(String json) {

		System.out.println("*********************************3\n");
		System.out.println("/question/update received: " + json + "\n\n");

		try {
			QuestionModel question = gson.fromJson(json, QuestionModel.class);
			
			//TODO Check if all data is existent
			
//			ParameterizedSparqlString selectQuery = new ParameterizedSparqlString();
//			selectQuery.append("SELECT * WHERE {");
//			selectQuery.append("?question rdf:type inspire:Question .");
//			selectQuery.append("?question rdfs:label ?label .");
//			selectQuery.append("?question inspire:QuestionHasQuestionType ?questionType .");
//			
//			
//			selectQuery.append(MessageFormat.format("FILTER(?question=<{0}>)", questionURI));
//			selectQuery.append("}");
			
			
	

//		try {
//			ParameterizedSparqlString selectQuery = new ParameterizedSparqlString();
//			selectQuery.append("SELECT * WHERE {");
//			selectQuery.append("?question rdf:type inspire:Question .");
//			selectQuery.append("?question rdfs:label ?label .");
//			selectQuery.append("?question inspire:QuestionHasQuestionType ?questionType .");
//			
//			
//			selectQuery.append(MessageFormat.format("FILTER(?question=<{0}>)", questionURI));
//			selectQuery.append("}");
//
//			ResultSet results = ontologyManger.performSelectQuery(selectQuery);
//
//			QuestionModel tempModel = new QuestionModel();
//
//			while (results.hasNext()) {
//				QuerySolution soln = results.next();
//				tempModel.setQuestionLabel(soln.get("?label").toString());
//				tempModel.setQuestionType(soln.get("?questionType").toString());
//				tempModel.setQuestionURI(questionURI);
//			}


			return Response.status(Status.OK).entity("{}").build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.OK).entity("error occured, check server").build();
		}
	}
		 

	@GET
	@Path("/question/list")
	public Response getAllQuestionList() {

		ParameterizedSparqlString insertQuery = new ParameterizedSparqlString();
		insertQuery.append("SELECT * WHERE {");
		insertQuery.append("?question rdf:type inspire:Question .");
		insertQuery.append("?question rdfs:label ?label .");
		insertQuery.append("?question inspire:QuestionHasQuestionType ?questionType .");
		insertQuery.append("?question inspire:QuestionHasAnswer ?answerURI .");
		insertQuery.append("?answerURI rdfs:label ?answerLabel .");
		insertQuery.append("}");

		QueryExecution queryExecution = ontologyManger.performSelectQuery(insertQuery);
		ResultSet results = queryExecution.execSelect();

		HashMap<String, QuestionModel> tempArray = new HashMap<String, QuestionModel>();
		
		while (results.hasNext()) {
			QuerySolution soln = results.next();
			String questionURI = soln.get("?question").toString();
			
			//create Answer
			AnswerModel tempAnswer = new AnswerModel(soln.get("?answerURI").toString(), soln.get("?answerLabel").toString());
			
//			System.out.println("questionURI " +questionURI);
//			System.out.println("label " +soln.get("?label").toString());
//			System.out.println("questionType " +soln.get("?questionType").toString());
			
			if(tempArray.isEmpty() || !tempArray.containsKey(questionURI)){
				QuestionModel tempQuestion = new QuestionModel(questionURI, soln.get("?label").toString(), soln.get("?questionType").toString());
				tempArray.put(questionURI, tempQuestion);
			}

			QuestionModel tempQuestion = tempArray.get(questionURI);
			tempQuestion.addAnswer(tempAnswer);
		}
		
		queryExecution.close();
		
		String jSONString = gson.toJson(tempArray.values());

		System.out.println("/question/list sent: " + jSONString);

		return Response.status(Status.OK).entity(jSONString).build();

	}
	
	@GET
	@Path("/question/types")
	public Response getQuestionTypes() {
		
		System.out.println("triggered: /question/types");

		ParameterizedSparqlString selectQuery = new ParameterizedSparqlString();
		selectQuery.append("SELECT ?questiontype ?label WHERE {");
		selectQuery.append("?questiontype rdf:type inspire:QuestionType .");
		selectQuery.append("?questiontype rdfs:label ?label .");
		selectQuery.append("}");
		
		QueryExecution queryExecution = ontologyManger.performSelectQuery(selectQuery);
		ResultSet results = queryExecution.execSelect();

		ArrayList<QuestionTypeModel> tempArray = new ArrayList<QuestionTypeModel>();

		while (results.hasNext()) {
			QuerySolution soln = results.next();

			tempArray.add(new QuestionTypeModel(soln.get("?questiontype").toString(), soln.get("?label").toString()));
		}
		
		queryExecution.close();

		String jSONString = gson.toJson(tempArray);

		System.out.println("/question/types sent: " + jSONString);

		return Response.status(Status.OK).entity(jSONString).build();
	}
	
	
	

	@GET
	@Path("/provider/competences")
	public Response getAllCompetencies() {

		ParameterizedSparqlString insertQuery = new ParameterizedSparqlString();
		insertQuery.append("SELECT ?compt ?label WHERE {");
		insertQuery.append("?compt rdf:type inspire:CompetenceType .");
		insertQuery.append("?compt rdfs:label ?label .");
		insertQuery.append("}");

		QueryExecution queryExecution = ontologyManger.performSelectQuery(insertQuery);
		ResultSet results = queryExecution.execSelect();

		ArrayList<CompetenceTypeModel> tempArray = new ArrayList<CompetenceTypeModel>();

		while (results.hasNext()) {
			QuerySolution soln = results.next();
			tempArray.add(new CompetenceTypeModel(soln.get("?compt").toString(), soln.get("?label").toString()));
		}
		queryExecution.close();

		String jSONString = gson.toJson(tempArray);

		System.out.println("/provider/competences sent: " + jSONString);

		return Response.status(Status.OK).entity(jSONString).build();
	}

	@GET
	@Path("/provider/itsolution")
	public Response getAllITSolutions() {

		ParameterizedSparqlString insertQuery = new ParameterizedSparqlString();
		insertQuery.append("SELECT ?its ?label WHERE {");
		insertQuery.append("?its rdf:type inspire:ITSolution .");
		insertQuery.append("?its rdfs:label ?label .");
		insertQuery.append("}");

		QueryExecution queryExecution = ontologyManger.performSelectQuery(insertQuery);
		ResultSet results = queryExecution.execSelect();
		
		ArrayList<ITSolutionModel> tempArray = new ArrayList<ITSolutionModel>();

		while (results.hasNext()) {
			QuerySolution soln = results.next();
			tempArray.add(new ITSolutionModel(soln.get("?its").toString(), soln.get("?label").toString()));
		}
		queryExecution.close();

		String jSONString = gson.toJson(tempArray);

		System.out.println("/provider/itsolution sent: " + jSONString);

		return Response.status(Status.OK).entity(jSONString).build();

	}

	@POST
	@Path("/provider/add")
	public Response addProvider(String json) {

		System.out.println("I received: " + json + "\n\n");

		try {
			ProviderModel provider = gson.fromJson(json, ProviderModel.class);

			ArrayList<String> tempCompetenceIDs = new ArrayList<String>();

			ParameterizedSparqlString insertQuery = new ParameterizedSparqlString();
			insertQuery.append("INSERT DATA { ");

			for (CompetenceModel competence : provider.getCompetenceList()) {
				String competenceID = GlobalVariables.getRandomIDWithPrefix("competence");
				tempCompetenceIDs.add(competenceID);

				insertQuery.append(MessageFormat.format("inspire_data:{0} rdf:type inspire:Competence ;", competenceID));
				insertQuery.append(MessageFormat.format("rdfs:label \"{0}\" ;", competenceID));

				for (CompetenceTypeModel competenceType : competence.getCompetenceList()) {
					insertQuery.append(MessageFormat.format("inspire:CompetenceHasCompetenceType <{0}> ;",
							competenceType.getCompetenceURI()));
				}
				insertQuery.append(".");
			}

			String providerID = GlobalVariables.getRandomIDWithPrefix("provider");

			insertQuery.append(MessageFormat.format("inspire_data:{0} rdf:type inspire:Provider ;", providerID));
			insertQuery.append(MessageFormat.format("rdfs:label \"{0}\" ;", provider.getProviderName()));

			for (String compID : tempCompetenceIDs) {
				insertQuery.append(MessageFormat.format("inspire:ProviderHasCompetence inspire_data:{0} ;", compID));
			}

			insertQuery.append(".");
			insertQuery.append("}");

			System.out.println("\n\n\n" + insertQuery.toString());

			ontologyManger.performUpdateQuery(insertQuery);

			return Response.status(Status.OK).entity("{}").build();

		} catch (com.google.gson.JsonSyntaxException e) {
			System.err.println("could not convert");

			return Response.status(Status.OK).entity("sorry, no chance").build();
		}
	}
	
	
	
	

}
