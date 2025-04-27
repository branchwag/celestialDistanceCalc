//CelestialDistanceCalculator.java
/**
 * The CelestialDistanceCalculator serves as the launching point for the entire application, only initing an instance of the Main Frame and setting * it to visible. From there, the MainFrame file takes care of the next portion of work. 
 * @author LeAnne Branch
 * @version Last modified 27_April_2025
 */
//main class to launch app
public class CelestialDistanceCalculator {
	// main method
	public static void main(String[] args) {

		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}
}
