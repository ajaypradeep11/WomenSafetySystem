package com.readsms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class Parent extends Activity {
    /** Called when the activity is first created. */
    @Override
     
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent);
        
      }
    public void track_click(View view)
    {
    	
     Intent in=new Intent(this,ParentDaughterList.class);
   	 startActivity(in);
    }
    
    public void adddaguhter_click(View view)
    {
    	
     Intent in=new Intent(this,Daughter.class);
   	 startActivity(in);
    }
    
    public void exitapp_click(View view)
    {
    	//System.exit(0);
    	//finish();
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
    
    
 
}