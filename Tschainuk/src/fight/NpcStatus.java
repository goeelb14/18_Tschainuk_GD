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

/**
 *
 * Diese Klasse existiert als Verbindung zwischen der Instanz des Gegners welche die Statistiken enthält
 * und dem Gegner, welchen man auf der Karte sieht
 */
public class NpcStatus 
{
 private Map<NpcCharacterAppState, Enemy> npcMap = new HashMap<>();
 
 private Combat combat = new Combat();
 private CharacterGameStats player = new CharacterGameStats(); // dies ist der Spieler

 NpcStatus status = new NpcStatus();
 
 public boolean takeDamage(NpcCharacterAppState character)
 {  
    int damage = combat.calcDamage(player, npcMap.get(character), true); // Schaden von Angriff kalkulieren
    npcMap.get(character).takeDamage(damage); //Gegner Schaden zufügen
    
    if(npcMap.get(character).getStat(StatEnum.HPNow)<= 0)
    {
        character.kill();
    }
    
     return false;
 }
 
 public void registerNpc(NpcCharacterAppState appState)
 {
  Enemy e = new Enemy(EnemyEnum.HADLER); //vorläufig existiert nur Hadler als Gegner
  npcMap.put(appState, e);
 }
 
 
}
