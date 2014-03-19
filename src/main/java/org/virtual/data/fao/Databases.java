package org.virtual.data.fao;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;
import javax.xml.namespace.QName;

public class Databases {

	@Inject
	public Databases() {
		
	}
	
	public Collection<Database> find() {
		//TODO
		return Arrays.asList(new Database(new QName("todo")),new Database(new QName("todo2")));
	}
}
