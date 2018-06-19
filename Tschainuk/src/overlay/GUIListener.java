/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay;

import character.CharacterGameStats;
import character.StatEnum;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Marina
 */
public class GUIListener implements ActionListener,Observer{

    private ViewPort vp; 
    private NiftyJmeDisplay niftyDisplay;
    private int counter = 0;
   private StartDisplay di;
    private HudDisplay hud;
    boolean end;
   AudioNode aud;
   Node rootNode;
   AssetManager assetManager;
 
   

    public GUIListener(ViewPort guiViewPort, NiftyJmeDisplay niftyDisplay, HudDisplay hud, AudioNode aud, Node rootNode, AssetManager assetManager)
    { vp= guiViewPort;
        this.niftyDisplay=niftyDisplay;
      this.hud=hud;
      di= new StartDisplay(niftyDisplay);
      end=false;
      this.aud=aud;
      this.rootNode=rootNode;
      this.assetManager=assetManager;

        
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
       nifty.registerScreenController(hud);
       
      nifty.fromXml("DisplayImages/HudXML.xml", "HUDScreen", hud);
     hud.update(nifty.getScreen("HUDScreen"), new CharacterGameStats());
       
        vp.addProcessor(niftyDisplay);
       
        
       }
         }
         else if(name.equals("LeftMouse"))
         {
             
             hud.gunShot();
         }
         else if(name.equals("EPressed")&&end)
         {
             System.exit(0);
         }
        
       
    }
    }

    @Override
    public void update(Observable o, Object o1) {
       
           CharacterGameStats cgs= (CharacterGameStats)o;  
           if(cgs.getStat(StatEnum.HPNow)<=0&&end==false)
           {
               end=true;
               vp.removeProcessor(niftyDisplay);
               Nifty nifty = niftyDisplay.getNifty();
               nifty.registerScreenController(di);
       
               nifty.fromXml("DisplayImages/HudXML.xml", "EndScreen", di);
        
             //   di.update(niftyDisplay.getNifty().getScreen("EndScreen"),cgs);
       
                vp.addProcessor(niftyDisplay);
                aud.stop();
                aud= new AudioNode(assetManager,"music/EndMusic.wav");
                aud.setPositional(false);
                rootNode.attachChild(aud);
                
                aud.setLooping(true);
                aud.play();
                
               
           }
           else if(counter>0&&end==false)
       {   
       vp.removeProcessor(niftyDisplay);
       Nifty nifty = niftyDisplay.getNifty();
       nifty.registerScreenController(hud);
       
      nifty.fromXml("DisplayImages/HudXML.xml", "HUDScreen", hud);
        
      hud.update(niftyDisplay.getNifty().getScreen("HUDScreen"),cgs);
       
        vp.addProcessor(niftyDisplay);
        
       }
    }
    
}
