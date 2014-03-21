package org.virtual.data.fao.resources;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.virtualrepository.Property;

@XmlRootElement(name="item")
public class Dimension extends AbstractResource {
	
	public static final String dataset_mnemonic_property = Dimension.class.getCanonicalName();

	@XmlElement
	String dimensionType;
	
	public String name() {
		String[] parts = urn().toString().split(":");
		int l = parts.length;
		return parts[l-2]+"-"+parts[l-1];
	}
	
	String dimensionType() {
		return dimensionType;
	}
	
	@Override
	public String toString() {
		return super.toString()+", type=" + dimensionType;
	}
	
	//jaxb callback
	void afterUnmarshal(Unmarshaller u, Object parent) {

		super.afterUnmarshal(u, parent);
		
		if (dimensionType!=null)
			this.properties.add(new Property("type",dimensionType));
		
		this.properties.add(new Property(dataset_mnemonic_property,mnemonic(),false));
	}

	
}
