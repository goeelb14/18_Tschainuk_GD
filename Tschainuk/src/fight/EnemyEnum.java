/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fight;

import character.StatEnum;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Marina
 */
public enum EnemyEnum
{
    HADLER("Hadler",2,40,4,3,5,4),
    BLOBBLEGUM("Blobblegum",4,80,4,5,5,9),
    MR_CHESS("Mr. Chess",10,200,16,10,40,128);
    private String name;
    private Map<StatEnum,Integer> stats;
    private int expWorth;
    EnemyEnum(String name, int level, int hp_max, int defense, int strength, int luck, int expWorth)
    {

        System.out.println("EnemyEnum Initialize");
        System.out.println(StatEnum.Defense);

        stats=new HashMap<>();
        this.name=name;
        this.expWorth=expWorth;
        stats.put(StatEnum.HPMax,hp_max);
        stats.put(StatEnum.Defense,defense);
        stats.put(StatEnum.Strength,strength);
        stats.put(StatEnum.Luck,luck);

        stats.put(StatEnum.Level,level);


    }
    public int getStat(StatEnum stat)
    {
        return stats.get(stat);
    }
    public int getExpWorth()
    {
        return expWorth;
    }
    
}
