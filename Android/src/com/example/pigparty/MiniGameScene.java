package com.example.pigparty;



import java.net.Socket;
import java.util.LinkedList;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.example.pigparty.SceneManager.SceneType;



import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public abstract class MiniGameScene extends Scene implements Receiver,IOnMenuItemClickListener{
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
	protected static final int MENU_CONTINUE = 0;
	protected static final int MENU_QUIT = 1;
	
    protected Engine engine;
    protected Activity activity;
    protected ResourcesManager resourcesManager;
    protected VertexBufferObjectManager vbom;
    protected Camera camera;
    protected LinkedList<Thread> threads;
    protected ReceiverThread receiverthread;
    protected MenuScene pauseScreen;
    //protected Receiver receiver;
    //---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------
    
    public MiniGameScene()
    {
        this.resourcesManager = ResourcesManager.getInstance();
        this.engine = resourcesManager.engine;
        this.activity = resourcesManager.activity;
        this.vbom = resourcesManager.vbom;
        this.camera = resourcesManager.camera;
        receiverthread = new ReceiverThread();
        receiverthread.setListener(this);
        createScene();


        
    }
	public void createPauseScene(){
		ResourcesManager resourcesManager = ResourcesManager.getInstance();
		((MainActivity) activity).enableAccelerometer();
		
		
		pauseScreen = new MenuScene(camera);
		
		//Set up background image
		final Sprite menuBackgroundSprite = new Sprite(0,0,resourcesManager.mPauseBackgroundTextureRegion,vbom);
		pauseScreen.attachChild(menuBackgroundSprite);
		menuBackgroundSprite.setPosition(200, 20);
		//Set up continue button
		final IMenuItem continueButton = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_CONTINUE, resourcesManager.mPauseContinueTextureRegion, resourcesManager.activity.getVertexBufferObjectManager()), 1.2f, 1);
		pauseScreen.addMenuItem(continueButton);
		
		//Set up quit button
		final IMenuItem quitButton  = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_QUIT, resourcesManager.mPauseQuitTextureRegion, resourcesManager.activity.getVertexBufferObjectManager()), 1.2f, 1);
		pauseScreen.addMenuItem(quitButton);
		
	    pauseScreen.buildAnimations();
	    pauseScreen.setBackgroundEnabled(false);
	    
	    continueButton.setPosition(270, 200);
	    quitButton.setPosition(320, 240);
	    
	    pauseScreen.setOnMenuItemClickListener(this);
	    
	    
	}
    public void onReceive(){
    	char fromserver = receiverthread.fromServer;
    	Log.i(null,"received "+fromserver+ " in "+SceneManager.getInstance().getCurrentSceneType().toString());
    	if(fromserver=='f'){
    		//SceneManager.getInstance().createLoadingScene(new OnCreateSceneCallback());
    		SceneManager.getInstance().createTooBadScene();
    	}
    	else if(fromserver=='w'){
    		SceneManager.getInstance().createGoodJobScene();
    	}
    	else if(fromserver=='j'){
    		SceneManager.getInstance().createStartScene();
    	}
    	else if(fromserver=='q'){
    		Log.e(null,"User Quit Game");
    	}
    }

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
        switch(pMenuItem.getID())
        {
        case MENU_CONTINUE:
			((MainActivity) activity).enableAccelerometer();
			ConnectionManager.getInstance().sendChar('p');
			this.pauseScreen.back();
            return true;
        case MENU_QUIT:
        	SceneManager.getInstance().createStartScene();
        	ConnectionManager.getInstance().sendChar('q');
            return true;
        default:
            return false;
    }
	}
	
    
    //---------------------------------------------
    // ABSTRACTION
    //---------------------------------------------
    
    public abstract void createScene();
    
    public abstract void onBackKeyPressed();
    
    public abstract SceneType getSceneType();
    
    public abstract void disposeScene();
    

    
    
}

