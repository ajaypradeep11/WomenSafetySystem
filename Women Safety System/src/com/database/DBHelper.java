package com.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	private static String CREATE_TABLE_SQL1="create table MOBILENO(mobile TEXT)";
	private static String CREATE_TABLE_SQL2="create table DAUGHTER(mobile TEXT)";
	private static String CREATE_TABLE_SQL3="create table APPUSER(user TEXT,mobile TEXT)";
	private static String DATABASE_NAME="MOBILEDB";
	private static final int DATABASE_VERSION = 1;
	private static String TABLE_NAME1="MOBILENO";
	private static String TABLE_NAME2="DAUGHTER";
	private static String TABLE_NAME3="APPUSER";
	
	

	private static final String DROP_TABLE1="DROP TABLE IF EXISTS " + TABLE_NAME1;
	private static final String DROP_TABLE2="DROP TABLE IF EXISTS " + TABLE_NAME2;
	private static final String DROP_TABLE3="DROP TABLE IF EXISTS " + TABLE_NAME3;
	
	public DBHelper(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_SQL1);
		db.execSQL(CREATE_TABLE_SQL2);
		db.execSQL(CREATE_TABLE_SQL3);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
		 Log.w("Example", "Upgrading database, this will drop tables and recreate.");
         db.execSQL(DROP_TABLE1);
         db.execSQL(DROP_TABLE2);
         onCreate(db);
	}
	
	

}
 


