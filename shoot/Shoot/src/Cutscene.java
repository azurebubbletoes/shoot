
import java.util.Vector;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.midlet.MIDlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ceruleansea
 */
public class Cutscene{
   
    Vector sprites;
    Engine e;
    int index;
    Sprite sprite;
  
    
    public void populate(){
         for(int i=1; i<7;i++){
           try{
               String z="//assets/"+i+".png";
               Sprite s= new Sprite(Image.createImage(z));
               s.setPosition(0, 0);
               s.paint(e.g);
               sprites.addElement(s);
           }catch(Exception ee){};
       }
    }
    
    public void render(){
             
            try{
             //  String z="//assets/"+(index)+".png";
               Sprite s= (Sprite) sprites.elementAt(index-1);
               sprite.setPosition(0, 0);
               sprite.paint(e.g);
               //sprites.addElement(sprite);
           }catch(Exception ee){};
    };
    
    public void remove(){
        sprites.removeAllElements();
    
    }
  public Cutscene(Engine e){
       this.e=e;
      index=0;
       // counter=20;
       populate();
      
    }
}
