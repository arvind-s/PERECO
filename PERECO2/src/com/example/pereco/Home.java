package com.example.pereco;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Home extends Activity{
	
	Button b1,b2;
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.home);
	
	//final MyAsyncTask obj=new MyAsyncTask();
	
	Timer t = new Timer();
	//System.out.println(Inter.lati+Inter.longi);
	
    b1=(Button) findViewById(R.id.navibut);
	b2=(Button) findViewById(R.id.emerbut);
	
	b1.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			startActivity(new Intent(android.content.Intent.ACTION_VIEW, 
				    Uri.parse("https://maps.google.co.in/maps?saddr="+Inter.lati+"+"+Inter.longi+"&daddr="+Inter.lat2+"+"+Inter.lon2)));
		}
   });
	final GPSTracker gps=new GPSTracker(this);
	b2.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			
		    gps.getLocation();
			double lat=gps.getLatitude();
			double lon=gps.getLongitude();
			startActivity(new Intent(android.content.Intent.ACTION_VIEW, 
				    Uri.parse("https://maps.google.co.in/maps?saddr="+lat+"+"+lon+"&daddr="+"12.946801"+"+"+"77.6598721")));
		}
   });
	
	t.scheduleAtFixedRate(new TimerTask() {

	    @Override
	    public void run() {
	    	
	    	runOnUiThread(new Runnable() {

	    	    @Override
	    	    public void run() {
	    	        new MyAsyncTask().execute(""+Inter.lati+" "+Inter.longi);
	    	        
	    	    }
	    	     
	    	});
	    	
	    }
	         
	},
	//Set how long before to start calling the TimerTask (in milliseconds)
	0,
	//Set the amount of time between each execution (in milliseconds)
	5000);
	}
	
	private class MyAsyncTask extends AsyncTask<String, Integer, Double>{
		 
		@Override
		protected Double doInBackground(String... params) {
			// TODO Auto-generated method stub
			postData(params[0]);
			//System.out.println("Do in background");
			return null;
		}
 
		protected void onPostExecute(Double result){
			//pb.setVisibility(View.GONE);
			Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
			//postData(params[0]);
		}
		protected void onProgressUpdate(Integer... progress){
			//pb.setProgress(progress[0]);
		}
 
		public void postData(String valueIWantToSend) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://192.168.101.188/receiver.php");
 
			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("myHttpData", valueIWantToSend));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
 
				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost);
 
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
 
	}
}
