package action;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.LinkedList;
import java.util.List;
import fight.NpcStatus;

public class GunActionAppState extends AbstractAppState implements ActionListener, PhysicsTickListener
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
        private Vector3f charLocation;

    public GunActionAppState(BulletAppState bulletAppState)
    {
        this.bulletAppState = bulletAppState;
    }
    
    private NpcStatus npcStatus;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app)
    {
        super.initialize(stateManager, app);
        
        this.assetManager = app.getAssetManager();
        this.rootNode = ((SimpleApplication) app).getRootNode();
        this.stateManager = stateManager;
        this.inputManager = app.getInputManager();
        this.cam = app.getCamera();
                        
        npcStatus = new NpcStatus();
        
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
        
        RigidBodyControl cont = new RigidBodyControl(2f);
        geom.addControl(cont);
        
        bulletAppState.getPhysicsSpace().add(cont);
 
        return geom;
    }
    
    @Override
    public void update(float tpf)
    {
        
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

    @Override
    public void prePhysicsTick(PhysicsSpace space, float tpf)
    {
       for(int i=0;i<geoms.size();i++)
       {
           flugbahn = rootNode.getChild("MainCharacter").getControl(BetterCharacterControl.class).getViewDirection();
           charLocation = rootNode.getChild("MainCharacter").getLocalTranslation();
           //flugbahn implementieren
       }
       geoms.clear();
    }

    @Override
    public void physicsTick(PhysicsSpace space, float tpf)
    {
    }
}
