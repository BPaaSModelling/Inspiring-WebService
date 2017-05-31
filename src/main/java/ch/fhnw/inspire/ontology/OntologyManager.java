package ch.fhnw.inspire.ontology;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

public class OntologyManager {
	
	private static OntologyManager INSTANCE;
	private static String TRIPLESTOREENDPOINT 	= "http://localhost:3030/inspire";
	private static String UPDATEENDPOINT 		= TRIPLESTOREENDPOINT + "/update";
	private static String QUERYENDPOINT			= TRIPLESTOREENDPOINT + "/query";

	public static synchronized OntologyManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new OntologyManager();
		}
		return INSTANCE;
	}
	
	public OntologyManager(){
		
	}

	public void performUpdateQuery(ParameterizedSparqlString query) {
		
		addNamespacesToQuery(query);
		
		System.out.println("performUpdateQuery " +query.toString());
		
		UpdateRequest update = UpdateFactory.create(query.toString());
		UpdateProcessor up = UpdateExecutionFactory.createRemote(update, UPDATEENDPOINT);
		up.execute();
	}

	private void addNamespacesToQuery(ParameterizedSparqlString query) {
		
		for(Namespaces item : Namespaces.values()){
			query.setNsPrefix(item.getPrefix(), item.getURI());
		}
	}

	public QueryExecution performSelectQuery(ParameterizedSparqlString queryStr) {
		addNamespacesToQuery(queryStr);
		System.out.println("\nperformed SELECT: " +queryStr.toString());
		Query query = QueryFactory.create(queryStr.toString());
		return QueryExecutionFactory.sparqlService(QUERYENDPOINT, query);
	}
	
}
