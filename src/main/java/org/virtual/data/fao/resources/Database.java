package org.virtual.data.fao.resources;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="item")
public class Database extends AbstractResource {

	public String name() {
		return mnemonic(); //for databases, mnemonics are unique
	}
	
	
}
