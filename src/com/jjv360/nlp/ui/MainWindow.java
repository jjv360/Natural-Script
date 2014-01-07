package com.jjv360.nlp.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import com.jjv360.nlp.compiler.Compiler;

public class MainWindow {
	
	// Static vars
	public static String VERSION	= "1.0.0";
	
	// Vars
	JFrame window					= null;
	JLabel statusLabel				= null;
	JTextPane textPane				= null;
	JButton runButton				= null;
	
	public MainWindow() {
		
		// Create window
		window = new JFrame("NLP");
		window.setBounds(50, 50, 800, 600);
		window.setVisible(true);
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Add menu bar
		JMenuBar menuBar = new JMenuBar();
		window.add(menuBar, BorderLayout.NORTH);
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		// Add status bar
		JPanel statusBar = new JPanel(new BorderLayout());
		statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
		window.add(statusBar, BorderLayout.SOUTH);
		
		// Add status label
		statusLabel = new JLabel("NLP version " + VERSION);
		statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		statusBar.add(statusLabel, BorderLayout.CENTER);
		
		// Add content pane
		JPanel contentPane = new JPanel(new BorderLayout());
		window.add(contentPane, BorderLayout.CENTER);
		
		// Add toolbar
		Box toolbar = Box.createHorizontalBox();
		toolbar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		contentPane.add(toolbar, BorderLayout.NORTH);
		
		// Add run button
		runButton = new JButton("Run");
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				run();
			}
		});
		toolbar.add(runButton);
		
		// Add text pane
		textPane = new JTextPane();
		contentPane.add(textPane, BorderLayout.CENTER);
		
		// Show window
		window.setVisible(true);
		window.revalidate();
		
	}
	
	void compile() {
		
		// Get text
		String document = textPane.getText();
		
		// Create compiler
		Compiler c = new Compiler();
		
		// Compile
		c.compileIncludes();
		c.compile(document);
		
		// Debug output
		c.listThings();
		
	}
	
	void run() {

		// Create script engine
		ScriptEngineManager man = new ScriptEngineManager();
		ScriptEngine engine = man.getEngineByName("nlp");
		
		// Run code
		try {
			engine.eval(textPane.getText());
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		
	}

}
