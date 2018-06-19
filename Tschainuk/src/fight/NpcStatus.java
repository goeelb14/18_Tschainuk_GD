/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fight;

import character.CharacterGameStats;
import character.NpcCharacterAppState;
import character.StatEnum;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;
import java.util.HashMap;
import java.util.Map;
import overlay.GUIListener;

/**
 *
 * Diese Klasse existiert als Verbindung zwischen der Instanz des Gegners welche die Statistiken enthält
 * und dem Gegner, welchen man auf der Karte sieht
 */
public class NpcStatus 
{
 private Map<NpcCharacterAppState, Enemy> npcMap = new HashMap<>();
 
 private Combat combat = new Combat();
 private AssetManager assetManager;
 private CharacterGameStats player ;// dies ist der Spieler
private Node rootNode;

 public NpcStatus(GUIListener guid, CharacterGameStats player, Node rootNode)
 {
    this.player=player;
    player.addObserver(guid);
    this.rootNode=rootNode;
   
 }
 
 public void setAssetManager(AssetManager asset)
 {
     this.assetManager=asset;
 }
 
 public boolean playerDamage(NpcCharacterAppState npc)
 {
     int playerDamage = combat.calcDamage(player, npcMap.get(npc), false);
     System.out.println("Damage:" + playerDamage);
     player.takeDamage(playerDamage);

     if(player.getStat(StatEnum.HPNow)<=0)
     {
        
     }

     return true;
 }
 
 public CharacterGameStats getPlayerStats()
 {
     return player;
 }

 public boolean takeDamage(NpcCharacterAppState character)
 {  
    int damage = combat.calcDamage(player, npcMap.get(character), true); // Schaden von Angriff kalkulieren
    npcMap.get(character).takeDamage(damage); //Gegner Schaden zufügen
    
    if(npcMap.get(character).getStat(StatEnum.HPNow)<=0&&!npcMap.get(character).isDead())
    {
        npcMap.get(character).declareDead();
        player.NextLevelReached(npcMap.get(character).getExpWorth());
        System.out.println("LevelUpdate");
           
        return true;        
    }   
     return false;
 }
 
 public void registerNpc(NpcCharacterAppState appState)
 {
  if(!npcMap.containsKey(appState)) 
  { 
        EnemyEnum type = EnemyEnum.HADLER;
        System.out.println("type: "+type.name());
        Enemy e = new Enemy(EnemyEnum.HADLER); //vorläufig existiert nur Hadler als Gegner
        npcMap.put(appState, e);
  }
 } 
}
