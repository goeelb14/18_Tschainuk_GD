
package playercharacter;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

public class PhysicalCharacterMovement implements AnalogListener
{
 
    private BetterCharacterControl playerControl;
    private Camera flyCam;
    
    public PhysicalCharacterMovement(BetterCharacterControl playerControl, Camera flyCam)
    {
        this.playerControl = playerControl;
        this.flyCam = flyCam;
    }
    
   
    @Override
    public void onAnalog(String name, float value, float tpf) {
        /*
         camDir.set(cam.getDirection()).multLocal(speed, 0.0f, speed);
            camLeft.set(cam.getLeft()).multLocal(strafeSpeed);
            walkDirection.set(0, 0, 0);
            if (left) {
                walkDirection.addLocal(camLeft);
            }
            if (right) {
                walkDirection.addLocal(camLeft.negate());
            }
            if (up) {
                walkDirection.addLocal(camDir);
            }
            if (down) {
                walkDirection.addLocal(camDir.negate());
            }
            player.setWalkDirection(walkDirection);
            
     
            cam.setLocation(new Vector3f(PlayerModel.getLocalTranslation().x,PlayerModel.getLocalTranslation().y + headHeight,PlayerModel.getLocalTranslation().z));
        */
        
    }
    
    
    
}
