package com.celestialdistance;

import com.celestialdistance.view.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

//main class to launch app
public class CelestialDistanceCalculator {
	//main method
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("Error: " + e);
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainFrame mainFrame = new MainFrame();
				mainFrame.setVisible(true);
			}
		});
	}
}
