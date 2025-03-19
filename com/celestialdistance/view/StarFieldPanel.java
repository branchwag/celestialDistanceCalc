package com.celestialdistance.view;

import com.celestialdistance.model.CelestialObject;
import com.celestialdistance.model.SkyMap;


import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

//Panel for Star Field (populated by interactive celestial objects)
public class StarFieldPanel extends JPanel {
	//instance vars
	private SkyMap skyMap;
	private ObjectInfoPanel infoPanel;
	private List<int[]> backgroundStars = new ArrayList<>();

	//CONSTRUCTOR
	public StarFieldPanel(SkyMap skyMap, ObjectInfoPanel infoPanel) {
		this.skyMap = skyMap;
		this.infoPanel = infoPanel;

		setBackground(Color.BLACK);
		setupMouseListeners();

		//init the background stars after component is shown to prevent issue where background stars would change on click
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				if (backgroundStars.isEmpty()) {
					initBackgroundStars();
				}
				removeComponentListener(this);
			}
		});
	}

	//function to init background stars
	private void initBackgroundStars() {
		//generate 200 random small stars for depth
		for (int i = 0; i < 200; i++) {
			int x = (int)(Math.random() * getWidth());
			int y = (int)(Math.random() * getHeight());
			int size = (int)(Math.random() * 2) + 1;
			backgroundStars.add(new int[]{x, y, size});
		}
	}

	//function to set up mouse listeners
	private void setupMouseListeners() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//find if any object was clicked
				CelestialObject clickedObject = skyMap.findNearestObject(
					e.getX(),
					e.getY(),
					getWidth(),
					getHeight(),
					15); //15 pixel threshold

				if (clickedObject != null) {
					skyMap.setSelectedObject(clickedObject);
					infoPanel.updateObjectInfo(clickedObject);
					repaint();
				}
			}
		});
	}

	@Override
	//function to paint things on the screen
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		//drawing the background stars as small random dots for effect
		drawBackgroundStars(g);

		//draw celestial objects
		for (CelestialObject object : skyMap.getAllObjects()) {
			int[] coords = object.getScreenCoordinates(getWidth(), getHeight());
			boolean isSelected = object == skyMap.getSelectedObject();
			object.draw(g, coords[0], coords[1], isSelected);

			//draw the object's name if it's selected
			if (isSelected) {
				g.setColor(Color.WHITE);
				g.drawString(object.getName(), coords[0] + 10, coords[1]);
			}
		}

		drawCoordinateGrid(g);
	}

	//function to draw the lil background stars for the first timeif not yet drawn or retrieve if drawn already
	private void drawBackgroundStars(Graphics g) {
		g.setColor(new Color(180, 180, 180));

		//if not drawn yet, draw them
		if(backgroundStars.isEmpty()) {
			initBackgroundStars();
		}

		//if already drawn, just retrieve on repaint
		for (int[] star : backgroundStars) {
			g.fillRect(star[0], star[1], star[2], star[2]);
		}
	}

	//function to draw coordinate grid
	private void drawCoordinateGrid(Graphics g) {
		//LINE DRAWING FIRST
		g.setColor(new Color(50, 50, 50)); //dark grey
		//g.setColor(new Color(150, 150, 150)); //lighter grey
		
		//Vertical Right Ascension Lines
		//24 hrs where 1 hr is 15 degrees of celestial sphere
		//bc earth rotates 15 degrees per hour
		for (int ra = 0; ra <= 24; ra += 2) {
			int x = getWidth() * ra / 24;
			g.drawLine(x, 0, x, getHeight());
		}

		//Horizontal Declination Lines
		//like latitude
		//-90 to 90
		//-90 is south celestial pole
		//0 celestial equator
		//90 is north celestial pole
		for (int dec = -90; dec <= 90; dec += 30) {
			int y = getHeight() * (90 - dec) / 180;
			g.drawLine(0, y, getWidth(), y);
		}

		//DRAW TEXT LABELS
		g.setColor(new Color(230, 230, 230)); //almost white
		
		//RA text labels
		for (int ra = 0; ra <= 24; ra += 2) {
			if (ra != 0) {
				int x = getWidth() * ra / 24;
				g.drawString("  " + ra + "h", x, 15);
			}
		}

		//Declination text labels
		for (int dec = -90; dec <= 90; dec += 30) {
			int y = getHeight() * (90 - dec) / 180;
			//ty https://www.degreesymbol.net/
			g.drawString(dec + "°", 5, y + 15); // plus 15 pixel offset to give text a lil padding
		}
	}
}

					
