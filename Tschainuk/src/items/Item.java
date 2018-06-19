/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import fight.SkillModifier;

/*
    This class was planned to implement such things as armors, important items for the game, herbs, etc...
    However, due to the fact that we had so many things to do and so little time, I never fully implemented it
    But, unfortunately, I couldn't delete it because of the other classes such as SkillModifier or Enemies that can 
    infact drop items, but we do not have any models or use for items at this stage of the game.
    --Marina
*/
public class Item 
{
    private ItemEnum type;
    public Item(ItemEnum type)
    {
        this.type=type;
    }
    public SkillModifier getModifier()
    {
        return type.getMod();
    }
}
