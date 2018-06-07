/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fight;

import character.StatEnum;

/**
 *
 * @author Marina
 */
public class SkillModifier {
    private String name;
    private double statBonus;
    //If not total Value, then it's a multiplier
    private boolean totalValue;
    private StatEnum affectedStat;
    public SkillModifier(String name, double statBonus, boolean totalValue, StatEnum affectedStat)
    {
       this.name=name;
       this.statBonus=statBonus;
       this.totalValue=totalValue;
       this.affectedStat=affectedStat;
    }

    public String getName() {
        return name;
    }

    public double getStatBonus() {
        return statBonus;
    }

    public boolean isTotalValue() {
        return totalValue;
    }

    public StatEnum getAffectedStat() {
        return affectedStat;
    }
    
}
