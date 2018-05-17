package action;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

public class GunActionAppState extends AbstractAppState implements ActionListener
{
    //MainApplication Attributes
    private Node rootNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private Camera cam;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app)
    {
        super.initialize(stateManager, app);
        
        this.assetManager = app.getAssetManager();
        this.rootNode = ((SimpleApplication) app).getRootNode();
        this.stateManager = stateManager;
        this.inputManager = app.getInputManager();
        this.cam = app.getCamera();
        
        setUpMouseButton();
    }
    
    @Override
    public void update(float tpf)
    {
        
    }
    
    private void setUpMouseButton()
    {
        inputManager.addMapping("Mouse1", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(this, "Mouse1");
    }
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf)
    {
        Vector2f v2f = inputManager.getCursorPosition();
        Vector3f v3f = cam.getWorldCoordinates(v2f, 0.0f);
        Vector3f dir = cam.getWorldCoordinates(v2f, 1.0f).subtractLocal(v3f);
            
        Ray ray = new Ray(v3f, dir);
            
        CollisionResults cr = new CollisionResults();
        rootNode.collideWith(ray, cr);
            
        for(int i = 0; i < cr.size(); i++)
        {
            float dist = cr.getCollision(i).getDistance();
            Vector3f cp = cr.getCollision(i).getContactPoint();
            String target = cr.getCollision(i).getGeometry().getName();
                
            System.out.println("Selection:" + i + ", Target: " + target + ", Contact Point: " + cp + ", Distance: " + dist);
        }
    }
}
