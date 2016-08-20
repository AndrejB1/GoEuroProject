package com.andrej;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// Class for parsing api connection input into a JSONArray.
public class JParser {


    public static JSONArray getJsonFromUrl(String url){

        // Declare connection and reader to make them closeable in the 'finally' block.
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try{

            // Establish connection, input stream, reader, and a StringBuilder to receive the information.
            URL apiURL = new URL(url);
            connection = (HttpURLConnection) apiURL.openConnection();
            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null){
                sb.append(line);
                sb.append("\n");
            }

            // Create a JSONArray from the API's results.
            return new JSONArray(sb.toString());

        // Warn the user of an invalid city name.
        }catch(MalformedURLException e){
            e.printStackTrace();
            System.out.println("The city name you provided is invalid. Please retry with a valid city name");
            System.exit(0);
        // Tells the user to retry due to unknown IO failure.
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Error while reading from the API (Possible connection failure). Please try again.");
            System.exit(0);

        }finally {
            if( connection != null )
            connection.disconnect();

            if( reader != null )
             try{ reader.close(); }
             catch(IOException e){ e.getMessage();}
        }

        return null;
    }

}
