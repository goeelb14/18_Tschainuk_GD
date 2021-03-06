/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay;

import character.CharacterGameStats;
import character.StatEnum;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.input.controls.ActionListener;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import com.jme3.scene.control.Control;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.NiftyControl;
import de.lessvoid.nifty.controls.label.LabelControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.SizeValue;
import items.Item;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Marina
 */
public class HudDisplay extends AbstractAppState implements ScreenController {

  private Application app;
  private AppStateManager stateManager;
  private Nifty nifty;
  private Screen screen;
  
  private Node rootNode;
  private NiftyJmeDisplay niftyDisplay;
  private AssetManager assetManager;
    private int itemIndex;

    public HudDisplay(Node rootNode, AssetManager assetManager,NiftyJmeDisplay niftyDisplay) {
       this.rootNode=rootNode;
       this.assetManager=assetManager;
       this.niftyDisplay=niftyDisplay;
       this.nifty=niftyDisplay.getNifty();
       this.screen=nifty.getScreen("HUDScreen");
       
       
       
    }
  public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
       
  }
  
  
    public void onStartScreen() 
    {
  }

    public void onEndScreen() {
  }
    
    public void gunShot() 
    {
        
        AudioNode audio_gun = new AudioNode(assetManager, "music/gunshot.wav");
        audio_gun.setPositional(false);
        audio_gun.setLooping(false);
        audio_gun.setVolume(2);
        rootNode.attachChild(audio_gun);
        audio_gun.play();
        Element layer = this.screen.findElementById("layerHUD");
        layer.getRenderer(ImageRenderer.class).setImage(nifty.createImage(this.screen, "DisplayImages/HudShoot.png", false));
        Timer t = new Timer();
        t.schedule(new TT(layer,this.screen), 150);
        
            
      
    }
    public int getItemIndex()
    {
        return itemIndex;
    }

    void displayItems(Object[] currentItems,Screen screen) {
        this.screen=screen;
        System.out.println("current Items length"+currentItems.length);
        
        if(currentItems.length>0){
        Element emp= this.screen.findElementById("empty");
        emp.setVisible(false);
        for(int i=0;i< currentItems.length;i++)
        {
            Element layer= this.screen.findElementById("layerOuter");
            layer.setVisible(true);
            System.out.println("Screen null "+screen==null);
            String path = ((Item)currentItems[i]).getType().getImagePath();
            String panelname= "item"+(i+1);
            System.out.println("panelname"+panelname);
            Element img= this.screen.findElementById(panelname);
            
            System.out.println(path);
           NiftyImage newImage = nifty.getRenderEngine().createImage(this.screen,path, false);
           img.getRenderer(ImageRenderer.class).setImage(newImage);
           Element lbl = this.screen.findElementById("lblitem"+(i+1));
            lbl.getRenderer(TextRenderer.class).setText(((Item)currentItems[i]).getType().getName());
            Element lbla = this.screen.findElementById("item"+(i+1)+"Amount");
            lbla.getRenderer(TextRenderer.class).setText("Amount: "+((Item)currentItems[i]).getAmount());
            
            itemIndex=i;
        
            
          
        }
        }
        else
        {
            Element layer= this.screen.findElementById("layerOuter");
            layer.setVisible(false);
            Element emp= this.screen.findElementById("empty");
            emp.setVisible(true);
        }
    }
    class TT extends TimerTask
    {
        private Element layer;
        private Screen screen;
        public TT(Element layer,Screen screen)
        {
            this.layer=layer;
            this.screen=screen;
        }
        @Override
        public void run() {
           layer.getRenderer(ImageRenderer.class).setImage(nifty.createImage(this.screen, "DisplayImages/HudNeu.png", false)); 
        }
        
    }
   
  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    this.app = app;
    this.stateManager = stateManager;
  }    

   
    public void update(Screen screen, CharacterGameStats cgs) 
    {
        this.screen=screen;
        System.out.println("UPDATE HUD");
       // System.out.println(screen.getScreenId());
        
     //  CharacterGameStats cgs= (CharacterGameStats)o;
       Element lblHP = this.screen.findElementById("lblHP");
       //lblHP.getRenderer(TextRenderer.class).setText("Current HP: "+ cgs.getStat(StatEnum.HPNow)+"/"+cgs.getStat(StatEnum.HPMax));
        lblHP.getRenderer(TextRenderer.class).setText("Current HP: "+cgs.getStat(StatEnum.HPNow) +"/" +cgs.getStat(StatEnum.HPMax));
        Element lblLevel = this.screen.findElementById("lblLevel");
        
        lblLevel.getRenderer(TextRenderer.class).setText("Current Level: "+ cgs.getStat(StatEnum.Level));
        Element pnlBar= this.screen.findElementById("lifeBar");
        int hpbar= Math.round(new Float(cgs.getStat(StatEnum.HPNow))/cgs.getStat(StatEnum.HPMax)*294);
        pnlBar.setConstraintWidth(new SizeValue(hpbar+"px"));
      //niftyDisplay.getNifty().update();
        
     
       
       
    }

   
}
