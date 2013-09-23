package com.example.pigparty;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



/**
 * This just allows a user to enter the computer's ip address.
 * It is a temporary solution to starting the game and 
 * will not be how the final implementation will work
 * @author zac
 * PARAMETERS:
 *  connectButton - used for connecting to server
 *  startButton - used for starting the game activity
 *  ipfield - textfield for user to enter ipaddress
 *  ip - the ip address entered by user
 *  connection - a boolean to see if the connection succeeds
 */
public class ConnectActivity extends Activity{

}