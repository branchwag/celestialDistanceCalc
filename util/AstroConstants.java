package com.celestialdistance.util;

//utility class for astro constants and conversions
public class AstroConstants { 
	public static final double SPEED_OF_LIGHT = 299792.458; //km per sec
	public static final double PARSEC_TO_LIGHT_YEAR = 3.26156; //one parsec is 3.26 light years
	public static final double HUBBLE_CONSTANT = 70.0; //rate at which universe is expanding, ~70km/s per megaparsec
	//for every megaparsec (~3.26 million light years of distance from Earth), galaxies are moving away at 70 kilometers per second
	
	//function to convert parsecs to light years
	public static double parsecsToLightYears(double parsecs) {
		//parsec times 3.26156 to get light years
		return parsecs * PARSEC_TO_LIGHT_YEAR;
	}

	//function to convert light years to parsecs
	public static double lightYearsToParsecs(double lightYears) {
		//light years divided by 3.26156
		return lightYears / PARSEC_TO_LIGHT_YEAR;
	}

	//function to convert hours, minutes, seconds to decimal hours
	public static double hmsToDecimalHours(int hours, int minutes, double seconds) {
		return hours + minutes/60.0 + seconds/3600.0;
	}

	//function to convert degrees, arcminutes, and arcseconds to decimal degrees
	public static double dmsToDecimalDegrees(int degrees, int arcminutes, double arcseconds) {
		double result = Math.abs(degrees) + arcminutes/60.0 + arcseconds/3600.0;
		return degrees < 0 ? -result : result;
	}
}
