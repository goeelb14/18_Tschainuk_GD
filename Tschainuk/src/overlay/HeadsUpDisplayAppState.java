package overlay;

import com.jme3.app.Application;
import com.jme3.app.LegacyApplication;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

public class HeadsUpDisplayAppState extends AbstractAppState
{
    //Mainapplication attributes
    private AssetManager assetManager;
    private Node rootNode;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private Camera flyCam;
    private Node guiNode;
    private AppSettings settings;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) 
    {
        super.initialize(stateManager, app);
        
        this.assetManager = app.getAssetManager();
        this.rootNode = ((SimpleApplication) app).getRootNode();
        this.guiNode = ((SimpleApplication) app).getGuiNode();
        this.settings = app.getContext().getSettings();
        this.stateManager = stateManager;
        this.inputManager = app.getInputManager();
        this.flyCam = app.getCamera();
        
        setCrosshair();
    }
    
    //sets crosshair at center of Screen
    private void setCrosshair()
    {
        Geometry c = createGeometry("Center-Mark", Vector3f.ZERO, ColorRGBA.Red);
        c.scale(4f);
        c.setLocalTranslation(settings.getWidth() / 2, settings.getHeight() / 2, 0f);

        guiNode.attachChild(c);
    }

    //creates crosshair
    private Geometry createGeometry(String name, Vector3f location, ColorRGBA color)
    {
        Geometry geom;
        Box box = new Box(location, 1, 1, 1);
        geom = new Geometry(name, box);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);

        return geom;
    }
}
