/**
 * Class that processes the values of the object and generates the description of the object
 * Author: Ivan Segade Carou
 */
// package which the class belongs to
package ai.djl.examples.Ivan;



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
        String result = "\nSize: ";
        //variables that retrieve the data from the object
        int objectHeight = od.getHeight();
        int objectWidth = od.getWidth();
        int objectX = od.getPositionX();
        int objectY = od.getPositionY();
// the result variable stores the value for each parameter
        result += "It has a " + calculateHeightSize(imageHeight, objectHeight);
        result += " and " + calculateWidthSize(imageWidth, objectWidth) + " size";
        result += "\nLocation: it is from " + calculateHorizontalPosition(imageWidth, objectX);
        result += " and " + calculateVerticalPosition(imageHeight, objectY);

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
            result = "small height";
        else if (objectHeight <= medium)
            result = "medium height";
        else
            result = "large height";

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
            result = "small width";
        else if (objectWidth <= medium)
            result = "medium width";
        else
            result = "large width";

        // it returns the description to the generate description method
        return result;
    } // end calculate width


    /**
     * method that calculates the vertical position of the object in the image
     * It receives the Y coordinate of the object and the height of the image
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
            result = "the top";
        else if (objectY <= bottom)
            result = "the middle";
        else
            result = "the bottom";

        // it returns the description to the generate description method
        return result;
    } // end calculate vertical position


    /**
     * method that calculates the horizontal position of the object in the image
     * it receives the X coordinates of the object and the width of the image
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
            result = "the left";
        else if (objectX <= right)
            result = "the center";
        else
            result = "the right";

        // it returns the description to the generate description method
        return result;
    } // end calculate horizontal position









} // end class
