package org.virtual.data.fao;

import static java.lang.System.*;

import java.util.Collection;
import java.util.Collections;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtualrepository.AssetType;
import org.virtualrepository.spi.Browser;
import org.virtualrepository.spi.MutableAsset;

@Singleton
public class DatabaseBrowser implements Browser {

	private static final Logger log = LoggerFactory.getLogger(DatabaseBrowser.class);
	
	private final Database db;
	
	public DatabaseBrowser(Database db) {
		this.db=db;
	}
	
	@Override
	public Iterable<? extends MutableAsset> discover(Collection<? extends AssetType> types) throws Exception {
		

		log.info("discovering codelists in {}",db.name());
		
		long time = currentTimeMillis();

		Collection<? extends MutableAsset> assets = Collections.emptySet();
		
		log.info("discovered {} codelists from {} in {} ms.",assets.size(),db.name(),currentTimeMillis()-time);

		return Collections.emptySet();
	}
}
