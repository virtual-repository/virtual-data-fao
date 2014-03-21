package org.acme;

import static java.lang.System.*;
import static java.util.Arrays.*;
import static org.acme.Utils.*;

import javax.inject.Inject;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtual.data.fao.Databases;
import org.virtual.data.fao.resources.Database;
import org.virtual.data.fao.utils.Dependencies;
import org.virtualrepository.Asset;
import org.virtualrepository.RepositoryService;
import org.virtualrepository.VirtualRepository;
import org.virtualrepository.csv.CsvCodelist;
import org.virtualrepository.impl.Repository;
import org.virtualrepository.tabular.Row;
import org.virtualrepository.tabular.Table;

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
	
	
	@Test
	public void discoverCodelistFromDatabase() throws Exception {
	
		VirtualRepository repository = new Repository();
		
		RepositoryService firstService = repository.services().iterator().next();
		
		Iterable<? extends Asset> assets = firstService.proxy().browser().discover(asList(CsvCodelist.type));
		
		for (Asset a : assets)
			log.info(a.toString());
		
	}
	
	@Test
	public void discoverAllCodelists() throws Exception {
	
		VirtualRepository repository = new Repository();
		
		repository.discover(CsvCodelist.type);
		
		for (Asset a : repository)
			log.info(a.id()+" : "+a.name());
	}
	
	@Test
	public void retrieveCodelistFromDatabase() throws Exception {
	
		VirtualRepository repository = new Repository();
		
		repository.discover(CsvCodelist.type);
		
		Asset codelist = repository.lookup("urn:faodata:dimension:agro-maps:crop");
		
		Table table =repository.retrieve(codelist,Table.class);
		
		for (Row row : table)
			System.out.println(row);
	}
}
