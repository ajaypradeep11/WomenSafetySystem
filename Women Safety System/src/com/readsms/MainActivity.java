package com.readsms;

import com.database.DBAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.telephony.TelephonyManager;

import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    Context context;
    DBAdapter db;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context=getApplicationContext();
        db=new DBAdapter(this);  
        db.open();
      }
    
    public void parent_click(View view)
    {
    	if(db.isParentRegistered())
    	{
    		Intent i = new Intent(this, Parent.class); 
    		startActivity(i);
    	}
    	else
    	{
    		Intent i = new Intent(this, ParentRegister.class); 
    		startActivity(i);
    	}
    }
    
    public void kid_click(View view)
    {
    	if(db.isWomenRegistered())
    	{
    		Intent i = new Intent(this, LocationRequest.class); 
        	startActivity(i);}
    	else
    	{
    		Intent i = new Intent(this, DaughterRegister.class); 
    		startActivity(i);
    	}
    	
    
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