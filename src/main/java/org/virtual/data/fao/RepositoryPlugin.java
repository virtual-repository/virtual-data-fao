package org.virtual.data.fao;

import static java.util.Collections.*;

import java.util.Collection;

import javax.inject.Inject;
import javax.xml.namespace.QName;

import org.virtual.data.fao.utils.Dependencies;
import org.virtualrepository.Property;
import org.virtualrepository.RepositoryService;
import org.virtualrepository.spi.Lifecycle;
import org.virtualrepository.spi.Plugin;

import dagger.Module;
import dagger.ObjectGraph;

@Module(includes=Dependencies.class)
public class RepositoryPlugin implements Plugin, Lifecycle {

	public static QName name = new QName("fao.data.org");
	
	public static String about="a one paragraph summary of the repository";
	
	@Inject
	RepositoryProxy proxy;
	
	
	@Override
	public void init() throws Exception {
		
		ObjectGraph.create(this).inject(this);
		
	}
	
	@Override
	public Collection<RepositoryService> services() {
		
		RepositoryService service = new RepositoryService(name,proxy,properties());
		
		return singleton(service);
	}
	
	
	private Property[] properties() {
		
		Property blurb = new Property("data.fao.org",about);
		
		return new Property[]{blurb};
}
}
