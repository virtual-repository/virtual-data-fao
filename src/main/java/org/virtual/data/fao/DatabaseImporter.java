package org.virtual.data.fao;

import static java.lang.System.*;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtualrepository.csv.CsvAsset;
import org.virtualrepository.impl.Type;
import org.virtualrepository.spi.Importer;
import org.virtualrepository.tabular.Table;

@Singleton
public class DatabaseImporter implements Importer<CsvAsset,Table> {

	private static final Logger log = LoggerFactory.getLogger(DatabaseImporter.class);
	
	private final Database db;
	
	public DatabaseImporter(Database db) {
		this.db=db;
	}
	
	@Override
	public Class<Table> api() {
		return Table.class;
	}
	
	@Override
	public Type<CsvAsset> type() {
		return CsvAsset.type;
	}
	
	public Table retrieve(CsvAsset asset) throws Exception {
		
		log.info("retrieving codelist {} from {}",asset.name(),db.name());
		
		long time = currentTimeMillis();

		
		log.info("retrieved codelist {} from {} in {} ms.",asset.name(),db.name(),currentTimeMillis()-time);

		//TODO
		return null;
		
	};
}
