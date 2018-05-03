package map;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class MapAppState extends AbstractAppState
{
    private Node rootNode;
    private BulletAppState bulletAppState;
    
    public MapAppState(BulletAppState bulletAppState)
    {
        this.bulletAppState = bulletAppState;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) 
    {
        super.initialize(stateManager, app); 
        rootNode = ((SimpleApplication)app).getRootNode();
        
        Spatial scene =  app.getAssetManager().loadModel("Scenes/entryLevel.scene");
        scene.setName("MainScene");
        
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-1f, -1f, -1.0f).normalizeLocal());
        rootNode.addLight(sun);

        
        DirectionalLight sun2 = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-1f, -1f, -1.0f).normalizeLocal().negate());
        rootNode.addLight(sun2);
        
        //Spatial terrain = rootNode.getChild("basic");
        
        CollisionShape cs = CollisionShapeFactory.createMeshShape((Node) scene);
        RigidBodyControl rbc = new RigidBodyControl(cs, 0);
        scene.addControl(rbc);
        
        rootNode.attachChild(scene);
        
        //bulletAppState.getPhysicsSpace().add(rbc);
        bulletAppState.getPhysicsSpace().add(scene);
    }

    @Override
    public void update(float tpf) 
    {
        super.update(tpf); 
    }
    
}