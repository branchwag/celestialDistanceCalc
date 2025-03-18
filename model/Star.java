package com.celestialdistance.model;

import java.awt.Color;

//class for stars
public class Star extends CelestialObject {
	//instance vars
	private double parallax; //in arcseconds
	private double absoluteMagnitude; //true brightness, M = m - 5log10(d) + 5 where m is how bright it looks from earth and d is distance. Ex. Sun is +4.83 absolute mag with -26.74 apparent mag
	private String spectralType; //classification based on Morgan-Keenan (MK) system - Letter, Number, Luminosity
	//SOME NOTES ON SPECTRAL TYPE:
	//Letter (O, B, A, F, G, K, M) - represents star temp and color
	//	O is hottest and bluest
	//	M is coolest and reddest
	//Ex. Sun is G-type with mod surface temp, yellow
	//Number (0-9, within each letter class)
	//Ex. G2 means Sun is hotter than G5 star but cooler than G0 star
	//Luminosity Class (I, II, III, IV, V, etc.)
	//I = Supergiant, II = Bright Giant, III = Giant, IV = Subgiant, V = Main-sequence star
	//Ex. Sun is G2V - dwarf star in G2 category
	
	//CONSTRUCTOR
	public Star(String name, double rightAscension, double declination, double apparentMagnitude, double parallax, double absoluteMagnitude, String spectralType) {
		super(name, rightAscension, declination, apparentMagnitude, getColorForSpectralType(spectralType));
		this.parallax = parallax;
		this.absoluteMagnitude = absoluteMagnitude;
		this.spectralType = spectralType;
	}

	//GETTERS
	public double getParallax() { return parallax; }
	public double getAbsoluteMagnitude() { return absoluteMagnitude; }
	public String getSpectralType() { return spectralType; }

	//function to calc distance in light years using parallax
	public double calcParallaxDistance() {
		if (parallax <= 0) {
			return Double.NaN; //cannot calc if less than 0
		}
	
		double distanceInParsecs = 1.0 / parallax;

		//convert parsecs to light years (1 parsec is 3.26 light years)
		return distanceInParsecs * 3.26;
	}

	//function to calc distance in light years using spectroscopic parallax method - i.e. compare absolute mag to apparent mag
	public double calcSpecDistance() {
		//forumla is 5 * log10(d) = m - M + 5
		//d is distance in parsecs
		//m is apparent mag
		//M is absolute Mag
		double distanceInParsecs = Math.pow(10, (getApparentMagnitude() - absoluteMagnitude + 5) / 5);
		
		//convert parsecs to light years
		return distanceinParsecs * 3.26;
	}

	@Override
	public double calcDistance() {
		//using parallax method as primary
		//otherwise go with spec method
		if (parallax > 0) {
			return calcParallaxDistance();
		} else { 
			return calcSpecDistance();
		}
	}

	@Override
	//function too return info about the star
	//makes a string with all of the info in it using StringBuilder
	public String getInfo() {
		StringBuilder info = new StringBuilder();
		
		info.append("Star: ").append(getName()).append("\n");
		info.append("Spectral Type: ").append(spectralType).append("\n");
		info.append("Right Ascension: ").append(String.format("%.2f", getRightAscension())).append("h\n");
		info.append("Declination: ").append(String.format("%.2f", getDeclination())).append("°\n");
		info.append("Apparent Magnitude: ").append(String.format("%.2f", getApparentMagnitude())).append("\n");
		info.append("Absolute Magnitude: ").append(String.format("%.2f", absoluteMagnitude)).append("\n");
			
		if (parallax > 0) {
			info.append("Parallax: ").append(String.format("%.4f", parallax)).append(" arcsec\n");
		
			info.append("Distance (parallax method): ").append(String.format("%.2f", calculateParallaxDistance())).append(" light years\n");
		}
			
		info.append("Distance (spectroscopic method): ").append(String.format("%.2f", calculateSpectroscopicDistance())).append(" light years");
			
		return info.toString();
	}

	//function to determine color for spectral type
	private static Color getColorForSpectralType(String spectralType) {
		char type = spectralType.charAt(0);
		switch (type) {
		case 'O': return new Color(155, 176, 255); // Blue
		case 'B': return new Color(170, 191, 255); // Blue-white
		case 'A': return new Color(202, 215, 255); // White
		case 'F': return new Color(248, 247, 255); // Yellow-white
		case 'G': return new Color(255, 244, 234); // Yellow
		case 'K': return new Color(255, 210, 161); // Orange
		case 'M': return new Color(255, 160, 142); // Red
		default: return Color.WHITE;
		}
	}
}



