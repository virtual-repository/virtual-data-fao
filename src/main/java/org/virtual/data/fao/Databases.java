package org.virtual.data.fao;

import static java.lang.System.*;
import static org.virtual.data.fao.io.ResourceType.*;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtual.data.fao.io.Request;
import org.virtual.data.fao.resources.Database;

public class Databases {

	private static final Logger log = LoggerFactory.getLogger(Databases.class);
	
	
	@Inject
	Provider<Request> requests;
	
	
	public Collection<Database> find() {
		
		log.info("looking for databases...");
		
		long time = currentTimeMillis();
		
		Request request = requests.get();
		
		try {
			
			Collection<Database> dbs = request.over(databases).execute();
			
			log.info("found {} databases in {} ms.",dbs.size(),currentTimeMillis()-time);
			
			return dbs;

		}
		catch(Exception e) {
			throw new RuntimeException("cannot discover databases (see cause)",e);
		}
		
	}
}
