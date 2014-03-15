package org.virtual.data.fao;

import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.virtualrepository.AssetType;
import org.virtualrepository.spi.Browser;
import org.virtualrepository.spi.MutableAsset;

@Singleton
public class RepositoryBrowser implements Browser {

	@Inject
	public RepositoryBrowser() {}
	
	@Override
	public Iterable<? extends MutableAsset> discover(Collection<? extends AssetType> types) throws Exception {
		
		//TODO
		
		return Collections.emptySet();
	}
}
