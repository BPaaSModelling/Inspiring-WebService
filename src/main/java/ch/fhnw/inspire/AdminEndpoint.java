package ch.fhnw.inspire;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

import com.google.gson.Gson;

import ch.fhnw.inspire.models.CompetenceModel;
import ch.fhnw.inspire.models.ITSolutionModel;
import ch.fhnw.inspire.models.QuestionModel;
import ch.fhnw.inspire.ontology.OntologyManager;

@Path("/admin")
public class AdminEndpoint {
	
	private Gson gson = new Gson();
	private OntologyManager ontologyManger = OntologyManager.getInstance();
	
	@POST
	@Path("/question/add")
	public Response getQuestionToAdd(String json) {
		
		System.out.println("I received: " +json +"\n\n");
		
		try{
			QuestionModel question = gson.fromJson(json, QuestionModel.class);
			
			UUID test = UUID.randomUUID();
			
			ParameterizedSparqlString insertQuery = new ParameterizedSparqlString();
			insertQuery.append("INSERT DATA { ");
			insertQuery.append(MessageFormat.format("inspire_data:{0} rdf:type inspire:Question ;", test.toString().replaceAll("-", "")));
			insertQuery.append(MessageFormat.format("rdfs:label \"{0}\" ;", question.getQuestionLabel()));
			insertQuery.append(MessageFormat.format("inspire:QuestionHasQuestionType {0} .", question.getQuestionType()));
			insertQuery.append("}");
			ontologyManger.performUpdateQuery(insertQuery);
			
			String toBeSent = gson.toJson(question);
			
			return Response.status(Status.OK).entity(toBeSent).build();
			
		}catch (com.google.gson.JsonSyntaxException e){
			System.err.println("could not convert");
			
			return Response.status(Status.OK).entity("sorry, no chance").build();
		}
	}
	
	
	@GET
	@Path("/question/list")
	public Response getAllQuestionList() {
		
		ParameterizedSparqlString insertQuery = new ParameterizedSparqlString();
		insertQuery.append("SELECT * WHERE {");
		insertQuery.append("?question rdf:type inspire:Question .");
		insertQuery.append("?question rdfs:label ?label .");
		insertQuery.append("}");

		ResultSet results = ontologyManger.performSelectQuery(insertQuery);
		
		ArrayList<QuestionModel> tempArray = new ArrayList<QuestionModel>();
		
		while (results.hasNext()) {
			QuerySolution soln = results.next();
			
			tempArray.add(new QuestionModel(soln.get("?question").toString(), soln.get("?label").toString()));
		}
		
		
		String jSONString = gson.toJson(tempArray);
		
		System.out.println("What I sent: " +jSONString);
		
		
		
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
		
		ResultSet results = ontologyManger.performSelectQuery(insertQuery);
		
		ArrayList<CompetenceModel> tempArray = new ArrayList<CompetenceModel>();
		
		while (results.hasNext()) {
			QuerySolution soln = results.next();
			tempArray.add(new CompetenceModel(soln.get("?compt").toString(), soln.get("?label").toString()));
		}
		
		String jSONString = gson.toJson(tempArray);
		
		System.out.println("What I sent: " +jSONString);
		
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
		
		ResultSet results = ontologyManger.performSelectQuery(insertQuery);
		
		ArrayList<ITSolutionModel> tempArray = new ArrayList<ITSolutionModel>();
		
		while (results.hasNext()) {
			QuerySolution soln = results.next();
			tempArray.add(new ITSolutionModel(soln.get("?its").toString(), soln.get("?label").toString()));
		}
		
		String jSONString = gson.toJson(tempArray);
		
		System.out.println("What I sent: " +jSONString);
		
		return Response.status(Status.OK).entity(jSONString).build();
		
	}

}
