package com.jjv360.nlp.script;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;

public class Sentence {
	
	// Store tree
	Tree tree 				= null;
	String verb				= null;
	String noun1			= null;
	String noun2			= null;
	
	public Sentence(Tree t) {
		
		// Save tree
		tree = t;
		
		// Get verb
		TregexPattern pattern = TregexPattern.compile("@VB");
		TregexMatcher matcher = pattern.matcher(tree);
		if (matcher.findNextMatchingNode()) {
		
			Tree match = matcher.getMatch();
			verb = edu.stanford.nlp.ling.Sentence.listToString(match.yield());
		
		}
		
		// Get first noun
		pattern = TregexPattern.compile("@NP");
		matcher = pattern.matcher(tree);
		if (matcher.findNextMatchingNode()) {
			
			Tree match = matcher.getMatch();
			noun1 = edu.stanford.nlp.ling.Sentence.listToString(match.yield());
		
		}
		
		// Get second noun
		if (matcher.findNextMatchingNode()) {
			
			Tree match = matcher.getMatch();
			noun2 = edu.stanford.nlp.ling.Sentence.listToString(match.yield());
		
		}
		
	}
	
	public String getVerb() {
		return verb;
	}
	
	public String getNoun1() {
		return noun1;
	}
	
	public String getNoun2() {
		return noun2;
	}
	
	public String toString() {
		
		return "Verb: " + verb + ", Noun 1: " + noun1 + ", Noun2: " + noun2;
		
	}

}
