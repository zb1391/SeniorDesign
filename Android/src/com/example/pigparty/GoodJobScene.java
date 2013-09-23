package com.example.pigparty;

import java.util.Random;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.example.pigparty.SceneManager.SceneType;

public class GoodJobScene extends MiniGameScene {
	private Sprite balloons,goodJob;
	private AnimatedSprite confetti,pig;
	private PhysicsWorld mPhysicsWorld;
	
	@Override
	public void createScene() {
		
		setBackground(new Background(0.72f, 0.83f, 0.94f));
		//Log.d(null,"inside createScene");
		// TODO Auto-generated method stub

		
		this.registerUpdateHandler(new TimerHandler(2.5f, new ITimerCallback(){

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				//SceneManager.getInstance().createTooBadScene();
				SceneManager.getInstance().loadGameScene(engine);
				
			}
			
		}));
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
		Random random = new Random();
		Balloon.makeBalloons(mPhysicsWorld, this);
		/*balloons = new Sprite(0, 0, ResourcesManager.getInstance().mGoodJobBalloons, vbom);
		balloons.setPosition(0,0);
		attachChild(balloons);*/
		//Log.d(null,"finished creating balloon setting position attaching child");
		confetti = new AnimatedSprite(150,110,ResourcesManager.getInstance().mGoodJobConfettiTextureRegion,vbom);
		confetti.animate(100);
		attachChild(confetti);
		
		pig = new AnimatedSprite(275,150,ResourcesManager.getInstance().mGoodJobPigTextureRegion,vbom);
		pig.animate(150);
		attachChild(pig);
		
		goodJob = new Sprite(0,0,ResourcesManager.getInstance().mGoodJobText,vbom);
		goodJob.setPosition(120,60);
		attachChild(goodJob);
		

		Log.d(null,"finished creating scene");
		
	}

	public void onReceive(){
		
	}
	
	@Override
	public void onBackKeyPressed() {

	} 

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_GOODJOB;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub

	}

}
