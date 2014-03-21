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
	private List<LocalisedValue> names;
	
	@XmlElement
	private URI urn;
	
	@XmlElement
	private URI uri;
	
	@XmlElement
	private String uuid;

	@XmlElement
	private String mnemonic;
	
	@XmlElement(name="description")
	private List<LocalisedValue> descriptions;
	
	
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
	
	
	public List<LocalisedValue> names() {
		return names;
	}
	
	
	public List<LocalisedValue> descriptions() {
		return descriptions;
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
			for (LocalisedValue name : names)
				properties.add(new Property(format("name",name.lang()==null?"":"-"+name.lang()), name.value()));
		
		if (descriptions!=null)
			for (LocalisedValue description : descriptions)
				properties.add(new Property(format("description%s",description.lang()==null?"":"-"+description.lang()), description.value()));
		
		properties.add(new Property("uri",uri));
		properties.add(new Property("urn",urn));
		properties.add(new Property("uuid",uuid,false));
		
		
	}
}
