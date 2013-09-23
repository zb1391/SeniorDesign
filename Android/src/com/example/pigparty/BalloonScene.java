package com.example.pigparty;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import com.example.pigparty.SceneManager.SceneType;

public class BalloonScene extends MiniGameScene{
	private float curY,prevCury,startlimit,endlimit;
	private boolean up,down;
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		super.createPauseScene();
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));

		
		final Sprite bg = new Sprite(0,0,ResourcesManager.getInstance().mBalloonBackgroundRegion,vbom);
		bg.setPosition(0,0);
		
		final float centerX = 400;
		final float centerY = (MainActivity.CAMERA_HEIGHT - ResourcesManager.getInstance().mBalloonPumpTextureRegion.getHeight()) / 2;
		prevCury = centerY;
		startlimit = centerY;
		up=false;
		down=false;
		final Sprite face = new Sprite(centerX, centerY, ResourcesManager.getInstance().mBalloonPumpTextureRegion, vbom) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				//Log.d(null,"location is "+(pSceneTouchEvent.getY() - this.getHeight() / 2));
				curY = pSceneTouchEvent.getY() - this.getHeight() / 2;
				if(curY>100 && curY<300){
					//Log.d(null,"Cury is "+curY);
					//Log.i(null,"PrevCury is "+prevCury);
					if(curY > prevCury){
						down=true;
						if(up){
							startlimit=curY;
							up=false;
						}
						prevCury=curY;
					}
					else if(curY < prevCury){
						//Log.e(null,"curY is less than prevCury");
						up=true;
						if(down){
							endlimit = curY;
							if(endlimit-startlimit>10){
								Log.e(null,"send " + (endlimit-startlimit)/165*100 + " to server");
								int value = (int) ((endlimit-startlimit)/165*100);
								ConnectionManager.getInstance().sendData(Integer.toString(value));
							}
							startlimit=0;
							endlimit=0;
							down=false;
						}
						prevCury = curY;
						//Log.d(null,"send " + (endlimit-startlimit) + " to server");
					}
				}
				if(curY>100 && curY<300)
					this.setPosition(centerX, curY);
				return true;
			}
		};

		attachChild(face);
		registerTouchArea(face);
		setTouchAreaBindingOnActionDownEnabled(true);
		attachChild(bg);
		ConnectionManager.getInstance().sendChar('s');
		receiverthread.execute();
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
		// TODO Auto-generated method stub
		return SceneType.SCENE_BALLOON;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}

}
