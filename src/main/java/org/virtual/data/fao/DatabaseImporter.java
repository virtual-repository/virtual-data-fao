package org.virtual.data.fao;

import static java.lang.System.*;
import static org.virtual.data.fao.resources.Dimension.*;
import static org.virtual.data.fao.resources.ResourceType.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Provider;
import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtual.data.fao.io.Request;
import org.virtual.data.fao.resources.Database;
import org.virtual.data.fao.resources.Member;
import org.virtual.data.fao.resources.Name;
import org.virtualrepository.Property;
import org.virtualrepository.csv.CsvCodelist;
import org.virtualrepository.impl.Type;
import org.virtualrepository.spi.Importer;
import org.virtualrepository.tabular.Column;
import org.virtualrepository.tabular.DefaultTable;
import org.virtualrepository.tabular.Row;
import org.virtualrepository.tabular.Table;

public class DatabaseImporter implements Importer<CsvCodelist,Table> {

	private static final Logger log = LoggerFactory.getLogger(DatabaseImporter.class);
	
	private final Database db;
	private final Provider<Request> requests;
	
	private static Column codecol = new Column("code");
	private static Column urncol = new Column("urn");
	private static Column uricol = new Column("uri");
	private static Column uuidcol = new Column("uuid");

	
	public DatabaseImporter(Database db,Provider<Request> requests) {
		this.db=db;
		this.requests=requests;
	}
	
	@Override
	public Class<Table> api() {
		return Table.class;
	}
	
	@Override
	public Type<CsvCodelist> type() {
		return CsvCodelist.type;
	}
	
	public Table retrieve(CsvCodelist asset) throws Exception {
		
		log.info("retrieving codelist {} from {}",asset.name(),db.mnemonic());
		
		long time = currentTimeMillis();

		
		try {
			
			if (!asset.properties().contains(dataset_mnemonic_property))
				throw new IllegalArgumentException("invalid asset: no '"+dataset_mnemonic_property+"' property");
			
			String ds_mnemonic = asset.properties().lookup(dataset_mnemonic_property).value(String.class);
			
			Collection<Member> members = requests.get().over(membersOf(db.mnemonic(),ds_mnemonic)).execute();

			log.info("retrieved codelist {} from {} in {} ms.",asset.name(),db.mnemonic(),currentTimeMillis()-time);
			
			
			asset.properties().add(new Property("count",members.size()));
			
			return tableFrom(members);
			
		}
		catch(Exception e) {
			throw new RuntimeException("cannot discover codelists (see cause)",e);
		}
		

	}
	
	private Table tableFrom(Collection<Member> members) {
	
	
		Collection<Row> rows= new ArrayList<>();
		List<Column> cols= new ArrayList<>(Arrays.asList(codecol,uricol,urncol,uuidcol));
		
		for (Member member : members) {
			
			Map<QName,String> data = new HashMap<>();
			
			for (Name name : member.allNames()) {
				
				if (name.lang().equals("en"))
					data.put(codecol.name(),name.value());
				
				else {
					
					Column col = new Column("name-"+name.lang());
					if (!cols.contains(col))
						cols.add(col);
					data.put(col.name(),name.value());
					
				}
			}
			
			data.put(uricol.name(),member.uri().toString());
			data.put(urncol.name(),member.urn().toString());
			data.put(uuidcol.name(),member.uuid().toString());
			
			
			rows.add(new Row(data));		
			
		}

		
		return new DefaultTable(cols, rows);
	}
}
