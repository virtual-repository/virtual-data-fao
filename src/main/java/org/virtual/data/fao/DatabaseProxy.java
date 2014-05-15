package org.virtual.data.fao;

import static org.virtualrepository.spi.ImportAdapter.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Provider;
import javax.inject.Singleton;

import org.virtual.data.fao.io.Request;
import org.virtual.data.fao.resources.Database;
import org.virtualrepository.csv.CsvCodelist;
import org.virtualrepository.csv.Table2CsvStream;
import org.virtualrepository.spi.Browser;
import org.virtualrepository.spi.Importer;
import org.virtualrepository.spi.Publisher;
import org.virtualrepository.spi.ServiceProxy;

@Singleton
public class DatabaseProxy implements ServiceProxy {

	private final DatabaseBrowser browser;
	
	private final List<Publisher<?,?>> publishers = new ArrayList<Publisher<?,?>>();
	private final List<Importer<?,?>> importers = new ArrayList<Importer<?,?>>();

	public DatabaseProxy(Database db, Provider<Request> requests)  {
	
		this.browser= new DatabaseBrowser(db,requests);
		
		DatabaseImporter importer = new DatabaseImporter(db,requests);
		
		importers.add(importer);
		
		importers.add(adapt(importer, new Table2CsvStream<CsvCodelist>()));
		
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
