package com.example.pigparty;

import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.IGameInterface.OnCreateResourcesCallback;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;
import org.andengine.ui.IGameInterface.OnPopulateSceneCallback;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.LayoutGameActivity;

import com.example.pigparty.SceneManager.SceneType;



import android.opengl.GLES20;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;

/**
 * This is the game activity for traffic dodge.
 * Source code started from an example provided by andegnine (our game engine)
 * 
 * A couple of notes about some variables:
 *   TextureRegion class defines the rectangle for a sprite. It is used to tell the
 *     sprite which part of the texture the sprite is showing. So it is required
 *     in the setup of the game that each sprite have a TextureRegion
 *   Sprite class and AnimatedSprite class is a subclass of Texture which pretty
 *     much just displays the image you want within the texture region defined
 *   The params CAMERA_WIDTH/HEIGHT define the dimensions of our game. These
 *      are hard coded which is one advantage of using adengine so we dont have
 *      to worry about different screen resolutions
 *   Scene class defines what is currently shown on the camera
 *   
 *   This code has some stuff with PhysicsWorld class. We dont use this code at all
 *   in our game but I want to leave it here as a reference on how to use gravity
 *   in a game in case we need it for a different mini game
 * 
 * 3/30 - added code for to handle pause/menu options
 *      code now implements IOnMenuItemClickListener and its methods
 *      added a params to handle the Menu Scene
 *      Added Methods:
 *      	onKeyDown()
 *      	onMenuItemClicked()
 *      	CreateMenuScene()
 *      Im not sure yet if I want to create a new Game Activty for each mini game
 *      or if I create a bunch of mini-game classes that have methods
 *      for loading the scene to the camera and gameplay
 *      if I create a new activty for each mini-game, I need to disable the back button
 *      I wont know until I try making the next mini-game and see how it flows. So dont
 *      worry about it yet
 *      **Dont make a new activity for each game
 *      here is something useful:
 *      http://www.matim-dev.com/full-game-tutorial---part-2.html
 * 
 * 3/31 - adding code for mini-game class hierarchy
 * 		created the MiniGame base class
 * 		created the SceneManager (gameManager)
 * 		created the ResourceManager
 * 		created a loading screen while traffic-dodge loads its resources
 * 
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga
 *
 * @author Nicolas Gramlich
 * @since 18:47:08 - 19.03.2010
 */
public class MainActivity extends BaseGameActivity implements IAccelerationListener, IOnSceneTouchListener {
	// ===========================================================
	// Constants
	// ===========================================================

	protected static final int CAMERA_WIDTH = 720;
	protected static final int CAMERA_HEIGHT = 480;
	protected static final int MENU_RESET = 0;
	protected static final int MENU_QUIT = MENU_RESET + 1;
	// ===========================================================
	// Fields
	// ===========================================================

	protected Camera mCamera; 

	
	private ResourcesManager resourcesManager;
	


	private float accelerometerData;
	
	// ===========================================================
	// Fields for Socket
	// ===========================================================
	private DataSender sender;
	private DataOutputStream DOS;
	private Thread thread;
	private String ip;
	// ===========================================================
	// Fields for Menu Scene
	// ===========================================================
	protected MenuScene mMenuScene;
	private float data;
	private float olddata;
	
	// ===========================================================
	// Constructors
	// ===========================================================


	
	/**
	 * This method returns the current value of the accelerometer data
	 * We use it in the DataSender class to retrieve accelerometer data
	 * in a different thread and send it to the server
	 * @return
	 */
	public float getAccelerometerData(){
		return accelerometerData;
	}


	/**
	 * This is a method required by any BaseGameActivity
	 * This method always sets up the camera and for us it will
	 * also create a new Thread to continually send accelerometer data
	 * to the server. We need a new thread because any socket code cant be
	 * called in the main thread
	 */
	@Override
	public EngineOptions onCreateEngineOptions() {

		//Get the ipaddress from the ConnectActivty
		//Intent intent = getIntent();
		//ip = intent.getStringExtra("ipaddress");
		ip="172.31.154.25";
		olddata = 0;
		//create camera
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		Log.d(null,"INSIDE ONCREATEENGINEOPTIONS");
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}

	
	/** 
	 * This is where we load all of the resources needed to display the game
	 * Initialization of all TextureRegions occurs here
	 */
	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException{
		
		//create the resource manager
		ResourcesManager.prepareManager(mEngine, this, mCamera, getVertexBufferObjectManager());
	    resourcesManager = ResourcesManager.getInstance();
	    //resourcesManager.loadMenuResources();
	    ResourcesManager.getInstance().loadGameResources();
		Log.d(null,"FINISHED CREATE RESOURCES");
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	
	
	
	/**
	 * After all resources are loaded, this method actually creates the scene
	 */
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		Log.d(null,"STARTING ONCREATESCENE()");
		this.mEngine.registerUpdateHandler(new FPSLogger());

		SceneManager.getInstance().createLoadingScene(pOnCreateSceneCallback);
		//Create the MenuScene
		//this.createMenuScene();
		
		//pOnCreateSceneCallback.onCreateSceneFinished(SceneManager.getInstance().getCurrentScene());
		Log.d(null,"FINISHED CALLING CREATELOADINGSCENE()");
		

		
		
	}
	
	/**
	 * So in our code, the loading screen displays while the game
	 * loads all of the game resources.
	 * This method says what to do once the resources have finished loading
	 * which is to display the GameScene instead of LoadingScene
	 * 
	 */
	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		// TODO Auto-generated method stub
	    mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() 
	    {
	            public void onTimePassed(final TimerHandler pTimerHandler) 
	            {
	                mEngine.unregisterUpdateHandler(pTimerHandler);
	                SceneManager.getInstance().createStartScene();
	                //SceneManager.getInstance().createGoodJobScene();
	              /*  mEngine.unregisterUpdateHandler(pTimerHandler);
	                
	        		//Give the ipaddress to the new thread to connect
	        		sender = new DataSender(ResourcesManager.getInstance().activity,ip);

	        		accelerometerData=0;
	        		thread = new Thread(sender);
	        		thread.start();
	                SceneManager.getInstance().createTrafficScene();*/

	            }
	    }));
	    pOnPopulateSceneCallback.onPopulateSceneFinished();
		
	}

	
	/**
	 * this is required for an touchable screen
	 * it is part of the source code/physics example that we do not currently use
	 */
	@Override
	public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
		return false;

	}

	
	@Override
	public void onAccelerationAccuracyChanged(final AccelerationData pAccelerationData) {

	}

	/**
	 * This is an abstract method from IAccelerationListener.
	 * When the Accelerometer data changes it rotates the steering wheel 
	 * accordingly,and updates the current value of 
	 * the accelerometerData parameter
	 */
	@Override
	public void onAccelerationChanged(final AccelerationData pAccelerationData) {
		SceneManager sm = SceneManager.getInstance();
		//Log.e(null,"acc changed");
		if(sm.getCurrentSceneType() == SceneType.SCENE_TRAFFIC){
			
			accelerometerData = (float)5*pAccelerationData.getX();
			TrafficDodgeScene curscene = (TrafficDodgeScene) sm.getCurrentScene();
			curscene.wheelSprite.setRotation(accelerometerData);
			data = getAccelerometerData();
			//Log.e(null,"acc data is "+data);
			if(data>10)
				data='r';
			else if(data<-10)
				data='l';
			else
				data='n';
			if(data!=olddata){
				char bdata = (char) data;
				ConnectionManager.getInstance().sendChar(bdata);
				olddata=data;
			}
		}
		//theWheel.setRotation(accelerometerData);
		//Log.d(null, "X = "+accelerometerData);
	}

	@Override
	public void onResumeGame() {
		super.onResumeGame();

		this.enableAccelerationSensor(this);
	}

	@Override
	public void onPauseGame() {
		super.onPauseGame();
		this.disableAccelerationSensor();
	}

	
	/**
	 * This method checks to see if the user has clicked the menu button
	 * This is the key on every android phone. If the button is pressed,
	 * it adds the menu on top of the main scene.
	 * 
	 * I NEED TO ADD SOMETHING TO HANDLE WHAT HAPPENS IN THE DATASENDER THREAD
	 * WE NEED TO TELL THE COMPUTER SIDE THAT THE PHONE IS CURRENTLY PAUSED AND TO
	 * PAUSE THE GAME ON THE COMPUTER SIDE TOO
	 *    maybe i change accelerometerData = 1111 or something and if the computer
	 *    receives this special value it knows to pause the game until it receives
	 *    the resume key (maybe 2222 or something) 
	 * Do i need to call this.disableAccelerationSensor()? or does the hitting the 
	 * MENU Button natively call the onPauseGame() method? idk yet
	 * 
	 * Hitting the Menu Button doesnt pause the game.
	 * Calling onPauseGame pauses everything including the menu we created
	 * so we cant call this.
	 * 
	 * I decided to disableAccelerometerSensor() when this button is pressed.
	 * I dont know what happens in the thread w/o seeing it on Praveen's side
	 * but I'm assuming it will continually send the last value read from 
	 * the accelerometer. So if we manually change it to something we should 
	 * be good
	 */@Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) 
	 {  
	     if (keyCode == KeyEvent.KEYCODE_BACK)
	     {
	         SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
	     }
	     return false; 
	 }
	 
	 public void enableAccelerometer(){
		 this.enableAccelerationSensor(this);
	 }
	 
	 public void disableAccelerometer(){
		 this.disableAccelerationSensor();
	 }
	/*@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if(pKeyCode == KeyEvent.KEYCODE_MENU && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			SceneManager sm = SceneManager.getInstance();
			//If the Menu Scene is showing right now
			if(sm.getCurrentScene().hasChildScene()) {
				this.enableAccelerationSensor(this);

				this.mMenuScene.back();
			//Otherwise Display it
			} else {
				this.disableAccelerationSensor();

				sm.getCurrentScene().setChildScene(this.mMenuScene, false, true, true);
			}
			return true;
		} 
		else if (pKeyCode == KeyEvent.KEYCODE_BACK)
	    {
	        SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
	        return true;
	    }
	    
		else {
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}*/
	
	
	/**
	 * This method is required for IOnMenuItemClicked
	 * It says what happens if u hit one each item in the MenuScreen
	 */
	/*@Override
	public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
		switch(pMenuItem.getID()) {
			case MENU_RESET:
				SceneManager sm = SceneManager.getInstance();
				this.enableAccelerationSensor(this);

				sm.getCurrentScene().reset();

				sm.getCurrentScene().clearChildScene();
				//this.mMenuScene.reset();
				return true;
			case MENU_QUIT:

				this.finish();
				return true;
			default:
				return false;
		}
	}*/
	
	@Override
	protected void onDestroy()
	{
	    super.onDestroy();
	        
	    if (this.isGameLoaded())
	    {
	        System.exit(0);    
	    }
	}



	/**
	 * This method creates the Menu Scene. It adds the proper id to each button
	 * so OnMenuItemClicked knows which button is which
	 */
	/*protected void createMenuScene() {
		this.mMenuScene = new MenuScene(this.mCamera);

		final SpriteMenuItem resetMenuItem = new SpriteMenuItem(MENU_RESET, resourcesManager.mMenuResetTextureRegion, this.getVertexBufferObjectManager());
		resetMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.mMenuScene.addMenuItem(resetMenuItem);

		final SpriteMenuItem quitMenuItem = new SpriteMenuItem(MENU_QUIT, resourcesManager.mMenuQuitTextureRegion, this.getVertexBufferObjectManager());
		quitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.mMenuScene.addMenuItem(quitMenuItem);

		this.mMenuScene.buildAnimations();

		this.mMenuScene.setBackgroundEnabled(false);

		this.mMenuScene.setOnMenuItemClickListener(this);
	}*/





	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}