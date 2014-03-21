package org.virtual.data.fao.resources;

import static java.lang.String.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;

import org.virtualrepository.Property;

public abstract class AbstractResource {


	@XmlElement(name="label")
	private List<Name> names;
	
	@XmlElement
	private URI urn;
	
	@XmlElement
	private URI uri;
	
	@XmlElement
	private String uuid;

	@XmlElement
	private String mnemonic;
	
	List<Property> properties = new ArrayList<>();
	
	public URI urn() {
		return urn;
	}
	
	public URI uri() {
		return uri;
	}
	
	public String uuid() {
		return uuid;
	}
	
	public abstract String name();
	
	public List<Name> allNames() {
		return names;
	}
	
	public String mnemonic() {
		
		return mnemonic;
		
	}
	
	public Property[] properties() {
		return properties.toArray(new Property[properties.size()]);
	}
	
	@Override
	public String toString() {
		return "name=" + name() + ", urn="+urn+", uri="+uri+", properties=" + properties;
	}
	
	//jaxb callback
	void afterUnmarshal(Unmarshaller u, Object parent) {

		if (names!=null)
			for (Name name : names)
				properties.add(new Property(format("name(%s)",name.lang()), name.value()));
		
		
		properties.add(new Property("uri",uri));
		properties.add(new Property("urn",urn));
		properties.add(new Property("uuid",uuid,false));
		
	}
}
