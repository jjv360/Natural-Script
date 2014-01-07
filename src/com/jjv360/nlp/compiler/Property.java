package com.jjv360.nlp.compiler;

public class Property {
	
	// Vars
	public String name			= null;
	public String type			= "thing";
	public Thing thingValue		= null;
	public String textValue		= "";
	public double numberValue	= 0;
	
	public Property copy() {
		
		Property p = new Property();
		p.name = name;
		p.type = type;
		p.thingValue = thingValue;
		p.textValue = textValue;
		p.numberValue = numberValue;
		return p;
		
	}

}
