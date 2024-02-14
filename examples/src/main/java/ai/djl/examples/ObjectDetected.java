/**
 * Class that stores the information for each object detected
 * Author: Ivan Segade Carou
 */

package ai.djl.examples;

//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;

public class ObjectDetected {
    private String name;
    private int height;
    private int width;
    private int positionX;
    private int positionY;
    private String description;


    public ObjectDetected() {
    }

    public ObjectDetected(String name, int height, int width, int positionX, int positionY, String description) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.positionX = positionX;
        this.positionY = positionY;
        this.description = description;
    } // end constructor


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ObjectDetected{" +
                "name='" + name + '\'' +
                ", height=" + height +
                ", width=" + width +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", description='" + description + '\'' +
                '}';
    }
} // end class
