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
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Schule
 */
public class TerrainTest extends AbstractAppState
{
    private Node rootNode;

    @Override
    public void initialize(AppStateManager stateManager, Application app) 
    {
        super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
        //viewPort.setBackgroundColor(ColorRGBA.Yellow);
        Spatial scene =  app.getAssetManager().loadModel("Scenes/newScene.j3o");            
        
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-1f, -1f, -1.0f).normalizeLocal());
        scene.addLight(sun);
        
        rootNode = ((SimpleApplication)app).getRootNode();
        rootNode.attachChild(scene);
    }

    @Override
    public void update(float tpf) 
    {
        super.update(tpf); //To change body of generated methods, choose Tools | Templates.
    }
    
}
