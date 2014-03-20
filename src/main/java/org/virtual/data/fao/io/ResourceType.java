package org.virtual.data.fao.io;

import static javax.ws.rs.core.MediaType.*;

import javax.ws.rs.core.MediaType;

import org.virtual.data.fao.resources.Database;

public class ResourceType<T> {
	
	public static ResourceType<Database> databases = new ResourceType<>("databases.xml",Database.class);
	
	private final String path;
	private final Class<T> type;
	private MediaType media = APPLICATION_XML_TYPE;
	
	private ResourceType(String path, Class<T> type) {
		
		this.path=path;
		this.type=type;
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
}
