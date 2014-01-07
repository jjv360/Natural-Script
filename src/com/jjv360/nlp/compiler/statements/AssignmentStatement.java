package com.jjv360.nlp.compiler.statements;

import java.util.List;
import java.util.regex.Pattern;

import com.jjv360.nlp.compiler.EnglishProcessor;
import com.jjv360.nlp.compiler.Thing;

public class AssignmentStatement extends BaseStatement {

	// Vars
	Pattern p1			= null;
	Pattern p2			= null;
	
	public AssignmentStatement() {
		
		// Create patterns
		p1 = Pattern.compile("(.*) is (.*)");
		p2 = Pattern.compile("(.*) are (.*)");
		
	}

	public boolean parse(EnglishProcessor processor, String sentence) {
		
		String[] nouns = getGroupsFromMatchers(sentence, p1, p2);
		if (nouns != null) {
			
			// Get the thing
			List<Thing> things1 = processor.getThingsFromStatement(nouns[1]);
			List<Thing> things2 = processor.getThingsFromStatement(nouns[2]);
			
			// Check that there is only one item in the second list
			if (things2.size() != 1) {
				
				System.out.println("Compile error: This sentence didn't make sense: " + sentence);
				return true;
				
			}
			
			// Assign each item in first list
			Thing thing2 = things2.get(0);
			for (Thing t : things1) {
				
				// Check type
				if (thing2.type.equalsIgnoreCase("kind")) {
					
					// Creating new thing of the specified type
					t.type = thing2.name;
					t.properties.clear();
					t.properties.putAll(thing2.properties);
					
				} else {
					
					// Copy thing
					t.type = thing2.type;
					t.properties.clear();
					t.properties.putAll(thing2.properties);
					t.id = thing2.id;
					t.textValue = thing2.textValue;
					t.numberValue = thing2.numberValue;
					
				}
				
			}

			return true;
			
		}
		
		// Didn't match
		return false;
		
	}

}
