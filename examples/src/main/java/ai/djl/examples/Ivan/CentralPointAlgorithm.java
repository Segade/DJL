package ai.djl.examples.Ivan;
/**
 * Class that contains the methods to calculate if one object is in front of another object through the central points of the bounding boxes
 */
// importing the necessary libraries

import java.util.List;


public class CentralPointAlgorithm implements AlgorithmInterface{
    /**
     * Method that calculates the distance between the central point of one object and the bounding box of the other object
     * It receives an int array with the X and Y corrdinates of the central point, and the ObjectDetected object that contains the data of the bounding box
     * It returns the distance  between the central point and the bounding box.
     * If the central point is inside, the method returns 0
     * It is private to avoid accessing outside the class
     *
     * @param centralPoints
     * @param od
     * @return
     */

    private static double calculateDistance(int[] centralPoints, ObjectDetected od) {
        // Declaring the variables for the calculation
        int pointX = centralPoints[0];
        int pointY = centralPoints[1];
        int boxX = od.getPositionX();
        int boxY = od.getPositionY();
        int width = od.getWidth();
        int height = od.getHeight();

        // If the point is inside the bounding box, return 0
        if (pointX > boxX && pointX < boxX + width && pointY > boxY && pointY < boxY + height)
            return 0;


        // Calculating the distance from the X and Y coordinates to the boudning box
        int closestX = Math.max(boxX, Math.min(pointX, boxX + width));
        int closestY = Math.max(boxY, Math.min(pointY, boxY + height));
        double deltaX = pointX - closestX;
        double deltaY = pointY - closestY;

        // It returns the distance
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);

    }// end calculate distance

    /**
     * Method that iterates the list of the objects and compares one object with the rest of the objects individually
     * It receives the list of objects as a List
     * It returns the result of the iteration
     * It is public to allow the access outside the class
     *
     * @param listOfObjects
     * @return
     */

    public static String calculateDepth(List<ObjectDetected> listOfObjects) {
        // Declaring the String that will contain the final result of the iteration
        String result = "";
// for loops that iterate the list of objects for the two objects to be compared
        for (int x = 0; x < listOfObjects.size() - 1; x++) {
            for (int y = x + 1; y < listOfObjects.size(); y++) {
                // It calculates the distances for the two objects
                int distance1 = (int) calculateDistance(listOfObjects.get(x).getCentralPoint(), listOfObjects.get(y));
                int distance2 = (int) calculateDistance(listOfObjects.get(y).getCentralPoint(), listOfObjects.get(x));
// if the distance of the first object is inside, but the second is not
                if (distance1 == 0 && distance2 != 0)
                    result += "\nThe " + listOfObjects.get(x).getName() + " is in front of the " + listOfObjects.get(y).getName();

                // if the distance of the second object is inside, but the first is not
                if (distance2 == 0 && distance1 != 0)
                    result += "\nThe " + listOfObjects.get(y).getName() + " is in front of the " + listOfObjects.get(x).getName();

            } // end for y
        } //end for x

        // If no detection is found a message is returned
        if (result.equals(""))
            result = "No information is available";

        // It returns the result of the overall calculation
        return result;
    } // end calculate depth

} // end class
