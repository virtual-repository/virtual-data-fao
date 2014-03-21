package org.virtual.data.fao.resources;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.virtualrepository.Property;

@XmlRootElement(name="item")
public class Member extends AbstractResource {

	@XmlElement(name="properties")
	private Props props;
	
	public String name() {
		String[] parts = urn().toString().split(":");
		int l = parts.length;
		return parts[l-2]+"-"+parts[l-1];
	}
	
	public String bk() {
		return (props==null || props.bk==null)?null:props.bk; 
	}
	
	
	
	//jaxb callback
	void afterUnmarshal(Unmarshaller u, Object parent) {

		super.afterUnmarshal(u, parent);
		
		if (props!=null && props.bk!=null)
			properties.add(new Property("bk",props.bk,false));
	}

	
	static class Props {
		
		@XmlElement
		String bk;
		
	}
	
}
