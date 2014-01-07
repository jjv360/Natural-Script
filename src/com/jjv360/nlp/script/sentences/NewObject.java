package com.jjv360.nlp.script.sentences;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptContext;

public class NewObject extends BaseSentence {

	// Patterns
	Pattern p1 = Pattern.compile("Create a new (?<type>.*) called (?<name>.*)", Pattern.CASE_INSENSITIVE);
	Pattern p2 = Pattern.compile("Create a (?<type>.*) called (?<name>.*)", Pattern.CASE_INSENSITIVE);
	Pattern p3 = Pattern.compile("(?<name>.*) is a new (?<type>.*)", Pattern.CASE_INSENSITIVE);
	Pattern p4 = Pattern.compile("(?<name>.*) is a (?<type>.*)", Pattern.CASE_INSENSITIVE);

	public int getConfidence(ScriptContext ctx, String sentence) {
		
		// Check if matched
		if (getMatched(sentence, p1) != null)
			return CONFIDENCE_HIGH;
		else
			return CONFIDENCE_NONE;
		
	}

	public void parse(ScriptContext ctx, String sentence) {
		
		// Check
		Matcher m = getMatched(sentence, p1);
		if (m != null) {
			
			// Check the parent type
			String parentStr = trimName(m.group("parent"));
			String typeStr = trimName(m.group("type"));
			
			
		}
		
	}

}
