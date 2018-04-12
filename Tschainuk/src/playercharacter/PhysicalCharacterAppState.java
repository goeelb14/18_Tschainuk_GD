package playercharacter;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

public class PhysicalCharacterAppState extends AbstractAppState implements ActionListener
{
    private Node rootNode;
    private AssetManager assetManager;
    private BulletAppState bulletAppState;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private Camera flyCam;
    private Node playerNode = new Node("playernode");
    private BetterCharacterControl playerControl;
    private Vector3f walkDirection = new Vector3f();
    private ChaseCamera chaseCam;
    
    
    
    //Attributes for Movement
    private boolean moveLeft, moveRight, moveForward, moveBack;
    
    public PhysicalCharacterAppState(BulletAppState bulletAppState)
    {
        this.bulletAppState = bulletAppState;
    }

    //Initializes PhysicalCharacter Properties
    private void initChar() {
        // Load any model
        Node myCharacter = (Node) assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
        myCharacter.scale(0.036f);
        
        
        //create WrapperNode
        playerNode.setLocalTranslation(-7, 50, 0);
        
        //attach nodes
        playerNode.attachChild(myCharacter);
        
        
        //create player control
        playerControl = new BetterCharacterControl(3f, 7f, 1f);
        playerControl.setGravity(new Vector3f(0f,1.5f,0f));
        playerNode.addControl(playerControl);
        playerNode.addControl(new PhysicalCharacterMovementControl(playerControl, flyCam, inputManager));
    
        
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
        setupKeys();
    }
    
    @Override
    public void update(float tpf) 
    {
        super.update(tpf);
        
        Vector3f camDirFirst = flyCam.getDirection();
        Vector3f playerPos = playerNode.getWorldTranslation().add(new Vector3f(0, 7f, 0));
        
        playerControl.setViewDirection(camDirFirst);
        flyCam.setLocation(playerPos);
        
        
        Vector3f camDir = flyCam.getDirection().clone().multLocal(10f);
        Vector3f camLeft = flyCam.getLeft().clone().multLocal(10f);
        
        camDir.y = 0f;
        camLeft.y = 0f;
        
        walkDirection.set(0, 0, 0);
        if (moveLeft) {
            walkDirection.addLocal(camLeft);
        }
        if (moveRight) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (moveForward) {
            walkDirection.addLocal(camDir);
        }
        if (moveBack) {
            walkDirection.addLocal(camDir.negate());
        }
        playerControl.setWalkDirection(walkDirection);
    }
    
    public void onAction(String name, boolean isPressed, float tpf)
    {
        if (name.equals("CharLeft")) 
        {
            if (isPressed) 
            {
                moveLeft = true;
            } else 
            {
                moveLeft = false;
            }
        } 
        else if (name.equals("CharRight")) 
        {
            if (isPressed) 
            {
                moveRight = true;
            } 
            else 
            {
                moveRight = false;
            }
        } 
        else if (name.equals("CharUp")) 
        {
            if (isPressed) 
            {
                moveForward = true;
            } 
            else 
            {
                moveForward = false;
            }
        } 
        else if (name.equals("CharDown"))
        {
            if (isPressed) 
            {
                moveBack = true;
            } 
            else 
            {
                moveBack = false;
            }
        }
        else if(name.equals("CharSpace"))
            playerControl.jump();
    }
    
    private void setupKeys() {
        inputManager.addMapping("CharLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("CharRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("CharUp", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("CharDown", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("CharSpace", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "CharLeft");
        inputManager.addListener(this, "CharRight");
        inputManager.addListener(this, "CharUp");
        inputManager.addListener(this, "CharDown");
        inputManager.addListener(this, "CharSpace");
    }
}