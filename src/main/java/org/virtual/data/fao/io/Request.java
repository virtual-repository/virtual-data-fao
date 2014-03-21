package org.virtual.data.fao.io;

import static javax.ws.rs.client.ClientBuilder.*;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtual.data.fao.resources.ResourceType;
import org.w3c.dom.Element;

public class Request {
	
	private static final Logger log = LoggerFactory.getLogger(Request.class);
	
	private static final String baseURI = "http://data.fao.org/developers/api/v1/all/resources/";
	
	private static String showLanguage = "showLanguage";

	private static String page="page";
	private static String pageSize="pageSize";
	private static int pageSizeDefault=100;

	private static String fields="fields";
	private static String fieldsDefault="mnemonic,uri,urn,uuid,label@en,label@fr,label@ru,label@zh,label@ar,label@es";
	
	//move outside only if and when configuration becomes external
	private static ClientConfig configuration = new ClientConfig()
												.property(ClientProperties.CONNECT_TIMEOUT, 3000)
												. property(ClientProperties.READ_TIMEOUT, 5000);

	private WebTarget target;
	
	@Inject
	public Request() {
	
		this.target = newClient(configuration).target(baseURI);
		
	}
	
	public <T> ExecuteClause<T> over(ResourceType<T> resource) {
		
		return new ExecuteClause<T>(resource);
	}

	
	
	public class ExecuteClause<T> {
		
		
		private final ResourceType<T> resource;
		
		
		public ExecuteClause(ResourceType<T> resource) {
			this.resource=resource;
		}
		
		
		public Collection<T> execute() throws Exception {
			
			
			target = target.path(resource.path())
						   .queryParam(showLanguage,"true")
						   .queryParam(pageSize,pageSizeDefault)
						   .queryParam(fields,fieldsDefault);
			
			
			
			//collect all results across multiple pages
			Collection<T> results = new ArrayList<>();
			
			int lastPage=1;
			Results response = null;
			
			do {
				
				WebTarget nextTarget = target.queryParam(page,lastPage);
				
				response = nextTarget.request(resource.media()).get(Results.class);
				
				log.trace("requesting {}", nextTarget.getUri());
				
				for (Element element : response)
					results.add(resource.bind(element));				
				
				lastPage++;
			}
			while (response.hasMore());
				
			
			return results;
		}
		
	}
}
