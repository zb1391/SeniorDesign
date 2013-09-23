package com.example.pigparty;



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


public class LoadingScene extends MiniGameScene{

	private Sprite splash;
	private AnimatedSprite ellipse;
	private PhysicsWorld mPhysicsWorld;
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		setBackground(new Background(0.72f, 0.83f, 0.94f));
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
		
		splash = new Sprite(720,480,resourcesManager.mloadingTextureRegion,vbom);
		splash.setPosition(0,0);
		attachChild(splash);
		ellipse = new AnimatedSprite(570, 215, resourcesManager.mellipseRegion, vbom);
		ellipse.animate(180);
		attachChild(ellipse);
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
	    splash.detachSelf();
	    splash.dispose();
	    this.detachSelf();
	    this.dispose();
	}
	
	public void onReceive(){
		Log.e(null, "inside loading screens onreceive");
		char game = receiverthread.fromServer;
        if(game=='1'){
        	SceneManager.getInstance().createTrafficScene();
        }
        else if(game=='2'){
        	SceneManager.getInstance().createBalloonScene();
        }
        else if(game=='4'){
        	SceneManager.getInstance().PokePigTarget=1;
        	Log.d(null,"Trying to create poke scene");
        	SceneManager.getInstance().createPokeScene();
        }
        else if(game=='5'){
        	SceneManager.getInstance().PokePigTarget=2;
        	Log.d(null,"Trying to create poke scene");
        	SceneManager.getInstance().createPokeScene();
        }
        else if(game=='6'){
        	SceneManager.getInstance().PokePigTarget=3;
        	Log.d(null,"Trying to create poke scene");
        	SceneManager.getInstance().createPokeScene();
        }
        else{
        	Log.e(null,"Something bad happened");
        	Log.e(null,"Attempting to retrieve game from server again");
        	ConnectionManager.getInstance().sendChar('z');
        	receiverthread= new ReceiverThread();
        	receiverthread.setListener(this);
        	receiverthread.execute();
        	Log.e(null,"Sent a z and waiting for next game");
        }
	}

}

