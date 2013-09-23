package com.example.pigparty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import android.os.AsyncTask;
import android.util.Log;

public class ReceiverThread extends AsyncTask<Void,Void,Void>{
	private Socket s;
	private BufferedReader in;
	private Receiver listener;
	public char fromServer;
	public void setListener(Receiver r){
		listener=r;
	}
	@Override
	protected Void doInBackground(Void... params) {
		s = ConnectionManager.getInstance().s;
		in = ConnectionManager.getInstance().in;
		char buf[]= new char[1];
		try {
			in.read(buf);
			fromServer = buf[0];
			Log.i(null, "Received "+buf[0]);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e(null,"finished receiving");
		return null;
	}
	
    protected void onPostExecute(Void x) {
    	listener.onReceive();
    }






	
}
