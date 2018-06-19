/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay;

import character.CharacterGameStats;
import com.jme3.input.controls.ActionListener;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;

/**
 *
 * @author Marina
 */
public class StatsListener implements ActionListener{

    private ViewPort vp; 
    private NiftyJmeDisplay niftyDisplay;
    private int counter = 0;
    private StatsDisplay sd;
 
    public StatsListener(ViewPort guiViewPort, NiftyJmeDisplay niftyDisplay,CharacterGameStats cgs) {
        vp= guiViewPort;
        this.niftyDisplay=niftyDisplay;
        sd= new StatsDisplay(niftyDisplay,cgs);
      
       
    }
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
          if(name.equals("VPressed")&&isPressed)
         {
             vp.removeProcessor(niftyDisplay);
             Nifty nifty = niftyDisplay.getNifty();
             
              
            nifty.registerScreenController(sd);
             nifty.fromXml("DisplayImages/StatsXML.xml", "showStats", sd);
             
             sd.showStats(nifty.getScreen("showStats"));
            
        vp.addProcessor(niftyDisplay);
         }
    }
    
}
