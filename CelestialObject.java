
//ABSTRACT BASE CLASS FOR ALL CELESTIAL OBJECTS
import java.awt.Color;
import java.awt.Graphics;

//ABSTRACT BASE CLASS FOR ALL CELESTIAL OBJECTS
public abstract class CelestialObject {
	// instance vars
	private String name;
	private double rightAscension; // in hours (0 to 24)
	private double declination; // in degrees (-90 to 90)
	private double apparentMagnitude; // how bright object appears from Earth. Lower values are brighter objects
						// here (Sun -26.74 while Sirius is -1.46)
	private Color color;

	// Constructor (with params)
	public CelestialObject(String name, double rightAscension, double declination, double apparentMagnitude,
			Color color) {
		this.name = name;
		this.rightAscension = rightAscension;
		this.declination = declination;
		this.apparentMagnitude = apparentMagnitude;
		this.color = color;
	}

	// GETTERS
	public String getName() {
		return name;
	}

	public double getRightAscension() {
		return rightAscension;
	}

	public double getDeclination() {
		return declination;
	}

	public double getApparentMagnitude() {
		return apparentMagnitude;
	}

	public Color getColor() {
		return color;
	}

	// ABSTRACT function to calc distance based on object type
	// this is going to provide distance in light years
	public abstract double calcDistance();

	// ABSTRACT function to get info about the object
	public abstract String getInfo();

	// DRAW OBJECT
	public void draw(Graphics g, int x, int y, boolean selected) {
		// calc size based on apparent magnitude (i.e brighter = larger)
		int size = Math.max(2, 10 - (int) (apparentMagnitude));

		// draw object using graphics g
		g.setColor(color);
		g.fillOval(x - size / 2, y - size / 2, size, size);

		// if selected, draw a lil highlight
		if (selected) {
			g.setColor(Color.WHITE);
			g.drawOval(x - size / 2 - 2, y - size / 2 - 2, size + 4, size + 4);
		}
	}

	// Convert equatorial coordinates to screen coordinates
	// takes in panel width and height
	// returns x, y screen coordinates as array of ints
	public int[] getScreenCoordinates(int width, int height) {
		// converting Right A (0 to 24 h) to x coordinate
		int x = (int) (width * rightAscension / 24.0);
		// convert Dec (-90 to 90) to y coordinate
		int y = (int) (height * (90 - declination) / 180.0);

		return new int[] { x, y };
	}
}
