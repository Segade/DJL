/**
 * Class that processes the values of the object and generates the description of the object
 * Author: Ivan Segade Carou
 */
// package which the class belongs to
package ai.djl.examples;

public class ObjectFunctionality {
    // declaration of the values to calculate the section of the image
// the 15% of the value for objects with size less than that percentage
    static final double SMALLSIZE = 0.15;
    // the 40% after the 15% for objects that ocupate that space in the image
    static final double MEDIUMSIZE = 0.40;
    // one third of the size of the image to localise the object in the image
    static final double ONETHIRD = 0.33;
    // two thirds of the size iof the image to localise the object in the image
    static final double TWOTHIRDS = 0.66;

    /**
     * method that calls the rest of the submethods that process the values
     * It recieves the image values and the object to be processed
     * it returns the whole description already processed
     *
     * @param imageHeight
     * @param imageWidth
     * @param od
     * @return
     */

    public static String generateDescription(int imageHeight, int imageWidth, ObjectDetected od) {
// declaration of the variables to work with the data
        String result;
        //variables that retrieve the data from the object
        int objectHeight = od.getHeight();
        int objectWidth = od.getWidth();
        int objectX = od.getPositionX();
        int objectY = od.getPositionY();
// the result variable stores the value for each parameter
        result = "\n" + calculateHeightSize(imageHeight, objectHeight);
        result += "\n" + calculateWidthSize(imageWidth, objectWidth);
        result += "\n" + calculateHorizontalPosition(imageWidth, objectX);
        result += "\n" + calculateVerticalPosition(imageHeight, objectY);

        return result;
    } // end generate description


    /**
     * method that calculates the height of the object in the image
     * It recieves the height of the image and the object
     * it returns the string with the description
     * it is private because non external classes can access it
     *
     * @param imageHeight
     * @param objectHeight
     * @return
     */

    private static String calculateHeightSize(int imageHeight, int objectHeight) {
// the pixel value  is less than this variable the object is small
        int small = (int) (imageHeight * SMALLSIZE);
        //the pixel value for the calculation of the medium size range
        int medium = (int) (imageHeight * MEDIUMSIZE);
        String result = "";

// if statement that calculates small, medium and large size
        if (objectHeight <= small)
            result = "it has small height size";
        else if (objectHeight <= medium)
            result = "it has a medium height size";
        else
            result = "it has a large height size";

        // it returns the description to the generate description method
        return result;
    } // end calculate height size

    /**
     * method that calculates the width of the object in the image
     * * It recieves the width of the image and the object
     * * it returns the string with the description
     * * it is private because non external classes can access it
     * * @param imageWidth
     *
     * @param objectWidth
     * @return
     */

    private static String calculateWidthSize(int imageWidth, int objectWidth) {
        // the pixel value  is less than this variable the object is small
        int small = (int) (imageWidth * SMALLSIZE);
        //the pixel value for the calculation of the medium size range
        int medium = (int) (imageWidth * MEDIUMSIZE);
        String result = "";

        // if statement that calculates small, medium and large size
        if (objectWidth <= small)
            result = "it has a small width size";
        else if (objectWidth <= medium)
            result = "it has a medium width size";
        else
            result = "it has a large width size";

        // it returns the description to the generate description method
        return result;
    } // end calculate width


    /**
     * method that calculates the vertical position of the object in the image
     * It receives the X coordinate of the object and the height of the image
     * it returns the string with the description
     * it is private because non external classes can access it
     *
     * @param imageY
     * @param objectY
     * @return
     */

    private static String calculateVerticalPosition(int imageY, int objectY) {
        // the pixel value is less than the variable the object is on the top
        int top = (int) (imageY * ONETHIRD);
        // the pixel value for the calculation of the middle position
        int bottom = (int) (imageY * TWOTHIRDS);
        String result = "";

        // if statement that calculates the top, middle or bottom position
        if (objectY < top)
            result = "it is at the top";
        else if (objectY <= bottom)
            result = "it is in the middle";
        else
            result = "it is at the bottom";

        // it returns the description to the generate description method
        return result;
    } // end calculate vertical position


    /**
     * method that calculates the horizontal position of the object in the image
     * it receives the Y coordinates of the object and the width of the image
     * it returns the string with the description
     * it is private because non external classes can access it
     *
     * @param imageX
     * @param objectX
     * @return
     */

    private static String calculateHorizontalPosition(int imageX, int objectX) {
// the pixel valueis less than the variable the object is on the left
        int left = (int) (imageX * ONETHIRD);
        //the pixel value for the calculation of the center position
        int right = (int) (imageX * TWOTHIRDS);
        String result = "";

        // if statement that calculates the left, center or right position
        if (objectX < left)
            result = "it is on the left";
        else if (objectX <= right)
            result = "it is in the center";
        else
            result = "it is on the right";

        // it returns the description to the generate description method
        return result;
    } // end calculate horizontal position


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


    public static int calculatePercentageOfArea(int boxArea, int overlapArea) {
        return (overlapArea * 100) / boxArea;
    } // end calculate percentage of area


} // end class
