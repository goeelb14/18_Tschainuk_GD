/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package character;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import fight.NpcStatus;
import java.util.LinkedList;
import java.util.List;
import overlay.GUIListener;

/**
 *
 * @author Simon Pusterhofer
 * Diese Klasse Verwaltet eine Liste von Npcs
 */
public class NpcManager 
{
    private BulletAppState bulletAppState;
    private AssetManager assetManager;
    private Node rootNode;
    private AppStateManager stateManager;
    private NpcStatus npcStatus;
    private GUIListener guid;
    private CharacterGameStats cgs;
    private List<NpcCharacterAppState> npcList;
    private int npcSequenceNumber;
    
    public NpcManager(BulletAppState bulletAppState, GUIListener guid,CharacterGameStats cgs, Node rootNode,AssetManager assetManager, AppStateManager stateManager)
    {
        npcList = new LinkedList<>();
        npcSequenceNumber = 0;    
        this.rootNode = rootNode;
        this.stateManager = stateManager;
        this.guid = guid;
        this.cgs = cgs;
        this.bulletAppState = bulletAppState;
        this.assetManager = assetManager;
    
        initialize();
        npcStatus.setAssetManager(assetManager);
    }
    
    private void initialize()
    {
        npcStatus = new NpcStatus(guid,cgs,rootNode);
    }
    
    public void spawnNpc(Vector3f startingPos)
    {
        String npcName = "Npc"+npcSequenceNumber;
        NpcCharacterAppState npc = new NpcCharacterAppState(bulletAppState, guid, cgs, rootNode, assetManager, npcStatus, startingPos, npcName);
        stateManager.attach(npc);                
        bulletAppState.getPhysicsSpace().addTickListener(npc);
        
        npcList.add(npc);
        npcSequenceNumber++;
    }
}
