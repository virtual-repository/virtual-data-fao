package org.virtual.data.fao;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtual.data.fao.io.Request;
import org.virtual.data.fao.resources.Database;
import org.virtual.data.fao.utils.Dependencies;
import org.virtualrepository.RepositoryService;
import org.virtualrepository.spi.Lifecycle;
import org.virtualrepository.spi.Plugin;

import dagger.ObjectGraph;


public class RepositoryPlugin implements Plugin, Lifecycle {

	private static final Logger log = LoggerFactory.getLogger(RepositoryPlugin.class);
	
	@Inject
	Databases finder;
	
	@Inject
	Provider<Request> requests;
	
	@Override
	public void init() throws Exception {

		ObjectGraph.create(new Dependencies()).inject(this);
		
	}
	
	@Override
	public Collection<RepositoryService> services() {
		
		log.info("initialising plugin...");
		
		Collection<RepositoryService> services = new ArrayList<>();
		
		//one database, one repository
		for (Database db : finder.find())
			services.add(new RepositoryService(new QName("http://data.fao.org",db.name()), new DatabaseProxy(db,requests), db.properties()));
		
		return services;
	}
	
	
}
