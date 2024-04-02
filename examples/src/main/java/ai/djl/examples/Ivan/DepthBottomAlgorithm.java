package ai.djl.examples.Ivan;

import java.util.List;

public class DepthBottomAlgorithm {

public static String calculateDepth(List<ObjectDetected> listOfObjects){
    String depth = "";

//Loop for the reference object
    for (int x = 0; x < listOfObjects.size() - 1; x++) {
        // loop that compares the reference object to the rest of objects
        for (int y = x + 1; y < listOfObjects.size(); y++) {
            int result = calculateOverlapArea(listOfObjects.get(x), listOfObjects.get(y));

            if (result > 0)
depth += calculateBottom(listOfObjects.get(x), listOfObjects.get(y));

        } // end for y
    } // end for y

    if (depth.equals("") )
depth = "No information is available";

            return depth;
        } // end of calculate depth by bottom

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


    private static String calculateBottom(ObjectDetected od, ObjectDetected od1){

    double ONETHIRD = 0.1;

    if (od.getPositionY() > od1.getPositionY()+ (od1.getHeight() * ONETHIRD) )
        return "\nThe " + od.getName() + " is in front of the " + od1.getName();

    if (od1.getPositionY() > od.getPositionY() + (od.getHeight() *ONETHIRD) )
    return "\nThe " + od1.getName() + " is in front of the " + od.getName();

    return "";
    } // end calculate depth


} // end class
