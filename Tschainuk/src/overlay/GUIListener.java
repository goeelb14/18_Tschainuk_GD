/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;

/**
 *
 * @author Marina
 */
public class GUIListener implements ActionListener{

    private ViewPort vp; 
    private NiftyJmeDisplay niftyDisplay;
    private int counter = 0;
   private StartDisplay di;
    private HudDisplay hud;
    public GUIListener(ViewPort guiViewPort, NiftyJmeDisplay niftyDisplay, HudDisplay hud) {
        vp= guiViewPort;
        this.niftyDisplay=niftyDisplay;
      this.hud=hud;
      di= new StartDisplay(niftyDisplay);
       
    }

 
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf){
    
     if(isPressed)
     {
         if(name.equals("CPressed"))
         {
       if(counter==0)
       {
           vp.removeProcessor(niftyDisplay);
       Nifty nifty = niftyDisplay.getNifty();
       nifty.fromXml("DisplayImages/HudXML.xml", "story", di);
       
        vp.addProcessor(niftyDisplay);
        counter++;
       }
       
       else
       {
      vp.removeProcessor(niftyDisplay);
       Nifty nifty = niftyDisplay.getNifty();
      nifty.fromXml("DisplayImages/HudXML.xml", "HUDScreen", hud);
       
        vp.addProcessor(niftyDisplay);
       }
         }
         else if(name.equals("LeftMouse"))
         {
             
             hud.gunShot();
         }
       
    }
    }
    
}
