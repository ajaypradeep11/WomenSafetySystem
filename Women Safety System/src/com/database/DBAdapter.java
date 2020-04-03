package com.database;


import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {

	private Context context;
	private SQLiteDatabase db;
	private DBHelper dbHelper;
		
	public DBAdapter(Context context){	
		this.context = context;
		}
		
	public DBAdapter open() throws SQLException {
		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();
		return this;
		}
		
	public void close() {
		dbHelper.close();
		}

	// my functions
	 
    
	public String insertSQL(String sql)
	{
		try{
			db.execSQL(sql);
		}catch(Exception ee)
		{
			return ee.getMessage();
		}
		return "Insert Success";
	}
	
	public String deleteSQL(String sql)
	{
		try{
			db.execSQL(sql);
		}catch(Exception ee)
		{
			return ee.getMessage();
		}
		return "Delete Success";
	}
	
	public String updateSQL(String sql)
	{
		try{
			db.execSQL(sql);
		}catch(Exception ee)
		{
			return ee.getMessage();
		}
		return "Update Success";
	}
	
	public Cursor fetchRecordsSQL(String sql)
	{
		Cursor c = db.rawQuery(sql , null);
		return c;
	}
	
	public String dropTableSQL(String sql)
	{
	try{
		db.execSQL(sql);
	}catch(Exception ee)
	{
		return ee.getMessage();
	}
	return "Drop Table Success";
	}
	       
    public ArrayList<String> getRecords(String sql)
    {
    	  ArrayList<String> lst=new ArrayList<String>();
          Cursor c=fetchRecordsSQL(sql);
          while (c.moveToNext())
            {
        	  String cname =c.getColumnName(0);
    	      String data1 = c.getString(c.getColumnIndex(cname));
    	     
              lst.add(data1);
             }
          return lst;
    }
    
    public boolean isParentRegistered()
    {
    	  
          Cursor c=fetchRecordsSQL("select * from APPUSER where user='parent'");
          
          while (c.moveToNext())
            {
        	  return true;
             }
          return false;
    }

    public boolean isWomenRegistered()
    {
    	  
          Cursor c=fetchRecordsSQL("select * from APPUSER where user='women'");
          
          while (c.moveToNext())
            {
        	  return true;
             }
          return false;
    }

    public String getWomenMobile()
    {
    	  String mno="";
          Cursor c=fetchRecordsSQL("select mobile from APPUSER where user='women'");
          
          while (c.moveToNext())
            {
        	  String cname =c.getColumnName(0);
    	      mno = c.getString(c.getColumnIndex(cname));
             }
          return mno;
    }
	
}
