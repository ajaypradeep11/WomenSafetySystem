package com.readsms;

import com.database.DBAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddRelative extends Activity {
    /** Called when the activity is first created. */
	
	DBAdapter db;
	EditText ed1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addrelative);
        
        ed1=(EditText)findViewById(R.id.editText1);
        db=new DBAdapter(this);  
        db.open();
    }
    
    public void back_click(View view)
    {
    	 Intent in=new Intent(AddRelative.this,MainActivity.class);
    	 startActivity(in);
    }
    
    public void edit_click(View view)
    {
    	 Intent in=new Intent(AddRelative.this,DeleteMobile.class);
    	 startActivity(in);
    }
       
    
    public void store_click(View view)
    {
    	try{
    	String mobile=ed1.getText().toString();
    	
        String sql="insert into MOBILENO values('"+mobile+"')";
        String rv=db.insertSQL(sql);
        Toast.makeText(getBaseContext(), rv, Toast.LENGTH_SHORT).show();
        ed1.setText("");
		
    	}catch(Exception e)
    	{
    		Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
    	}
    }
}