/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fight;

import character.StatEnum;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Marina
 */
public class Enemy 
{
<<<<<<< HEAD
     private Map<StatEnum, Integer> stats;
     private  EnemyEnum type;
=======
     Map<StatEnum, Integer> stats;
     private EnemyEnum type;
>>>>>>> 2b4fdecdf8126c6300e963c4a45ce5b87c022239
     private int expWorth;
     public Enemy(EnemyEnum type)
     {
         this.type=type;
         initialize();
     }
      private void initialize()
    {
        stats = new HashMap<>();
        stats.put(StatEnum.Level, type.getStat(StatEnum.Level));
        stats.put(StatEnum.HPMax, type.getStat(StatEnum.HPMax));
        stats.put(StatEnum.HPNow, type.getStat(StatEnum.HPMax));
        stats.put(StatEnum.Strength, type.getStat(StatEnum.Strength));
        stats.put(StatEnum.Defense, type.getStat(StatEnum.Defense));
        stats.put(StatEnum.Luck, type.getStat(StatEnum.Luck));
        expWorth=type.getExpWorth();
        
        
        
    }
      public int getStat(StatEnum stat)
      {
          return stats.get(stat);
      }
      public int getExpWorth()
      {
          return expWorth;
      }
      public void takeDamage(int damage)
      {
         int currentHP= stats.get(StatEnum.HPNow);
         if(damage>=currentHP)
         {
             stats.put(StatEnum.HPNow, 0);
         }
         else
         {
             stats.put(StatEnum.HPNow, currentHP-damage);
         }
      }
      
}
