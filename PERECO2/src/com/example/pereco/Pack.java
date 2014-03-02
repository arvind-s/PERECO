package com.example.pereco;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

public class Pack extends Activity{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pack);
		
		MediaPlayer mp = MediaPlayer.create(getBaseContext(),R.raw.alert);
		
		AudioManager am = 
			    (AudioManager) getSystemService(Context.AUDIO_SERVICE);

			am.setStreamVolume(
			    AudioManager.STREAM_MUSIC,
			    am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
			    0);
		
		
	    mp.start();
	 
		//GPSTracker gps = new GPSTracker(this);
		Thread logoTimer = new Thread() {
            public void run(){
                try{
                    int logoTimer = 0;
                    while(logoTimer < 3000){
                        sleep(100);
                        logoTimer = logoTimer +100;
                    };
                    startActivity(new Intent("com.example.HOME"));
                } 
                 
                catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                 
                finally{
                    finish();
                }
            }
        };
         
        logoTimer.start();
    }
	

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/

}
