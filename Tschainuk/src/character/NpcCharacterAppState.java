package character;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.input.InputManager;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import fight.NpcStatus;
import overlay.GUIListener;
import overlay.HudDisplay;

public class NpcCharacterAppState extends AbstractAppState implements PhysicsTickListener
{
    //main application attributes
    private BulletAppState bulletAppState;
    private AssetManager assetManager;
    private Node rootNode;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private Camera flyCam;
    private Spatial npc;
    private NpcStatus npcStatus;
    private int damageCooldown = 100;
    
    public NpcCharacterAppState(BulletAppState bulletAppState, GUIListener guid,CharacterGameStats cgs)
    {
        this.bulletAppState = bulletAppState;
       
        npcStatus = new NpcStatus(guid,cgs);
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
    
    //"Tötet" den Npc
    private void kill()
    {
        npc.rotate(FastMath.DEG_TO_RAD * 90, 0 , 0);
        npc.getControl(BetterCharacterControl.class).setEnabled(false);
    }
    
    @Override
    public void update(float tpf)
    {
        moveNpc();
    }
    
    //initializes Npc
    private void createNpc()
    {
        npc = assetManager.loadModel("Textures/Hadler.obj");
        npc.scale(0.15f);
        npc.rotate(0f, FastMath.DEG_TO_RAD * 270, 0);
        
        Node npcNode = new Node("Npc");
        npcNode.attachChild(npc);
        
        npcNode.setLocalTranslation(-15f, 0f, 0f);
        
        BetterCharacterControl npcControl = new BetterCharacterControl(0.825f, 2.2f, 1f);
        npcControl.setGravity(new Vector3f(0, 1.5f, 0));
        npcNode.addControl(npcControl);
        
        CollisionShape colShape = new CapsuleCollisionShape(0.825f,2.2f);
        GhostControl cont = new GhostControl(colShape);
        cont.addCollideWithGroup(1);
        cont.setEnabled(true);
        npcNode.addControl(cont);
        
        npcStatus.registerNpc((NpcCharacterAppState)this);
        
        bulletAppState.getPhysicsSpace().add(npcNode);
        bulletAppState.getPhysicsSpace().add(cont);
        
        rootNode.attachChild(npcNode);
    }
    
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
        
        if(dist < 3f)
        {
            if(damageCooldown<=0)
            {
            npcStatus.playerDamage((NpcCharacterAppState)this);
<<<<<<< HEAD
            
=======
            damageCooldown = 200;
            }
            else
            {
                damageCooldown--;
            }
>>>>>>> ccd144fbe92874550fc504c66babdb0e2901541b
        }
    }

    @Override
    public void prePhysicsTick(PhysicsSpace space, float tpf) 
    {

    }

    @Override
    public void physicsTick(PhysicsSpace space, float tpf) {
        int count = npc.getControl(GhostControl.class).getOverlappingCount();
        if(count>=3)
        {            
           if(npcStatus.takeDamage(this))
            {
               kill();
            }
        }
    }
}
