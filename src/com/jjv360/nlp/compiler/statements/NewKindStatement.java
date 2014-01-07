package com.jjv360.nlp.compiler.statements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jjv360.nlp.compiler.EnglishProcessor;
import com.jjv360.nlp.compiler.Thing;

public class NewKindStatement extends BaseStatement {
	
	// Vars
	Pattern p1			= null;
	Pattern p2			= null;
	
	public NewKindStatement() {
		
		// Create patterns
		p1 = Pattern.compile("(.*) is a kind of (.*)");
		p2 = Pattern.compile("(.*) is a type of (.*)");
		
	}

	public boolean parse(EnglishProcessor processor, String sentence) {
		
		// Check for new kind command
		Matcher m1 = p1.matcher(sentence);
		Matcher m2 = p2.matcher(sentence);
		if (m1.matches() || m2.matches()) {
			
			// Get nouns
			String noun1str = (m1.matches() ? m1.group(1).trim() : m2.group(1).trim());
			String noun2str = (m1.matches() ? m1.group(2).trim() : m2.group(2).trim());
			
			// Get things
			Thing noun1 = processor.getThing(noun1str);
			Thing noun2 = processor.getThing(noun2str);
			
			// Check that the second noun is a kind
			if (!noun2.type.equalsIgnoreCase("kind")) {
				
				// Invalid type
				System.out.println("Compile error: The second noun (" + noun2.name + ") is not a kind.");
				return true;
				
			}
			
			// Check if the first noun is the right kind of thing, or can be converted to it
			if (noun1.type.equalsIgnoreCase("kind") && !noun1.parentType.equalsIgnoreCase(noun2.name)) {
				
				// Invalid type
				System.out.println("Compile error: You already told me that the first noun (" + noun1.name + ") is a kind of " + noun1.type + ", so I can't set it as a kind of " + noun2.name + " now.");
				return true;
				
			}
			
			// Check if the first noun is something else already
			if (noun1.type.length() > 0) {
				
				// Invalid type
				System.out.println("Compile error: You already told me that the first noun (" + noun1.name + ") is a kind of " + noun1.type + ", so I can't set it as a kind of " + noun2.name + " now.");
				return true;
				
			}
			
			// Make the thing
			noun1.type = "kind";
			noun1.parentType = noun2.name;
			System.out.println("New thing: " + noun1.name + ", inherits from " + noun1.parentType);
			return true;
			
		}
		
		// Didn't match
		return false;

	}

}
