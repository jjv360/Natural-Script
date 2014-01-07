package com.jjv360.nlp.script;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import com.jjv360.nlp.script.sentences.*;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.trees.Tree;

public class NLPEngine extends AbstractScriptEngine {
	
	// Create list of parsers
	ArrayList<BaseSentence> sentenceParsers		= new ArrayList<BaseSentence>();
	
	public NLPEngine() {
		super();
		
		// Add sentence parsers
		sentenceParsers.add(new NewKind());
		
		// Add default types
		Type t = new Type();
		t.name = "Thing";
		put("thing", t);
		
	}

	public Bindings createBindings() {
		
		// Create bindings
		SimpleBindings sb = new SimpleBindings();
		
		// Return new bindings
		return sb;
		
	}

	public Object eval(String script, ScriptContext context) throws ScriptException {

		StringReader sr = new StringReader(script);
		return eval(sr, context);
		
	}

	public Object eval(Reader reader, ScriptContext context) throws ScriptException {
		
		// Load parser
		LexicalizedParser parser = NLPFactory.getParser();
		
		// Create document parser
		DocumentPreprocessor dp = new DocumentPreprocessor(reader);
		
		// Get list of sentences
		Iterator<List<HasWord>> it = dp.iterator();
		while (it.hasNext()) {
			
			// Get tree
			Tree tree = parser.apply(it.next());
			
			// Get list of simple sentences
			List<Tree> sentenceTrees = getSentenceTrees(tree);

			// Parse sentence tree
			for (Tree t : sentenceTrees) {
				
				// Get verb
				Sentence sentence = new Sentence(t);
				System.out.println(t + " > " + sentence);
				
			}
			
		}
		
		// Done
		return null;
		
	}
	
	List<Tree> getSentenceTrees(Tree tree) {
		
		// Go through children
		ArrayList<Tree> sentenceTrees = new ArrayList<Tree>();
		boolean foundSubSentence = false;
		for (Tree t : tree.children()) {
			
			// Check type
			if (t.label().value().equals("S")) {
				
				// Add sub-sentence
				foundSubSentence = true;
				sentenceTrees.addAll(getSentenceTrees(t));
				
			}
			
		}
		
		// If no sub-sentences, add this
		if (!foundSubSentence) 
			sentenceTrees.add(tree);
		
		// Return list
		return sentenceTrees;
		
	}
	
	public ScriptEngineFactory getFactory() {
		return NLPFactory.sharedInstance();
	}

}
