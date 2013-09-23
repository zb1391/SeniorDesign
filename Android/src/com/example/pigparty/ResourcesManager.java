package com.example.pigparty;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;
import android.widget.Toast;



/**
 * This class is responsible for loading and unloading the resources of each
 * MiniGame
 * For us it will also provide reference to some commonly used objects
 * (camera, engine, etc)
 * @author zac
 *
 */
public class ResourcesManager {
	
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    private static final ResourcesManager INSTANCE = new ResourcesManager();
    public Engine engine;
    public MainActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;
    
    //---------------------------------------------
    // LOADING SCREEN VARIABLES
    //---------------------------------------------
    public ITextureRegion mloadingTextureRegion;
    private BitmapTextureAtlas mloadingTextureAtlas;
    public ITiledTextureRegion mellipseRegion;
    private BitmapTextureAtlas mellipseTextureAtlas;
    
    //---------------------------------------------
    // CONNECT SCENE VARIABLES
    //---------------------------------------------
    public ITextureRegion mConnectBackgroundRegion,mConnectOKButton;
    private BitmapTextureAtlas mConnectBackgroundAtlas,mConnectMenuAtlas;
    public ITextureRegion mConnectB1,mConnectB2,mConnectB3,mConnectB4,mConnectB5,mConnectB6,mConnectB7,mConnectB8,mConnectB9,mConnectB0;
    public ITextureRegion mConnectDot, mConnectDel, mConnectTestC;
    public ITexture mFontTexture;
    public Font mFont;
    
    
    //---------------------------------------------
    // TRAFFIC SCENE VARIABLES
    //---------------------------------------------
    public ITextureRegion traffic_background_region, mCarTextureRegion,mWheelTextureRegion;
    private BitmapTextureAtlas mBackgroundTextureAtlas,mStreetTextureAtlas,mCarTextureAtlas,mirrorTextureAtlas, mFreshenerTextureAtlas,mWheelTextureAtlas;
    public ITiledTextureRegion mStreetTextureRegion,mMirrorTextureRegion, mFreshenerTextureRegion;
    private BitmapTextureAtlas mTrafficAirbagAtlas1,mTrafficAirbagAtlas2,mTrafficAirbagAtlas3;
    public ITextureRegion mTrafficAigbagRegion1,mTrafficAigbagRegion2,mTrafficAigbagRegion3;

    //---------------------------------------------
    // Balloon SCENE VARIABLES
    //---------------------------------------------
	private BitmapTextureAtlas mBalloonPumpAtlas,mBalloonBackgroundTextureAtlas;
	public ITextureRegion mBalloonPumpTextureRegion,mBalloonBackgroundRegion;
    
	
    //---------------------------------------------
    // Poke SCENE VARIABLES
    //---------------------------------------------
    private BitmapTextureAtlas mPokeBackgroundAtlas,mPokePigAtlas;
    public TiledTextureRegion mPiggy1,mPiggy2,mPiggy3;
    public ITextureRegion mPokeBackgroundRegion;
	//---------------------------------------------
    // Menu Variables
    //---------------------------------------------
	private BitmapTextureAtlas mMenuTexture;
	public ITextureRegion mMenuResetTextureRegion,mMenuQuitTextureRegion;

	//---------------------------------------------
    // Start Scene Variables
    //---------------------------------------------
	private BitmapTextureAtlas mStartBackgroundAtlas,mstartTextureAtlas;
	public ITextureRegion mStartBackgroungTextureRegion, mStartTextureRegion,mConnectTextureRegion,mQuitTextureRegion;
	
	//---------------------------------------------
    // GoodJob Scene Variables
    //---------------------------------------------
	private BitmapTextureAtlas mGoodJobBalloonsAtlas,mGoodJobConfettiAtlas,mGoodJobPigAtlas,mGoodJobTextAtlas;
	public ITextureRegion mGoodJobBalloons,mGoodJobText;
	public ITiledTextureRegion mGoodJobConfettiTextureRegion,mGoodJobPigTextureRegion;
	
	//---------------------------------------------
    // TooBad Scene Variables
    //---------------------------------------------
	private BitmapTextureAtlas mTooBadBackgroundAtlas,mTooBadPigAtlas;
	public ITextureRegion mTooBadBackgroundTextureRegion;
	public ITiledTextureRegion mTooBadPigTextureRegion;
	
	//---------------------------------------------
    // Pause Screen Variables
    //---------------------------------------------
	private BitmapTextureAtlas mPauseBackgroundAtlas,mPauseMenuAtlas;
	public ITextureRegion mPauseBackgroundTextureRegion,mPauseContinueTextureRegion,mPauseQuitTextureRegion;
	
	//---------------------------------------------
    // Animated Balloon Variables
    //---------------------------------------------
	private BitmapTextureAtlas mBalloonsAtlas1,mBalloonsAtlas2;
	public ITextureRegion mRedBalloon,mOrangeBalloon,mYellowBalloon,mGreenBalloon,mBlueBalloon,mPurpleBalloon;
	
    public void loadMenuResources()
    {
        //loadMenuGraphics();
    }
    
    public void loadGameResources()
    {
        loadBalloons();
    	loadPauseGraphics();
    }
    
    /**
     * Loads the graphics for when user hits the menu button during the game
     */
   /* public void loadMenuGraphics(){
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	
		this.mMenuTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
		this.mMenuResetTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, activity, "menu_reset.png", 0, 0);
		this.mMenuQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, activity, "menu_quit.png", 0, 50);
		this.mMenuTexture.load();
    }*/
    
    public void loadBalloons(){
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Balloons/");
    	mBalloonsAtlas1 = new BitmapTextureAtlas(activity.getTextureManager(),1024,1024,TextureOptions.BILINEAR);
    	mRedBalloon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBalloonsAtlas1, activity, "red.png", 0, 0);
    	mOrangeBalloon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBalloonsAtlas1, activity, "orange.png", 151, 0);
    	mYellowBalloon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBalloonsAtlas1, activity, "yellow.png", 151*2, 0);
    	mGreenBalloon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBalloonsAtlas1, activity, "green.png", 151*3, 0);
    	mBlueBalloon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBalloonsAtlas1, activity, "blue.png", 151*4, 0);
    	mPurpleBalloon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBalloonsAtlas1, activity, "purple.png", 151*5, 0);
    	mBalloonsAtlas1.load();
    }
    
    
    /**
     * Loads the graphics for the first screen - start screen
     */
    public void loadStartGraphics(){
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Start/");
    	
    	this.mStartBackgroundAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
    	this.mStartBackgroungTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mStartBackgroundAtlas, activity, "Titlepage4.png", 0, 0);
    	this.mStartBackgroundAtlas.load();
    	
    	this.mstartTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 128, TextureOptions.BILINEAR);
    	this.mStartTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mstartTextureAtlas, activity, "start_button.png", 0, 0);
    	this.mConnectTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mstartTextureAtlas, activity, "connect_button.png", 0, 40);
    	this.mQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mstartTextureAtlas, activity, "quit_button.png", 0, 80);
    	this.mstartTextureAtlas.load();
    }
    
    public void unloadStartGraphics(){
    	mStartBackgroundAtlas.unload();
    	mStartBackgroungTextureRegion=null;
    	mstartTextureAtlas.unload();
    	mStartTextureRegion=null;
    	mConnectTextureRegion=null;
    	mQuitTextureRegion=null;
    	
    }
    public void loadTooBadGraphics(){
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/TooBad/");
    	
    	//may cause problems cuz bg is 721x481
    	this.mTooBadBackgroundAtlas = new BitmapTextureAtlas(activity.getTextureManager(),1024,512,TextureOptions.BILINEAR);
    	this.mTooBadBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mTooBadBackgroundAtlas, activity, "ToobadBG.png", 0, 0);
    	mTooBadBackgroundAtlas.load();
    	
    	
    	this.mTooBadPigAtlas = new BitmapTextureAtlas(activity.getTextureManager(),1024,256,TextureOptions.BILINEAR);
    	this.mTooBadPigTextureRegion =BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mTooBadPigAtlas, activity, "pig2.png", 0, 0, 3, 1);
    	mTooBadPigAtlas.load();
    }
    
    public void unloadTooBadGraphics(){
    	mTooBadBackgroundAtlas.unload();
    	mTooBadBackgroundTextureRegion=null;
    	mTooBadPigAtlas.unload();
    	mTooBadPigTextureRegion=null;
    }
    
    
    public void loadGoodJobGraphics(){
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/GoodJob/");
    	
    	mGoodJobBalloonsAtlas = new BitmapTextureAtlas(activity.getTextureManager(),1024,512,TextureOptions.BILINEAR);
    	mGoodJobBalloons = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mGoodJobBalloonsAtlas, activity, "Balloons.png", 0, 0);
    	mGoodJobBalloonsAtlas.load();
    	
    	mGoodJobConfettiAtlas = new BitmapTextureAtlas(activity.getTextureManager(),1024,512,TextureOptions.BILINEAR);
    	mGoodJobConfettiTextureRegion =BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mGoodJobConfettiAtlas, activity, "Confettisprite.png", 0, 0, 2, 1);
    	mGoodJobConfettiAtlas.load();
    	
    	mGoodJobPigAtlas = new BitmapTextureAtlas(activity.getTextureManager(),512,256,TextureOptions.BILINEAR);
    	mGoodJobPigTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mGoodJobPigAtlas, activity, "Goodjobpigsprite.png", 0, 0, 2, 1);
    	mGoodJobPigAtlas.load();
    	
    	mGoodJobTextAtlas = new BitmapTextureAtlas(activity.getTextureManager(),512,256,TextureOptions.BILINEAR);
    	mGoodJobText = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mGoodJobTextAtlas, activity, "Goodjobtext.png", 0, 0);
    	mGoodJobTextAtlas.load();
    }
        

    public void unloadGoodJobGraphics(){
    	mGoodJobBalloonsAtlas.unload();
    	mGoodJobBalloons=null;
    	mGoodJobConfettiAtlas.unload();
    	mGoodJobConfettiTextureRegion=null;
    	mGoodJobPigAtlas.unload();
    	mGoodJobPigTextureRegion=null;
    	mGoodJobTextAtlas.unload();
    	mGoodJobText=null;
    }
    
    public void loadPauseGraphics(){
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Pause/");
    	this.mPauseBackgroundAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
    	this.mPauseBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mPauseBackgroundAtlas, activity, "Pausedballoon.png", 0, 0);
    	this.mPauseBackgroundAtlas.load();
    	
    	this.mPauseMenuAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
    	this.mPauseContinueTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mPauseMenuAtlas, activity, "Continuebut.png", 0, 0);
    	this.mPauseQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mPauseMenuAtlas, activity, "Quitbut.png", 0, 39);
    	this.mPauseMenuAtlas.load();
    	
    }
    
    public void unloadPauseGraphics(){
    	mPauseBackgroundAtlas.unload();
    	mPauseBackgroundTextureRegion=null;
    	mPauseMenuAtlas.unload();
    	mPauseContinueTextureRegion=null;
    	mPauseQuitTextureRegion=null;
    }
    public void loadConnectGraphics(){
    	FontFactory.setAssetBasePath("Font/");
    	mFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
    	this.mFont = FontFactory.createFromAsset(activity.getFontManager(), mFontTexture, activity.getAssets(), "apple_casual.ttf", 48, true, android.graphics.Color.WHITE);
    	this.mFont.load();
    	
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Connect/");
    	
    	this.mConnectBackgroundAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
    	this.mConnectBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mConnectBackgroundAtlas, activity, "EnterIP3.png", 0, 0);
    	this.mConnectBackgroundAtlas.load();
    
    	this.mConnectMenuAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	this.mConnectB1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mConnectMenuAtlas, activity, "01.png", 0, 0);
    	this.mConnectB2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mConnectMenuAtlas, activity, "02.png", 50, 0);
    	this.mConnectB3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mConnectMenuAtlas, activity, "03.png", 100, 0);
    	this.mConnectB4 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mConnectMenuAtlas, activity, "04.png", 150, 0);
    	this.mConnectB5 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mConnectMenuAtlas, activity, "05.png", 200, 0);
    	this.mConnectB6 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mConnectMenuAtlas, activity, "06.png", 250, 0);
    	this.mConnectB7 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mConnectMenuAtlas, activity, "07.png", 450, 0);
    	this.mConnectB8 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mConnectMenuAtlas, activity, "08.png", 300, 0);
    	this.mConnectB9 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mConnectMenuAtlas, activity, "09.png", 350, 0);
    	this.mConnectB0 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mConnectMenuAtlas, activity, "0.png", 400, 0);
    	this.mConnectDot = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mConnectMenuAtlas, activity, "dot.png", 500, 0);
    	this.mConnectDel = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mConnectMenuAtlas, activity, "delete.png", 550, 0);
    	this.mConnectTestC = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mConnectMenuAtlas, activity, "Connect.png", 600, 0);
    	this.mConnectMenuAtlas.load();
    }
    
    public void unloadConnectGraphics(){
    	mConnectBackgroundAtlas.unload();
    	this.mConnectBackgroundRegion=null;
    	
    	mConnectMenuAtlas.unload();
    	mConnectB1=null;
    	mConnectB2=null;
    	mConnectB3=null;
    	mConnectB4=null;
    	mConnectB5=null;
    	mConnectB6=null;
    	mConnectB7=null;
    	mConnectB8=null;
    	mConnectB9=null;
    	mConnectB0=null;
    	mConnectDot=null;
    	mConnectDel=null;
    	mConnectTestC=null;
    }
    
    public void loadBalloonGameGraphics(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/BalloonPop/");

		this.mBalloonBackgroundTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
		this.mBalloonBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBalloonBackgroundTextureAtlas, activity, "PUMP.png", 0, 0);
		this.mBalloonBackgroundTextureAtlas.load();
		
		this.mBalloonPumpAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		this.mBalloonPumpTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBalloonPumpAtlas, activity, "H1.png", 0, 0);
		this.mBalloonPumpAtlas.load();
    }
    
    public void unloadBalloonGameGraphics(){
    	mBalloonPumpAtlas.unload();
    	mBalloonPumpTextureRegion=null;
    	mBalloonBackgroundTextureAtlas.unload();
    	mBalloonBackgroundRegion=null;
    }
    
    public void loadPokeGameGraphics(){
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Poke/");
		
    	this.mPokePigAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 128, 256, TextureOptions.BILINEAR);
		this.mPiggy1 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mPokePigAtlas, activity, "Piggy1.png", 0, 0, 2, 1); 
		this.mPiggy2 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mPokePigAtlas, activity, "Piggy2.png", 0, 50, 2, 1);
		this.mPiggy3 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mPokePigAtlas, activity, "Piggy3.png", 0, 100, 2, 1); // 64x32
		this.mPokePigAtlas.load();
		
		this.mPokeBackgroundAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
		this.mPokeBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mPokeBackgroundAtlas, activity, "BG.png", 0, 0); // 64x32
		this.mPokeBackgroundAtlas.load();
    }
    
    public void unloadPokeGameGraphics(){
    	mPokePigAtlas.unload();
    	mPiggy1=null;
    	mPiggy2=null;
    	mPiggy3=null;
    	mPokeBackgroundAtlas.unload();
    	mPokeBackgroundRegion=null;
    }
    
    /**
     * This method loads all of the graphics for the 
     * TrafficDodge MiniGame.
     */
    public void loadGameGraphics(){
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/traffic/");
    	
    	//This is loading everything from stacy's images
    	this.mBackgroundTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 720, 480, TextureOptions.BILINEAR);
    	this.traffic_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBackgroundTextureAtlas, activity, "Background_1.png", 0, 0);
    	mBackgroundTextureAtlas.load();
    	
    	this.mStreetTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
    	this.mStreetTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mStreetTextureAtlas, activity, "rs05.png", 0, 0, 3,1);
    	this.mStreetTextureAtlas.load();
    	
    	this.mCarTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 720, 480, TextureOptions.BILINEAR);
    	this.mCarTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mCarTextureAtlas, activity, "Car_dashboard.png", 0, 0);
		this.mCarTextureAtlas.load();
		
		this.mirrorTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 128, TextureOptions.BILINEAR);
		this.mMirrorTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mirrorTextureAtlas, activity, "RearviewMirrorSprite.png", 0, 0, 2,1);
		this.mirrorTextureAtlas.load();
		
		this.mFreshenerTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.mFreshenerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mFreshenerTextureAtlas, activity, "AirFreshnerSprite.png", 0, 0, 2,1);
    	this.mFreshenerTextureAtlas.load();
    	
    	this.mWheelTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(),512,512,TextureOptions.BILINEAR);
    	this.mWheelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mWheelTextureAtlas, activity, "Wheel.png", 0, 0);
    	this.mWheelTextureAtlas.load();
    	
    	mTrafficAirbagAtlas1 = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
    	this.mTrafficAigbagRegion1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mTrafficAirbagAtlas1, activity, "Airbagsprite1.png", 0, 0);
    	mTrafficAirbagAtlas1.load();
    	
    	mTrafficAirbagAtlas2 = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
    	this.mTrafficAigbagRegion2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mTrafficAirbagAtlas2, activity, "Airbagsprite2.png", 0, 0);
    	mTrafficAirbagAtlas2.load();
    	
    	mTrafficAirbagAtlas3 = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
    	this.mTrafficAigbagRegion3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mTrafficAirbagAtlas3, activity, "Airbagsprite3.png", 0, 0);
    	mTrafficAirbagAtlas3.load();
    	


    }
    
    /**
     * This unloads all of the TrafficDodge Resources.
     * We need to do this do make memory for
     * other resources in the future.
     */
    public void unloadGameGraphics(){
    	mBackgroundTextureAtlas.unload();
    	traffic_background_region=null;
    	mStreetTextureAtlas.unload();
    	mStreetTextureRegion=null;
    	mCarTextureAtlas.unload();
    	mCarTextureRegion=null;
    	mirrorTextureAtlas.unload();
    	mMirrorTextureRegion=null;
    	mFreshenerTextureAtlas.unload();
    	mFreshenerTextureRegion=null;
    	mWheelTextureAtlas.unload();
    	mWheelTextureRegion=null;
    }
    
    /**
     * This is for our loading screen
     */
    public void loadSplashScreen()
    {
    	Log.d(null,"Called ResourceManager.loadSplashScreen");
	    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Loading/");
	    mloadingTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 720, 480, TextureOptions.BILINEAR);
	    mloadingTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mloadingTextureAtlas, activity, "Loadingpage3.png", 0, 0);
	    mloadingTextureAtlas.load();
	    
	    mellipseTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(),512,128,TextureOptions.BILINEAR);
	    mellipseRegion =  BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mellipseTextureAtlas, activity, "Loadingsprite.png", 0, 0, 3,1);
	    mellipseTextureAtlas.load();
    }
    /**
     * This is for our loading screen
     */
    public void unloadSplashScreen()
    {
    	mloadingTextureAtlas.unload();
    	mloadingTextureRegion = null;
    	mellipseTextureAtlas.unload();
    	mellipseRegion=null;
    }
    
    /**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * <br><br>
     * We use this method at beginning of game loading, to prepare Resources Manager properly,
     * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
     */
    public static void prepareManager(Engine engine, MainActivity activity, Camera camera, VertexBufferObjectManager vbom){
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }
    
    public void gameToast(final String msg) {
    	activity.runOnUiThread(new Runnable() {
        @Override
        public void run() {
           Toast.makeText(ResourcesManager.getInstance().activity.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    	});
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static ResourcesManager getInstance()
    {
        return INSTANCE;
    }
}