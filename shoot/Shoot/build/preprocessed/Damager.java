
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ceruleansea
 */
public class Damager extends Thing{
    Graphics g;
    Dude player;
    int stageWidth,stageHeight;        
    Vector d;        
    int size;
    Random r;
    int cooldown;
    int duration=200;
    
    public Damager(int w,int h,Dude p,Graphics g){
        stageWidth=w;
        stageHeight=h;
        player=p;
        this.g=g;
        size=20;
        d=new Vector();
        r=new Random(50);
        cooldown=100;
    }
    
    public void create(){
        cooldown--;
        if(cooldown<=0){
            try{
                for(int i=0;i<size;i++){
                    Sprite s=new Sprite(Image.createImage("//assets/damager.png"));
                    int rx=Math.abs(r.nextInt(stageWidth))+1;
                    int ry=-1;
                    s.setPosition(rx, ry);
                    s.paint(g);
                    Thing t=new Thing(s,"damager");
                    d.addElement(t);

                }
            }catch(Exception e){}
        cooldown=r.nextInt(400)+100;
        }
        
    }//create
    
    public void render(){
         for(int i=0;i<d.size();i++){
                   Thing t=(Thing)d.elementAt(i);
                   t.getSprite().setPosition(t.getSprite().getX(),t.getSprite().getY());
                   t.getSprite().paint(g);
                }//for
    }
    
    public void animate(){
        //duration--;
        //if(du<=0){
             for(int i=0;i<d.size();i++){
                     int speed=i+1;
                     Thing t=(Thing)d.elementAt(i);
                     t.getSprite().setPosition(t.getSprite().getX(),t.getSprite().getY()+speed);
                     t.getSprite().paint(g);

                     if(t.getSprite().getY()>=stageHeight)
                        d.removeElementAt(i);
             }//for}
        //}    
    }
    
    public void evaluateCollisions(){
        for(int i=0;i<d.size();i++){
            Thing t=(Thing)d.elementAt(i); 
            if(t.getSprite().collidesWith(player.getSprite(), true)){
                 try{ 
           // if (boom == null) {                               
                // write pre-init user code here
                Sprite boom = new Sprite(Image.createImage("//assets/explosion.png"), 64, 64);                
               
                boom.setPosition(player.getSprite().getX(), player.getSprite().getY());
               // boom.setFrame(0);
                boom.nextFrame();
                boom.paint(g);
                
                
           // }
                  }catch(Exception e){} 
                player.setCurhp(player.getCurhp()-20);
                d.removeElementAt(i);
            }//if collide
        }//for
    }//collision
}
