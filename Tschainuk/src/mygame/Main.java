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
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import overlay.HudDisplay;

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
    }
    HudDisplay di;
    public void niftyInit()
    {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
         di= new HudDisplay();
        Nifty nifty = niftyDisplay.getNifty();

        guiViewPort.addProcessor(niftyDisplay);
        
       nifty.fromXml("HUDXML.xml", "start", di);
        
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
