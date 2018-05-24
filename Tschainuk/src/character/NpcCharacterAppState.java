package character;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.InputManager;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class NpcCharacterAppState extends AbstractAppState
{
    //main application attributes
    private BulletAppState bulletAppState;
    private AssetManager assetManager;
    private Node rootNode;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private Camera flyCam;
    
    public NpcCharacterAppState(BulletAppState bulletAppState)
    {
        this.bulletAppState = bulletAppState;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) 
    {
        super.initialize(stateManager, app);
        
        this.assetManager = app.getAssetManager();
        this.rootNode = ((SimpleApplication) app).getRootNode();
        this.stateManager = stateManager;
        this.inputManager = app.getInputManager();
        this.flyCam = app.getCamera();
        
        createNpc();
    }
    
    @Override
    public void update(float tpf)
    {
        moveNpc();
    }
    
    //initializes Npc
    private void createNpc()
    {
        Spatial npc = assetManager.loadModel("Textures/Hadler.obj");
        npc.scale(0.15f);
        npc.rotate(0f, FastMath.DEG_TO_RAD * 270, 0);
        
        Node npcNode = new Node("Npc");
        npcNode.attachChild(npc);
        
        npcNode.setLocalTranslation(-15f, 0f, 0f);
        
        BetterCharacterControl npcControl = new BetterCharacterControl(0.825f, 2.2f, 1f);
        npcControl.setGravity(new Vector3f(0, 1.5f, 0));
        npcNode.addControl(npcControl);
        
        bulletAppState.getPhysicsSpace().add(npcNode);
        
        rootNode.attachChild(npcNode);
    }
    
    private Spatial npc;
    private Spatial me;
    private Vector3f lookAtMe;
    private Vector3f followMe;
    private float dist;
    
    //logical movement for npc
    private void moveNpc()
    {
        npc = rootNode.getChild("Npc");
        me = rootNode.getChild("MainCharacter");

        lookAtMe = npc.getWorldTranslation().subtract(me.getWorldTranslation());
        followMe = me.getWorldTranslation().subtract(npc.getWorldTranslation());
        
        npc.getControl(BetterCharacterControl.class).setViewDirection(lookAtMe);
        npc.getControl(BetterCharacterControl.class).setWalkDirection(followMe);
        
        dist = npc.getWorldTranslation().distance(me.getWorldTranslation());
        
        npc.getControl(BetterCharacterControl.class).setEnabled(true);
        
        if(dist < 5f)
        {
            npc.getControl(BetterCharacterControl.class).setEnabled(false);
            npc.getControl(BetterCharacterControl.class).setEnabled(true);
        }
    }
}
