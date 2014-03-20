package org.virtual.data.fao.io;

import static javax.ws.rs.client.ClientBuilder.*;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class Request {
	
	private static final Logger log = LoggerFactory.getLogger(Request.class);
	
	private static final String baseURI = "http://data.fao.org/developers/api/v1/all/resources/";
	
	private static String showLanguage = "showLanguage";

	//move outside only if and when configuration becomes external
	private static ClientConfig configuration = new ClientConfig()
												.property(ClientProperties.CONNECT_TIMEOUT, 3000)
												. property(ClientProperties.READ_TIMEOUT, 5000);

	private WebTarget target;
	
	private final JAXBContext context;
	
	
	@Inject
	public Request(JAXBContext context) {
	
		this.context=context;
		this.target = newClient(configuration).target(baseURI);
		
	}
	
	public <T> ExecuteClause<T> over(ResourceType<T> resource) {
		
		return new ExecuteClause<T>(resource);
	}

	
	
	public class ExecuteClause<T> {
		
		
		private final ResourceType<T> resource;
		
		
		public ExecuteClause(ResourceType<T> resource) {
			this.resource=resource;
			target = target.path(resource.path());
		}
		
		
		public Collection<T> execute() throws Exception {
			
			
			target = target.queryParam(showLanguage,"true");
			
			log.trace("requesting {}", target.getUri());
			
			Results response = target.request(resource.media()).get(Results.class);
			
			Unmarshaller u = context.createUnmarshaller();
			
			Collection<T> results = new ArrayList<>();
			
			for (Element element : response)
				results.add(resource.type().cast(u.unmarshal(element)));
			
			return results;
		}
		
	}
}
