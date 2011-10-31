package com.app.lcfs;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

public class LiveCameraFeedSenderActivity extends Activity {
  private static final String TAG = "CameraDemo";
  Preview preview; 
  

  /** Called when the activity is first created. */
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
try{
        	
}

catch(Exception e){}
    
    preview = new Preview(this); 
    ((FrameLayout) findViewById(R.id.preview)).addView(preview); 
    }}
