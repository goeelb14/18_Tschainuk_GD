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
   HEALING_HERB("Healing Herb", new SkillModifier("Healing Herb,",30,true,StatEnum.HPNow),"ItemSprites/healingherb.png");
    private String name;
    private SkillModifier mod;
    private String imagePath;
    ItemEnum(String name, SkillModifier mod, String imagePath)
    {
       this.name=name;
       this.mod=mod;
       this.imagePath=imagePath;
    }
    public String getImagePath()
    {
        return imagePath;
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
