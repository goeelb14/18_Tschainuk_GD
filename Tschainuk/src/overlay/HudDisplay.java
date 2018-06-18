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
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.NiftyControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.Observable;
import java.util.Observer;


/**
 *
 * @author Marina
 */
public class HudDisplay extends AbstractAppState implements ScreenController, Observer {

  private Application app;
  private AppStateManager stateManager;
  private Nifty nifty;
  private Screen screen;
  
  private Node rootNode;
  private NiftyJmeDisplay niftyDisplay;
  private AssetManager assetManager;

   

    

    public HudDisplay(Node rootNode, AssetManager assetManager) {
       this.rootNode=rootNode;
       this.assetManager=assetManager;
       
       
       
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

    
    }
  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    this.app = app;
    this.stateManager = stateManager;
  }    

    @Override
    public void update(Observable o, Object o1) 
    {
       CharacterGameStats cgs= (CharacterGameStats)o;
       screen.findElementById("lblLevel").getRenderer(TextRenderer.class).setText("Current Level: "+cgs.getBaseStat(StatEnum.Level));
       String hptext = "Current HP: "+cgs.getBaseStat(StatEnum.HPNow)+"/" + cgs.getBaseStat(StatEnum.HPMax);
        screen.findElementById("lblLevel").getRenderer(TextRenderer.class).setText(hptext);
       
    }
}
