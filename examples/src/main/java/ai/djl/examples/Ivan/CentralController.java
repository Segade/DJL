package ai.djl.examples.Ivan;
/**
 * Class that instantiates the interface and handles all the methods and classes that the program needs
 */

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
    MyVoice myVoice;
    CentralView centralView;
    // declaration of the variables to work with the object detection process
    List<ObjectDetected> listOfObjects = new ArrayList<ObjectDetected>();
    String description = "No information available";
    String overlapping = "No information available";
    String depthOverlap = "No information available";
    String depthCentralPoint = "No information available";
    String depthBottom = "No information available";

    /**
     * Constructor of the controller
     */
    CentralController() {

    } // end constructor

    public void display() {
        //It instantiates the MyVoice class
//         MyVoice myVoice = MyVoice.getInstance();

        // It calls out the presentation message of the program
         //myVoice.speak("Welcome to the central application");

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


    /**
     * Method that calls all the required methods and classes to generate all the possible details that the program works with
     * It receives the String with the path to the picture to be processed
     * It is public to allow the access outside the class
     * It throws the required exceptions in the case an error related to these occurs
     *
     * @param imagePath
     * @throws ModelException
     * @throws TranslateException
     * @throws IOException
     */

    public void loadImage(String imagePath) throws ModelException, TranslateException, IOException {
        listOfObjects.clear();
        // try statement that handles the possible exceptions
        try {

            // it declares the variable that contains the path to the picture
            Path imageFile = Paths.get(imagePath.trim());

            // It declares the Image object
            Image img = ImageFactory.getInstance().fromFile(imageFile);
            // It calls the method that generates the description
            // It stores the information in the description variable
            description = getDescription(img);


            // It calls the method that generates the overlapping
            // It stores the information in the overlapping variable
            overlapping = OverlappingAlgorithm.getOverlap(listOfObjects);

            // It calls the method that generates the depth using the OverlappingAlgorithm class
            // It stores the information in the depthOverlap variable
            depthOverlap = OverlappingAlgorithm.calculateDepth(listOfObjects);

            // It calls the method that generates the depth using the CentralpointsAlgorithm
            // It stores the information in the depthCentralPoint variable
            depthCentralPoint = CentralPointAlgorithm.calculateDepth(listOfObjects);
            // It calls the method that generates the depth using the BottomAlgorithm
            // It stores the information in the depthBottom variable
            depthBottom = BottomAlgorithm.calculateDepth(listOfObjects);

            // By default the description variable is shown
            displayMessage(Choice.DESCRIPTION);

            // the panel with the rest of the information buttons is displayed
            centralView.buttonsPanel.setVisible(true);

        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, ioe, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }  // end load image


    /**
     * Method that handles the selection of the user of the information picked in the interface
     * It recieves a valid option of the Choice enum and displays the corresponding message
     * It is public to allow the access outside the class
     *
     * @param option
     */

    public void displayMessage(Choice option) {

        switch (option) {
            case DESCRIPTION:
                JOptionPane.showMessageDialog(null, description, "Description", JOptionPane.INFORMATION_MESSAGE);
                break;

            case OVERLAPPING:
                JOptionPane.showMessageDialog(null, overlapping, "Overlapping", JOptionPane.INFORMATION_MESSAGE);
                break;

            case DEPTHOVERLAP:
                JOptionPane.showMessageDialog(null, depthOverlap, "Depth by overlapping", JOptionPane.INFORMATION_MESSAGE);
                break;

            case DEPTHCENTRALPOINT:
                JOptionPane.showMessageDialog(null, depthCentralPoint, "Depth by Central Point", JOptionPane.INFORMATION_MESSAGE);
                break;

            case DEPTHBOTTOM:
                JOptionPane.showMessageDialog(null, depthBottom, "Depth by bottom", JOptionPane.INFORMATION_MESSAGE);

        } // end switch

    } // end display message

    /**
     * Method that calls the method that processes the picture and generate the detection
     * Also, it calls the corresponding methods to generate the description of the picture with the location and size of each object
     * It receives the image object to be process
     * It returns the String with the description
     * It is private to avoid accessing outside the class
     * It throws the required exceptions in the case an error related to these occurs
     *
     * @param img
     * @return
     * @throws IOException
     * @throws ModelException
     * @throws TranslateException
     */

    private String getDescription(Image img) throws IOException, ModelException, TranslateException {
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


    /**
     * method that receives the result of the prediction in String format
     * it converts String into a JSON object and extracts the values of each field
     * It returns an ObjectDetected with all the data inserted
     * It is private to avoid accessing outside the class
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
     * It is private to avoid accessing outside the class
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
     * It is private to avoid accessing outside the class
     *
     * @param value
     * @return
     */

    private static int fetchDecimals(String value) {
        return Integer.parseInt(value.substring(value.indexOf(".") + 1));
    } // end fetch decimals


} // end of class
