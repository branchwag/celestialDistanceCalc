package com.celestialdistance.view;

import com.celestialdistance.model.SkyMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java.awt.BorderLayout;
import java.awt.Dimension;

//main app frame for calc
public class MainFrame extends JFrame {
	private SkyMap skyMap;
	private StarFieldPanel starFieldPanel;
	private ObjectInfoPanel objectInfoPanel;

	// constructor to set up window
	public MainFrame() {
		setTitle("Celestial Distance Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1200, 600));
		setResizable(false);
		setMaximizedBounds(null);

		// init model
		skyMap = new SkyMap();

		// init panels
		objectInfoPanel = new ObjectInfoPanel();
		starFieldPanel = new StarFieldPanel(skyMap, objectInfoPanel);

		// make the split pane layout
		JSplitPane splitPane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT,
				starFieldPanel,
				objectInfoPanel);

		splitPane.setResizeWeight(0.8); // making this 80 percent of width to star field
		// the below two lines fix an issue where a user could previously resize the
		// panels, getting them stuck at a weird size
		// so, no resizing!!
		splitPane.setDividerSize(0);
		splitPane.setEnabled(false);

		// set up frame
		getContentPane().add(splitPane, BorderLayout.CENTER);

		// packitup!
		pack();

		setLocationRelativeTo(null); // centering on the screen

	}
}
