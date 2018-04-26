package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import map.MapAppState;
import playercharacter.PhysicalCharacterAppState;

public class Main extends SimpleApplication
{

    Material mat_terrain;
    private BulletAppState bulletAppState;

    public static void main(String[] args)
    {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp()
    {
        this.bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        PhysicalCharacterAppState pca = new PhysicalCharacterAppState(bulletAppState);
        MapAppState tt = new MapAppState(bulletAppState);
        stateManager.attach(pca);
        stateManager.attach(tt);
        viewPort.setBackgroundColor(ColorRGBA.White);
        flyCam.setMoveSpeed(50);
        setCrosshair();
    }

    private void setCrosshair()
    {
        Geometry c = createGeometry("Center-Mark", Vector3f.ZERO, ColorRGBA.Red);
        c.scale(4f);
        c.setLocalTranslation(settings.getWidth() / 2, settings.getHeight() / 2, 0f);

        guiNode.attachChild(c);
    }

    public Geometry createGeometry(String name, Vector3f location, ColorRGBA color)
    {
        Geometry geom;
        Box box = new Box(location, 1, 1, 1);
        geom = new Geometry(name, box);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);

        return geom;
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
