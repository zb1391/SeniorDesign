package com.example.pigparty;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import android.util.Log;



public class SceneManager {
    //---------------------------------------------
    // SCENES
    //---------------------------------------------
    
    private MiniGameScene splashScene;
    private MiniGameScene menuScene;
    private MiniGameScene trafficScene;
    private MiniGameScene startScene;
    private MiniGameScene connectScene;
    private MiniGameScene balloonScene;
    private MiniGameScene pokeScene;
    private MiniGameScene goodJobScene;
    private MiniGameScene tooBadScene;
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final SceneManager INSTANCE = new SceneManager();
    
    private SceneType currentSceneType = SceneType.SCENE_SPLASH;
    
    private MiniGameScene currentScene;
    
    private Engine engine = ResourcesManager.getInstance().engine;
    
    private char game;
    public int PokePigTarget;
    public enum SceneType
    {
        SCENE_SPLASH,
        SCENE_MENU,
        SCENE_TRAFFIC,
        SCENE_START,
        SCENE_CONNECT,
        SCENE_BALLOON,
        SCENE_POKE,
        SCENE_GOODJOB,
        SCENE_TOOBAD
    }
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------
    
    /**
     * This method sets the scene in the MainActivty
     * to the desired scene
     * @param scene
     */
    public void setScene(MiniGameScene scene)
    {
        engine.setScene(scene);
        currentScene = scene;
        currentSceneType = scene.getSceneType();
       
    }
    
    /**
     * This method calls the above setScene based
     * on which scene the ResourceManager wants
     * @param sceneType
     */
    public void setScene(SceneType sceneType)
    {
        switch (sceneType)
        {
            case SCENE_MENU:
                setScene(menuScene);
                break;
            case SCENE_TRAFFIC:
                setScene(trafficScene);
                break;
            case SCENE_SPLASH:
                setScene(splashScene);
                break;
            case SCENE_START:
            	setScene(startScene);
            	break;
            case SCENE_CONNECT:
            	setScene(connectScene);
            	break;
            case SCENE_BALLOON:
            	setScene(balloonScene);
            	break;
            case SCENE_POKE:
            	setScene(pokeScene);
            	break;
            case SCENE_GOODJOB:
            	setScene(goodJobScene);
            	break;
            case SCENE_TOOBAD:
            	setScene(tooBadScene);
            	break;
            default:
                break;
        }
    }
    
    public void disposeCurrentScene(SceneType sceneType){
        switch (sceneType)
        {
            case SCENE_TRAFFIC:
                disposeTrafficScene();
                break;
            case SCENE_BALLOON:
            	disposeBalloonScene();
            	break;
            case SCENE_POKE:
            	disposePokeScene();
            	break;
            case SCENE_GOODJOB:
            	disposeGoodJobScene();
            	break;
            case SCENE_TOOBAD:
            	disposeTooBadScene();
            default:
                break;
        }
    }
    
    public void createStartScene(){
    	ResourcesManager.getInstance().loadStartGraphics();
    	startScene = new StartScene();
    	currentScene = startScene;
    	setScene(startScene);
    }
    
    public void disposeStartScene(){
    	ResourcesManager.getInstance().unloadStartGraphics();
    	startScene.disposeScene();
    	startScene=null;
    }
    
    public void createConnectScene(){
    	Log.e(null,"inside create connect scene");
    	ResourcesManager.getInstance().loadConnectGraphics();
    	connectScene = new ConnectScene();
    	setScene(connectScene);
    	disposeStartScene();
    }
    
    public void disposeConnectScene(){
    	Log.e(null,"inside dispose connect scene");
    	ResourcesManager.getInstance().unloadConnectGraphics();
    	connectScene.disposeScene();
    	connectScene=null;
    }
    
    public void createGoodJobScene(){
    	Log.e(null,"Inside create good job scene");
    	ResourcesManager.getInstance().loadGoodJobGraphics();
    	goodJobScene = new GoodJobScene();
    	currentScene = goodJobScene;
    	disposeCurrentScene(getCurrentSceneType());
    	setScene(goodJobScene);
    }
    
    public void disposeGoodJobScene(){
    	Log.e(null,"inside dispose good job scene");
    	ResourcesManager.getInstance().unloadGoodJobGraphics();
    	goodJobScene.disposeScene();
    	goodJobScene=null;
    }
    
    public void createTooBadScene(){
    	Log.e(null,"inside create too bad scene");
    	ResourcesManager.getInstance().loadTooBadGraphics();
    	tooBadScene = new TooBadScene();
    	disposeCurrentScene(getCurrentSceneType());
    	setScene(tooBadScene);
    }
    
    public void disposeTooBadScene(){
    	Log.e(null,"inside dispose too bad scene");
    	ResourcesManager.getInstance().unloadTooBadGraphics();
    	tooBadScene.disposeScene();
    	tooBadScene=null;
    }
    public void createTrafficScene(){
    	Log.e(null, "inside create traffic scene");
    	ResourcesManager.getInstance().loadGameGraphics();
    	trafficScene = new TrafficDodgeScene();
    	currentScene = trafficScene;
    	//pOnCreateSceneCallback.onCreateSceneFinished(trafficScene);
    	setScene(trafficScene);
    	//disposeLoadingScene();
    }
    
    
    public void createBalloonScene(){
    	Log.e(null, "inside create balloon scene");
    	ResourcesManager.getInstance().loadBalloonGameGraphics();
    	balloonScene = new BalloonScene();
    	currentScene = balloonScene;
    	//pOnCreateSceneCallback.onCreateSceneFinished(trafficScene);
    	setScene(balloonScene);
    	//disposeLoadingScene();
    }
    
    public void createPokeScene(){
    	Log.e(null,"inside create Poke Scene");
    	ResourcesManager.getInstance().loadPokeGameGraphics();
    	pokeScene = new PokeScene();
    	currentScene = pokeScene;
    	setScene(pokeScene);
    	//disposeLoadingScene();
    }
    
    public void disposeTrafficScene(){
    	ResourcesManager.getInstance().unloadGameGraphics();
    	trafficScene.disposeScene();
    	trafficScene=null;
    }
    
    public void disposeBalloonScene(){
    	ResourcesManager.getInstance().unloadBalloonGameGraphics();
    	balloonScene.disposeScene();
    	balloonScene=null;
    }
    
    public void disposePokeScene(){
    	ResourcesManager.getInstance().unloadPokeGameGraphics();
    	pokeScene.disposeScene();
    	pokeScene=null;
    	
    }
    
    
    /**
     * Create Loading Scene
     * @return
     */
    public void createLoadingScene(OnCreateSceneCallback pOnCreateSceneCallback){
    	Log.d(null, "Called SceneManager.createLoadingScene");
        ResourcesManager.getInstance().loadSplashScreen();
        splashScene = new LoadingScene();
        currentScene = splashScene;
        setScene(splashScene);
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }
    

    
    
    
    
    public void loadGameScene(final Engine mEngine)
    {
    	//Log.d(null,getCurrentSceneType().toString());
    	disposeCurrentScene(getCurrentSceneType());
        setScene(splashScene);
       

        mEngine.registerUpdateHandler(new TimerHandler(4f, new ITimerCallback() 
        {
        	
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
            	System.gc();
            	MiniGameScene curScene = getCurrentScene();
            	Log.d(null,"current scene is "+getCurrentSceneType().toString());
            	splashScene.receiverthread=new ReceiverThread();
            	splashScene.receiverthread.setListener(curScene);
            	splashScene.receiverthread.execute();
                ConnectionManager.getInstance().sendChar('z');
                mEngine.unregisterUpdateHandler(pTimerHandler);
                
            }
        }));
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static SceneManager getInstance()
    {
        return INSTANCE;
    }
    
    public SceneType getCurrentSceneType()
    {
        return currentSceneType;
    }
    
    public MiniGameScene getCurrentScene()
    {
        return currentScene;
    }
    

}
