package org.virtual.data.fao;

import java.util.Arrays;

import javax.xml.namespace.QName;

import org.virtualrepository.Property;

public class Database {

	private final QName name;
	private Property[] properties;
	
	public Database(QName name, Property ... properties) {
		this.name=name;
		this.properties=properties;
	}
	
	
	QName name() {
		return name;
	}
	
	Property[] properties() {
		return properties;
	}


	@Override
	public String toString() {
		return "Database [name=" + name + ", properties=" + Arrays.toString(properties) + "]";
	}
	
	
}
