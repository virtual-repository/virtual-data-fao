package org.virtual.data.fao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.virtualrepository.spi.Browser;
import org.virtualrepository.spi.Importer;
import org.virtualrepository.spi.Lifecycle;
import org.virtualrepository.spi.Publisher;
import org.virtualrepository.spi.ServiceProxy;

@Singleton
public class RepositoryProxy implements ServiceProxy, Lifecycle {

	@Inject
	RepositoryBrowser browser;
	
	@Inject
	RepositoryImporter importer;
	
	private final List<Publisher<?,?>> publishers = new ArrayList<Publisher<?,?>>();
	private final List<Importer<?,?>> importers = new ArrayList<Importer<?,?>>();
	
	@Override
	public void init() throws Exception {
	
		// TODO Auto-generated method stub
		//configure importers and publishers
		importers.add(importer);
	}
	
	@Inject
	public RepositoryProxy() {}
	
	@Override
	public Browser browser() {
		return browser;
	}
	
	@Override
	public List<? extends Importer<?, ?>> importers() {
		return importers;
	}
	
	@Override
	public List<? extends Publisher<?, ?>> publishers() {
		return publishers;
	}
}
