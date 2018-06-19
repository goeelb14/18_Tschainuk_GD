package mygame;

import action.GunActionAppState;
import character.CharacterGameStats;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import overlay.GUIListener;
//import overlay.HudDisplay;
import overlay.HudDisplay;
import overlay.StartDisplay;
import overlay.StatsListener;

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
        niftyInit();
        attachBulletAppState();
        
        addAppStates();
        
        viewPort.setBackgroundColor(ColorRGBA.White);
        flyCam.setMoveSpeed(50);
        
        
        
    }
    StartDisplay di;
    GUIListener guiL;
    AudioNode aud;
   HudDisplay hud;
   NiftyJmeDisplay niftyDisplay;
    public void niftyInit()
    {
       
        aud= new AudioNode(assetManager,"music/SteamTrack.wav");
        aud.setPositional(false);
        rootNode.attachChild(aud);
        aud.setLooping(true);
        aud.play();
         niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        hud=new HudDisplay(this.rootNode,assetManager,niftyDisplay);    
        di= new StartDisplay(niftyDisplay);
          cgs=new CharacterGameStats();
         guiL= new GUIListener(guiViewPort,niftyDisplay,hud,aud, rootNode, assetManager,cgs);
         
         
        inputManager.addMapping("CPressed", new KeyTrigger(KeyInput.KEY_C));
        inputManager.addListener(guiL, "CPressed");
        inputManager.addMapping("LeftMouse", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(guiL, "EPressed");
        inputManager.addMapping("EPressed", new KeyTrigger(KeyInput.KEY_E));
        inputManager.addListener(guiL, "BPressed");
        inputManager.addMapping("BPressed", new KeyTrigger(KeyInput.KEY_B));
        inputManager.addListener(guiL, "UPressed");
        inputManager.addMapping("UPressed", new KeyTrigger(KeyInput.KEY_U));
        
        inputManager.addListener(guiL, "LeftMouse");
        
        setDisplayStatView(false); setDisplayFps(false);
                 
        Nifty nifty = niftyDisplay.getNifty();
        nifty.fromXml("DisplayImages/HudXML.xml", "start", di);
       
        guiViewPort.addProcessor(niftyDisplay);
       
       
       
       
       
       
        
    }
    private CharacterGameStats cgs;
    private StatsListener sl;
    private void addAppStates()
    {
        
        PhysicalCharacterAppState pcas = new PhysicalCharacterAppState(bulletAppState);
        MapAppState mas = new MapAppState(bulletAppState);
      
        NpcCharacterAppState ncas = new NpcCharacterAppState(bulletAppState,guiL,cgs,rootNode,assetManager);
        HeadsUpDisplayAppState hudas = new HeadsUpDisplayAppState();
        GunActionAppState gaas = new GunActionAppState(bulletAppState);
        
        //Hinzufügen der PhysicsTickListener
        this.bulletAppState.getPhysicsSpace().addTickListener(gaas);
        this.bulletAppState.getPhysicsSpace().addTickListener(ncas);
        
        stateManager.attach(pcas);
        stateManager.attach(mas);
        stateManager.attach(hudas);
        stateManager.attach(ncas);
        stateManager.attach(gaas);
        sl= new StatsListener(guiViewPort,niftyDisplay,cgs);
        inputManager.addMapping("VPressed", new KeyTrigger(KeyInput.KEY_V));
        inputManager.addListener(sl, "VPressed");
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
