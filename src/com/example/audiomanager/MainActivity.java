package com.example.audiomanager;

import android.media.AudioManager;
import android.app.ProgressDialog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button Vibrate , Ring , Silent , Mode ;
	private TextView Status;
	private AudioManager myAudioManager;
	private ProgressDialog progress;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progress = new ProgressDialog(this);


		Vibrate = (Button)findViewById(R.id.button2);
		Ring = (Button)findViewById(R.id.button4);
		Silent = (Button)findViewById(R.id.button3);
		Mode = (Button)findViewById(R.id.button1);
		Status = (TextView)findViewById(R.id.textView2);
		//     ProgressDialog progress = new ProgressDialog(this);

		myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

	}

	public void vibrate(View view){
		myAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	}
	public void ring(View view){
		myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	}
	public void silent(View view){
		myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	}
	public void sleep(View view) throws InterruptedException{
		Status.setText("Current Status: Sleep INIZIO");
		int mod_pre_sleep=0;
		// inizio della progress bar
		progress.setMessage("Sleeping :) ");
		progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		//	      progress.setIndeterminate(false);
		progress.setMax(100);
		int jumpTime = 0;
		progress.show();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// salva lo stato corrente per ripristinarlo poi
					int mod_pre_sleep = myAudioManager.getRingerMode();  
					// metti SILENT mode
					myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
					while (progress.getProgress() <= progress.getMax()) {
						Thread.sleep(1000);
						handle.sendMessage(handle.obtainMessage());
						if (progress.getProgress() == progress.getMax()) {
							progress.dismiss();
							myAudioManager.setRingerMode(mod_pre_sleep);
						}
					}
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		progress.setProgress(0);
	}


	Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			progress.incrementProgressBy(10);
		}
	};

	public void mode(View view){
		int mod = myAudioManager.getRingerMode();
		if(mod == AudioManager.RINGER_MODE_NORMAL){
			Status.setText("Current Status: Ring");
		}
		else if(mod == AudioManager.RINGER_MODE_SILENT){
			Status.setText("Current Status: Silent");
		}
		else if(mod == AudioManager.RINGER_MODE_VIBRATE){
			Status.setText("Current Status: Vibrate");
		}
		else{

		}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}