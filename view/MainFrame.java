package com.celestialdistance.view;

import com.celestialdistance.model.SkyMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.BorderLayout;
import java.awt.Dimension; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

//main app frame for calc
public class MainFrame extends JFrame {
	private SkyMap skyMap;
	private StarFieldPanel starFieldPanel;
	private ObjectInfoPanel objectInfoPanel;

	//constructor to set up window
	public MainFrame() {
		setTitle("Celestial Distance Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 600));

		//init model
		skyMap = new SkyMap();

		//init panels
		objectInfoPanel = new ObjectInfoPanel();
		starFieldPanel = new StarFieldPanel(skyMap, objectInfoPanel);

		//make the split pane layout
		JSplitPane splitPane = new JSplitPane(
			JSplitPane.HORIZONTAL_SPLIT,
			starFieldPanel,
			objectInfoPanel
		);
		splitPane.setResizeWeight(0.7); //making this 70 percent of width to star field

		//menu bar
		JMenuBar menuBar = createMenuBar();
		//set up frame
		setJMenuBar(menuBar);
		getContentPane().add(splitPane, BorderLayout.CENTER);

		//status bar
		JPanel statusBar = new JPanel();
		JLabel statusLabel = new JLabel("Ready");
		statusBar.add(statusLabel);
		getContentPane().add(statusBar, BorderLayout.SOUTH);

		//packitup!
		pack();
		
		setLocationRelativeTo(null); //centering on the screen

	}

	//function to make the menu bar
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		//file menu
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		JMenuItem exitItem = new JMenuItem("Exit", KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		exitItem.addActionListener(e -> System.exit(0));

		fileMenu.add(exitItem);

		//view menu
		JMenu viewMenu = new JMenu("View");
		viewMenu.setMnemonic(KeyEvent.VK_V);

		JMenuItem refreshItem = new JMenuItem("Refresh", KeyEvent.VK_R);
		refreshItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		refreshItem.addActionListener(e -> starFieldPanel.repaint());

		viewMenu.add(refreshItem);

		//help menu
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);

		JMenuItem aboutItem = new JMenuItem("About", KeyEvent.VK_A);
		aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(
					MainFrame.this,
					"Celestial Distance Calculator\n" + 
					"Version 1.0\n\n" +
					"An app for calculating distances to celestial objects using various astronomical methods.",
					"About",
					JOptionPane.INFORMATION_MESSAGE
				);
			}
		});

		helpMenu.add(aboutItem);

		menuBar.add(fileMenu);
		menuBar.add(viewMenu);
		menuBar.add(helpMenu);

		return menuBar;
	}
}



