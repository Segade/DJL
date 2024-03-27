/**
 * Class that executes the code that displays the description of an image
 * Author: Ivan Segade Carou
 */

package ai.djl.examples;
// import the necesssary libraries for the class

import java.util.List;
import java.util.ArrayList;

import ai.djl.Application;
import ai.djl.ModelException;
import ai.djl.engine.Engine;
import ai.djl.examples.Ivan.CentralController;
import ai.djl.examples.inference.ObjectDetection;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.icu.impl.UResource;
import org.checkerframework.checker.optional.qual.Present;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ObjectDetectorDrive {
    /**
     * method that is executed when the program is run
     *
     * @param args
     * @throws IOException
     * @throws ModelException
     * @throws TranslateException
     */
    public static void main(String[] args) throws IOException, ModelException, TranslateException {
        // It declares the variable that stores the path to the image
        Path imageFile = Paths.get("examples/src/test/resources/dog_bike_car.jpg");
        // It creates an image class to be able to work with it
        Image img = ImageFactory.getInstance().fromFile(imageFile);

        // it declares a DetectedObjects variable
        // the predict method returns the data that is used to generate the description
        DetectedObjects detection = ObjectDetector.predict(img);

        // it declares a variable that stores the data of each object
        List<ObjectDetected> listOfObjects = new ArrayList<ObjectDetected>();

        // the height and width of the image in pixels
        int height = img.getHeight();
        int width = img.getWidth();

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
        System.out.print("\nthe height is " + height + "\nthe width is " + width);

        // loop that iterates the ArrayList and
        // displays the description of each object
        for (ObjectDetected od : listOfObjects)
            System.out.println("\nName: " + od.getName() + "\nDescription: " + od.getDescription());

        System.out.println();
        for (int x = 0; x < listOfObjects.size() - 1; x++) {
            for (int y = x + 1; y < listOfObjects.size(); y++) {
                int result = ObjectFunctionality.calculateOverlapArea(listOfObjects.get(x), listOfObjects.get(y));
                if (result > 0) {
                    System.out.println("The " + listOfObjects.get(x).getName() + " is overlapping by " +
                            listOfObjects.get(y).getName() + ObjectFunctionality.calculatePercentageOfArea((listOfObjects.get(x).getWidth() * listOfObjects.get(x).getHeight()), result)
                            + "%");

                    System.out.println("The " + listOfObjects.get(y).getName() + " is overlapping by " +
                            listOfObjects.get(x).getName() + ObjectFunctionality.calculatePercentageOfArea((listOfObjects.get(y).getWidth() * listOfObjects.get(y).getHeight()), result)
                            + "%");

                } // end if
            } // end for y

        } // end for x


String description = CentralPointsAlgorithm.calculateDepth(listOfObjects);
System.out.println("Central Points \n" + description);
    } // end main


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


} // end class