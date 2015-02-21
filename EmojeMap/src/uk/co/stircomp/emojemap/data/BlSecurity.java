package uk.co.stircomp.emojemap.data;

import java.util.LinkedList;
import java.util.ListIterator;

public class BlSecurity {
	
	private String name;
	private LinkedList<BlField> fields;
	
	public BlSecurity(String name) {
		
		this.name = name;
		fields = new LinkedList<BlField>();
		
	}
	
	public String getName() {
		
		return this.name;
		
	}
	
	public void addField(String name, String value) {
		
		fields.add(new BlField(name, value));
		
	}
	
	public void addField(BlField field) {
		
		fields.add(field);
		
	}

	public String getFieldValue(String name) {
		
		ListIterator<BlField> li = fields.listIterator();
		while (li.hasNext()) {
			
			BlField field = li.next();
			
			if (field.getName().equals(name)) {
				return field.getValue();
			}
			
			
		}
		
		return null;
				
	}
	
	public LinkedList<BlField> getFields() {
		
		return fields;
		
	}
	
}
