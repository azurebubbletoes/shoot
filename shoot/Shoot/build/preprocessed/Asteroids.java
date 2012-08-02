
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
public class Asteroids {
    Vector asteroids;
    Graphics g;
    Random rand;
    int stageHeight;
    int stageWidth;
    int cooldown;
    Dude p;
    PowerUp pu;
    GameDesign design;
    Audio audio;
    Damager da;
    
    public Vector getAsteroids(){return asteroids;}
    public int getCooldown(){return cooldown;}
    public void setCooldown(int a){cooldown=a;}
    
    public Asteroids(Graphics d, int w,int h,Dude p,PowerUp q,Damager t,GameDesign sss){
        asteroids=new Vector();
        g=d;
        rand=new Random(30);
        stageWidth=w;
        stageHeight=h;
        cooldown=60;
        this.p=p;
        design=sss;
        pu=q;
        audio=new Audio();
        da=t;
    }
    
    
    
    public void create(){
        cooldown--;
        if(cooldown<=0){
            try{
                Sprite t= new Sprite(Image.createImage("//assets/asteroid.png"));
                Asteroid a= new Asteroid(t,"asteroid",g);
                int x= (Math.abs(rand.nextInt(stageWidth))%stageWidth)-t.getWidth()/2;
                int y=0-rand.nextInt(50)-a.getHeight(); 
                a.setPosition(x, y);
                a.setSpeed(rand.nextInt(2)+1);
                asteroids.addElement(a);
            }catch(Exception e){}
            cooldown=rand.nextInt(50)+50;
        }//if
        
    }//createAsteroids
    
    public void render(){

        for(int i=0;i<asteroids.size();i++){
            Asteroid temp= (Asteroid)asteroids.elementAt(i);
            temp.setPosition(temp.getX(), temp.getY());
            temp.getSprite().paint(g);
        }//for
    }//render
    
    public void animate(){
    
        for(int i=0;i<asteroids.size();i++){
            Asteroid temp= (Asteroid)asteroids.elementAt(i);
            temp.setPosition(temp.getX(), temp.getY()+temp.getSpeed());//rand.nextInt(5)+1);
            temp.getSprite().paint(g);
            
            if(temp.getY()>stageHeight){
                asteroids.removeElementAt(i);
            }
        }//for
    }//animate
    
    public void evaluateCollisions(){
//        design.animateExplosions();
        for(int i=0;i<asteroids.size();i++){
            Asteroid a=(Asteroid)asteroids.elementAt(i);
            Vector bullets=p.getBullets();
            Vector bb=pu.getBomb();
                    
            //asteroids vs powerups
            
            //bombs
           if(bb.size()>0){
                for(int j=0;j<bb.size();j++){
                    Thing t=(Thing)bb.elementAt(j);

                    if(a.collidesWith(t.getSprite())){
                  //      design.createExplosions(t.getSprite().getX(), t.getSprite().getY());
                       audio.playMusic("boom");
                        p.setScore(p.getScore()+a.getScore());
                        a=null;
                      
                        bb.removeElementAt(j);
                        asteroids.removeElementAt(i);
                        break;
                    }


                }//for1
            }//if
          
                //shield
            if(pu.getShield().size()>0){
                for(int j=0;j<pu.getShield().size();j++){
                    Thing t=(Thing)pu.getShield().elementAt(j);

                    if(a.collidesWith(t.getSprite())){
//                         design.createExplosions(t.getSprite().getX(), t.getSprite().getY());
                         audio.playMusic("boom");
                        //pu.getBomb().removeElementAt(j);
                        p.setScore(p.getScore()+a.getScore());
                        try{
                        asteroids.removeElementAt(i);}catch(Exception e){}
                    }
                    //damager
                        for(int k=0;k<da.d.size();k++){
                            Thing h=(Thing)da.d.elementAt(k);
                            
                            if(t.getSprite().collidesWith(h.getSprite(), true)){
//                                 design.createExplosions(h.getSprite().getX(), h.getSprite().getY());
                                da.d.removeElementAt(k);
                                pu.getShield().removeAllElements();
                                break;
                            }
                        }//for
                }//for
            }//if
            
                //laser
            if(pu.getLaser().size()>0){
                for(int j=0;j<pu.getLaser().size();j++){
                    Thing t=(Thing)pu.getLaser().elementAt(j);

                    if(a.collidesWith(t.getSprite())){
//                         design.createExplosions(t.getSprite().getX(), t.getSprite().getY());
                        audio.playMusic("boom");
                       
                        p.setScore(p.getScore()+a.getScore());
                        asteroids.removeElementAt(i);
                        
                    }
                      //damager  
                     for(int k=0;k<da.d.size();k++){
                            Thing h=(Thing)da.d.elementAt(k);
                            
                            if(t.getSprite().collidesWith(h.getSprite(), true)){
                             //    design.createExplosions(h.getSprite().getX(), h.getSprite().getY());
                                da.d.removeElementAt(k);
                                pu.getLaser().elementAt(j);
                            }
                     }//for

                }//for
            }//if
            
            //asteroids vs bullets
             for(int j=0;j<bullets.size();j++){
                  
                    Thing b=(Thing) bullets.elementAt(j);
                    Sprite z=b.getSprite();
                    if(a.collidesWith(z)){
//                         design.createExplosions(z.getX(), z.getY());
                        audio.playMusic("boom");
                     //   createExplosion(a.getX(),a.getY());
                        p.setScore(p.getScore()+a.getScore());
                        asteroids.removeElementAt(i);
                        p.getBullets().removeElementAt(j);
                        
                       
                    }//if
               }//for
           //}//if
            
            
            if(a.collidesWith(p.getSprite())){
                //asteroid and player
                a.setStatus(false);
                int q=a.getSpeed()-1>1?a.getSpeed()-1:1;
                a.setSpeed(q);
                a.setHp(a.getHp()-1);
                if(a.getHp()<=0){
//                     design.createExplosions(a.getX()+5, a.getY()+5);
                      p.setScore(p.getScore()+a.getScore());
                      asteroids.removeElementAt(i);
                      
                }else{
                    p.setCurhp(p.getCurhp()-a.getDamage());
                    //create explosions
                }//else
                
            }else{
                if(a.getMaxhp()>a.getHp()){
                   if(!a.getStatus()){
                       a.setSpeed(a.getSpeed()+1);
                       a.setStatus(true);
                   }//                     
                }//if
            }//else
        }//for
    }//collisions
    
    
}
