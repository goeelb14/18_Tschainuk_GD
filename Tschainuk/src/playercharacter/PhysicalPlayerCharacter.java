/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playercharacter;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.scene.Node;

/**
 *
 * @author Simon Pusterhofer
 */
public class PhysicalPlayerCharacter {

    private Node rootNode;
    private AssetManager assetManager;
    private BulletAppState bulletAppState;
           

    public PhysicalPlayerCharacter(Node rootNode, AssetManager assetManager) {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
        bulletAppState = new BulletAppState();
    }
    
    
    
    public void initChar() {
        // Load any model
        Node myCharacter = (Node) assetManager.loadModel("Models/Oto/Oto.mesh.xml");
        rootNode.attachChild(myCharacter);
        // Create a appropriate physical shape for it
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        CharacterControl myCharacter_phys = new CharacterControl(capsuleShape, 0.01f);
        // Attach physical properties to model and PhysicsSpace
        myCharacter.addControl(myCharacter_phys);
        //bulletAppState.getPhysicsSpace().add(myCharacter_phys);
        
    }
}
