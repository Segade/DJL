package ai.djl.examples.Ivan;


import java.util.List;

public class OverlappingAlgorithm {

    public static String getOverlap(List<ObjectDetected> listOfObjects){
        String overlap = "";


        //Loop for the reference object
        for (int x = 0; x < listOfObjects.size() - 1; x++) {
            // loop that compares the reference object to the rest of objects
            for (int y = x + 1; y < listOfObjects.size(); y++) {
                // It stores the value of the common area
                int result = calculateOverlapArea(listOfObjects.get(x), listOfObjects.get(y));

                // if the area is bigger than zero means there is common area
                if (result > 0) {
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

        return overlap;
    } // end get overlap


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



    private static int calculatePercentageOfArea(int boxArea, int overlapArea) {
        return (overlapArea * 100) / boxArea;
    } // end calculate percentage of area

} // end class
