/**
 * CelestialDistanceCalculatorTest.java
 * A simple test class for the Celestial Distance Calculator.
 * @author LeAnne Branch
 * @version Last modified 27_April_2025
 */

import java.util.List;

//TEST CLASS
public class CelestialDistanceCalculatorTest {
    //main method
    public static void main(String[] args) {
        System.out.println("Starting Celestial Distance Calculator Tests...");
        
        // Run all the tests
        testCelestialObjectCreation();
        testStarDistanceCalculations();
        testGalaxyDistanceCalculations();
        testCoordinateConversion();
        testSkyMapFunctionality();
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Tests creation of celestial objects.
     */
    private static void testCelestialObjectCreation() {
        System.out.println("\n=== Testing Celestial Object Creation ===");
        
        try {
            // Create a fake test star
            Star star = new Star("Test Star", 12.5, 45.0, 2.5, 0.5, 3.0, "G2V");
            System.out.println("Created star: " + star.getName());
            
            // Check the star properties
            assert star.getName().equals("Test Star") : "Star name mismatch";
            assert star.getRightAscension() == 12.5 : "RA mismatch";
            assert star.getDeclination() == 45.0 : "Declination mismatch";
            assert star.getApparentMagnitude() == 2.5 : "Apparent magnitude mismatch";
            assert star.getParallax() == 0.5 : "Parallax mismatch";
            assert star.getAbsoluteMagnitude() == 3.0 : "Absolute magnitude mismatch";
            assert star.getSpectralType().equals("G2V") : "Spectral type mismatch";
            
            // Create a fake test galaxy
            Galaxy galaxy = new Galaxy("Test Galaxy", 15.0, -30.0, 8.0, 0.05, "Spiral");
            System.out.println("Created galaxy: " + galaxy.getName());
            
            // Check galaxy properties
            assert galaxy.getName().equals("Test Galaxy") : "Galaxy name mismatch";
            assert galaxy.getRightAscension() == 15.0 : "RA mismatch";
            assert galaxy.getDeclination() == -30.0 : "Declination mismatch";
            assert galaxy.getApparentMagnitude() == 8.0 : "Apparent magnitude mismatch";
            assert galaxy.getRedshift() == 0.05 : "Redshift mismatch";
            assert galaxy.getGalaxyType().equals("Spiral") : "Galaxy type mismatch";
            
            System.out.println("Celestial object creation tests passed");
        } catch (AssertionError e) {
            System.out.println("Test failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Tests distance calcs for stars.
     */
    private static void testStarDistanceCalculations() {
        System.out.println("\n=== Testing Star Distance Calculations ===");
        
        try {
            // Test parallax method
            Star star1 = new Star("Parallax Star", 0, 0, 0, 0.5, 0, "G2V");
            double distance1 = star1.calcParallaxDistance();
            double expected1 = 1.0 / 0.5 * 3.26; // 1/parallax * 3.26 light years
            assert Math.abs(distance1 - expected1) < 0.01 : "Parallax distance calculation incorrect";
            System.out.println("Parallax distance for 0.5 arcsec = " + distance1 + " light years");
            
            // Test spectroscopic method
            Star star2 = new Star("Spectroscopic Star", 0, 0, 5.0, 0, 0.0, "G2V");
            double distance2 = star2.calcSpecDistance();
            double expected2 = Math.pow(10, (5.0 - 0.0 + 5) / 5) * 3.26;
            assert Math.abs(distance2 - expected2) < 0.01 : "Spectroscopic distance calculation incorrect";
            System.out.println("Spectroscopic distance for m=5, M=0 = " + distance2 + " light years");
            
            // Test fallback to spectroscopic when parallax not available
            Star star3 = new Star("Fallback Star", 0, 0, 6.0, 0, 1.0, "G2V");
            double distance3 = star3.calcDistance();
            double expected3 = Math.pow(10, (6.0 - 1.0 + 5) / 5) * 3.26;
            assert Math.abs(distance3 - expected3) < 0.01 : "Fallback distance calculation incorrect";
            
            System.out.println("Star distance calculation tests passed");
        } catch (AssertionError e) {
            System.out.println("Test failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Tests distance calculations for galaxies.
     */
    private static void testGalaxyDistanceCalculations() {
        System.out.println("\n=== Testing Galaxy Distance Calculations ===");
        
        try {
            // Test redshift method
            Galaxy galaxy = new Galaxy("Test Galaxy", 0, 0, 0, 0.05, "Spiral");
            double distance = galaxy.calcRedshiftDistance();
            
            // Expected calculation: v = c * z, d = v / H0 * 3.26
            double velocity = 299792.458 * 0.05; // speed of light * redshift
            double distanceInMegaparsecs = velocity / 70.0; // velocity / Hubble constant
            double expected = distanceInMegaparsecs * 3.26; // convert to millions of light years
            
            assert Math.abs(distance - expected) < 0.01 : "Redshift distance calculation incorrect";
            System.out.println("Redshift distance for z=0.05 = " + distance + " million light years");
            
            System.out.println("Galaxy distance calculation tests passed");
        } catch (AssertionError e) {
            System.out.println("Test failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Tests conversion between celestial and screen coordinates.
     */
    private static void testCoordinateConversion() {
        System.out.println("\n=== Testing Coordinate Conversion ===");
        
        try {
            CelestialObject obj = new Star("Test Star", 12.0, 45.0, 0, 0, 0, "G2V");
            
            // Test with a 1000x800 screen
            int width = 1000;
            int height = 800;
            int[] coords = obj.getScreenCoordinates(width, height);
            
            // Expected: x = width * RA / 24, y = height * (90 - Dec) / 180
            int expectedX = (int)(width * 12.0 / 24.0); // 500
            int expectedY = (int)(height * (90 - 45.0) / 180.0); // 200
            
            assert coords.length == 2 : "Coordinate array should have 2 elements";
            assert coords[0] == expectedX : "X coordinate calculation incorrect";
            assert coords[1] == expectedY : "Y coordinate calculation incorrect";
            
            System.out.println("Celestial coordinates (RA=12h, Dec=45°) → Screen coordinates: (" + 
                              coords[0] + ", " + coords[1] + ")");
            System.out.println("Coordinate conversion tests passed");
        } catch (AssertionError e) {
            System.out.println("Test failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Tests SkyMap functionality.
     */
    private static void testSkyMapFunctionality() {
        System.out.println("\n=== Testing SkyMap Functionality ===");
        
        try {
            SkyMap skyMap = new SkyMap();
            
            // Test that the sky map is initialized with celestial objects
            List<CelestialObject> objects = skyMap.getAllObjects();
            assert objects != null : "Sky map objects list is null";
            assert !objects.isEmpty() : "Sky map has no objects";
            System.out.println("Sky map initialized with " + objects.size() + " celestial objects");
            
            // Test finding nearest object functionality
            int screenWidth = 1000;
            int screenHeight = 800;
            
            // Create a click point and test finding nearest object
            // We'll check if the object returned is not null when we click close to an object
            CelestialObject firstObject = objects.get(0);
            int[] firstObjectCoords = firstObject.getScreenCoordinates(screenWidth, screenHeight);
            
            // Click exactly on the object
            CelestialObject found = skyMap.findNearestObject(
                firstObjectCoords[0], 
                firstObjectCoords[1], 
                screenWidth, 
                screenHeight, 
                15 // threshold
            );
            
            assert found != null : "Should find an object when clicking on its position";
            assert found == firstObject : "Found object should be the same as the expected object";
            
            // Click far from any object
            CelestialObject notFound = skyMap.findNearestObject(
                -1000, 
                -1000, 
                screenWidth, 
                screenHeight, 
                15
            );
            
            assert notFound == null : "Should not find an object when clicking far away";
            
            // Test object selection
            skyMap.setSelectedObject(firstObject);
            CelestialObject selected = skyMap.getSelectedObject();
            assert selected == firstObject : "Selected object doesn't match";
            
            System.out.println("SkyMap functionality tests passed");
        } catch (AssertionError e) {
            System.out.println("Test failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
