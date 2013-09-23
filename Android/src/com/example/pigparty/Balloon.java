package com.example.pigparty;

import java.util.Random;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Balloon extends Sprite{
	private static final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 1f, 0f);	
	private Random random;
	public Sprite string;
	private PhysicsConnector connector;
	public  Body body;
	private PhysicsWorld mPhysicsWorld;
	private Scene scene;
	private Balloon balloon;
	public Balloon(final float pX, final float pY, final ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager,final PhysicsWorld mPhysicsWorld,Scene scene) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		//this.string = new Sprite(pX-88,pY,string,pVertexBufferObjectManager);
		
		random = new Random();
		float scale = ((float)random.nextInt(5)+5)/10;
		Log.e(null,"scale is "+scale);
		setScale(scale);
		this.mPhysicsWorld=mPhysicsWorld;
		this.scene=scene;
		createPhysics();
		balloon = this;
		scene.attachChild(this);
		this.registerUpdateHandler(new TimerHandler(.05f,new ITimerCallback(){

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				pTimerHandler.reset();
				//Log.d(null,"pos is "+getY());
				if(getY()<(-30- getHeightScaled()) || getY() >700){
					//Log.e(null,"reseting position");
					float scale = ((float)random.nextInt(5)+5)/10;
					balloon.setScale(scale);
					setPosition((float)random.nextInt(720),700);
					//Destroy physics on old balloon
					mPhysicsWorld.unregisterPhysicsConnector(connector);
					mPhysicsWorld.destroyBody(connector.getBody());
					//set up new physics for new balloon
					body = PhysicsFactory.createCircleBody(mPhysicsWorld, balloon, BodyType.DynamicBody, FIXTURE_DEF);
					mPhysicsWorld.unregisterPhysicsConnector(connector);
					connector = new PhysicsConnector(balloon, body, true, true);
					mPhysicsWorld.registerPhysicsConnector(connector);
					jumpBalloon();
				}
			}
			
		}));
		jumpBalloon();
	}
	
	public void createPhysics(){
		body = PhysicsFactory.createCircleBody(mPhysicsWorld, this, BodyType.DynamicBody, FIXTURE_DEF);
		this.setUserData(body);
		connector = new PhysicsConnector(this, body, true, true);
		mPhysicsWorld.registerPhysicsConnector(connector);
	}
	
	public void jumpBalloon() {
		final Body balloonBody = (Body)getUserData();

		final Vector2 velocity = Vector2Pool.obtain(random.nextInt(10)-5,-(random.nextInt(5)+5));
		balloonBody.setLinearVelocity(velocity);
		Vector2Pool.recycle(velocity);
	}
	
	public static void makeBalloons(PhysicsWorld mPhysicsWorld, Scene scene){
		Random random = new Random();
		new Balloon(random.nextInt(720),500,ResourcesManager.getInstance().mRedBalloon,ResourcesManager.getInstance().vbom,mPhysicsWorld,scene);
		new Balloon(random.nextInt(720),500,ResourcesManager.getInstance().mOrangeBalloon,ResourcesManager.getInstance().vbom,mPhysicsWorld,scene);
		new Balloon(random.nextInt(720),500,ResourcesManager.getInstance().mYellowBalloon,ResourcesManager.getInstance().vbom,mPhysicsWorld,scene);
		new Balloon(random.nextInt(720),500,ResourcesManager.getInstance().mGreenBalloon,ResourcesManager.getInstance().vbom,mPhysicsWorld,scene);
		new Balloon(random.nextInt(720),500,ResourcesManager.getInstance().mBlueBalloon,ResourcesManager.getInstance().vbom,mPhysicsWorld,scene);
		new Balloon(random.nextInt(720),500,ResourcesManager.getInstance().mPurpleBalloon,ResourcesManager.getInstance().vbom,mPhysicsWorld,scene);
	}
}
