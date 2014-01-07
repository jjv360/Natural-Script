package com.jjv360.nlp.compiler;

import java.util.HashMap;

public class Thing {
	
	// Vars
	public String name								= null;
	public String type								= "";
	public String parentType						= null;
	public HashMap<String, Thing> properties		= new HashMap<String, Thing>();
	public int id									= -1;
	public String textValue							= "";
	public double numberValue						= 0;

}
