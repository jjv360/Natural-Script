package com.jjv360.nlp.compiler;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public abstract class LanguageProcessor {

	// Vars
	HashMap<String, Thing> things 			= new HashMap<String, Thing>();
	int lastThingId							= 0;
	
	public Collection<Thing> getThings() {
		
		return things.values();
		
	}
	
	public Thing createThing() {
		
		// Create thing
		Thing thing = new Thing();
		thing.id = lastThingId++;
		
		// Done
		return thing;
		
	}
	
	public Thing getThing(String name) {
		
		// Trim
		name = name.trim();
		
		// Check if thing exists
		Thing thing = things.get(name.toLowerCase());
		if (thing == null) {
			
			// Create thing
			thing = createThing();
			thing.name = name;
			things.put(name.toLowerCase(), thing);
			
		}
		
		// Done
		return thing;
		
	}
	
	boolean convertThing(Thing thing, Thing targetKind) {
		
		// Check if the thing is compatible
		Thing curKind = targetKind;
		while (true) {
			
			// If null, we are out of types and not compatible
			if (curKind == null)
				return false;
			
			// Check kind
			if (thing.type.equals(curKind.name))
				break;
			
			// Get next kind in the chain
			curKind = things.get(curKind.parentType);
			
		}
		
		// Convert thing
		if (thing.type.equals(targetKind.name)) {
			
			// Already the right type
			return true;
			
		} else {
			
			// Convert
			System.out.println("Convert thing: " + thing.name + " from " + thing.type + " to " + targetKind.name);
			thing.type = targetKind.name;
			
		}
		
		// Done
		return true;
		
	}
	
	// Abstract methods
	public abstract List<Command> getCommands(String document);
	public abstract List<Thing> getThingsFromStatement(String text);
	
}
