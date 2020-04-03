package com.readsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import android.widget.Toast;

public class IncomingSms extends BroadcastReceiver {

	final SmsManager sms = SmsManager.getDefault();
	public Context context=null;
	String location="";
	String recvmobile="";
	@Override
	public void onReceive(Context context_para, Intent intent) {
		// TODO Auto-generated method stub
		context=context_para;
		
		Toast.makeText(context,"in OnReceive",Toast.LENGTH_LONG).show();
			
		readSMS(intent);
		
	}//onReceive
	
	public void parseSMSContent(String phoneno,String smsdata)
	{
		try{
		//Toast.makeText(context,"In ParseSMS"+smsdata,Toast.LENGTH_LONG).show();
		
		if(smsdata.startsWith("<<")&&(smsdata.endsWith(">>")))
		{
			smsdata=smsdata.substring(2,smsdata.length()-2);
			location=smsdata;
		//	Toast.makeText(context,"Emergency="+smsdata,Toast.LENGTH_LONG).show();
		//	replayToMessage(phoneno);
			
			showDialogBOX();
		}
		}catch(Exception ee)
		{
			Toast.makeText(context,"Error="+ee.getMessage(),Toast.LENGTH_LONG).show();
		}
	}
	
	public void readSMS(Intent intent)
    {
	   Bundle bundle=intent.getExtras();
	 //  Toast.makeText(context,"in OnReceive read sms",Toast.LENGTH_LONG).show();

	    Object[] messages=(Object[])bundle.get("pdus");
	    SmsMessage[] sms=new SmsMessage[messages.length];

	    for(int n=0;n<messages.length;n++){
	        sms[n]=SmsMessage.createFromPdu((byte[]) messages[n]);
	    }
	    String address="";
	    String body="";
	    
	    for(SmsMessage msg:sms)
	    {
	        address=msg.getOriginatingAddress();
	        body=msg.getMessageBody();
	       // Toast.makeText(context,"in OnReceive read sms"+address+"  "+body,Toast.LENGTH_LONG).show();
	    }
	    recvmobile=address;
	    parseSMSContent(address,body);
       return;
    }
	
	public void showDialogBOX()
	{
		//AlertMessage msg=new AlertMessage();
		Intent i = new Intent(context, AlertMessage.class); 
		i.putExtra("location",location);
		i.putExtra("mobile",recvmobile);
	    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	    context.startActivity(i);
		
	}
		
	public void replayToMessage(String sender)
	{
		 try{
				
			 SmsManager sms=SmsManager.getDefault();  
			
			 sms.sendTextMessage(sender, null, "Thanks for your SMS", null, null);
		
			 Toast.makeText(context, "SMS Send to "+sender, Toast.LENGTH_SHORT).show();
		 }catch(Exception ee)
		 {
			 Toast.makeText(context, "Error SMS "+ee.getMessage(), Toast.LENGTH_SHORT).show();
		 }
	}
	
		
}//class
