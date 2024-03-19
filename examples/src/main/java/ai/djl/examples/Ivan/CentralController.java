package ai.djl.examples.Ivan;


import ai.djl.ModelException;

import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.translate.TranslateException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;



public class CentralController {
    // declaration the MyVoice class used
    //MyVoice myVoice;
    CentralView centralView;
    List<ObjectDetected> listOfObjects = new ArrayList<ObjectDetected>();


    /**
     * Constructor of the controller
     */
    CentralController() {
        // call to the method that instantiates the view class
        display();
    } // end constructor

    public void display() {
        //It instantiates the MyVoice class
//        MyVoice myVoice = MyVoice.getInstance();

        // It calls out the presentation message of the program
//        myVoice.speak("Welcome to the central application");

        // It instantiates the interface
         centralView = new CentralView(this);
        centralView.display();
    } // end display


    /**
     * public method that calls the unique MyVoice class
     * and calls the speak method to call out the text passed as paramter
     *
     * @param textToSpeech
     */
    public void speak(String textToSpeech) {
//        myVoice = MyVoice.getInstance();
//        myVoice.speak(textToSpeech);

    } // end of speak


    public void loadImage(String imagePath) throws  ModelException, TranslateException, IOException{
try {
    Path imageFile = Paths.get(imagePath.trim() );

    Image img = ImageFactory.getInstance().fromFile(imageFile);
    String description = getDescription(img);
    System.out.println("Secaiv\n" + description);
    description = getOverlap();
    System.out.println("\n" + description);
    //JOptionPane.showMessageDialog(null, description, "Result", JOptionPane.INFORMATION_MESSAGE);
} catch (IOException ioe)
{System.out.println((ioe));}

    }  // end load image


    private String getDescription(Image img) throws IOException, ModelException, TranslateException{
        // the height and width of the image in pixels
        int height = img.getHeight();
        int width = img.getWidth();

        DetectedObjects detection = ObjectDetector.predict(img);

        // loop that iterates the list of detected objects
        // and calls the createObject that converts the prediction into JSON objects
        // it also calls the generateDescription method that converts the JSON object into string to stores in the objectDetected
        // and it stores the data  in theobjectDetected object
        // it adds each object to the ArrayList
        for (int x = 0; x < detection.getNumberOfObjects(); x++) {
            String result = detection.item(x).toString();
            ObjectDetected objectDetected = createObject(result);
            String description = ObjectFunctionality.generateDescription(height, width, objectDetected);
            objectDetected.setDescription(description);
            listOfObjects.add(objectDetected);
        }// end for

        // loop that iterates the ArrayList and
        // displays the description of each object
         String description = "";
        for (ObjectDetected od : listOfObjects)
            description += "\nName: " + od.getName() + "\nDescription: " + od.getDescription();

        return description;
    } // end get description



    private String getOverlap(){
    String overlap = "";


        //Loop for the reference object
        for (int x = 0; x < listOfObjects.size() - 1; x++) {
            // loop that compares the reference object to the rest of objects
            for (int y = x + 1; y < listOfObjects.size(); y++) {
                // It stores the value of the common area
                int result = ObjectFunctionality.calculateOverlapArea(listOfObjects.get(x), listOfObjects.get(y));

                // if the area is bigger than zero means there is common area
                if (result > 0) {
                    // It displays the percentage of the reference object  covered by the new one
                    overlap += "\nThe " + listOfObjects.get(x).getName() + " is overlapping by " +
                            listOfObjects.get(y).getName() + ObjectFunctionality.calculatePercentageOfArea((listOfObjects.get(x).getWidth() * listOfObjects.get(x).getHeight()), result)
                            + "%";

                    // It displays the percentage of the new object covered by the reference object
                     overlap += "\nThe " + listOfObjects.get(y).getName() + " is overlapping by " +
                            listOfObjects.get(x).getName() + ObjectFunctionality.calculatePercentageOfArea((listOfObjects.get(y).getWidth() * listOfObjects.get(y).getHeight()), result)
                            + "%";

                } // end if
            } // end for y

        } // end for x

        return overlap;
    } // end get overlap

    /**
     * method that receives the result of the prediction in String format
     * it converts String into a JSON object and extracts the values of each field
     * It returns an ObjectDetected with all the data inserted
     * It is private because non external classes can access it
     *
     * @param result
     * @return
     */

    private static ObjectDetected createObject(String result) {
        // it declares a new empty ObjectDetected
        ObjectDetected od = new ObjectDetected();

        // it converts the String with the result into a JSONElement
        JsonElement je = JsonParser.parseString(result);
        // It converts the JSONElement into a JSONObject
        JsonObject jo = je.getAsJsonObject();

        // it retrieves the name value from the JSONObject and stores in the name variable in String format
        String name = jo.get("class").toString().replace("\"", "");
        od.setName(name);

// It retrieves the bounds value where the data of the image is stored
        // It stores the data in the bounds variable
        String bound = jo.get("bounds").toString();

        // it converts the String with the bounds into a JSONElement
        JsonElement jee = JsonParser.parseString(bound);
        // It converts the JSONElement into a JSONObject
        JsonObject joo = jee.getAsJsonObject();

        // It calls the fetchBounds method and passes the ObjectDetected object and the bounds value in String format
        od = fetchBounds(od, jo.get("bounds").toString());

// it returns the ObjectDetected with all the values stored
        return od;
    } // end create object


    /**
     * method that receives the ObjectDetected where the data is stored
     * and the bounds values in String format to convert it into JSON and extract the data
     * It returns the ObjectDetected object with all the data
     * It is private because non external classes can access it
     *
     * @param od
     * @param bounds
     * @return
     */

    private static ObjectDetected fetchBounds(ObjectDetected od, String bounds) {
// it declares the variable that stores the corresponding value for storing in the correspondign field
        int aux;
        // it converts the String with the bounds into a JSONElement
        JsonElement je = JsonParser.parseString(bounds);
// It converts the JSONElement into a JSONObject
        JsonObject jo = je.getAsJsonObject();
// the fetchDecimals is called to convert the values in integer values
        // it retrieves the X coordinate value
        aux = fetchDecimals(jo.get("x").toString());
        // it stores the value in the ObjectDetected
        od.setPositionX(aux);

        // it retrieves the Y coordinate value
        aux = fetchDecimals(jo.get("y").toString().replace("\"", ""));
        // it stores the value in the ObjectDetected
        od.setPositionY(aux);

// it retrieves the height coordinate value
        aux = fetchDecimals(jo.get("height").toString().replace("\"", ""));
// it stores the value in the ObjectDetected
        od.setHeight(aux);

        // it retrieves the width coordinate value
        aux = fetchDecimals(jo.get("width").toString().replace("\"", ""));
// it stores the value in the ObjectDetected
        od.setWidth(aux);


        // It returns the ObjectDetected with the values stored
        return od;
    } // fetch bounds

    /**
     * method that fetches the values from the bounds.
     * the values are defined as decimals values. Therefore they must be converted into integer values to be able to manipulate them
     * It receives the value as a decimal
     * It returns the value in integer format
     * It is private because non external classes can access it
     *
     * @param value
     * @return
     */

    private static int fetchDecimals(String value) {
        return Integer.parseInt(value.substring(value.indexOf(".") + 1));
    } // end fetch decimals




} // end of class
