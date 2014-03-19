package org.virtual.data.fao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.virtualrepository.spi.Browser;
import org.virtualrepository.spi.Importer;
import org.virtualrepository.spi.Publisher;
import org.virtualrepository.spi.ServiceProxy;

@Singleton
public class DatabaseProxy implements ServiceProxy {

	private final DatabaseBrowser browser;
	
	private final List<Publisher<?,?>> publishers = new ArrayList<Publisher<?,?>>();
	private final List<Importer<?,?>> importers = new ArrayList<Importer<?,?>>();

	public DatabaseProxy(Database db) {
	
		this.browser= new DatabaseBrowser(db);
		
		//TODO
		importers.add(new DatabaseImporter(db));
		
	}
	
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