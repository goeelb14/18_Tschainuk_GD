/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Schule
 */
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
        super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
        rootNode = ((SimpleApplication)app).getRootNode();
        
        Spatial scene =  app.getAssetManager().loadModel("Scenes/newScene.j3o");
        rootNode.attachChild(scene);
        
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-1f, -1f, -1.0f).normalizeLocal());
        scene.addLight(sun);
        
        Spatial terrain = rootNode.getChild("level");
        
        CollisionShape cs = CollisionShapeFactory.createMeshShape(terrain);
        RigidBodyControl rbc = new RigidBodyControl(cs, 0);
        terrain.addControl(rbc);
        bulletAppState.getPhysicsSpace().addAll(terrain);
    }

    @Override
    public void update(float tpf) 
    {
        super.update(tpf); //To change body of generated methods, choose Tools | Templates.
    }
    
}
