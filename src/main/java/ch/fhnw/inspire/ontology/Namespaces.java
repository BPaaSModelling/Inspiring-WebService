package ch.fhnw.inspire.ontology;

public enum Namespaces {
	
	RDFS("rdfs", "http://www.w3.org/2000/01/rdf-schema#"),
	RDF("rdf","http://www.w3.org/1999/02/22-rdf-syntax-ns#"),
	OWL("owl","http://www.w3.org/2002/07/owl#"),
	XSD("xsd","http://www.w3.org/2001/XMLSchema#"),
	INSPIRE_DATA("inspire_data","http://ikm-group.ch/archiMEO/inspire_data#"),
	INSPIRE("inspire","http://ikm-group.ch/archiMEO/inspire#"),
	;
	
	private String prefix;
	private String uri;

	Namespaces(String prefix, String uri){
		this.prefix = prefix;
		this.uri = uri;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getURI() {
		return uri;
	}
}
