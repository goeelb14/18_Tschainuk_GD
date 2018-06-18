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
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.label.LabelControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author Marina
 */
public class StatsDisplay extends AbstractAppState implements ScreenController {

  private Application app;
  private AppStateManager stateManager;
  private Nifty nifty;
  private Screen screen;
  
  private Element progressBarElement;
   private CharacterGameStats cgs; 
   private NiftyJmeDisplay niftyDisplay;

    public StatsDisplay(NiftyJmeDisplay niftyDisplay,CharacterGameStats cgs) {
        this.cgs=cgs;
        this.niftyDisplay=niftyDisplay;
        nifty=niftyDisplay.getNifty();
        screen=nifty.getScreen("showStats");
         

       
      
    }
    
    
  
  @Override
  public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        
        
        
  }
  public void showStats(Screen screen)
  {
      
        this.screen=screen;
        
       
        System.out.println(screen.getScreenId());
        Element lblHP = this.screen.findElementById("HPMax");
        lblHP.getRenderer(TextRenderer.class).setText("Max HP: " +cgs.getStat(StatEnum.HPMax));
        Element lblStrength = this.screen.findElementById("Strength");
        lblStrength.getRenderer(TextRenderer.class).setText("Strength: " +cgs.getStat(StatEnum.Strength));
        Element lblDefense = this.screen.findElementById("Defense");
        lblDefense.getRenderer(TextRenderer.class).setText("Defense: " +cgs.getStat(StatEnum.Defense));
        Element lblLuck = this.screen.findElementById("Luck");
        lblLuck.getRenderer(TextRenderer.class).setText("Luck: " +cgs.getStat(StatEnum.Luck));
        Element lblLevel = this.screen.findElementById("Level");
        lblLevel.getRenderer(TextRenderer.class).setText("Current Level: " +cgs.getStat(StatEnum.Level));
        Element lblNextLevel = this.screen.findElementById("NextLevel");
        lblNextLevel.getRenderer(TextRenderer.class).setText("Exp until next Level: " +cgs.WhenNextLevel());
       
//
//        Label lblStrength= this.screen.findNiftyControl("Strength", Label.class);
//        lblStrength.setText("Strength: " +cgs.getStat(StatEnum.Strength));
//        Label lblDefense= this.screen.findNiftyControl("Defense", Label.class);
//        lblDefense.setText("Defense: " +cgs.getStat(StatEnum.Defense));
//        Label lblLuck= this.screen.findNiftyControl("Luck", Label.class);
//        lblDefense.setText("Luck: " +cgs.getStat(StatEnum.Luck));
//        Label lblNextLevel= this.screen.findNiftyControl("Luck", Label.class);
//        lblNextLevel.setText("Next Level: " +cgs.WhenNextLevel());
  }
  
  
    public void onStartScreen() 
    {
    }

    public void onEndScreen() {
    
    }
    
  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    this.app = app;
    this.stateManager = stateManager;
  }
    
}
