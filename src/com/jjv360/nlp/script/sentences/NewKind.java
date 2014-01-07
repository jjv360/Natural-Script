package com.jjv360.nlp.script.sentences;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptContext;

import com.jjv360.nlp.script.Type;

public class NewKind extends BaseSentence {

	// Patterns
	Pattern p1 = Pattern.compile("(?<type>.*) is a new kind of (?<parent>.*)", Pattern.CASE_INSENSITIVE);
	Pattern p2 = Pattern.compile("(?<type>.*) is a kind of (?<parent>.*)", Pattern.CASE_INSENSITIVE);
	Pattern p3 = Pattern.compile("(?<type>.*) is a new type of (?<parent>.*)", Pattern.CASE_INSENSITIVE);
	Pattern p4 = Pattern.compile("(?<type>.*) is a type of (?<parent>.*)", Pattern.CASE_INSENSITIVE);
	Pattern p5 = Pattern.compile("Create a kind of (?<parent>.*) called (?<type>.*)", Pattern.CASE_INSENSITIVE);
	Pattern p6 = Pattern.compile("Create a type of (?<parent>.*) called (?<type>.*)", Pattern.CASE_INSENSITIVE);

	public int getConfidence(ScriptContext ctx, String sentence) {
		
		// Check if matched
		if (getMatched(sentence, p1, p2, p3, p4, p5, p6) != null)
			return CONFIDENCE_HIGH;
		else
			return CONFIDENCE_NONE;
		
	}

	public void parse(ScriptContext ctx, String sentence) {
		
		// Check
		Matcher m = getMatched(sentence, p1, p2, p3, p4, p5, p6);
		if (m != null) {
			
			// Check the parent type
			String parentStr = trimName(m.group("parent"));
			String typeStr = trimName(m.group("type"));
			Object o = getItem(ctx, parentStr, false);
			if (o == null) {
				
				System.out.println("Script error: I don't know what a " + parentStr + " is.");
				return;
				
			}
			
			if (!o.getClass().isAssignableFrom(Type.class)) {
				
				System.out.println("Script error: A " + parentStr + " isn't a type.");
				return;
				
			}
			
			// Check type
			Type parent = (Type) o;
			o = getItem(ctx, typeStr, false);
			if (o != null) {
				
				System.out.println("Script error: " + typeStr + " already exists.");
				return;
				
			}
			
			// Make
			System.out.println("Creating a new type (" + typeStr + ") that extends from " + parentStr);
			Type t = new Type();
			t.name = typeStr;
			t.inheritsFrom = parent.name;
			t.classObj = parent.classObj;
			ctx.setAttribute(t.name.toLowerCase(), t, ScriptContext.ENGINE_SCOPE);
			
		}
		
	}

}
