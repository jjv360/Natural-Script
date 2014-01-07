package com.jjv360.nlp.script.sentences;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptContext;

public abstract class BaseSentence {
	
	// Confidence levels
	public static int CONFIDENCE_NONE	= -1;
	public static int CONFIDENCE_LOW	= 1;
	public static int CONFIDENCE_MEDIUM	= 5;
	public static int CONFIDENCE_HIGH	= 9;
	
	public Matcher getMatched(String input, Pattern... patterns) {
		
		for (Pattern p : patterns) {
			
			// Create matcher
			Matcher m = p.matcher(input);
			if (!m.matches())
				continue;
			
			// Found, return
			return m;
			
		}
		
		return null;
		
	}
	
	public String trimName(String name) {
		
		// Remove unneeded words
		name = name.toLowerCase();
		if (name.startsWith("a "))
			name = name.substring(2);
		if (name.startsWith("an "))
			name = name.substring(3);
		if (name.startsWith("the "))
			name = name.substring(4);
		
		return name;
		
	}
	
	public Object getItem(ScriptContext ctx, String name, boolean create) {
		
		// Find object
		Object o = ctx.getAttribute(name);
		if (o != null || !create)
			return o;
		
		// Create object
		return null;
		
	}

	public abstract int getConfidence(ScriptContext ctx, String sentence);
	public abstract void parse(ScriptContext ctx, String sentence);

}
