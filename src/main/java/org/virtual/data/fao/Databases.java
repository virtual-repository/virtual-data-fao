package org.virtual.data.fao;

import static java.lang.System.*;
import static java.util.Arrays.*;
import static org.virtual.data.fao.resources.ResourceType.*;

import java.util.Collection;
import java.util.Iterator;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtual.data.fao.io.Request;
import org.virtual.data.fao.resources.Database;

@Singleton
public class Databases {

	private static final Logger log = LoggerFactory.getLogger(Databases.class);
	
	public static final Collection<String> dbCL = asList(
									"agro-maps",
									"aquastat",
									"crop-calendar",
									"empres-i",
									"faodata",
									"faostat",
									"fishstat",
									"gaez",
									"glipha",
									"hungermap");

	
	@Inject
	Provider<Request> requests;
	
	
	public Collection<Database> find() {
		
		log.info("looking for databases...");
		
		long time = currentTimeMillis();
		
		try {
			
			Collection<Database> dbs = requests.get().over(databases).execute();
			
			int size = dbs.size();
			
			//prune off
			Iterator<Database> it = dbs.iterator();
			
			while (it.hasNext())
				if (!dbCL.contains(it.next().mnemonic()))
					it.remove();
			
			log.info("found {} databases in {} ms, retained {} from controlled lists",size,currentTimeMillis()-time,dbs.size());
			
			
			return dbs;

		}
		catch(Exception e) {
			throw new RuntimeException("cannot discover databases (see cause)",e);
		}
		
	}
}
