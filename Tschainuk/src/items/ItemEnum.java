/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import character.StatEnum;
import fight.SkillModifier;

/**
 *
 * @author Marina
 */
public enum ItemEnum {
   HEALING_HERB("Healing Herb", new SkillModifier("Healing Herb,",30,true,StatEnum.HPNow));
    private String name;
    SkillModifier mod;
    ItemEnum(String name, SkillModifier mod)
    {
       this.name=name;
       this.mod=mod;
    }
    public String getName()
    {
        return name;
    }
    public SkillModifier getMod()
    {
        return mod;
    }
    
}
