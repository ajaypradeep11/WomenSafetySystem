package com.readsms;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.database.DBAdapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

public class LocationRequest extends Activity implements LocationListener {
    /** Called when the activity is first created. */
	private LocationManager locationManager;
	double lat,longt;
	boolean helprequest=false;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationrequest);
       
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,((60*1000)*5),10,this);  // 60*1000 -> 1 min * 5 -> 5 min 
    
        
    }//onCreate
    
   
    public void  ImgButton_OnClick(View view)
    {
    	double lat;
   	    double longt;
   	
    	try{
    	 Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    	 lat=lastLocation.getLatitude();
    	 longt=lastLocation.getLongitude();
    	// Toast.makeText(getBaseContext(), "Location " +lat+" "+longt, Toast.LENGTH_LONG).show();
    	 String data="<<"+lat+","+longt+">>";
    	 
    	 helprequest=true;	// this flag is used to send the women location to webserver for tracking
    	 
    	 SendSMSToALL(data);
    	 
    	}catch(Exception ee)
    	{
    		Toast.makeText(getBaseContext(), "Waiting for Location" +ee.getMessage(), Toast.LENGTH_LONG).show();
    	}
    }
    
    public void SendSMSToALL(String data)
    {
     try{
    	DBAdapter db;
    	db=new DBAdapter(this);  
        db.open();
         
   		SmsManager sms=SmsManager.getDefault();
   		
   		ArrayList<String> mnos=new ArrayList<String>();   
   		
   		//Toast.makeText(getBaseContext(), "SMS Data " +data , Toast.LENGTH_LONG).show();	
   		   		
   		mnos=db.getRecords("select mobile from MOBILENO");
   		
   		for(int i=0;i<mnos.size();i++)
   		{
   			String mobile=mnos.get(i).toString();
   			
       		Toast.makeText(getBaseContext(), "SMS Send to " +mobile, Toast.LENGTH_LONG).show();	
       		sms.sendTextMessage(mobile, null, data, null, null);
       			
       	}
   		db.close();
     }catch(Exception e)
      {
     		 Toast.makeText(getBaseContext(), "DB Error " +e.getMessage(), Toast.LENGTH_LONG).show();
      }
    }

    
    public void addmobile_click(View view)
    {
    	 Intent in=new Intent(LocationRequest.this,AddRelative.class);
    	 startActivity(in);
    }
    
    public void quitapp_click(View view)
    {
    	System.runFinalizersOnExit(true);
    	System.exit(0);
    	android.os.Process.killProcess(android.os.Process.myPid());
    	
    }
    
    @Override
    public void onStop(){
        super.onStop();
        finish();
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }
        
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		if(helprequest==true)			// when help button is pressed current location is updated in server
		{
			lat=location.getLatitude();
			longt=location.getLongitude();
		
			//String str = "Latitude: "+location.getLatitude()+" Longitude:"+location.getLongitude();
			//Toast.makeText(getBaseContext(), "Location Changed"+ str, Toast.LENGTH_LONG).show();
			
			String devicemno=GetMobileNumber();  // Get the Mobile Number from registeration
			
			if(lat!=0 && longt!=0 && devicemno!="")
			{
				//call code to update in server
				String url="http://ezeelearn.com/StoreRecords.php?mobile="+devicemno+"&latitute="+lat+"&longitude="+longt;
				Toast.makeText(getBaseContext(), url, Toast.LENGTH_LONG).show();
				UpdateLocationInWebserver(url);
			}
			else
			{
				Toast.makeText(getBaseContext(), "Lat Long Mbno Empty", Toast.LENGTH_LONG).show();
			}
		}
	} //on location changed
	
	
	public void UpdateLocationInWebserver(String url)
	{
		//urlString = "http://www.ezeelearn.com/JSONStoreStudent.aspx?regno=+"+regno+"&sname="+sname+"&branch="+branch;
		
	     HTTPDataHandler hh = new HTTPDataHandler(this);
	     String  stream = hh.GetHTTPData(url);
	     String data="";
	     try{
	    	 
	    	   JSONObject reader= new JSONObject(stream);
               JSONArray jsonArray = reader.optJSONArray("result");
              
               if(jsonArray.length()>0) // check for data from remote server
               {
                 for(int i=0; i < jsonArray.length(); i++)
                 {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    data = jsonObject.optString("output").toString();
                 }
               }
               Toast.makeText(getBaseContext(), "Sever Response"+data , Toast.LENGTH_LONG).show();
	     }catch(JSONException ee)
	     {
	    	 Toast.makeText(getBaseContext(), "Error GetResponse "+ee.getMessage() , Toast.LENGTH_LONG).show();
	     }
	}//update in Webserver
    
		
	
	public String  GetMobileNumber()
	{
		DBAdapter db=new DBAdapter(this);  
        db.open();
        
        String womenmobile=db.getWomenMobile();
        db.close();
        return womenmobile;
        
	}
	
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
    
}//class