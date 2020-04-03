package com.readsms;



import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.MalformedURLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;


public class HTTPDataHandler {

    static String stream = null;
    Context context;
    public HTTPDataHandler(Context c){
    	context=c;
    }

    public String GetHTTPData(String urlString){
        try{
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Check the connection status
            if(urlConnection.getResponseCode() == 200)
            {
                // if response code = 200 ok
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                // Read the BufferedInputStream
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    sb.append(line);
                }
                stream = sb.toString();
                // End reading...............

                // Disconnect the HttpURLConnection
                urlConnection.disconnect();
            }
            else
            {
                // Do something
            	Toast.makeText(context, "Else  Url connection !=200",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
        	Toast.makeText(context, "Error "+e.getMessage(),Toast.LENGTH_LONG).show();
        }finally {

        }
        // Return the data from specified url
        return stream;
    }
    
}
