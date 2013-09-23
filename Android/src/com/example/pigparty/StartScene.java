package com.example.pigparty;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.example.pigparty.SceneManager.SceneType;

public class StartScene extends MiniGameScene implements IOnMenuItemClickListener{

	public static final int MENU_START = 0;
	public static final int MENU_QUIT = 1;
	public static final int MENU_CONNECT = 2;
	private MenuScene mMenuScene;
	private Sprite backgroundSprite,startButton, quitButton, connectButton;
	private PhysicsWorld mPhysicsWorld;
	private StartScene scene;

	@Override
	public void createScene() {
		scene = this;
		this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, 0), false);

		final VertexBufferObjectManager vertexBufferObjectManager = vbom;
		
		final Rectangle left = new Rectangle(0, 0, 2, MainActivity.CAMERA_HEIGHT, vertexBufferObjectManager);
		final Rectangle right = new Rectangle(MainActivity.CAMERA_WIDTH - 2, 0, 2, MainActivity.CAMERA_HEIGHT, vertexBufferObjectManager);

		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 1f,0);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, left, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, right, BodyType.StaticBody, wallFixtureDef);

		attachChild(left);
		attachChild(right);

		registerUpdateHandler(this.mPhysicsWorld);
		Balloon.makeBalloons(mPhysicsWorld, this);
		
		startButton = new Sprite(0,0,resourcesManager.mStartTextureRegion, resourcesManager.activity.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				startButton.setScale(1.1f);
				
				this.registerUpdateHandler(new TimerHandler(.2f,new ITimerCallback(){

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub
						startButton.setScale(1f);
			        	if(ConnectionManager.getInstance().isConnected()==true)
			        		//SceneManager.getInstance().createLoadingScene();
			        		SceneManager.getInstance().loadGameScene(engine);
			        		//SceneManager.getInstance().createBalloonScene();
			        	else
			        		resourcesManager.gameToast("Please Connect to Computer First");
					}
					
				}));
				return true;
				
			}
		};
		registerTouchArea(startButton);
		startButton.setPosition(90,340);
		
		quitButton = new Sprite(0,0,resourcesManager.mQuitTextureRegion, resourcesManager.activity.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				quitButton.setScale(1.1f);
				
				this.registerUpdateHandler(new TimerHandler(.2f,new ITimerCallback(){

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub
						quitButton.setScale(1f);
			        	if(ConnectionManager.getInstance().isConnected()==true)
			        		ConnectionManager.getInstance().close();
			        	else
			        		System.exit(0);
			           
					}
					
				}));
				return true;
				
			}
		};
		registerTouchArea(quitButton);

		connectButton = new Sprite(0,0,resourcesManager.mConnectTextureRegion, resourcesManager.activity.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				connectButton.setScale(1.1f);
				
				this.registerUpdateHandler(new TimerHandler(.2f,new ITimerCallback(){

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub
						connectButton.setScale(1f);
						SceneManager.getInstance().createConnectScene();
					}
					
				}));
				return true;
				
			}
		};
		registerTouchArea(connectButton);
		
		createBackground();
	    connectButton.setPosition(250,340);
	    quitButton.setPosition(474,340);
	    attachChild(quitButton);
	    attachChild(startButton);
	    attachChild(connectButton);
		
		//createMenuChildScene();

	}
	//186 217 242
	private void createBackground(){
		setBackground(new Background(0.72f, 0.83f, 0.94f));
		backgroundSprite = new Sprite(720,480,resourcesManager.mStartBackgroungTextureRegion,vbom);
		backgroundSprite.setPosition(0,0);
		attachChild(backgroundSprite);
	}
	
	private void createMenuChildScene(){
		ResourcesManager resourcesManager = ResourcesManager.getInstance();
		
		
		mMenuScene = new MenuScene(camera);
		Balloon.makeBalloons(mPhysicsWorld, mMenuScene);
		//Set up start button
		final IMenuItem startButton = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_START, resourcesManager.mStartTextureRegion, resourcesManager.activity.getVertexBufferObjectManager()), 1.2f, 1);
		mMenuScene.addMenuItem(startButton);
		
		//Set up quit button
		final IMenuItem quitButton  = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_QUIT, resourcesManager.mQuitTextureRegion, resourcesManager.activity.getVertexBufferObjectManager()), 1.2f, 1);
		mMenuScene.addMenuItem(quitButton);
		
		final IMenuItem connectButton = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_CONNECT, resourcesManager.mConnectTextureRegion, resourcesManager.activity.getVertexBufferObjectManager()), 1.2f, 1);
		mMenuScene.addMenuItem(connectButton);
		
	    mMenuScene.buildAnimations();
	    mMenuScene.setBackgroundEnabled(true);
	    
		//Set the positions on screen
	    startButton.setPosition(90,340);
	    connectButton.setPosition(250,340);
	    quitButton.setPosition(474,340);
	    
		//startButton.setPosition(startButton.getX(), startButton.getY() + 10);
		//quitButton.setPosition(quitButton.getX(), quitButton.getY() - 110);
	    
		this.mMenuScene.setOnMenuItemClickListener(this);
		
		//attach the buttons to the StartScene
		setChildScene(this.mMenuScene, false, true, true);
	}
	
	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_START;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		
		Log.e(null, "Menu Item clicked! with ID = "+pMenuItem.getID());
        switch(pMenuItem.getID())
        {
        case MENU_START:
        	if(ConnectionManager.getInstance().isConnected()==true)
        		//SceneManager.getInstance().createLoadingScene();
        		SceneManager.getInstance().loadGameScene(engine);
        		//SceneManager.getInstance().createBalloonScene();
        	else
        		resourcesManager.gameToast("Please Connect to Computer First");
            return true;
        case MENU_QUIT:
        	if(ConnectionManager.getInstance().isConnected()==true)
        		ConnectionManager.getInstance().close();
        	else
        		System.exit(0);
            return true;
        case MENU_CONNECT:
        	SceneManager.getInstance().createConnectScene();
        	return true;
        default:
            return false;
    }
	}


}
