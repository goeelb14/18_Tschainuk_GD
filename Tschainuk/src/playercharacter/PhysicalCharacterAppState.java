package playercharacter;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

public class PhysicalCharacterAppState extends AbstractAppState {

    private Node rootNode;
    private AssetManager assetManager;
    private BulletAppState bulletAppState;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private Camera flyCam;
    private Node playerNode = new Node("playernode");
    private BetterCharacterControl playerControl;
    
    private boolean moveLeft, moveRight, moveForward, moveBack;
    
    public PhysicalCharacterAppState(BulletAppState bulletAppState)
    {
        this.bulletAppState = bulletAppState;
    }

    //Initializes PhysicalCharacter Properties
    private void initChar() {
        // Load any model
        Node myCharacter = (Node) assetManager.loadModel("Models/Oto/Oto.mesh.xml");
        //myCharacter.setLocalScale(1,0.55f,1);
        
        
        //create WrapperNode
        playerNode.setLocalTranslation(-7, 50, 0);
        
        //attach nodes
        playerNode.attachChild(myCharacter);
        
        
        //create player control
        playerControl = new BetterCharacterControl(3f, 7f, 1f);
        playerControl.setGravity(new Vector3f(0f,1.5f,0f));
        playerNode.addControl(playerControl);
    
        
        //bulletAppState.setDebugEnabled(true);
        
        //attach control and player to physicspace
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
        this.flyCam = app.getCamera();
        
        initChar();
    }

    @Override
    public void update(float tpf) 
    {
        super.update(tpf);
        
        Vector3f camDir = flyCam.getDirection();
        Vector3f playerPos = playerNode.getWorldTranslation();
        
        playerControl.setViewDirection(camDir);
        flyCam.setLocation(playerPos);
    }
}