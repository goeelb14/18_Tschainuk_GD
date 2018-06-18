package mygame;

import action.GunActionAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import map.MapAppState;
import overlay.HeadsUpDisplayAppState;
import character.NpcCharacterAppState;
import character.PhysicalCharacterAppState;
import com.jme3.audio.AudioNode;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
<<<<<<< HEAD
import overlay.GUIListener;
//import overlay.HudDisplay;
import overlay.HudDisplay;
import overlay.StartDisplay;
=======
import overlay.HudDisplay;
>>>>>>> bd423014027cdb83daeb37cf4868ab01e7b26423

public class Main extends SimpleApplication
{
    private BulletAppState bulletAppState;

    public static void main(String[] args)
    {
        Main app = new Main();
        app.start();
        
    }

    @Override
    public void simpleInitApp()
    {
        
        attachBulletAppState();
        addAppStates();
        
        viewPort.setBackgroundColor(ColorRGBA.White);
        flyCam.setMoveSpeed(50);
        niftyInit();
        
        
    }
    StartDisplay di;
    GUIListener guiL;
    AudioNode aud;
   HudDisplay hud;
    public void niftyInit()
    {
       hud=new HudDisplay(this.rootNode,assetManager);
        aud= new AudioNode(assetManager,"music/SteamTrack.wav");
        aud.setPositional(false);
        rootNode.attachChild(aud);
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);

        di= new StartDisplay(niftyDisplay);
        aud.setLooping(true);
        aud.play();
         guiL= new GUIListener(guiViewPort,niftyDisplay,hud);
         
         
        inputManager.addMapping("CPressed", new KeyTrigger(KeyInput.KEY_C));
        inputManager.addListener(guiL, "CPressed");
        inputManager.addMapping("LeftMouse", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        
        inputManager.addListener(guiL, "LeftMouse");
        setDisplayStatView(false); setDisplayFps(false);
                 
        Nifty nifty = niftyDisplay.getNifty();
        nifty.fromXml("DisplayImages/HudXML.xml", "start", di);
       
        guiViewPort.addProcessor(niftyDisplay);
       
       
       
       
       
       
        
    }
    
    private void addAppStates()
    {
        PhysicalCharacterAppState pcas = new PhysicalCharacterAppState(bulletAppState);
        MapAppState mas = new MapAppState(bulletAppState);
        NpcCharacterAppState ncas = new NpcCharacterAppState(bulletAppState);
        HeadsUpDisplayAppState hudas = new HeadsUpDisplayAppState();
        GunActionAppState gaas = new GunActionAppState(bulletAppState);
        
        stateManager.attach(pcas);
        stateManager.attach(mas);
        stateManager.attach(hudas);
        stateManager.attach(ncas);
        stateManager.attach(gaas);
    }
    
    private void attachBulletAppState()
    {
        this.bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
    }
    
    @Override
    public void simpleUpdate(float tpf)
    {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm)
    {
        //TODO: add render code
    }
}
