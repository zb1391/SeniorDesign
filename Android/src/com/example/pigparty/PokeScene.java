package com.example.pigparty;

import java.util.Random;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import com.example.pigparty.SceneManager.SceneType;

public class PokeScene extends MiniGameScene {
	public int mFaceCount;
	public static int difficulty = 0;
	private PhysicsWorld mPhysicsWorld;
	private int target;
	private static final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 1f, 0f);	
	
	private Random random;
	private Sprite bg;
	private Scene scene;
	

	
	@Override
	public void createScene() {
		super.createPauseScene();
		// TODO Auto-generated method stub
		random = new Random();
		createBackground();

		scene=this;
		mFaceCount=0;
		ConnectionManager.getInstance().sendChar('s');
		createPiggies(SceneManager.getInstance().PokePigTarget);
		receiverthread.execute();
	}

	private void createBackground(){
		
		
		bg = new Sprite(0,0,resourcesManager.mPokeBackgroundRegion,vbom);
		bg.setPosition(0, 0);
		attachChild(bg);

		this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, 0), false);

		final VertexBufferObjectManager vertexBufferObjectManager = vbom;
		final Rectangle ground = new Rectangle(0, MainActivity.CAMERA_HEIGHT - 2, MainActivity.CAMERA_WIDTH, 2, vertexBufferObjectManager);
		final Rectangle roof = new Rectangle(0, 0, MainActivity.CAMERA_WIDTH, 2, vertexBufferObjectManager);
		final Rectangle left = new Rectangle(0, 0, 2, MainActivity.CAMERA_HEIGHT, vertexBufferObjectManager);
		final Rectangle right = new Rectangle(MainActivity.CAMERA_WIDTH - 2, 0, 2, MainActivity.CAMERA_HEIGHT, vertexBufferObjectManager);

		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 1f,0);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, ground, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, roof, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, left, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, right, BodyType.StaticBody, wallFixtureDef);

		attachChild(ground);
		attachChild(roof);
		attachChild(left);
		attachChild(right);

		registerUpdateHandler(this.mPhysicsWorld);
	}
	
	
	private void createPiggies(int x){
		//target - the color of the pig to destroy
		//level 1 - create 12 pigs of color = target
		//level 2 - create 6 pigs of color = target, 6 pigs of randomball
		//level 3 - create 12 pigs of randomball
		//I want 6 pigs of the target
		//Then I want 6 random colored (any including the target)
		//Then I want 1 randomBall
		target = x;
		if(PokeScene.difficulty<2){
			if(target==1){
				for(int i=0;i<8;i++)
					addPig1((float)random.nextInt(640)+1,(float)random.nextInt(400)+1);
			}
			else if(target==2)
				for(int i = 0;i<8;i++)
					addPig2((float)random.nextInt(640)+1,(float)random.nextInt(400)+1);
			else
				for(int i =0;i<8;i++)
					addPig3((float)random.nextInt(640)+1,(float)random.nextInt(400)+1);
		}
		else if(PokeScene.difficulty>=2 && PokeScene.difficulty<4){
			if(target==1){
				for(int i=0;i<4;i++)
					addPig1((float)random.nextInt(640)+1,(float)random.nextInt(400)+1);
			}
			else if(target==2)
				for(int i = 0;i<4;i++)
					addPig2((float)random.nextInt(640)+1,(float)random.nextInt(400)+1);
			else
				for(int i =0;i<4;i++)
					addPig3((float)random.nextInt(640)+1,(float)random.nextInt(400)+1);
			for(int i=0;i<4;i++){
				RandomBall b = new RandomBall((float)random.nextInt(640)+1,(float)random.nextInt(400)+1,resourcesManager.mPiggy1,vbom,1);
				b.createTimerHandler();
				jumpFace(b);
			}
		}
		else{
			for(int i=0;i<8;i++){
				RandomBall b = new RandomBall((float)random.nextInt(640)+1,(float)random.nextInt(400)+1,resourcesManager.mPiggy1,vbom,1);
				b.createTimerHandler();
				jumpFace(b);
			}
		}
	}
	
	private void jumpFace(final AnimatedSprite face) {
		final Body faceBody = (Body)face.getUserData();

		final Vector2 velocity = Vector2Pool.obtain(5+random.nextInt(5),5+random.nextInt(5));
		//final Vector2 velocity = Vector2Pool.obtain(2,2);
		faceBody.setLinearVelocity(velocity);
		Vector2Pool.recycle(velocity);
	}

	private void addPig1(final float pX,final float pY){
		mFaceCount++;
		Debug.d("Faces: " + mFaceCount);
		Ball b = new Ball(pX, pY, resourcesManager.mPiggy1, vbom,1);
	}
	private void addPig2(final float pX,final float pY){
		mFaceCount++;
		Debug.d("Faces: " + mFaceCount);
		Ball b =new Ball(pX, pY, resourcesManager.mPiggy2, vbom,2);
	}
	private void addPig3(final float pX,final float pY){
		mFaceCount++;
		Debug.d("Faces: " + mFaceCount);
		Ball b = new Ball(pX, pY, resourcesManager.mPiggy3, vbom,3);

	}
	
	private void removeFace(final AnimatedSprite face) {
		mFaceCount--;
		Log.d(null,"facecount is "+mFaceCount);
		final PhysicsConnector facePhysicsConnector = this.mPhysicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(face);

		this.mPhysicsWorld.unregisterPhysicsConnector(facePhysicsConnector);
		this.mPhysicsWorld.destroyBody(facePhysicsConnector.getBody());

		unregisterTouchArea(face);
		detachChild(face);
		System.gc();
	}
	
	private class Ball extends AnimatedSprite{
		protected int id;
		private PhysicsConnector connector;
		public final Body body;
		
		public Ball(final float pX, final float pY, final TiledTextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager,int id) {
			super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
			this.id=id;
			body = PhysicsFactory.createCircleBody(mPhysicsWorld, this, BodyType.DynamicBody, FIXTURE_DEF);
			this.setScale(1.3f);
			this.animate(400);
			connector = new PhysicsConnector(this, body, true, true);
			mPhysicsWorld.registerPhysicsConnector(connector);
			this.setUserData(body);
			scene.attachChild(this);
			scene.registerTouchArea(this);
			jumpFace(this);

		}
		
		@Override
		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			
			Log.d(null,"id is "+id);
			removeFace(this);
			if(id!=target){
				Log.e(null,"Poked Wrong Pig");
				ConnectionManager.getInstance().sendChar('f');
			}
			else if(mFaceCount==0){
				Log.d(null,"Finished poking all the pigs");
				ConnectionManager.getInstance().sendChar('w');
			}
			
			return true;
			
		}
	}
	
	
	private class RandomBall extends Ball{
		public int x;
		public TimerHandler spriteTimeHandler;
		public RandomBall(final float pX, final float pY, final TiledTextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager,int id) {
			super(pX,pY,pTextureRegion,pVertexBufferObjectManager,id);
			x=0;
			createTimerHandler();
			this.registerUpdateHandler(spriteTimeHandler);
			mFaceCount++;
		}
		
		public RandomBall(final float pX, final float pY, final TiledTextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager,int x,int id) {
			this(pX,pY,pTextureRegion,pVertexBufferObjectManager,id);
			this.x=x;
		}
		
		public void createTimerHandler(){
			float time = (float) random.nextInt(3)+1;
			spriteTimeHandler = new TimerHandler(time,new pigITCallBack(this));
		}
		
		@Override
		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			Log.d(null,"id is "+id);

			engine.unregisterUpdateHandler(spriteTimeHandler);
			spriteTimeHandler=null;
			removeFace(this);
			if(id!=target){
				Log.e(null,"Poked Wrong Pig");
				ConnectionManager.getInstance().sendChar('f');
			}
			else if(mFaceCount==0){
				Log.d(null,"Finished poking all the pigs");
				ConnectionManager.getInstance().sendChar('w');
			}
			return true;
			
		}
		
		
	}
	
	private class pigITCallBack implements ITimerCallback{
		private RandomBall rb;
		
		public pigITCallBack(RandomBall rb){
			this.rb=rb;
		}
		
        @Override
        public void onTimePassed(final TimerHandler pTimerHandler) {
                // TODO Auto-generated method stub                  
        	pTimerHandler.reset();

        	
        	if(rb.x==0){
        		removeFace(rb);
        		rb = new RandomBall(rb.getX(),rb.getY(),resourcesManager.mPiggy2,vbom,1,2);

        	}
        	else if(rb.x==1){
        		removeFace(rb);
        		rb = new RandomBall(rb.getX(),rb.getY(),resourcesManager.mPiggy3,vbom,2,3);

        	}
        	else{
        		removeFace(rb);
        		rb = new RandomBall(rb.getX(),rb.getY(),resourcesManager.mPiggy1,vbom,0,1);

        	}
        	  
         }
		
	}
	
	public void onReceive(){
		char fromServer = receiverthread.fromServer;
		if(fromServer=='f')
			SceneManager.getInstance().createTooBadScene();
		else if(fromServer=='w'){
			PokeScene.difficulty++;
			SceneManager.getInstance().createGoodJobScene();
		}
		else if(fromServer=='j'){
			PokeScene.difficulty=0;
			SceneManager.getInstance().createStartScene();
		}
		else if(fromServer=='q'){
			PokeScene.difficulty=0;
			Log.e(null,"User Quit Game");
		}
	}
	
	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		//If the Menu Scene is showing right now
		if(hasChildScene()) {
			ConnectionManager.getInstance().sendChar('p');
			this.pauseScreen.back();
		//Otherwise Display it
		} else {
			ConnectionManager.getInstance().sendChar('p');
			setChildScene(pauseScreen, false, true, true);
		}
	} 
	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_POKE;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub

	}
	

}
