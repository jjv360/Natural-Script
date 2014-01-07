package com.jjv360.nlp;

import javax.swing.UIManager;

import com.jjv360.nlp.ui.MainWindow;

public class Main {
	
	// Vars
	static MainWindow mainWindow		= null;

	public static void main(String[] args) {
		
		// Set system visual styles
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Create main GUI
		mainWindow = new MainWindow();
		
	}

}
