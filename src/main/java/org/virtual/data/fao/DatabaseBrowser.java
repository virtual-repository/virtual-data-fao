package org.virtual.data.fao;

import static org.virtual.data.fao.resources.ResourceType.*;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Provider;

import org.virtual.data.fao.io.Request;
import org.virtual.data.fao.resources.Database;
import org.virtual.data.fao.resources.Dimension;
import org.virtualrepository.AssetType;
import org.virtualrepository.csv.CsvCodelist;
import org.virtualrepository.spi.Browser;
import org.virtualrepository.spi.MutableAsset;

public class DatabaseBrowser implements Browser {

	private final Database db;
	private final Provider<Request> requests;
	
	
	public DatabaseBrowser(Database db,Provider<Request> requests) {
		this.db=db;
		this.requests=requests;
	}
	
	
	@Override
	public Iterable<? extends MutableAsset> discover(Collection<? extends AssetType> types) throws Exception {
		
		Collection<Dimension> dimensions = null;
		
		try {
			
		    dimensions = requests.get().over(dimensionsOf(db.mnemonic())).execute();
			
		}
		catch(Exception e) {
			throw new RuntimeException("cannot discover codelists (see cause)",e);
		}
		
		
		if(types.contains(CsvCodelist.type))
			return csvAssetsFrom(dimensions);
		
		
		throw new IllegalArgumentException("invalid request for unsupported asset types "+types);
		
		
	}
	
	
	private Collection<CsvCodelist> csvAssetsFrom(Collection<Dimension> dimensions) {
		
		Collection<CsvCodelist> assets = new ArrayList<>();
		
		for (Dimension ds : dimensions)
			assets.add(new CsvCodelist(ds.urn().toString(),ds.name(),0,ds.properties()));
		
		return assets;
		
	}
	
}
