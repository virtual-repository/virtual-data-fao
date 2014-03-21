package org.virtual.data.fao.resources;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="item")
public class Member extends AbstractResource {

	
	public String name() {
		String[] parts = urn().toString().split(":");
		int l = parts.length;
		return parts[l-2]+"-"+parts[l-1];
	}
	
	//jaxb callback
	void afterUnmarshal(Unmarshaller u, Object parent) {

		super.afterUnmarshal(u, parent);
	}

	
}
