package ai.djl.examples;

public class Box {
    private String name;
    public int x;
    public int y;
    public int width;
    public int height;

    public Box(String name, int x, int y, int width, int height){
        this.name = name;
        this.x = x;
        this.y = y;
        this .width = width;
        this.height = height;
    } // end constructor

public boolean overlapsWith(Box other){
    // Check if one rectangle is to the left of the other
    if (this.x > other.x + other.width || other.x > this.x + this.width)
        return false;

    // Check if one rectangle is above the other
    if (this.y > other.y + other.height || other.y > this.y + this.height)
        return false;

    // If the above conditions are not met, the rectangles overlap
return true;
} // end overlaps with


} // end class
