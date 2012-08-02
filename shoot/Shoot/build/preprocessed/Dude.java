
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
public class Dude extends Thing{
    int livesLeft;
    int speed;
    int curHp;
    int maxLife;
    int bulletSpeed;
    int score;
    String status;
    int x,y;
    Vector bullets;
    Graphics g;
    int bulletCooldown,powerupCooldown;
    Vector powerup;
    boolean shield,bomb,laser;
    Audio audio;
    
    
    public Dude(Sprite s,String n, Graphics d){
        super(s,n);
        g=d;
        audio=new Audio();
        bullets=new Vector();
        livesLeft=3;
        speed=5;
        maxLife=100;
        curHp=100;
        bulletSpeed=3;
        status="normal"; 
        bulletCooldown=10;
        score=0;
        powerup=new Vector(3);
        powerupCooldown=20;
        shield=false;
        bomb=false;
        laser=false;
        
        //p=new Vector();
       // p.addElement(getSprite());
    }
    
  
    public void setLivesleft(int l){livesLeft=l;}
    public void setSpeed(int s){speed=s;}
    public void setCurhp(int c){curHp=c;}
    public void setMaxlife(int m){maxLife=m;}
    public void setStatus(String s){status=s;}
    public void setX(int x){this.x=x;}
    public void setY(int y){this.y=y;}
    public void setBulletspeed(int s){bulletSpeed=s;}
    public void setBulletcooldown(int s){bulletCooldown=s;}
    public void setBullets(Vector b){bullets=b;}
    public void setPowerup(Vector s){powerup=s;}
    public void setPowerupcooldown(int x){powerupCooldown=x;}
    public void setShield(boolean s){shield=s;}
    public void setLaser(boolean s){laser=s;}
    public void setBomb(boolean s){bomb=s;}
    public void setScore(int x){score=x;}

    public Sprite getSprite() {return super.getSprite();}
    public boolean getShield(){return shield;}
    public boolean getLaser(){return laser;}
    public boolean getBomb(){return bomb;}
    public int getPowerupcooldown(){return powerupCooldown;}
    public Vector getBullets(){return bullets;}
    public int getLivesleft(){return livesLeft;}
    public int getSpeed(){return speed;}
    public int getCurhp(){return curHp;}
    public int getMaxlife(){return maxLife;}
    public int getBulletspeed(){return bulletSpeed;}
    public int getBulletcooldown(){return bulletCooldown;}
    public String getStatus(){return status;}
    public Vector getPowerup(){return powerup;}
    public int getScore(){return score;}
    
    public void paint(Graphics g){this.getSprite().paint(g);};
    public int getX(){return x;}
    public int getY(){return y;}
    
    public int getHeight(){return super.getSprite().getHeight();}
    public int getWidth(){return super.getSprite().getWidth();}
    
    public void setPosition(int x,  int y){super.getSprite().setPosition(x, y);}
    
    public int getHpbar(){ return 50-(maxLife-curHp)/2;}//100-(maxLife-curHp)/4;}
    
    public void fire(){ //create
     bulletCooldown--;
     if(bulletCooldown<=0){  
        try{
            Thing t=new Thing(new Sprite(Image.createImage("//assets/bullet.png")),"bullet");
            audio.playMusic("pew");
            t.getSprite().setPosition(getSprite().getX()+getWidth()/2-2,getSprite().getY()+3);
            t.getSprite().paint(g);
            bullets.addElement(t);
        }catch(Exception e){}
        bulletCooldown=5;
     }
    }//fire
    
    public void renderBullets(){
      
        for(int i=0;i<bullets.size();i++){
            Thing temp=(Thing)bullets.elementAt(i);
            
            temp.getSprite().setPosition(temp.getSprite().getX(), temp.getSprite().getY());
            temp.getSprite().paint(g);
            
        }//for
     
    }//renderBullets
    
    public void animateBullets(){
        for(int i=0;i<bullets.size();i++){
            Thing temp=(Thing)bullets.elementAt(i);
            
            temp.getSprite().setPosition(temp.getSprite().getX(), temp.getSprite().getY()-getBulletspeed());
            temp.getSprite().paint(g);
            
            if(temp.getSprite().getY()<=0){
                bullets.removeElementAt(i);
            }//if
        }//for
    }//animateBullets
    
    public void addPowerUp(Thing p){
        if(powerup.size()<3){
            if(p.getName().equals("life")){
                livesLeft++;
            }else{
                powerup.addElement(p);
            }//else
        }//if
        
    }//powerup
    
    public void removePowerUp(int i){
         powerup.removeElementAt(i);
    }
    
    
}//end
