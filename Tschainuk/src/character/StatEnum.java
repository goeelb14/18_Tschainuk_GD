/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package character;

/**
 *
 * @author Marina
 */
public enum StatEnum {
     HPNow("HPNow"), HPMax("HPMax"),Level("Level"),
     Luck("Luck"),Strength("Strength"),Defense("Defense");
    private String name;
     StatEnum(String name)
    {
        this.name=name;
    }
     
   
}
