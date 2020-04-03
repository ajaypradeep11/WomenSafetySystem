package com.readsms;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.database.DBAdapter;


public class ParentDaughtereEdit extends ListActivity {
    /** Called when the activity is first created. */
	DBAdapter db;
	
	ArrayList<String> items=new ArrayList<String>();		
			
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.viewmanager);
        
        db=new DBAdapter(this);  
        db.open();
       
        items=db.getRecords("select mobile from mobileno");
               
        ArrayAdapter<String> ad=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        setListAdapter(ad);
    }
    
    protected void onListItemClick(ListView l,View v,int position,long id)
    {
       final int itemidx=position;
       super.onListItemClick(l, v, position, id);
       
       Toast.makeText(getBaseContext(), "Selected Item="+position,Toast.LENGTH_LONG).show();
       
       AlertDialog.Builder alt=new AlertDialog.Builder(ParentDaughtereEdit.this); 
       alt.setTitle("Delete MobileNo");    
       alt.setMessage("Delete");    
       alt.setIcon(R.drawable.delicon); 
       alt.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
       public void onClick(DialogInterface dialog, int which) 
         {    
    	   // TODO Auto-generated method stub  
    	   Toast.makeText(getBaseContext(), "Yes", Toast.LENGTH_LONG).show();
    	   deleteMobile(items.get(itemidx));
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
    
     public void deleteMobile(String pmobile)
     {
    	
    	 String rv=db.deleteSQL("delete from mobileno where mobile='"+pmobile+"'");
    	 Toast.makeText(getBaseContext(), rv, Toast.LENGTH_LONG).show();
    	 
    	 items=db.getRecords("select mobile from mobileno");
         
         ArrayAdapter<String> ad=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
         setListAdapter(ad);
      }
  }

