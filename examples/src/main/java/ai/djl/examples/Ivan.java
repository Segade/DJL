package ai.djl.examples;

public class Ivan {

    public static void main(String[] args) {
//        Box box1 = new Box("dog", 168, 761, 350, 624);
//        Box box2 = new Box("Bicycle", 162, 750, 207, 801);

        Box box1 = new Box("dog", 168, 350, 274, 593);
        Box box2 = new Box("bicycle", 162, 207, 594, 588);
        Box box3 = new Box("car", 611, 137, 293, 160);
int result;

result = ObjectFunctionality.calculateOverlapArea(box1, box2);
        if (result > 0 ) {
             System.out.println("dog and bicycle " + result +
                     "\nThe dog is overlapping by " + ObjectFunctionality.calculatePercentageOfArea(box1.width * box1.height, result)+
                     "\nThe bicycle is overlapping by " + ObjectFunctionality.calculatePercentageOfArea(box2.width * box2.height, result));

        } // end if

        result = ObjectFunctionality.calculateOverlapArea(box1, box3);
        if (result > 0 )
            System.out.println("dog and car " + result +
                    "\nThe dog is overlapping by " + ObjectFunctionality.calculatePercentageOfArea(box1.width * box1.height, result)+
                    "\nThe car is overlapping by " + ObjectFunctionality.calculatePercentageOfArea(box3.width * box3.height, result));

        result = ObjectFunctionality.calculateOverlapArea(box3, box2);
        if (result > 0 )
            System.out.println("car and bicycle" + result +
                    "\nThe bicycle is overlapping by " + ObjectFunctionality.calculatePercentageOfArea(box2.width * box2.height, result) +
                    "\nThe car is overlapping by " + ObjectFunctionality.calculatePercentageOfArea(box3.width * box3.height, result));


    } // end main


} // end class