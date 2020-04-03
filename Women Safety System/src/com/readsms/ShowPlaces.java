package com.readsms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ShowPlaces extends Activity {
    /** Called when the activity is first created. */
	WebView mainWebView;
	
	String slat="";
	String slang="";
	String smob="";
	
	double lat,lang;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showplaces);
        Bundle extras = getIntent().getExtras(); 
        if(extras !=null) {
            //location= extras.getString("location").toString();
        	smob=getIntent().getExtras().getString("mob"); 
        	slat=getIntent().getExtras().getString("lat");  
        	slang=getIntent().getExtras().getString("lang");
        	
        	try{
        		lat=Double.parseDouble(slat);
        		lang=Double.parseDouble(slang);
        	}catch(Exception ee)
        	{
        		Toast.makeText(getBaseContext(), "Lat Long Conversion Error"+ee.getMessage(), Toast.LENGTH_LONG).show();
        	}
        }
        mainWebView = (WebView) findViewById(R.id.webView1);
        
        showlocation(lat,lang);
    }
    
    
    public void showlocation(double lat,double lang)
     {
    	 WebSettings webSettings = mainWebView.getSettings();
         webSettings.setJavaScriptEnabled(true);
           
         mainWebView.setWebViewClient(new MyCustomWebViewClient());
         mainWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
              
        // mainWebView.loadUrl("https://www.google.co.in/maps/@10.8158447,78.689028,12z?hl=en");
        // String url="https://www.google.co.in/maps/search/"+place+"/@"+lat+","+lang+",12z?hl=en";
         
         String url="http://maps.google.com/?q="+lat+","+lang;
         Toast.makeText(getBaseContext(), "url="+url,Toast.LENGTH_LONG).show();
         mainWebView.loadUrl(url);
         return;
     }
         
    private class MyCustomWebViewClient extends WebViewClient {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
           view.loadUrl(url);
           return true;
       }
   }
    
    
    public void track_click(View view)
    {
    	
    }
    
    
    public void GetRecordsFromWebserver()
    {
   	 	//String urlString = "http://192.168.1.3/women/getrecords.php";
    	String urlString = "http://ezeelearn.com/getrecords.php";
		    try{
    	    	 HTTPDataHandler hh = new HTTPDataHandler(getBaseContext());
			     String  stream = hh.GetHTTPData(urlString);
			     
			     String data="";
			     String s1="";
			     
			     if(stream !=null){
		                try{
		                    // Get the full HTTP Data as JSONObject
		                    JSONObject reader= new JSONObject(stream);

		                    //Get the instance of JSONArray that contains JSONObjects
		                    JSONArray jsonArray = reader.optJSONArray("location");
		    
		                    //Iterate the jsonArray and print the info of JSONObjects
		                   
		                    if(jsonArray.length()>0) // check for data from remote server
		                    {
		                      for(int i=0; i < jsonArray.length(); i++)
		                      {
		                         JSONObject jsonObject = jsonArray.getJSONObject(i);
		                         String mobile = jsonObject.optString("mobile").toString();
		                         String latitute = jsonObject.optString("latitute").toString();
		                         String longitude = jsonObject.optString("longitude").toString();
		                         data = "mobile = "+ mobile +" \n Lat= "+ latitute +" \n Log= "+ longitude +" \n";
		                         s1=s1+data;
		                       }
		                    }
		                    else
		                    {
		                    	data="Record Not Found";
		                    	Toast.makeText(getBaseContext(), data, Toast.LENGTH_LONG).show();
		                    }
		                    
		                    Toast.makeText(getBaseContext(), s1, Toast.LENGTH_LONG).show();

		                }catch(JSONException e){
		                	 Toast.makeText(getBaseContext(), "JSONError"+e.getMessage(), Toast.LENGTH_LONG).show();
		                }
		            } // if statement end
			     
		    }catch(Exception e)
		    {
		    	Toast.makeText(getBaseContext(), "Error="+e.getMessage(), Toast.LENGTH_LONG).show();
		    }
    	}//GetRecords
    
    
    /*thread code
    get current location from web and again call the show location
    feel that move 
    
    
    */
  }