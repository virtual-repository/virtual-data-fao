package org.virtual.data.fao.io;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.dom.Element;

@XmlRootElement(name="result")
public class Results implements Iterable<Element> {

	@XmlElement
	private Items list;
	
	public boolean hasMore() {
		return list.total > (list.pageSize*list.page);
	}
	
	@Override
	public Iterator<Element> iterator() {
		return list.item.iterator();
	}
	
	private static class Items {
		
		@XmlElement
		int page;
		
		@XmlElement
		int pageSize;
		
		@XmlElement
		int total;
		
		@XmlElementWrapper(name="items")
		@XmlAnyElement(lax=true)
		List<Element> item;
		

		@Override
		public String toString() {
			return "List [page=" + page + ", size=" + pageSize + ", total=" + total + ", items size="+item.size()+"]";
		}
		
			
	}


	@Override
	public String toString() {
		return "Results [list=" + list + "]";
	}
	
}
