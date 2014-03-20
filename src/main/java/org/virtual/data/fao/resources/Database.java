package org.virtual.data.fao.resources;

import static java.lang.String.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.virtualrepository.Property;

@XmlRootElement(name="item")
public class Database {

	@XmlElement(name="label")
	private List<Name> names;
	
	@XmlElement
	private URI urn;
	
	@XmlElement
	private URI uri;
	
	private String name;
	
	private Property[] properties;
	
	@SuppressWarnings("unused")
	private Database() {
		//for jaxb
	}
	
	public Database(String name, Property ... properties) {
		this.name=name;
		this.properties=properties;
	}
	
	
	URI urn() {
		return urn;
	}
	
	URI uri() {
		return uri;
	}
	
	public String name() {
		return name;
	}
	
	public List<Name> allNames() {
		return names;
	}
	
	
	
	public Property[] properties() {
		return properties;
	}


	@Override
	public String toString() {
		return "Database [name=" + name + ", urn="+urn+", uri="+uri+", properties=" + Arrays.toString(properties) + "]";
	}
	
	
	//jaxb callback
	void afterUnmarshal(Unmarshaller u, Object parent) {

		List<Property> properties = new ArrayList<>();
		if (names!=null)
			for (Name name : names) {
				
				if (name.lang().equals("en"))
					this.name = name.value();
				
				properties.add(new Property(format("name(%s)",name.lang()), name.value()));
			}
		
		properties.add(new Property("uri",uri));
		properties.add(new Property("urn",urn));
		
		this.properties = properties.toArray(new Property[0]);
	}
}
