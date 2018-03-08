package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.terrain.geomipmap.TerrainQuad;
import map.MapAppState;
import playercharacter.PhysicalCharacterAppState;

public class Main extends SimpleApplication {

    private TerrainQuad terrain;
    private Material mat_terrain; 
    private BulletAppState bulletAppState;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState(PhysicsSpace.BroadphaseType.SIMPLE);
        flyCam.setMoveSpeed(50);
        PhysicalCharacterAppState pca = new PhysicalCharacterAppState(bulletAppState);
        MapAppState tt = new MapAppState(bulletAppState);
        stateManager.attach(pca);
        stateManager.attach(tt);
        viewPort.setBackgroundColor(ColorRGBA.White);
        }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
