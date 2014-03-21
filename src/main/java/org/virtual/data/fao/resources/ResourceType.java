package org.virtual.data.fao.resources;

import static javax.ws.rs.core.MediaType.*;
import static javax.xml.bind.JAXBContext.*;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;

import org.w3c.dom.Element;


public class ResourceType<T> {
	
	public static ResourceType<Database> databases = new ResourceType<>("databases.xml",Database.class);
	
	public static ResourceType<Dimension> dimensionsOf(String db) {
		return new ResourceType<>(String.format("%s/dimensions.xml",db),Dimension.class);
	}
	
	public static ResourceType<Member> membersOf(String db,String ds) {
		return new ResourceType<>(String.format("%s/%s/members.xml",db,ds),Member.class);
	}
	
	private final String path;
	private final Class<T> type;
	private final JAXBContext binder;
	private MediaType media = APPLICATION_XML_TYPE;
	
	private ResourceType(String path, Class<T> type) {
		
		try {

			this.path=path;
			this.type=type;
			this.binder= newInstance(type);

		}
		catch(Exception e) {
			throw new RuntimeException("jaxb initialisation problem",e);
		}
	}
	
	public MediaType media() {
		return media;
	}
	
	public String path() {
		return path;
	}
	
	public Class<T> type() {
		return type;
	}
	
	public T bind(Element e) throws Exception {
		
		return type.cast(binder.createUnmarshaller().unmarshal(e));
	}
}
