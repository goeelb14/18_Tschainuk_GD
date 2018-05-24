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
    
    private void addAppStates()
    {
        PhysicalCharacterAppState pcas = new PhysicalCharacterAppState(bulletAppState);
        MapAppState mas = new MapAppState(bulletAppState);
        NpcCharacterAppState ncas = new NpcCharacterAppState(bulletAppState);
        HeadsUpDisplayAppState hudas = new HeadsUpDisplayAppState();
        GunActionAppState gaas = new GunActionAppState();
        
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
