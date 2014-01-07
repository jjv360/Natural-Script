package com.jjv360.nlp.compiler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import com.jjv360.nlp.compiler.statements.*;

public class EnglishProcessor extends LanguageProcessor {
	
	// Regular expressions
	ArrayList<BaseStatement> statements		= new ArrayList<BaseStatement>();
	
	public EnglishProcessor() {
		
		// Add statements
		statements.add(new NewKindStatement());
		statements.add(new AssignmentStatement());
		
		// Create base thing
		Thing t = new Thing();
		t.name = "Thing";
		t.type = "kind";
		things.put("thing", t);
		
		// Create base number
		t = new Thing();
		t.name = "Number";
		t.type = "kind";
		t.parentType = "thing";
		things.put("number", t);
		
		// Create base text
		t = new Thing();
		t.name = "Text";
		t.type = "kind";
		t.parentType = "thing";
		things.put("text", t);
		
	}
	
	public List<Thing> getThingsFromStatement(String text) {
		
		// First split list
		String[] statements = text.split("( and )|( or )|(,)");
		ArrayList<Thing> things = new ArrayList<Thing>();
		for (String s : statements) {
			
			// Check for properties
			String[] properties = s.split("\\'s ");
			Thing thing = getThing(properties[0]);
			for (int i = 1 ; i < properties.length ; i++) {
				
				// Check if property exists
				Thing prop = thing.properties.get(properties[i]);
				if (prop == null) {
					
					// Create new thing
					prop = createThing();
					prop.name = properties[i];
					thing.properties.put(properties[i], prop);
					
				}
				
				// Store
				thing = prop;
				
			}
			
			things.add(thing);
			
		}
		
		return things;
		
	}

	public List<Command> getCommands(String document) {
		
		// Get list of sentences
		ArrayList<Command> commands = new ArrayList<Command>();
		StringBuilder sb = new StringBuilder();
		boolean quoted = false;
		int startIdx = 0;
		for (int i = 0 ; i < document.length() ; i++) {
			
			// Add character to string
			sb.append(document.charAt(i));
			
			// Check for quotes
			if (quoted && document.charAt(i) == '"')
				quoted = false;
			else if (!quoted && document.charAt(i) == '"')
				quoted = true;
			
			// Check for end of sentence
			if (!quoted && (document.charAt(i) == '.' || document.charAt(i) == '?' || document.charAt(i) == '!' || document.charAt(i) == '\n')) {
				
				// Save command
				Command c = getCommand(sb.toString(), startIdx, sb.length());
				if (c != null) commands.add(c);
				sb.setLength(0);
				startIdx = i + 1;
				
			}
			
		}
		
		// Check for final sentence
		Command c = getCommand(sb.toString(), startIdx, sb.length());
		if (c != null) 
			commands.add(c);
		
		// DOne
		return commands;
	
	}
	
	Command getCommand(String sentence, int start, int len) {
		
		// Validate length
		sentence = sentence.trim();
		if (sentence.length() == 0) 
			return null;
		
		// Remove punctuation
		if (sentence.endsWith(".") || sentence.endsWith("?") || sentence.endsWith("!"))
			sentence = sentence.substring(0, sentence.length()-1);
		
		// Go through sentences
		for (BaseStatement s : statements) {
			
			if (s.parse(this, sentence))
				return null;
			
		}
		
		
		// No sentence matched
		System.out.println("Sentence not understood: " + sentence);
		return null;
		
	}
	
	public Thing getThing(String name) {
		
		// Check type
		name = name.trim();
		if (name.startsWith("\"") || name.startsWith("'")) {
			
			// Text property
			Thing t = createThing();
			t.textValue = name.substring(1, name.length()-1);
			t.type = "text";
			return t;
			
		} else if (NumberUtils.isNumber(name)) {
			
			// Number property
			Thing t = createThing();
			t.numberValue = NumberUtils.toDouble(name);
			t.type = "number";
			return t;
			
		}
		
		// Check for unneeded words
		if (name.toLowerCase().startsWith("a "))
			name = name.substring(2);

		if (name.toLowerCase().startsWith("an "))
			name = name.substring(3);

		if (name.toLowerCase().startsWith("the "))
			name = name.substring(4);
		
		// If there's an S on the end and there's a thing without it, remove the s
		if (name.endsWith("s") && things.containsKey(name.substring(0, name.length()-1)))
			name = name.substring(0, name.length()-1);
		
		return super.getThing(name);
		
	}

}
