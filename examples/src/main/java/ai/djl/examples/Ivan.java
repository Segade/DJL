package ai.djl.examples;

public class Ivan {

    //String myJSON = "{\"name\":\"Ivan\", \"surname\": \"segade carou\", \"age\":\"42\", \"size\": {\"height\":\"165\", \"weight\":\"55\"}}";

    public static void main(String[] args) {
        String myJSON = "{\"name\":\"Ivan\", \"surname\": \"segade carou\", \"age\":\"42\", \"size\": {\"height\":\"165\", \"weight\":\"55\"}}";
//Gson jo = new Gson();
//String gsonObj=jo.toJson(myJSON);
        String out= String.valueOf(parseString(myJSON));
//System.out.println(gsonObj);
    //    System.out.println(myJSON);





}
   // String gsonObj1 = myJSON;

    public static String parseString(String gsonObj1)
    {
//         System.out.println(gsonObj1.name);
        String name = "";
      return name ;
    }

} // end class
