package character;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

public class PhysicalCharacterAppState extends AbstractAppState implements ActionListener
{
    //MainApplication Attributes
    private Node rootNode;
    private AssetManager assetManager;
    private BulletAppState bulletAppState;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private Camera flyCam;
    
    //LocalAttributes
    private Node playerNode;
    private BetterCharacterControl playerControl;
    private Vector3f walkDirection;
    
    //Attributes for Movement
    private boolean moveLeft, moveRight, moveForward, moveBack, sprint;
    
    //Constructor
    public PhysicalCharacterAppState(BulletAppState bulletAppState)
    {
        this.bulletAppState = bulletAppState;
        this.playerNode = new Node("MainCharacter");
        this.walkDirection = new Vector3f();
    }

    //Prepare Main Character
    @Override
    public void initialize(AppStateManager stateManager, Application app)
    {
        super.initialize(stateManager, app);
        
        this.assetManager = app.getAssetManager();
        this.rootNode = ((SimpleApplication) app).getRootNode();
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
        
        setWalkingDirection();
        setFirstPersonCam();
    }
    
    //Initializes Playcharacter
    private void initChar() 
    {
        // Load any model
        Node myCharacter = (Node) assetManager.loadModel("Models/Player/Player.mesh.xml");
        
        //Correctly Allign Player
        myCharacter.scale(0.045f);
        myCharacter.rotate(FastMath.DEG_TO_RAD * 90, FastMath.DEG_TO_RAD * 90, 0);
        myCharacter.move(new Vector3f(0f, 1.5f, 0f));
        
        //attach player to  wrappernode
        playerNode.attachChild(myCharacter);
        
        //Position Player
        playerNode.setLocalTranslation(50f, 0, 0); 
        
        //create player control
        playerControl = new BetterCharacterControl(1.15f, 3f, 100f);
        playerNode.addControl(playerControl);
        
//        bulletAppState.setDebugEnabled(true);

        //attach player to physicspace
        bulletAppState.getPhysicsSpace().add(playerNode);
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, -100f, 0));
        
        //attach wrapper to rootnode
        rootNode.attachChild(playerNode);
    }
    
    //Methode um Sichtfeld einzuschränken, geht momentan nicht
    private void setMaxFirstPersonCamMovableAngle()
    {
        if(flyCam.getUp().z < -0.75f)
            flyCam.setAxes(flyCam.getLeft(), new Vector3f(flyCam.getUp().x, flyCam.getUp().y, -0.75f), flyCam.getDirection());
        
        if(flyCam.getUp().z > 0.75f)
            flyCam.setAxes(flyCam.getLeft(), new Vector3f(flyCam.getUp().x, flyCam.getUp().y, 0.75f), flyCam.getDirection());
        
        if(flyCam.getUp().x < -0.75f)
            flyCam.setAxes(flyCam.getLeft(), new Vector3f(-0.75f, flyCam.getUp().y, flyCam.getUp().z), flyCam.getDirection());

        if(flyCam.getUp().x > 0.75f)
            flyCam.setAxes(flyCam.getLeft(), new Vector3f(0.75f, flyCam.getUp().y, flyCam.getUp().z), flyCam.getDirection());

        if(flyCam.getUp().y < 0.75f)
           flyCam.setAxes(flyCam.getLeft(), new Vector3f(flyCam.getUp().x, 0.75f, flyCam.getUp().z), flyCam.getDirection());
        
        System.out.println("FlyCam:Up:x" + flyCam.getUp().x);
        System.out.println("FlyCam:Up:y" + flyCam.getUp().y);
        System.out.println("FlyCam:Up:z" + flyCam.getUp().z);
        System.out.println("---------------------------------------");
    }
    
    //set cam to first person location of Player
    private void setFirstPersonCam()
    {
        Vector3f camDirFirst = flyCam.getDirection();
        Vector3f playerPos = playerNode.getWorldTranslation().add(new Vector3f(0f, 2.85f, 0f));
        //System.out.println(camDirFirst);
        
        flyCam.setLocation(playerPos);
        playerControl.setViewDirection(camDirFirst);
    }
    
    //set walking direction of player
    private void setWalkingDirection()
    {
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
        if(sprint && moveForward)
        {
            camDir = camDir.multLocal(1.1f);
            walkDirection.addLocal(camDir);
        }
        playerControl.setWalkDirection(walkDirection);
    }
    
    //get pressed keys from player to control him
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
        else if(name.equals("CharLeftShift"))
        {
            if(isPressed)
            {
                sprint = true;
            }
            else
            {
                sprint = false;
            }
        }
    }
    
    //define keys that are used for movement
    private void setupKeys() {
        inputManager.addMapping("CharLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("CharRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("CharUp", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("CharDown", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("CharLeftShift", new KeyTrigger(KeyInput.KEY_LSHIFT));
        inputManager.addListener(this, "CharLeft");
        inputManager.addListener(this, "CharRight");
        inputManager.addListener(this, "CharUp");
        inputManager.addListener(this, "CharDown");
        inputManager.addListener(this, "CharLeftShift");
         
    }
}