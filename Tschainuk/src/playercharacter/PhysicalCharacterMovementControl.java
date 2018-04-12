
package playercharacter;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class PhysicalCharacterMovementControl extends AbstractControl implements ActionListener
{
 
    private BetterCharacterControl playerControl;
    private Camera flyCam;
    private InputManager inputManager;
    
    public PhysicalCharacterMovementControl(BetterCharacterControl playerControl, Camera flyCam, InputManager inputManager)
    {
        this.playerControl = playerControl;
        this.flyCam = flyCam;
        this.inputManager = inputManager;
    }

    @Override
    protected void controlUpdate(float tpf)
    {
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp)
    {
        
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf)
    {
        
    }
}
