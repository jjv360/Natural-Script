package com.jjv360.nlp.compiler;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Compiler {
	
	// Vars
	LanguageProcessor languageProcessor		= new EnglishProcessor();
	
	public Compiler() {
		
	}
	
	String getContentsOfStream(InputStream is) {
		
		StringBuilder sb = new StringBuilder();
		try {

			InputStreamReader in = new InputStreamReader(is);
			char[] bfr = new char[1024*32];
			int amt = 0;
			while ((amt = in.read(bfr)) != -1)
				sb.append(bfr, 0, amt);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sb.toString();
		
	}
	
	public void compileIncludes() {
		
		// Get includes
		compile(getContentsOfStream(this.getClass().getResourceAsStream("/include/basic.nlp")));
		
	}
	
	public void compile(String document) {
		
		// Get a list of commands from the language processor
		List<Command> commands = languageProcessor.getCommands(document);
		
	}
	
	String toSentenceCase(String s) {
		
		if (s.length() == 0)
			return s;
		else
			return s.substring(0, 1).toUpperCase() + s.substring(1);
		
	}
	
	public void listThings() {

		// Output table header
		System.out.printf(" +--------------------------------------------------+------------------------------+----------------------------------------+\n");
		System.out.printf(" | %-48s | %-28s | %-38s |\n", "Name of thing", "Kind of thing", "Value");
		System.out.printf(" +--------------------------------------------------+------------------------------+----------------------------------------+\n");
		
		// Output things
		for (Thing t : languageProcessor.getThings()) {
			
			// Get kind text
			String kind = toSentenceCase(t.type);
			Thing nt = (t.parentType == null ? null : languageProcessor.things.get(t.parentType.toLowerCase()));
			if (t.parentType == null) {
				nt = languageProcessor.things.get(t.type.toLowerCase());
				if (nt != null) nt = languageProcessor.things.get(nt.parentType.toLowerCase());
			}
			while (nt != null) {
				kind += " > " + toSentenceCase(nt.name);
				nt = (nt.parentType == null ? null : languageProcessor.things.get(nt.parentType.toLowerCase()));
			}
			
			// Get variable value
			String val = "";
			if (t.type == null)								val = "";
			else if (t.type.equalsIgnoreCase("text"))		val = "\"" + t.textValue + "\"";
			else if (t.type.equalsIgnoreCase("number"))		val = "" + t.numberValue;
			else											val = toSentenceCase(t.type) + " @ " + t.id;
			
			// Output thing
			System.out.printf(" | %-48s | %-28s | %-38s |\n", toSentenceCase(t.name), kind, val);
			
			// Output properties
			listProperties(t, 0);
			
		}
		
		// Output table footer
		System.out.printf(" +--------------------------------------------------+------------------------------+----------------------------------------+\n");
		
	}
	
	void listProperties(Thing thing, int indent) {
		
		// Go through properties
		for (Thing t : thing.properties.values()) {
			
			// Get kind text
			String kind = toSentenceCase(t.type);
			Thing nt = (t.parentType == null ? null : languageProcessor.things.get(t.parentType.toLowerCase()));
			if (t.parentType == null) {
				nt = languageProcessor.things.get(t.type.toLowerCase());
				if (nt != null) nt = languageProcessor.things.get(nt.parentType.toLowerCase());
			}
			while (nt != null) {
				kind += " > " + toSentenceCase(nt.name);
				nt = (nt.parentType == null ? null : languageProcessor.things.get(nt.parentType.toLowerCase()));
			}
			
			// Get variable value
			String val = "";
			if (t.type == null)								val = "";
			else if (t.type.equalsIgnoreCase("text"))		val = "\"" + t.textValue + "\"";
			else if (t.type.equalsIgnoreCase("number"))		val = "" + t.numberValue;
			else											val = toSentenceCase(t.type) + " @ " + t.id;
			
			// Output thing
			String indentSpaces = "";
			for (int i = 0 ; i < indent ; i++) indentSpaces += "  ";
			System.out.printf(" | " + indentSpaces + "- %-" + (46 - indent*2) + "s | %-28s | %-38s |\n", toSentenceCase(t.name), kind, val);
			
			// Output properties
			listProperties(t, indent+1);
			
		}
		
		// Output break if needed
		if (thing.properties.size() > 0)
			System.out.printf(" | %-48s | %-28s | %-38s |\n", "", "", "");
		
	}

}
