
import java.util.Random;
import java.util.Vector;
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
public class Boss {
    Engine e;
    Sprite sprite;
    boolean goRight;
    int x,y;
    int speed,damage;
    Vector bullets;
    Vector particles;
    Random rand;
    int shootcoolDown,splashcoolDown;
    int k=0;
      int maxHP;
    int curHP;
    int deg=0;
   // int l=40;
    int bulletFest;
   
     public int getHpbar(){ return 50-(maxHP-curHP)/2;}
     
     
    public void setX(int x){this.x=x;}
    public void setY(int y){this.y=y;}
    public int getX(){return x;}
    public int getY(){return y;}
    public Boss(Engine x){
        e=x;
            try{
             sprite= new Sprite(Image.createImage("//assets/boss.png"));
            }catch(Exception e){}//catch
        
        rand=new Random(40);
        bulletFest=rand.nextInt(50)+1;
        goRight=true;
        this.x=0;
        y=20;
        speed=2;
        damage=40;
        bullets=new Vector();
        particles=new Vector();
        shootcoolDown=20;
        splashcoolDown=20;
        maxHP=100;
        curHP=100;
    }//constructor
    
    public void renderBoss(){
        sprite.setPosition(x, y);
        sprite.paint(e.g);
        
       if(curHP>=75){
        e.g.setColor(0, 255, 0);
       }
       if(curHP<=74 && curHP>=50){
         e.g.setColor(255, 255, 128);
       }
       if(curHP<=49&& curHP>=25){
            e.g.setColor(255, 184, 149);
       }
       if(curHP<24){
           e.g.setColor(255,0,0);
       }
       
        
        e.g.setColor(0, 255, 0);
        e.g.fillRect(sprite.getX()+10, sprite.getY()-2, getHpbar(), 2);
        
    }
    public void animateBoss(){
        
        if(goRight){
       // if(sprite.getX()<=0 || sprite.getX()<e.stageWidth-sprite.getWidth()){
            setX(sprite.getX()+speed);
          
        }else{
        //if(sprite.getX()>=e.stageWidth || sprite.getX()<e.stageWidth-sprite.getWidth()){
             setX(sprite.getX()-speed);//, sprite.getY());
          
        }
    }
    
    public void createBullet(){
        shootcoolDown--;
        if(shootcoolDown<=0){
            bulletFest--;
            
                if(bulletFest<=0){
                    int gap=sprite.getWidth()/5;
                    int there=sprite.getX();
                    for(int i=0;i<10;i++){
                        try{
                            Sprite s=new Sprite(Image.createImage("//assets/bullet.png"));
                            s.setPosition(there-20, sprite.getHeight());
                            s.paint(e.g);
                            bullets.addElement(s);
                            there+=gap;
                        }catch(Exception e){}//try
                    }
                   bulletFest=rand.nextInt(5)+1;
                }else{
                     try{
                       Sprite s=new Sprite(Image.createImage("//assets/bullet.png"));
                       s.setPosition(sprite.getX()+sprite.getWidth()/2, sprite.getHeight());
                       s.paint(e.g);
                       bullets.addElement(s);
                     
                    }catch(Exception e){}//try


                }
 
                
                
          shootcoolDown=20;
        }
    }//create
    
    public void renderBullets(){
        for(int i=0;i<bullets.size();i++){
            Sprite s=(Sprite) bullets.elementAt(i);
            s.setPosition(s.getX(), s.getY());
            s.paint(e.g);                
        }//for
    }//renderbullets
    
    public void animateBullets(){
         for(int i=0;i<bullets.size();i++){
            Sprite s=(Sprite) bullets.elementAt(i);
            s.setPosition(s.getX(), s.getY()+5);
            s.paint(e.g);  
            
            if(s.getY()>e.stageHeight)
                bullets.removeElementAt(i);
        }//for
    }//animate
    
    
    public void evaluateCollisions(){
        //player vs Boss' bullets
       
        for(int i=0;i<bullets.size();i++){
            Sprite temp=(Sprite) bullets.elementAt(i);
            if(temp.collidesWith(e.player.getSprite(), true)){
                bullets.removeElementAt(i);
                e.player.setCurhp(e.player.getCurhp()-damage);
                
            }
        }
        //Vector temp=e.player.bullets;
        for(int i=0;i<e.player.getBullets().size();i++){
            Thing t=(Thing) e.player.getBullets().elementAt(i);
            
            if(t.getSprite().collidesWith(sprite, true)){
               curHP=curHP-2;
               e.player.getBullets().removeElementAt(i);
               e.audio.playMusic("boom");
               
            }//collision
        }//for
        
        //laser
        for(int i=0;i<e.powerup.laser.size();i++){
            Thing x=(Thing) e.powerup.laser.elementAt(i);
            
            if(x.getSprite().collidesWith(sprite,true)){
                curHP=curHP-2;
                e.audio.playMusic("boom");
            }//if
        }
        
         //laser
        for(int i=0;i<e.powerup.bomb.size();i++){
            Thing x=(Thing) e.powerup.bomb.elementAt(i);
            
            if(x.getSprite().collidesWith(sprite,true)){
                curHP=curHP-2;
                e.audio.playMusic("boom");
                e.powerup.bomb.elementAt(i);
            }//if
        }
        
         for(int i=0;i<e.powerup.shield.size();i++){
            Thing x=(Thing) e.powerup.shield.elementAt(i);
            
            if(x.getSprite().collidesWith(sprite,true)){
                curHP=curHP-2;
                e.audio.playMusic("boom");
            }//if
        }
        
    }
    
    
      public void createSplash(){
        splashcoolDown--;
        if(splashcoolDown<=0){
            try{
                
       //          for(int l=1;l<=10;l++){
                 for(int i=0;i<36;i++){
                    for(int l=1;l<=100;l+=5){
                        int rx=(int)(l*Math.cos((deg*Math.PI)/180)+e.stageWidth/2);
                        int ry=(int)(l*Math.sin((deg*Math.PI)/180)+e.stageHeight/2);

                        Sprite s= new Sprite(Image.createImage("//particle.png"));
                        s.setPosition(rx, ry);
                        s.paint(e.g);

                        particles.addElement(s);
                        
                    }//inner for
                 }//for
            }catch(Exception e){}//try
          splashcoolDown=1000;
        }//splashCooldown
    }//splash
    
    public void renderSplash(){
        k++;
     //for(int l=1;l<=10;l++){
      //  int deg=0;
        for(int i=0;i<particles.size();i++){//,k+=10){
            for(int l=1;l<=100;l+=5){
                    int rx=(int)(l*Math.cos((deg*Math.PI)/180)+e.stageWidth/2);
                    int ry=(int)(l*Math.sin((deg*Math.PI)/180)+e.stageHeight/2);

                    Sprite s= (Sprite) particles.elementAt(i);
                    s.setPosition(rx, ry);
                    s.paint(e.g);

                   // particles.addElement(s);
    
            }//inner for     
       }//outer for
    }//renderSplash
    
    
    public void animateSplash(){
    // for(int l=1;l<=10;l++){
       deg+=10;
         for(int i=0;i<particles.size();i++){;//,k+=10){
               for(int l=1;l<=50;l+=5){   
                    int rx=(int)(l*Math.cos((deg*Math.PI)/180)+e.stageWidth/2);
                    int ry=(int)(l*Math.sin((deg*Math.PI)/180)+e.stageHeight/2);

                    Sprite s= (Sprite) particles.elementAt(i);
                    s.setPosition(rx, ry);
                    s.paint(e.g);

                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    if(deg==350){
                       deg=0;
                   }
               }//inner for     
           }//for
    
    }//animate Splash
    
    
    
    
    
    
    
  
    
    
    
    
    
    
    
    
    
}//boss
