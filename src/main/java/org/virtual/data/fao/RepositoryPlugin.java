package org.virtual.data.fao;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.xml.namespace.QName;

import org.virtual.data.fao.io.Request;
import org.virtual.data.fao.resources.Database;
import org.virtual.data.fao.utils.Dependencies;
import org.virtualrepository.RepositoryService;
import org.virtualrepository.spi.Lifecycle;
import org.virtualrepository.spi.Plugin;

import dagger.Module;
import dagger.ObjectGraph;

@Module(includes=Dependencies.class)
public class RepositoryPlugin implements Plugin, Lifecycle {

	public static QName name = new QName("fao.data.org");
	
	public static String about="a one paragraph summary of the repository";
	
	@Inject
	Databases finder;
	
	@Inject
	Provider<Request> requests;
	
	@Override
	public void init() throws Exception {
		
		ObjectGraph.create(this).inject(this);
		
	}
	
	@Override
	public Collection<RepositoryService> services() {
		
		Collection<RepositoryService> services = new ArrayList<>();
		
		for (Database db : finder.find())
				services.add(new RepositoryService(new QName(db.mnemonic()), new DatabaseProxy(db,requests), db.properties()));

		return services;
	}
	
	
}
