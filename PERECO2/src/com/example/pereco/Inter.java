package com.example.pereco;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class Inter extends Activity {
	
	static double lati,longi,lat2,lon2;
    GPSTracker gps=new GPSTracker(this);
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.inter);
	GPSTracker gps=new GPSTracker(this);
    gps.getLocation();
    
    if(gps.canGetLocation()==false)
    {
    	gps.showSettingsAlert();
    }
    
    lati=gps.getLatitude();
    longi=gps.getLongitude();
    System.out.println(lati+"+"+longi);
	DownloadFile file1=new DownloadFile();
	file1.execute("http://192.168.101.188/uploads/file1.txt");
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	File file = new File("/sdcard/file1.txt");
	BufferedReader br=null;
    try {
		br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    String line = null;
    String[] t1=null;
    String[] t2=null;
    try {
		while ((line = br.readLine()) != null)
		{
		 t1=line.split(":");	
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    double minlat=Double.parseDouble(t1[0]);
    double maxlat=Double.parseDouble(t1[1]);
    double minlon=Double.parseDouble(t1[2]);
    double maxlon=Double.parseDouble(t1[3]);
    
    int n=(int)(Math.random() * (4 - 1) + 1);
    if(n==1)
    {
    	lat2=Double.parseDouble(t1[4]);lon2=Double.parseDouble(t1[5]);
    	System.out.println(lat2+"+"+lon2);
   	}
    else if(n==2)
    {
    	lat2=Double.parseDouble(t1[6]);lon2=Double.parseDouble(t1[7]);
    	System.out.println(lat2+"+"+lon2);
    }
    else if(n==3)
    {
    	lat2=Double.parseDouble(t1[8]);lon2=Double.parseDouble(t1[9]);
    	System.out.println(lat2+"+"+lon2);
    }
    else
    {
    	lat2=Double.parseDouble(t1[10]);lon2=Double.parseDouble(t1[11]);
    	System.out.println(lat2+"+"+lon2);
    }
    if(minlat<lati && lati<maxlat && minlon<longi && longi<maxlon)
	{
		
    	startActivity(new Intent("com.example.PACK"));
		finish();
	}
    else
    {
    	startActivity(new Intent("com.example.SAFE"));
    	finish();
    }
  }
	
	private class DownloadFile extends AsyncTask<String, Integer, String> {
    	@Override
        protected String doInBackground(String... sUrl) {
            try {
                URL url = new URL(sUrl[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream("/sdcard/file1.txt");
                byte data[] = new byte[1024];                
                int count;
                while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
            }
            return null;
        }
	}
}
