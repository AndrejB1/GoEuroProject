package com.andrej;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;


public class Main {

    public static void main(String[] args){

        // If the user has not entered a city name, the application cannot run.
        // Provide instructions to the user and quit the application.
        if(args.length < 1){
            System.out.println("Please provide a valid city name to search for.");
            System.exit(0);
        }

        // Receive a JSONArray of locations that coincide with the entered city name.
        // Edit the same JSONArray to contain only the values that we want.
        JSONArray apiResults = JParser.getJsonFromUrl("http://api.goeuro.com/api/v2/position/suggest/en/" + args[0]);
        JSONArray editedArray = editArray(apiResults);

        // Declare the file to output to.
        File file = new File("CSVOutput/csv1.csv");

        try{

            // Since the Apache Commons CDL can only parse JSONObjects into .csv accurately
            // we need to convert our JSONArray to a regular String before writing it to the file.
            StringBuilder sb = new StringBuilder();
            for (int outerIndex = 0; outerIndex < editedArray.length(); outerIndex++) {

                JSONArray innerJSONArray = editedArray.getJSONArray(outerIndex);
                for(Object obj : innerJSONArray){
                    sb.append(obj.toString());
                    sb.append(", ");
                }
                sb.append("\n");
            }

            // Get a String and at last write it to the .csv file
            String toCsv = sb.toString();
            FileUtils.writeStringToFile(file, toCsv);

            // Print out the final results to the user, to let them know the program was successful.
            System.out.println(sb.toString());

        }catch(IOException e){
            System.out.println("There was an error with writing out the .csv file. Please try again.");
            e.printStackTrace();
        }
    }

    // This method will take the JSONArray we received from our JParser class and return
    // a brand new one filled only with the information we intend to write to our .csv file
    private static JSONArray editArray(JSONArray array){

        // Declare the JSONArray which will be returned at the end.
        JSONArray editedArray = new JSONArray();

        // Iterate through each entry of the parameter array, taking only
        // the information we want to add to 'editedArray'
        for(int i = 0; i < array.length(); i++){

            // Convert the current object in the array to a JSONObject rather than a JSONArray.
            // This will give us easier access to the fields we want.
            JSONObject obj = array.getJSONObject(i);

            // Create an entry for 'editedArray'.
            // We use the JSONObject from above to gather the necessary fields
            // but we must add them to a JSONArray, because JSONArray entries are
            // sorted in the order they are added, unlike JSONObject's.
            JSONArray cityDetails = new JSONArray();
            cityDetails.put(0, obj.get("_id"));
            cityDetails.put(1, obj.get("name"));
            cityDetails.put(2, obj.get("type"));
            cityDetails.put(3, obj.getJSONObject("geo_position").get("latitude"));
            cityDetails.put(4, obj.getJSONObject("geo_position").get("longitude"));

            editedArray.put(i, cityDetails);
        }
        return editedArray;
    }
}

