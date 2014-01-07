package com.jjv360.nlp.script;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class NLPFactory implements ScriptEngineFactory {
	
	// Vars
	static NLPFactory sharedInstance		= null;
	ArrayList<String> names					= null;
	static LexicalizedParser parser				= null;
	public static NLPFactory sharedInstance() {
		
		if (sharedInstance == null)
			sharedInstance = new NLPFactory();
		
		return sharedInstance;
		
	}
	
	public NLPFactory() {
		
		// Create list of names
		names = new ArrayList<String>();
		names.add("natural");
		names.add("natural language");
		names.add("natural script");
		names.add("nlp");
		names.add("nlp script");
		
	}
	
	public static LexicalizedParser getParser() {
		
		// Load parser
		if (parser == null)
			parser = LexicalizedParser.loadModel();
		
		// Return parser
		return parser;
		
	}

	public String getEngineName() {
		return "NLP Engine";
	}

	public String getEngineVersion() {
		return "1.0.0";
	}

	public List<String> getExtensions() {
		ArrayList<String> exts = new ArrayList<String>();
		exts.add("nlp");
		return exts;
	}
	
	public String getLanguageName() {
		return "Natural Script";
	}

	public String getLanguageVersion() {
		return "1.0.0";
	}

	public String getMethodCallSyntax(String obj, String m, String... args) {
		
		// Call function
		String s = "Call function " + m + " on " + obj;
		
		// Show args
		if (args.length > 0) s += " with ";
		for (int i = 0 ; i < args.length ; i++)
			s += (i == 0 ? "" : ", ") + args[i];
		
		// End sentence
		s += ". ";
		return s;
		
	}

	public List<String> getMimeTypes() {
		ArrayList<String> exts = new ArrayList<String>();
		exts.add("text/nlp");
		exts.add("text/x-nlp");
		exts.add("application/nlp");
		exts.add("application/x-nlp");
		return exts;
	}

	public List<String> getNames() {
		return names;
	}

	public String getOutputStatement(String toDisplay) {
		return "Say " + toDisplay + ".";
	}

	public Object getParameter(String key) {

		if (key.equals(ScriptEngine.ENGINE))			return getEngineName();
		if (key.equals(ScriptEngine.ENGINE_VERSION))	return getEngineVersion();
		if (key.equals(ScriptEngine.NAME))				return getLanguageName();
		if (key.equals(ScriptEngine.LANGUAGE))			return getLanguageName();
		if (key.equals(ScriptEngine.ENGINE))			return getEngineName();
		if (key.equals(ScriptEngine.ENGINE_VERSION))	return getEngineVersion();
		return null;
		
	}

	public String getProgram(String... statements) {

		StringBuilder sb = new StringBuilder();
		for (String statement : statements) {
			
			sb.append(statement);
			sb.append("\n");
			
		}
		
		return sb.toString();
		
	}

	public ScriptEngine getScriptEngine() {
		return new NLPEngine();
	}

}
