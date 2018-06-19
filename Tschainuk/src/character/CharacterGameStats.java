/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package character;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;
import fight.SkillModifier;
import items.Item;
import items.ItemEnum;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Marina
 */
public class CharacterGameStats extends Observable{
    
    private Map<StatEnum, Integer> baseStats;
    private Map<StatEnum, Integer> totalStats;
    private List<SkillModifier> modif;
    private List<Item> myItems;
    private boolean newLevel;
   
    
    
    private long exp;

      
    public long WhenNextLevel()
    {
        int level=baseStats.get(StatEnum.Level);
        long erg=3;
        for(int i =1;i<=level;i++)
        {
            erg+= Math.round((Math.pow(level, 3)*7/8));
        }
        return erg-exp;
    }
   
    
    public boolean NextLevelReached(long exp)
    {
        int level = getStat(StatEnum.Level);
        this.exp+=exp;
        while(this.exp>=WhenNextLevel())
        {

            levelupdate();
            newLevel=true;
            
        }
        System.out.println("LEvel: "+getStat(StatEnum.Level));
        return level != getStat(StatEnum.Level);
    }
    public boolean wasNewLevel()
    {
        if(newLevel)
        {
            newLevel=false;
            return true;
        }
        return false;
    }
    
    private void levelupdate()
    {
        int level = baseStats.get(StatEnum.Level)+1;
        baseStats.put(StatEnum.Level, level);
        totalStats.put(StatEnum.Level, level);
        if(level%4==0 &&level%7==0)
        {
            baseStats.put(StatEnum.HPMax,
            baseStats.get(StatEnum.HPMax)+ Math.round((float)( baseStats.get(StatEnum.HPMax)/10+2)));
            baseStats.put(StatEnum.Strength,
            baseStats.get(StatEnum.Strength)+ Math.round((float)( baseStats.get(StatEnum.Strength)/10+4)));
            baseStats.put(StatEnum.Defense,
            baseStats.get(StatEnum.Defense)+Math.round((float)( baseStats.get(StatEnum.Defense)/10+2)));
            baseStats.put(StatEnum.Luck,
            baseStats.get(StatEnum.Luck)+ Math.round((float)( baseStats.get(StatEnum.Luck)/10+3)));
        }
        else
        {
            baseStats.put(StatEnum.HPMax,
            baseStats.get(StatEnum.HPMax)+Math.round((float)( baseStats.get(StatEnum.HPMax)/10)));
            baseStats.put(StatEnum.Strength,
            baseStats.get(StatEnum.Strength)+Math.round((float)( baseStats.get(StatEnum.Strength)/10)));
            baseStats.put(StatEnum.Defense,
            baseStats.get(StatEnum.Defense)+Math.round((float)( baseStats.get(StatEnum.Defense)/10)));
            baseStats.put(StatEnum.Luck,
            baseStats.get(StatEnum.Luck)+Math.round((float)( baseStats.get(StatEnum.Luck)/10)));
        }
       
        updateStats();
        setChanged();
        this.notifyObservers();
    }
    
    public void updateStats()
    {
        totalStats.put(StatEnum.HPMax,baseStats.get(StatEnum.HPMax));
        totalStats.put(StatEnum.Strength,baseStats.get(StatEnum.Strength));
        totalStats.put(StatEnum.Defense,baseStats.get(StatEnum.Defense));
        totalStats.put(StatEnum.Luck,baseStats.get(StatEnum.Luck));
        for(int i =0;i<modif.size();i++)
        {
            applyMod(modif.get(i));
        }
        if(totalStats.get(StatEnum.HPMax)<totalStats.get(StatEnum.HPNow))
        {
            totalStats.put(StatEnum.HPNow,totalStats.get(StatEnum.HPMax));
        }
        setChanged();
        this.notifyObservers();
    }
    
   public void applyMod(SkillModifier mod)
   {
       if(mod.isTotalValue())
       {
           totalStats.put(mod.getAffectedStat(), Math.round( (float)(totalStats.get(mod.getAffectedStat())+mod.getStatBonus())));
       }
       else
       {
           totalStats.put(mod.getAffectedStat(), Math.round( (float)(totalStats.get(mod.getAffectedStat())*mod.getStatBonus())));
       }
       if(totalStats.get(mod.getAffectedStat())<=0)
       {
            totalStats.put(mod.getAffectedStat(), 3);
       }
       setChanged();
       this.notifyObservers();
   }
   
    public void addModifier(SkillModifier mod)
    {
        modif.add(mod);
        applyMod(mod);
    }
    
    public void removeModifier(SkillModifier mod)
    {
        modif.remove(mod);
    }
    
    public CharacterGameStats()
    {
        initialize();
    }
    
    private void initialize()
    {
        baseStats = new HashMap<>();
        baseStats.put(StatEnum.Level, 1);
        baseStats.put(StatEnum.HPMax, 20);
        baseStats.put(StatEnum.HPNow, 20);
        baseStats.put(StatEnum.Strength, 5);
        baseStats.put(StatEnum.Defense, 5);
        baseStats.put(StatEnum.Luck, 20);
        totalStats= new HashMap<>(baseStats);
        exp=0;

        modif= new ArrayList();
        myItems= new ArrayList<>();
        Item i = new Item(ItemEnum.HEALING_HERB);
        i.setAmount(3);
        myItems.add(i);
        newLevel=false;
       
        
        
        
    }
    public Object[] getCurrentItems()
    {
        return myItems.toArray();
    }

      

    public int getStat(StatEnum stat)
    {
        return totalStats.get(stat);
    }
    
    public int getBaseStat(StatEnum stat)
    {
        return baseStats.get(stat);
    }
    
     public void takeDamage(int damage)
      {
         int currentHP= totalStats.get(StatEnum.HPNow);
         if(damage>=currentHP)
         {
             totalStats.put(StatEnum.HPNow, 0);
         }
         else
         {
             totalStats.put(StatEnum.HPNow, currentHP-damage);
         }
         setChanged();
         notifyObservers();
      }
     
   public void addObserver(Observer o)
   {
      
       super.addObserver(o);
       setChanged();

       notifyObservers();
       
       
   }

    public void useItem(int itemIndex) {
        Item i = myItems.get(itemIndex
        
        );
        SkillModifier sk= i.getModifier();
        if(sk.getAffectedStat()==StatEnum.HPNow)
        {
            double v= sk.getStatBonus();
            if(sk.isTotalValue())
            {
               int HPNeu=(int)(v+getStat(StatEnum.HPNow));
               if(HPNeu>getStat(StatEnum.HPMax))
               {
                   HPNeu=getStat(StatEnum.HPMax);
               }
               totalStats.put(StatEnum.HPNow, HPNeu);
            }
        }
        
        i.setAmount(i.getAmount()-1);
        if(i.getAmount()==0)
        {
           myItems.remove(itemIndex); 
        }
        
    }

}
