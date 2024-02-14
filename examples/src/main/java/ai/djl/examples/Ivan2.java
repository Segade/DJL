package ai.djl.examples;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.print.attribute.standard.PagesPerMinute;

public class Ivan2 {

    public static void main(String[] args) {
        String myJSON = "{\"name\":\"Ivan\", \"surname\": \"segade carou\", \"age\":\"42\", \"size\": {\"height\":\"165\", \"weight\":\"55\"}}";


 JsonElement je =    JsonParser.parseString(myJSON);
 JsonObject jo = je.getAsJsonObject();
 String name = jo.get("name").toString().replace("\"", "");
 String surname = jo.get("surname").toString().replace("\"", "");
 int     age  = Integer.parseInt(   jo.get("age").toString().replace("\"", ""));
age += 12;
System.out.println("hello " + name  + " " + surname + "\nyour age is " + age);
    }
}
