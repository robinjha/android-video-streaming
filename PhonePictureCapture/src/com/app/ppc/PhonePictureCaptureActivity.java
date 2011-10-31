package com.app.ppc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class PhonePictureCaptureActivity extends Activity {
	private static final String TAG = "PPC";
	public DataInputStream dis;
	ImageView imview;
	int len;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
	    	super.onCreate(savedInstanceState);  
	    	setContentView(R.layout.main);
	    	
    	try{
    		
    		ServerSocket server=new ServerSocket(8000);//opening tcp sockets at port 8000
	    	Socket video_sender_socket=server.accept();// accepting incoming connections i.e. the video sender socket
    		
    		
    		
    		while(true){
    		
    			
    			DataInputStream dis=new DataInputStream(video_sender_socket.getInputStream());//create an input stream for the socket, image frames will come through this
    			len=Integer.parseInt(""+dis.readInt()); // get the size of the incoming image frame i.e. size of the byte array
    			byte [] buffer=new byte[len];// create the byte array where we will save the image data
    			dis.readFully(buffer,0,len);//read the image data from the socket inputstream
    			frame2file f2f=new frame2file();//inner class for dumping each byte array frame to a jpeg image in the sdcard    
    			f2f.execute(buffer); // calls the thread to perform the above function
    			// to use the function below comment the previous two lines and comment the next two lines
    			//frame2video f2v=new frame2video(); // puts each frame in the imageview to see the video but this does not work
	    	    //f2v.execute(buffer); // calls the thread to perform the above function
	    	    
	    	
    		}
    			
    	}catch(Exception e){e.printStackTrace();}
        
    }  
    
    
    public class frame2video extends AsyncTask<byte[],Void,Bitmap>{// class that deals with taking byte array and displaying it onto an imageview
    	
    	
    	protected void onPreExecute(){
    		
    		imview=(ImageView)findViewById(R.id.imview);// initialize the image view component
    	}
    	
    	
    	protected Bitmap doInBackground(byte[]...buffer){
    		
    	 try{	
    		 
    		 
    		Bitmap image=BitmapFactory.decodeByteArray(buffer[0], 0, len); // decode the byte array received to a bitmap format
      	    
      	    ByteArrayOutputStream output_stream = new ByteArrayOutputStream();
      	  
      	    output_stream.flush();
      	    
      	    image.compress(CompressFormat.PNG, 85, output_stream); // convert the bitmap image to PNG format and place the byte data into output_stream
      	  
    		output_stream.close();
      	    
    		return image; // return the image object 
    		
    	 }
    	 
    	 catch(Exception e){e.printStackTrace();}
    	
    	return null;
    	
    	}
    	
    	protected void onPostExecute(Bitmap b){
    		imview.setImageBitmap(b); //set the image view to the image object returned by doInBackground(byte[]...buffer) method 
    		imview.invalidate();// attempt to refresh the canvas in imview
    	}
    	
    	
    	
    	
    }
    
public class frame2file extends AsyncTask<byte[],Void,DataOutputStream>{//class that deals with taking the byte array and writing them to jpg files on the sdcard
    	
    	
    	
    	protected DataOutputStream doInBackground(byte[]...buffer){
    		
    	 try{	
    		 
    		 DataOutputStream dos=new DataOutputStream(new FileOutputStream("/sdcard/"+System.currentTimeMillis()+".jpg"));//create the file output stream to write the data onto the sdcard
    		 dos.write(buffer[0]);//write the byte array to the file
    		 dos.flush();
    		 
    		 return dos;//return the file stream object
    		}
    	 
    	 catch(Exception e){e.printStackTrace();}
    	
    	return null;
    	
    	}
    	
    	protected void onPostExecute(DataOutputStream dos){
    		
    	try{	
    		dos.close();//close the file stream object returned from doInBackground(byte[]...buffer) 
    	}
    	
    	catch(Exception e){e.printStackTrace();}
    		
    	}
    	
    	
    	
    	
    }
    
    
    
    
    
    
    
    
    
    
    
}