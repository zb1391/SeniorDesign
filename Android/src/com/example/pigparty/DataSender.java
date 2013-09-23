package com.example.pigparty;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;



/**
 * This class implements Runnable, which is what we need to have 
 * another thread running in android. We need a new thread 
 * for TCP socket sending/receiving. This is becauseandroid 
 * does not permit socket code within the main activity thread.
 * 
 * This class is instantiated within the MainActivity class 
 * to send accelerometer data
 * PARAMETERS:
 * data,olddata - these are accelerometer readings
 * DOS - this is the servers output stream and it is 
 *       how we send the data to the server
 * s - the socket connection between phone and computer
 * game - this is the driving game activity, 
 *         we need it to get the accelerometer data
 * 
 * @author zac
 *
 */
public class DataSender implements Runnable{
	private float data,olddata;
	private DataOutputStream DOS;
	private Socket s;
	private MainActivity game;
	private String ip;
	private byte bdata;
	private OutputStream socketOutputStream;
	public DataSender(MainActivity game,String ip){
		this.game=game;
		this.ip=ip;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	void setData(float data){
		this.data=data;
	}


	/**
	 * Required for a Runnable class
	 * this creates a new socket connection and continuously 
	 * sends data to the server in an infinite while loop
	 * 
	 * The loop only sends accelerometer data when 
	 *  significant change occurs 
	 *  (when car changes from left,right,straight)
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		/*Connect to Server*/
		olddata=0;
		try {
			s = new Socket(ip,6969);
			socketOutputStream = s.getOutputStream();
			socketOutputStream.write('c');
			DOS= new DataOutputStream(s.getOutputStream());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*Continuously send data*/
		while(true){

		//Log.d(null, "data is "+data);
		try {

			data = game.getAccelerometerData();
			//Log.e(null,"acc data is "+data);
			if(data>10)
				data='r';
			else if(data<-10)
				data='l';
			else
				data='n';
			if(data!=olddata){
				bdata = (byte) data;
				socketOutputStream.write(bdata);
				//DOS.writeUTF(Double.toString(data));
				olddata=data;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//TODO handle error like server disconnection
			//we need a message to pop up saying theres an error
			//and returns to the main home screen
		}
		}
		
	}

}