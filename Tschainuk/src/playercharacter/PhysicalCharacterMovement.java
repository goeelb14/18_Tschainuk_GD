
package playercharacter;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.renderer.Camera;

public class PhysicalCharacterMovement implements AnalogListener
{
 
    private BetterCharacterControl playerControl;
    private Camera flyCam;
    private InputManager inputManager;
    
    public PhysicalCharacterMovement(BetterCharacterControl playerControl, Camera flyCam, InputManager inputManager)
    {
        this.playerControl = playerControl;
        this.flyCam = flyCam;
        this.inputManager = inputManager;
        
        setupKeys();
    }
    
    private void setupKeys() {
        inputManager.addMapping("CharLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("CharRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("CharUp", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("CharDown", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addListener(this, "CharLeft");
        inputManager.addListener(this, "CharRight");
        inputManager.addListener(this, "CharUp");
        inputManager.addListener(this, "CharDown");
    }
    
    @Override
    public void onAnalog(String name, float value, float tpf) 
    {
        
    } 
}
