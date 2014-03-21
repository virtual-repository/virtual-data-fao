package org.virtual.data.fao.resources;

import static java.lang.String.*;

import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.virtualrepository.Property;


@XmlRootElement(name="item")
public class Database extends AbstractResource {

	@XmlElement(name="description")
	private List<LocalisedValue> descriptions;
	
	
	public String name() {
		return mnemonic(); //for databases, mnemonics are unique
	}
	
	//jaxb callback
	void afterUnmarshal(Unmarshaller u, Object parent) {

		super.afterUnmarshal(u, parent);
		
		if (descriptions!=null)
			for (LocalisedValue description : descriptions)
				properties.add(new Property(format("description(%s)",description.lang()), description.value()));
		
	
		
	}
	
}
