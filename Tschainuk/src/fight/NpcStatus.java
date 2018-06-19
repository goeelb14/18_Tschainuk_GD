/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fight;

import character.CharacterGameStats;
import character.NpcCharacterAppState;
import character.StatEnum;
import java.util.HashMap;
import java.util.Map;
import overlay.GUIListener;
import overlay.HudDisplay;

/**
 *
 * Diese Klasse existiert als Verbindung zwischen der Instanz des Gegners welche die Statistiken enthält
 * und dem Gegner, welchen man auf der Karte sieht
 */
public class NpcStatus 
{
 private Map<NpcCharacterAppState, Enemy> npcMap = new HashMap<>();
 
 private Combat combat = new Combat();
 private CharacterGameStats player ;// dies ist der Spieler

 public NpcStatus(GUIListener guid, CharacterGameStats player)
 {
    this.player=player;
    player.addObserver(guid);
 }
 
 public boolean playerDamage(NpcCharacterAppState npc)
 {
     int playerDamage = combat.calcDamage(player, npcMap.get(npc), false);
     System.out.println("Damage:" + playerDamage);
     player.takeDamage(playerDamage);
<<<<<<< HEAD
=======
     if(player.getStat(StatEnum.HPNow)<=0)
     {
         
     }
>>>>>>> ccd144fbe92874550fc504c66babdb0e2901541b
     return true;
 }


 
 public boolean takeDamage(NpcCharacterAppState character)
 {  
    int damage = combat.calcDamage(player, npcMap.get(character), true); // Schaden von Angriff kalkulieren
    npcMap.get(character).takeDamage(damage); //Gegner Schaden zufügen
    
    if(npcMap.get(character).getStat(StatEnum.HPNow)<= 0)
    {
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
