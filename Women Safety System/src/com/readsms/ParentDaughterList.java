package com.readsms;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.database.DBAdapter;

public class ParentDaughterList extends ListActivity {
    /** Called when the activity is first created. */
	DBAdapter db;
	
	ArrayList<String> items=new ArrayList<String>();		
			
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.viewmanager);
        
        db=new DBAdapter(this);  
        db.open();
       
        items=db.getRecords("select mobile from DAUGHTER");
               
        ArrayAdapter<String> ad=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        setListAdapter(ad);
    }
    
    protected void onListItemClick(ListView l,View v,int position,long id)
    {
       final int itemidx=position;
       super.onListItemClick(l, v, position, id);
       
       Toast.makeText(getBaseContext(), "Selected Item="+position,Toast.LENGTH_LONG).show();
       
       AlertDialog.Builder alt=new AlertDialog.Builder(ParentDaughterList.this); 
       alt.setTitle("Track");    
       alt.setMessage("Track");    
       alt.setIcon(R.drawable.delicon); 
       alt.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
       public void onClick(DialogInterface dialog, int which) 
         {    
    	   // TODO Auto-generated method stub  
    	   Toast.makeText(getBaseContext(), "Yes", Toast.LENGTH_LONG).show();
    	   
    	   TrackDaughter(items.get(itemidx));
    	  
         }
    	 });
       alt.setNegativeButton("No", new DialogInterface.OnClickListener(){ 
       public void onClick(DialogInterface dialog, int which) 
    	{  
    	   // TODO Auto-generated method stub  
    	   Toast.makeText(getBaseContext(), "No", Toast.LENGTH_LONG).show();
    	}     
       }); 
       alt.show();   
       }
    
       public void TrackDaughter(String mobile)
       {
    	String mob="";
       	String lat="";
       	String lang="";
       	String data=GetRecordsFromWebserver(mobile);
       	try{
       		String []latlang=data.split(",");
       		mob=latlang[0].toString();
              	lat=latlang[1].toString();
           	lang=latlang[2].toString();
           	
           	Intent i = new Intent(this, ShowPlaces.class);
           	i.putExtra("mob", mob);
            i.putExtra("lat", lat);
           	i.putExtra("lang", lang);
           	startActivity(i);
           	
       	}catch(Exception e)
       	{
       	  Toast.makeText(getBaseContext(), "Error Split"+e.getMessage(), Toast.LENGTH_LONG).show();
       	}
       }
       
       public String  GetRecordsFromWebserver(String dmobile)
       {
       	String data="";
      	 	//String urlString = "http://192.168.1.3/women/getrecords.php";
       	String urlString = "http://www.ezeelearn.com/getDaughterRecords.php?mobile="+dmobile;
   		    try{
       	    	 HTTPDataHandler hh = new HTTPDataHandler(getBaseContext());
   			     String  stream = hh.GetHTTPData(urlString);
   			     
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
   		                         data = mobile +","+ latitute +","+ longitude;
   		                        
   		                       }
   		                    }
   		                    else
   		                    {
   		                    	data="Record Not Found";
   		                    	Toast.makeText(getBaseContext(), data, Toast.LENGTH_LONG).show();
   		                    }
   		                    
   		                    Toast.makeText(getBaseContext(), data, Toast.LENGTH_LONG).show();

   		                }catch(JSONException e){
   		                	 Toast.makeText(getBaseContext(), "JSONError"+e.getMessage(), Toast.LENGTH_LONG).show();
   		                }
   		            } // if statement end
   			     
   		    }catch(Exception e)
   		    {
   		    	Toast.makeText(getBaseContext(), "Error="+e.getMessage(), Toast.LENGTH_LONG).show();
   		    }
   		    return data;
       	}//GetRecords
       
    
    
  }

