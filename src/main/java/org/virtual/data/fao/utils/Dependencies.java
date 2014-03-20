package org.virtual.data.fao.utils;

import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;

import org.virtual.data.fao.RepositoryPlugin;
import org.virtual.data.fao.resources.Database;

import dagger.Module;
import dagger.Provides;

@Module(injects=RepositoryPlugin.class)
public class Dependencies {

	
	@Provides @Singleton
	public JAXBContext jaxb() {
		
		try {
			return JAXBContext.newInstance(new Class<?>[]{Database.class});
		}
		catch(Exception e) {
			throw new AssertionError("jaxb is not correctly configured (see cause)",e);
		}
	}
}
