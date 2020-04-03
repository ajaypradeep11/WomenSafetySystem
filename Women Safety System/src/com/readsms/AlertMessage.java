package com.readsms;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;


public class AlertMessage extends Activity {
	 String location="";
	 String recvmobile="";
	 MediaPlayer mediaPlayer;
	@Override
       public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.msgscreen);
	        location=getIntent().getExtras().getString("location"); 
	        recvmobile=getIntent().getExtras().getString("mobile"); 
	        
	        playSound();
	        showmsg();
	      	}//oncreate
	
	
	   public void playSound()
	   {
		 
		   try {
	      // String filePath = Environment.getExternalStorageDirectory()+"/police.mp3";
	       String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/police.mp3";
	       Toast.makeText(this,"in plays File Path: "+filePath,Toast.LENGTH_LONG).show();
	       mediaPlayer = new  MediaPlayer();
	       mediaPlayer.setDataSource(filePath);
	       mediaPlayer.prepare();   
	       mediaPlayer.setLooping(true);
	       mediaPlayer.start();
	      
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this,"in plays 1"+e.getMessage(),Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this,"in plays 2"+e.getMessage(),Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this,"in plays 3"+e.getMessage(),Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	       
	   }
	   public void showmsg()
	   {
		   
	        AlertDialog alt=new AlertDialog.Builder(AlertMessage.this).create();
	        alt.setTitle("Your Daughter Need Help");
	        alt.setMessage("At GPS Location:"+location);
	        alt.setIcon(R.drawable.help);
	        alt.setButton("Ok", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	        // TODO Auto-generated method stub
	        	//Toast.makeText(getBaseContext(), "Ok", Toast.LENGTH_LONG).show();
	        	try{
	        	mediaPlayer.stop();
	        	}catch(Exception e){}
	        	
	        	finish();
	        	//Intent i = new Intent(AlertMessage.this, MainActivity.class); 
	        	//startActivity(i);

	        	String []latlang=location.split(",");
	        	String lat=latlang[0].toString();
	        	String lang=latlang[1].toString();
	        	
	        	//Toast.makeText(getBaseContext(), "Location="+location, Toast.LENGTH_LONG).show();
	        	//Toast.makeText(getBaseContext(), "Lat="+lat, Toast.LENGTH_LONG).show();
	        	//Toast.makeText(getBaseContext(), "Lang="+lang, Toast.LENGTH_LONG).show();
	        	if(haveNetworkConnection())
	        	{
	        		Intent i = new Intent(AlertMessage.this, ShowPlaces.class); 
	        		i.putExtra("lat", lat);
	        		i.putExtra("lang", lang);
	        		i.putExtra("mob", recvmobile);
	        		startActivity(i);
	        	}
	        	else
	        	{
	        		Toast.makeText(getBaseContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();
	        	}
	        }
	        });
	        alt.show();
	 		
	   }
	   
	   private boolean haveNetworkConnection() {
		    boolean haveConnectedWifi = false;
		    boolean haveConnectedMobile = false;

		    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		    for (NetworkInfo ni : netInfo) {
		        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
		            if (ni.isConnected())
		                haveConnectedWifi = true;
		        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
		            if (ni.isConnected())
		                haveConnectedMobile = true;
		    }
		    return haveConnectedWifi || haveConnectedMobile;
		}
	 
	 
   }//class
	   
