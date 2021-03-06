package org.acme;

import static java.lang.System.*;
import static java.util.Arrays.*;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtualrepository.Asset;
import org.virtualrepository.RepositoryService;
import org.virtualrepository.VirtualRepository;
import org.virtualrepository.csv.CsvCodelist;
import org.virtualrepository.csv.CsvTable;
import org.virtualrepository.impl.Repository;
import org.virtualrepository.tabular.Row;
import org.virtualrepository.tabular.Table;

public class IntegrationTests {

	private static final Logger log = LoggerFactory.getLogger(IntegrationTests.class);
	
	@BeforeClass
	public static void setup() {
		
		setProperty("org.slf4j.simpleLogger.log.org.virtual", "trace");
	
	}
	
	@Test
	public void discoverDatabases() {
	
		VirtualRepository repository = new Repository();
		
		for (RepositoryService s : repository.services())
			log.info("name={}\nproperties{}\n",s.name(),s.properties());
	}
	
	
	@Test
	public void discoverCodelist() throws Exception {
	
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
			log.info("id={}\nname={}\nproperties{}\n",a.id(),a.name(),a.properties());
	}
	
	@Test
	public void retrieveCodelist() throws Exception {
	
		VirtualRepository repository = new Repository();
		
		repository.discover(CsvCodelist.type);
		
		//want bigger? try: urn:faodata:dimension:faostat:dataset:item
		Asset codelist = repository.lookup("urn:faodata:dimension:agro-maps:crop");
		
		Table table =repository.retrieve(codelist,Table.class);
		
		for (Row row : table)
			System.out.println(row);
	}
	
	@Test
	public void retrieveCodelistAsStream() throws Exception {
	
		VirtualRepository repository = new Repository();
		
		repository.discover(CsvCodelist.type);
		
		//want bigger? try: urn:faodata:dimension:faostat:dataset:item
		Asset codelist = repository.lookup("urn:faodata:dimension:agro-maps:crop");
		
		InputStream stream =repository.retrieve(codelist,InputStream.class);
		
		for (Row row : new CsvTable((CsvCodelist) codelist, stream))
			System.out.println(row);
	}
}
