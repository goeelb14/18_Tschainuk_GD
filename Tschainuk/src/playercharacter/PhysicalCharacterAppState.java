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
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

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
        
        //create WrapperNode
        Node playerNode = new Node("playernode");
        playerNode.setLocalTranslation(new Vector3f(-7, 100, 0));
        
        
        //attach nodes
        playerNode.attachChild(myCharacter);
        
        //create player control
        BetterCharacterControl playerControl = new BetterCharacterControl(3f, myCharacter.getLocalTranslation().y, 1f);
        playerNode.addControl(playerControl);
        
        //bulletAppState.setDebugEnabled(true);
        
        //attach control and player to physicspace
        //bulletAppState.getPhysicsSpace().add(playerControl);
        bulletAppState.getPhysicsSpace().add(playerNode);
        
        rootNode.attachChild(playerNode);
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
        super.update(tpf);
    }

}