package org.acme;

import static java.lang.System.*;
import static org.acme.Utils.*;

import javax.inject.Inject;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtual.data.fao.Databases;
import org.virtual.data.fao.resources.Database;
import org.virtual.data.fao.utils.Dependencies;

import dagger.Module;

@Module(injects=IntegrationTests.class,includes=Dependencies.class)
public class IntegrationTests {

	private static final Logger log = LoggerFactory.getLogger(IntegrationTests.class);
	
	@Inject
	Databases dbs;
	
	@BeforeClass
	public static void setup() {
		
		setProperty("org.slf4j.simpleLogger.log.org.virtual", "trace");
	
	}
	
	@Test
	public void findDatabases() {
	
		inject(this);
		
		for (Database db : dbs.find())
			log.info(db.toString());
		
	}
	
	
}
