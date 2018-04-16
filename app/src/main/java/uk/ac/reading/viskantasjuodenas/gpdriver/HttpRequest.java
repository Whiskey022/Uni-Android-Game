package uk.ac.reading.viskantasjuodenas.gpdriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class to handle HTTP request
 */
public class HttpRequest {

    /**
     * Connecting to URL with provided URL
     * @param url URL to connect to
     * @return connection response
     */
    public static String getResponse(URL url){
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String result = "";

        try {
            //Try connecting
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            //Get response stream
            InputStream stream = connection.getInputStream();

            //Set reader for the response
            reader = new BufferedReader(new InputStreamReader(stream));

            //Read the response, all lines
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }
            result = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Disconnect from connection
            connection.disconnect();

            //Try closing the reader
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
