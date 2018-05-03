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
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class PhysicalCharacterAppState extends AbstractAppState implements ActionListener
{
    private Node rootNode;
    private AssetManager assetManager;
    private BulletAppState bulletAppState;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private Camera flyCam;
    private Node playerNode = new Node("MainCharacter");
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
        Node myCharacter = (Node) assetManager.loadModel("Models/Player/Player.mesh.xml");
        myCharacter.scale(0.09f);
        myCharacter.rotate(FastMath.DEG_TO_RAD * 90, FastMath.DEG_TO_RAD * 90, 0);
        myCharacter.move(new Vector3f(0f, 3f, 0f));
        
        //attach nodes
        playerNode.attachChild(myCharacter);
        
        playerNode.setLocalTranslation(50f, 0, 0);    
        
        //create player control
        playerControl = new BetterCharacterControl(1.3f, 6f, 1f);
        playerControl.setGravity(new Vector3f(0f,1.5f,0f));
        playerNode.addControl(playerControl);
        
        bulletAppState.setDebugEnabled(true);
        
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
        createNpc();
    }
    
    private void createNpc()
    {
        Spatial npc = assetManager.loadModel("Textures/Hadler.obj");
        npc.setName("Npc");
        npc.scale(0.5f);
  
        
        npc.setLocalTranslation(-50f, 100F, 0f);
        
        BetterCharacterControl npcControl = new BetterCharacterControl(1f, 7f, 1f);
        npcControl.setGravity(new Vector3f(0, 1.5f, 0));
        npc.addControl(npcControl);
        
        bulletAppState.getPhysicsSpace().add(npc);
        
        rootNode.attachChild(npc);
    }
    
    @Override
    public void update(float tpf) 
    {
        super.update(tpf);
        
        Vector3f camDirFirst = flyCam.getDirection();
        Vector3f playerPos = playerNode.getWorldTranslation().add(new Vector3f(0f, 5.7f, 0f));
        System.out.println(camDirFirst);
        
        flyCam.setLocation(playerPos);
        playerControl.setViewDirection(camDirFirst);
        
        
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
        
//        if(flyCam.getUp().z < -0.75f)
//            flyCam.setAxes(flyCam.getLeft(), new Vector3f(flyCam.getUp().x, flyCam.getUp().y, -0.75f), flyCam.getDirection());
//        
//        if(flyCam.getUp().z > 0.75f)
//            flyCam.setAxes(flyCam.getLeft(), new Vector3f(flyCam.getUp().x, flyCam.getUp().y, 0.75f), flyCam.getDirection());
//        
//        if(flyCam.getUp().x < -0.75f)
//            flyCam.setAxes(flyCam.getLeft(), new Vector3f(-0.75f, flyCam.getUp().y, flyCam.getUp().z), flyCam.getDirection());
//
//        if(flyCam.getUp().x > 0.75f)
//            flyCam.setAxes(flyCam.getLeft(), new Vector3f(0.75f, flyCam.getUp().y, flyCam.getUp().z), flyCam.getDirection());

//        if(flyCam.getUp().y < 0.75f)
//           flyCam.setAxes(flyCam.getLeft(), new Vector3f(flyCam.getUp().x, 0.75f, flyCam.getUp().z), flyCam.getDirection());
        
//        System.out.println("FlyCam:Up:x" + flyCam.getUp().x);
//        System.out.println("FlyCam:Up:y" + flyCam.getUp().y);
//        System.out.println("FlyCam:Up:z" + flyCam.getUp().z);
//        System.out.println("---------------------------------------");



        moveNpc();
    }
    
    
    private void moveNpc()
    {
        Spatial npc = rootNode.getChild("Npc");
        Spatial me = rootNode.getChild("MainCharacter");

        Vector3f lookAtMe = npc.getWorldTranslation().subtract(me.getWorldTranslation());
        Vector3f followMe = me.getWorldTranslation().subtract(npc.getWorldTranslation());
        
        npc.getControl(BetterCharacterControl.class).setViewDirection(lookAtMe);
        npc.getControl(BetterCharacterControl.class).setWalkDirection(followMe);
        
        float dist = npc.getWorldTranslation().distance(me.getWorldTranslation());
        
        npc.getControl(BetterCharacterControl.class).setEnabled(true);
        
        if(dist < 5f)
        {
            npc.getControl(BetterCharacterControl.class).setEnabled(false);
            npc.getControl(BetterCharacterControl.class).setEnabled(true);
        }
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
    }
    
    private void setupKeys() {
        inputManager.addMapping("CharLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("CharRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("CharUp", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("CharDown", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addListener(this, "CharLeft");
        inputManager.addListener(this, "CharRight");
        inputManager.addListener(this, "CharUp");
        inputManager.addListener(this, "CharDown");
    }
}