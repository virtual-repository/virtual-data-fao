package org.acme;

import static org.acme.Utils.*;

import javax.inject.Inject;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtual.data.fao.Database;
import org.virtual.data.fao.Databases;
import org.virtual.data.fao.utils.Dependencies;

import dagger.Module;

@Module(injects=IntegrationTests.class,includes=Dependencies.class)
public class IntegrationTests {

	private static final Logger log = LoggerFactory.getLogger(IntegrationTests.class);
	
	@Inject
	Databases dbs;
	
	@Test
	public void findDatabases() {
	
		inject(this);
		
		for (Database db : dbs.find())
			log.info(db.toString());
		
	}
	
	
}
