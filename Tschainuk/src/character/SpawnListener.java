/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package character;

import character.NpcManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector3f;

/**
 *
 * @author Marina
 */
public class SpawnListener implements ActionListener{

    private NpcManager man;
    private int counter;
    public SpawnListener(NpcManager man)
    {
        this.man=man;
        counter=0;
    }
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if(isPressed)
        {
            System.out.println("C Pressed SpawnListener");
            if(name.equals("CPressed")&&counter==1)
            {
                 man.spawnNpc(new Vector3f(-15f, 0, 0));
                 man.spawnNpc(new Vector3f(-6, 0, -8));
                 man.spawnNpc(new Vector3f(-20f, 0, 0));
                
            }
            counter++;
        }
    }
    
}
