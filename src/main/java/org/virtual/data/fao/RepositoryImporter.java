package org.virtual.data.fao;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.virtualrepository.csv.CsvAsset;
import org.virtualrepository.impl.Type;
import org.virtualrepository.spi.Importer;
import org.virtualrepository.tabular.Table;

@Singleton
public class RepositoryImporter implements Importer<CsvAsset,Table> {

	@Inject
	public RepositoryImporter() {
		// TODO Auto-generated constructor stub
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
		
		//TODO
		return null;
		
	};
}
