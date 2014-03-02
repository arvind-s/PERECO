package com.example.pereco;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


public class IncomingSms extends BroadcastReceiver 
{
	
	// Get the object of SmsManager
	final SmsManager sms = SmsManager.getDefault();
	static String message="";
	//Bundle m=new Bundle();
	String senderNum;
	
	public void onReceive(Context context, Intent intent) 
	{
	
		// Retrieves a map of extended data from the intent.
		final Bundle bundle = intent.getExtras();

		try 
		{
			
			if (bundle != null) 
			{
				
				final Object[] pdusObj = (Object[]) bundle.get("pdus");
				
				for (int i = 0; i < pdusObj.length; i++) 
				{
					
					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage.getDisplayOriginatingAddress();
					
			        this.senderNum = phoneNumber;
			        String message = currentMessage.getDisplayMessageBody();
			        IncomingSms.message+=message;

			        //Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);
			        
			        
			        int duration = Toast.LENGTH_LONG;
					Toast toast = Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, duration);
					toast.show();
					System.out.println("hello");
					if(message.contains("nms"))
					{
						abortBroadcast();
						System.out.println("Äborted");
					}
					
				} // end for loop
				//m.putInt("job", (int)message.charAt(message.length()-1)-48);
				//m.putString("Sender", this.senderNum);
				//m.putString("Message", this.message);
				
				
				System.out.println(this.senderNum);
				
              } // bundle is null

		} 
		catch (Exception e) 
		{
			Log.e("SmsReceiver", "Exception smsReceiver" +e);			
		}
		if(this.senderNum.contains("9591402872"))
		{
			System.out.println("coming her");
		    PackageManager pm = context.getPackageManager();
		    Intent appStartIntent = pm.getLaunchIntentForPackage("com.example.pereco");
		    if (null != appStartIntent)
		    {
		        context.startActivity(appStartIntent);
		    }
		}

	}
	
}