/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playercharacter;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.InputManager;
import com.jme3.scene.Node;

/**
 *
 * @author Simon Pusterhofer
 */
public class PhysicalCharacterAppState extends AbstractAppState {

    private Node rootNode;
    private AssetManager assetManager;
    private BulletAppState bulletAppState;
    private AppStateManager stateManager;
    private InputManager inputManager;
    
    public PhysicalCharacterAppState(BulletAppState bulletAppState)
    {
        this.bulletAppState = bulletAppState;
    }

    //Initializes PhysicalCharacter Properties
    private void initChar() {
        // Load any model
        Node myCharacter = (Node) assetManager.loadModel("Models/Oto/Oto.mesh.xml");
        myCharacter.setLocalTranslation(0, 100, 0);
        rootNode.attachChild(myCharacter);
        // Create a appropriate physical shape for it
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        CharacterControl myCharacter_phys = new CharacterControl(capsuleShape, 0.01f);
        // Attach physical properties to model and PhysicsSpace
        myCharacter.addControl(myCharacter_phys);
        bulletAppState.getPhysicsSpace().add(myCharacter_phys);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        assetManager = app.getAssetManager();
        rootNode = ((SimpleApplication) app).getRootNode();

        this.stateManager = stateManager;
        this.inputManager = app.getInputManager();
        
        initChar();

    }

    @Override
    public void update(float tpf) {
        super.update(tpf); //To change body of generated methods, choose Tools | Templates.
    }

}
