package action;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.LinkedList;
import java.util.List;

public class GunActionAppState extends AbstractAppState implements ActionListener
{
    //MainApplication Attributes
    private Node rootNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private Camera cam;
    private BulletAppState bulletAppState;
    
    private List<Geometry> geoms = new LinkedList<>();
    private Vector3f flugbahn;

    public GunActionAppState(BulletAppState bulletAppState)
    {
        this.bulletAppState = bulletAppState;
    }
        
    @Override
    public void initialize(AppStateManager stateManager, Application app)
    {
        super.initialize(stateManager, app);
        
        this.assetManager = app.getAssetManager();
        this.rootNode = ((SimpleApplication) app).getRootNode();
        this.stateManager = stateManager;
        this.inputManager = app.getInputManager();
        this.cam = app.getCamera();
                                
        setUpMouseButton();
    }
    
    private Geometry createGeometry()
    {
        Box b = new Box(0.1f, 0.1f, 0.1f);
        Geometry geom = new Geometry("Bullet", b);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Black);
        geom.setMaterial(mat);
        
        rootNode.attachChild(geom);
        
        geom.setLocalTranslation(rootNode.getChild("MainCharacter").getWorldTranslation());
        
        BoxCollisionShape colShape = new BoxCollisionShape(new Vector3f(0.1f, 0.1f, 0.1f));
        RigidBodyControl cont = new RigidBodyControl(colShape,0.05f);
        cont.setGravity(new Vector3f(0, 0.5f, 0));
        cont.setKinematic(true);
        geom.addControl(cont);
        
        bulletAppState.getPhysicsSpace().add(cont);
 
        return geom;
    }
    
    @Override
    public void update(float tpf)
    {
        flugbahn = rootNode.getChild("MainCharacter").getControl(BetterCharacterControl.class).getViewDirection();
        for(int i=0;i<geoms.size();i++)
        {
            geoms.get(i).setLocalTranslation(geoms.get(i).getLocalTranslation().add(flugbahn.normalizeLocal()));
        }
    }
    
    private void setUpMouseButton()
    {
        inputManager.addMapping("Mouse1", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(this, "Mouse1");
    }
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf)
    {        
        geoms.add(createGeometry());        
    }
}
