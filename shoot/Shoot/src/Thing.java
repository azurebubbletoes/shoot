
import javax.microedition.lcdui.game.Sprite;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ceruleansea
 */
public class Thing {
    public Sprite sprite;
    public String name;  
    
    public Thing(){}
    public Thing(Sprite s,String n){
        sprite=s;
        name=n;
    }
    
    public void setSprite(Sprite s){sprite=s;}
    public void setName(String n){name=n;}
    public Sprite getSprite(){return sprite;}
    public String getName(){return name;}
}
