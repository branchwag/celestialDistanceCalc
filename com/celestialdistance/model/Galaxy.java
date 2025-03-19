package com.celestialdistance.model;
import java.awt.Graphics;
import java.awt.Color;

//class for galaxy
//distance calc based on red-shift
public class Galaxy extends CelestialObject {
	//instance vars
	private double redshift; 
	private String galaxyType; //spiral, elliptical, etc.
	private static final double HUBBLE_CONSTANT = 70; //how far away it's moving is 70km/s/mpc bc universe is expanding
	private static final double SPEED_OF_LIGHT = 299792.458; //km per second
	
	//CONSTRUCTOR
	public Galaxy(String name, double rightAscension, double declination, double apparentMagnitude, double redshift, String galaxyType) {
		super(name, rightAscension, declination, apparentMagnitude, galaxyType.equalsIgnoreCase("Spiral") ? new Color (220, 220, 255) : new Color(255, 220, 220));
		this.redshift = redshift;
		this.galaxyType = galaxyType;
	}

	//GETTERS
	public double getRedshift() { return redshift; }
	public String getGalaxyType() { return galaxyType; }

	//function to calc distance based on Hubble's law w/redshift
	//returns distance in millions of light years
	public double calcRedshiftDistance() {
		if (redshift <= 0) {
			return Double.NaN; //can't use this method if redshift is less than 0
		}

		//for small redshifts: v = c * z;
		double velocity = SPEED_OF_LIGHT * redshift; //km/s

		//d = v / Ho
		double distanceInMegaparsecs = velocity / HUBBLE_CONSTANT;

		//convert megaparsecs to millions of light years
		//1 mpc is about 3.26 million light years
		return distanceInMegaparsecs * 3.26;
	}

	@Override
	public double calcDistance() {
		return calcRedshiftDistance();
	}

	@Override
	//function to return info on galaxies
	//returns String that is make using StringBuilder
	public String getInfo() {
		StringBuilder info = new StringBuilder();
		info.append("Galaxy: ").append(getName()).append("\n");
		info.append("Type: ").append(galaxyType).append("\n");
		info.append("Right Ascension: ").append(String.format("%.2f", getRightAscension())).append("h\n");
		info.append("Declination: ").append(String.format("%.2f", getDeclination())).append("°\n");
		info.append("Apparent Magnitude: ").append(String.format("%.2f", getApparentMagnitude())).append("\n");
		info.append("Redshift (z): ").append(String.format("%.4f", redshift)).append("\n");
		info.append("Distance: ").append(String.format("%.2f", calcRedshiftDistance())).append(" million light years");
        
		return info.toString();
	}
    
	@Override
	//function to DRAW the Galaxy
	public void draw(Graphics g, int x, int y, boolean selected) {
		int size = 8; // Base size
        
		g.setColor(getColor());
        
		if (galaxyType.equalsIgnoreCase("Spiral")) {
		// Draw as a spiral
		g.fillOval(x - size/2, y - size/2, size, size);
		g.drawOval(x - size, y - size, size*2, size*2);
		} else if (galaxyType.equalsIgnoreCase("Elliptical")) {
		// Draw as an ellipse
		g.fillOval(x - size/2, y - size/3, size, size/2);
		} else {
		// Draw as a generic blob
		g.fillOval(x - size/2, y - size/2, size, size);
        }
        
        // If selected by user, draw a lil highlight
        if (selected) {
            g.setColor(Color.WHITE);
            g.drawOval(x - size/2 - 2, y - size/2 - 2, size + 4, size + 4);
        }
    }
}	

