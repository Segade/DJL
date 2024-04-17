package ai.djl.examples.Ivan;

/**
 * Class that holds all the methods related to the overlapping calculations
 */

// importing the necessary libraires

import javax.swing.*;
import java.util.List;

public class OverlappingAlgorithm implements AlgorithmInterface{

    /**
     * This method iterates the lis of objects and displays the percentage of the common overlapping area between two objects
     * It receives the list of objects as a List
     * It returns a String with the final result
     * It is public to allow the access outside the class
     *
     * @param listOfObjects
     * @return
     */

    public static String getOverlap(List<ObjectDetected> listOfObjects) {
        String overlap = "";

        //Loop for the reference object
        for (int x = 0; x < listOfObjects.size() - 1; x++) {
            // loop that compares the reference object to the rest of objects
            for (int y = x + 1; y < listOfObjects.size(); y++) {
                // It stores the value of the common area
                int result = calculateOverlapArea(listOfObjects.get(x), listOfObjects.get(y));

                // if the area is bigger than zero means there is common area
                if (result >= 1) {
                    // It displays the percentage of the reference object  covered by the new one
                    overlap += "\nThe " + listOfObjects.get(x).getName() + " is overlapping by " +
                            listOfObjects.get(y).getName() + calculatePercentageOfArea((listOfObjects.get(x).getWidth() * listOfObjects.get(x).getHeight()), result)
                            + "%";

                    // It displays the percentage of the new object covered by the reference object
                    overlap += "\nThe " + listOfObjects.get(y).getName() + " is overlapping by " +
                            listOfObjects.get(x).getName() + calculatePercentageOfArea((listOfObjects.get(y).getWidth() * listOfObjects.get(y).getHeight()), result)
                            + "%";

                } // end if
            } // end for y

        } // end for x

        if (overlap.equals(""))
        overlap = "No information is available";

        return overlap;
    } // end get overlap


    /**
     * Method that calculates the common area between two objects using the pixel values
     * It receives two parameters. The two objects to check if there is overlapping between them
     * It returns the numeric value of the common area
     *It is public to allow the access outside the class
     * @param box1
     * @param box2
     * @return
     */

    public static int calculateOverlapArea(ObjectDetected box1, ObjectDetected box2) {
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
     * Method that calculate the percentage of the object area that is covered vy the common area with the other object
     * It receives the object area value and the common area value
     * It returns the numeric value of the object area that is overlapped
     *
     * @param boxArea
     * @param overlapArea
     * @return
     */

    private static int calculatePercentageOfArea(int boxArea, int overlapArea) {
        return (overlapArea * 100) / boxArea;
    } // end calculate percentage of area

    /**
     * Method that determinens if one object may be in front of the other and the likelyhood
     * It receives the names of the oobjects and the percentage of area
     * It returns a String with the result     * @param name1
     * It is private to avoid accessing outside the class
     *
     * @param name2
     * @param percentage
     * @return
     */

    private static String calculateLikelyhood(String name1, String name2, int percentage) {
        if (percentage == 100)
            return "\nThe " + name1 + " is in 100% in front of the " + name2;
        else if (percentage >= 90)
            return "\nit is more than likely that the " + name1 + " is in front of the " + name2;

        else if (percentage > 75)

            return "\nIt is likely that the " + name1 + " is in front of the " + name2;

        return "";
    } // end calculate likelyhood

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

        for (int x = 0; x < listOfObjects.size() - 1; x++) {
            for (int y = x + 1; y < listOfObjects.size(); y++) {
                // It stores the value of the common area
                int result = calculateOverlapArea(listOfObjects.get(x), listOfObjects.get(y));

                int percentage = calculatePercentageOfArea((listOfObjects.get(x).getWidth() * listOfObjects.get(x).getHeight()), result);

                depth += calculateLikelyhood(listOfObjects.get(x).getName(), listOfObjects.get(y).getName(), percentage);


                     percentage = calculatePercentageOfArea((listOfObjects.get(y).getWidth() * listOfObjects.get(y).getHeight()), result);
                     depth += calculateLikelyhood(listOfObjects.get(y).getName(), listOfObjects.get(x).getName(), percentage);


            }// end for y
        } // end for x

        if (depth.equals(""))
            depth = "No information is available";

        return depth;
    } // end calculate depth

} // end class
