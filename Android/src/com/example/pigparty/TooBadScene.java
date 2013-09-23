package com.example.pigparty;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;

import com.example.pigparty.SceneManager.SceneType;

public class TooBadScene extends MiniGameScene {

	private Sprite background;
	private AnimatedSprite pig;
	
	@Override
	public void createScene() {

		setBackground(new Background(0.72f, 0.83f, 0.94f));
		// TODO Auto-generated method stub
		background = new Sprite(0,0,ResourcesManager.getInstance().mTooBadBackgroundTextureRegion,vbom);
		background.setPosition(0,0);
		attachChild(background);
		
		pig = new AnimatedSprite(250,150,ResourcesManager.getInstance().mTooBadPigTextureRegion,vbom);
		pig.animate(150);
		attachChild(pig);
		
		this.registerUpdateHandler(new TimerHandler(4f, new ITimerCallback(){

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				SceneManager.getInstance().loadGameScene(engine);
				
			}
			
		}));
	}

	@Override
	public void onBackKeyPressed() {

	} 

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_TOOBAD;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub

	}

}
