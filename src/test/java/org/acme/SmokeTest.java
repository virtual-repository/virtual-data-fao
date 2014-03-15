package org.acme;

import static org.acme.Utils.*;
import static org.junit.Assert.*;
import static org.virtual.data.fao.RepositoryPlugin.*;

import javax.inject.Inject;

import org.junit.Test;
import org.virtual.data.fao.RepositoryPlugin;
import org.virtual.data.fao.utils.Dependencies;
import org.virtualrepository.VirtualRepository;
import org.virtualrepository.impl.Repository;

import dagger.Module;

@Module(injects=SmokeTest.class,includes=Dependencies.class)
public class SmokeTest {

	@Inject
	RepositoryPlugin plugin;
	
	@Test
	public void dependenciesAreInjected() {
		
		inject(this);
		
		assertNotNull(plugin);
		
	}
	
	@Test
	public void pluginCanBeActivated() {
		
		VirtualRepository repo = new Repository();
		
		assertNotNull(repo.services().lookup(name));
		
	}
	
	
	
}

