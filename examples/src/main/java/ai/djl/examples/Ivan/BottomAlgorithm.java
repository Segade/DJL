package ai.djl.examples.Ivan;

/**
 *Class that holds all the methods related to the bottom algorithm to determinen which object is at the bottom
 */

// Importing the necessary libraries
import java.util.List;

public class BottomAlgorithm implements AlgorithmInterface{

    /**
     * Method that iterates the list of objects and calculates if one object may be in front of other object
     * It receives the list of objects as a List
     * It returns a String with the result
     * It is public to allow the access outside the class
     *
     * @param listOfObjects
     * @return
     */

    public static String calculateDepth(List<ObjectDetected> listOfObjects) {
        String depth = "";

//Loop for the reference object
        for (int x = 0; x < listOfObjects.size() - 1; x++) {
            // loop that compares the reference object to the rest of objects
            for (int y = x + 1; y < listOfObjects.size(); y++) {
                int result = calculateOverlapArea(listOfObjects.get(x), listOfObjects.get(y));
// if statement if there is overlapping
                if (result > 0)
                    depth += calculateBottom(listOfObjects.get(x), listOfObjects.get(y));

            } // end for y
        } // end for y

        // No resultswere generated
        if (depth.equals(""))
            depth = "No information is available";

        return depth;
    } // end of calculate depth by bottom


    /**
     * Method that calculates the common area between two objects using the pixel values
     * It receives two parameters. The two objects to check if there is overlapping between them
     * It returns the numeric value of the common area
     *It is private to avoid accessing outside the class
     * @param box1
     * @param box2
     * @return
     */

    private static int calculateOverlapArea(ObjectDetected box1, ObjectDetected box2) {
        // Calculate the coordinates of the overlapping region
        int overlapX = Math.max(box1.getPositionX(), box2.getPositionX());
        int overlapY = Math.max(box1.getPositionY(), box2.getPositionY());

        // Calculate the dimensions of the overlapping region
        int overlapWidth = Math.min(box1.getPositionX() + box1.getWidth(), box2.getPositionX() + box2.getWidth()) - overlapX;
        int overlapHeight = Math.min(box1.getPositionY() + box1.getHeight(), box2.getPositionY() + box2.getHeight()) - overlapY;

        // Check for non-overlapping rectangles
        if (overlapWidth <= 0 || overlapHeight <= 0) {
            return 0;  // No overlap
        }

        // Calculate overlap area
        return overlapWidth * overlapHeight;
    } // end calculate overlap area

    /**
     * Method that calculates  which object has the location at the bottom of the other object
     * the object at the bottom has to be at least from the 10% of the height of the other object
     * It receives the two object to compare their locations
     * It returns a String variable with the result of the calculation
     * It is private to avoid accessing outside the class
     *
     * @param od
     * @param od1
     * @return
     */

    private static String calculateBottom(ObjectDetected od, ObjectDetected od1) {
//declaring the variable with the percentage of the height which is 10%
        double LEVELBOTTOM = 0.1;

        // if statements that compare the object locations and generate the result
        if (od.getPositionY() > od1.getPositionY() + (od1.getHeight() * LEVELBOTTOM))
            return "\nThe " + od.getName() + " is in front of the " + od1.getName();

        // if no object fulfill the condition it returns an empty String
        if (od1.getPositionY() > od.getPositionY() + (od.getHeight() * LEVELBOTTOM))
            return "\nThe " + od1.getName() + " is in front of the " + od.getName();

        return "";
    } // end calculate depth


} // end class
