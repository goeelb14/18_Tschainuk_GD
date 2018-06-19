/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fight;

import character.CharacterGameStats;
import character.StatEnum;
import items.Item;
import java.util.Random;

/**
 *
 * @author Marina
 */
public class Combat 
{
    private final Random r;
    public Combat()
    {
        r = new Random();
    }
    public int calcDamage(CharacterGameStats player, Enemy enemy, boolean playerAttack)
    {
        if(playerAttack)
        {
            int attack = player.getStat(StatEnum.Strength);
            int defense = enemy.getStat(StatEnum.Defense);
            int damage = Math.round((float)(attack*1.5-defense)*5/7);
            if(damage<=0)
            {
                int i = r.nextInt(2);
                if(i==1)
                {
                    damage=1;
                }
                else
                {
                    damage =0;
                }
                
            }
            
            if(isCritical(player.getStat(StatEnum.Luck)))
            {
                damage+=attack;
            }
            
             return damage;    
        }
        else
        {
            int attack = enemy.getStat(StatEnum.Strength);
            int defense = player.getStat(StatEnum.Defense);
            int damage = Math.round((float)(attack*3-defense)*5/7);
            if(damage<=0)
            {
                int i = r.nextInt(2);
                if(i==1)
                {
                    damage=1;
                }
                else
                {
                    damage =0;
                }
                
            }
            
            if(isCritical(enemy.getStat(StatEnum.Luck)))
            {
                damage+=attack;
            }
             return damage;
        }
       
    }
    public Item[] itemsDropped(Enemy enemy)
    {
        return null;
    }
    private boolean isCritical(int luck)
    {
        return luck>=r.nextInt(800);
    }
  
}
