package org.virtual.data.fao.resources;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class LocalisedValue {
	
	private static final String xmlns = "http://www.w3.org/XML/1998/namespace";

	@XmlAttribute(namespace=xmlns)
	private String lang;
	
	@XmlValue
	private String value;
	
	public String value() {
		return value;
	}
	
	public String lang() {
		return lang;
	}
}
