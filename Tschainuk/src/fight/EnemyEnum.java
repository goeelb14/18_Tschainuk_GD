/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fight;

import character.StatEnum;
import java.util.Map;

/**
 *
 * @author Marina
 */
public enum EnemyEnum
{
    HADLER("Hadler",2,10,4,3,5,4),
    BLOBBLEGUM("Blobblegum",4,14,4,5,5,9),
    MR_CHESS("Mr. Chess",10,56,16,10,40,128);
    private String name;
    private Map<StatEnum,Integer> stats;
    private int expWorth;
    EnemyEnum(String name, int level, int hp_max, int defense, int strength, int luck, int expWorth)
    {
        this.name=name;
        this.expWorth=expWorth;
        stats.put(StatEnum.HPMax,hp_max);
        stats.put(StatEnum.Defense,defense);
        stats.put(StatEnum.Strength,strength);
        stats.put(StatEnum.Luck,luck);
<<<<<<< HEAD
        stats.put(StatEnum.Level, level);
=======
        stats.put(StatEnum.Level,level);
>>>>>>> 2b4fdecdf8126c6300e963c4a45ce5b87c022239
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
