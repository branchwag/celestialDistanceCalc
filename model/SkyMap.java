package com.celestialdistance.model;

import java.util.ArrayList;
import java.util.List;

//class to manage collection of celestial objects
public class SkyMap {
	private List<CelestialObject> celestialObjects;
	private CelestialObject selectedObject;

	//CONSTRUCTOR
	public SkyMap() {
		celestialObjects = new ArrayList<>();
		initCelestialObjects();
	}

	//function to init the skymap with celestial objects
	private void initCelestialObjects() {
		//add a selection of objects
		//source: https://simbad.u-strasbg.fr/simbad/

		//STARS
		//Format:
		//Name, RA (Right Ascension, hours), Declination (degrees), apparent magnitude, parallax (arcsec), absolute magnitude, spectral type
		//https://simbad.u-strasbg.fr/simbad/sim-basic?Ident=Alpha+Centauri&submit=SIMBAD+search
		celestialObjects.add(new Star("Alpha Centauri A", 14.66, -60.83, 0.01, 0.75, 4.38, "G2V"));
        
		celestialObjects.add(new Star("Proxima Centauri", 14.50, -62.68, 11.09, 0.76, 15.49, "M5.5Ve"));
        
		celestialObjects.add(new Star("Barnard's Star", 17.96, 4.56, 9.53, 0.55, 13.22, "M4V"));
        
		celestialObjects.add(new Star("Sirius", 6.75, -16.72, -1.46, 0.38, 1.42, "A1V"));
        
		celestialObjects.add(new Star("Betelgeuse", 5.92, 7.41, 0.42, 0.005, -5.85, "M1-2Ia-ab"));
        
		celestialObjects.add(new Star("Vega", 18.62, 38.78, 0.03, 0.13, 0.58, "A0V"));

		//GALAXIES
		//Format:
		//Name, RA (Right Ascension, hours), Declination (degrees), apparent magnitude, redshift, galaxy type
		celestialObjects.add(new Galaxy("Andromeda Galaxy", 0.71, 41.27, 3.44, 0.001, "Spiral"));
        
		celestialObjects.add(new Galaxy("Triangulum Galaxy", 1.56, 30.66, 5.72, 0.000543, "Spiral"));
        
		celestialObjects.add(new Galaxy("Whirlpool Galaxy", 13.50, 47.20, 8.4, 0.001544, "Spiral"));
        
		celestialObjects.add(new Galaxy("Sombrero Galaxy", 12.67, -11.62, 8.98, 0.003416, "Spiral"));

		celestialObjects.add(new Galaxy("NGC 1275", 3.33, 41.51, 12.64, 0.017559, "Elliptical"));

		celestialObjects.add(new Galaxy("3C 273", 12.49, 2.05, 12.85, 0.158339, "Elliptical"));
	}

	//function to find the nearest celestial object to the given screen coordinates
	//Params:
	//x-coordinate on screen
	//y-coordinate on screen
	//panel width
	//panel height
	//the max distance threshold to consider this a hit
	public CelestialObject findNearestObject(int x, int y, int width, int height, int threshold) {
		CelestialObject nearest = null;
		double minDistance = Double.MAX_VALUE;

		//for each object in celestial objects, check distance
		for (CelestialObject object : celestialObjects) {
			int[] coords = object.getScreenCoordinates(width, height);
			//Euclidean distance between two points in a 2D plane 
			//point 1 (x, y)
			//point 2 (coords[0], coords[1]
			//d = sqrt[(x2 - x1)^2 + (y2 - y1)^2]
			//ex: (6, 8) as p1 and (3, 4) as p2
			//	d = sqrt[(3 - 6)^2 + (4 - 8)^2]
			//	d = sqrt[ 9 + 16]
			//	d = 5
			double distance = Math.sqrt(Math.pow(coords[0] - x, 2) + Math.pow(coords[1] - y, 2));

			if(distance < threshold && distance < minDistance) {
				nearest = object;
				minDistance = distance;
			}

		}

		return nearest;
	}

	//function to SET the selected object
	public void setSelectedObject(CelestialObject object) {
		this.selectedObject = object;
	}

	//function to GET the currently selected object
	public CelestialObject getSelectedObject() {
		return selectedObject;
	}

	//function to GET ALL celestial objects
	public List<CelestialObject> getAllObjects() {
		return celestialObjects;
	}
}

