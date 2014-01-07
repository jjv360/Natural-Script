package com.jjv360.nlp.compiler.statements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jjv360.nlp.compiler.EnglishProcessor;

public abstract class BaseStatement {
	
	public String[] getGroupsFromMatchers(String input, Pattern... patterns) {
		
		for (Pattern p : patterns) {
			
			// Create matcher
			Matcher m = p.matcher(input);
			if (!m.matches())
				continue;
			
			// Found, return groups
			String[] strs = new String[m.groupCount()+1];
			for (int i = 0 ; i < strs.length ; i++)
				strs[i] = m.group(i);
			
			return strs;
			
		}
		
		return null;
		
	}
	
	public abstract boolean parse(EnglishProcessor processor, String sentence);

}
