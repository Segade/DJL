package ai.djl.examples.Ivan;

import java.util.List;


public class CentralPointsAlgorithm {

    private static double calculateDistance(int[] centralPoints, ObjectDetected od){
        int pointX = centralPoints[0];
int pointY = centralPoints[1];
                int boxX = od.getPositionX();
        int boxY = od.getPositionY();
        int width = od.getWidth();
        int height = od.getHeight();

        // If the point is inside the bounding box, return 0
        if (pointX > boxX && pointX < boxX + width && pointY > boxY && pointY < boxY + height)
            return 0;

        int closestX = Math.max(boxX, Math.min(pointX, boxX + width));
        int closestY = Math.max(boxY, Math.min(pointY, boxY + height));
        double deltaX = pointX - closestX;
        double deltaY = pointY - closestY;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);

    }// end calculate distance

    public static String calculateDepth(List<ObjectDetected> listOfObjects){
        String result = "";

        for (int x = 0; x < listOfObjects.size() - 1; x++) {
            for (int y = x + 1; y < listOfObjects.size(); y++) {
                int distance1 = (int) calculateDistance(listOfObjects.get(x).getCentralPoint(), listOfObjects.get(y));
                int distance2 = (int) calculateDistance(listOfObjects.get(y).getCentralPoint(), listOfObjects.get(x));

                if (distance1 == 0 && distance2 != 0)
result += "\nThe " + listOfObjects.get(x).getName() + " is in front of the " + listOfObjects.get(y).getName();

                if (distance2 == 0 && distance1 != 0)
                    result += "\nThe " + listOfObjects.get(y).getName() + " is in front of the " + listOfObjects.get(x).getName();

            } // end for y
        } //end for x

        if  (result.equals(""))
            result = "No information is available";

                return result;
    } // end calculate depth

} // end class
