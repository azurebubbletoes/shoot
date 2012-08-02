
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
public class PowerUp extends Thing{
    Graphics g;
    int cooldown;
    Random rand;
    Vector powerUp,shield,bomb,laser;
    int stageWidth,stageHeight;
    Dude p;
    int shieldCooldown,bombCooldown;
    Audio audio;
    int k=0;
    
    public PowerUp(Graphics g,int w,int h,Dude p){
            super();
            audio= new Audio();
            this.g=g;
            cooldown=100;
            rand=new Random(40);
            shield=new Vector();
            powerUp=new Vector();
            stageWidth=w;
            stageHeight=h;
            this.p=p;
            shieldCooldown=400;
            bombCooldown=50;
            bomb=new Vector();
            laser=new Vector();
            
     }//constructor
        
    public void setCooldown(int s){cooldown=s;}
    public int getCooldown(){return cooldown;}
    
    
    public Vector getShield(){return shield;}
    public Vector getBomb(){return bomb;}
    public Vector getLaser(){return laser;}
    
    public Sprite getSprite(){return super.getSprite();}
    public void setPosition(int x,int y){getSprite().setPosition(x, y);}
    public int getHeight(){return getSprite().getHeight();}
    public int getWidth(){return getSprite().getWidth();}
    public int getX(){return getSprite().getX();}
    public int getY(){return getSprite().getY();}
    public String getName(){return super.getName();}
    public boolean collidesWith(Sprite s){return getSprite().collidesWith(s, true);}

    public void generate(){
        cooldown--;
        if(cooldown<=0){
            int x=rand.nextInt(4)+1;
            String i="",n="";
                
            //1 shield
            //2 laser
            //3 life
            //4 bomb
            //5 
            //6
           
                switch(x){
                    case 1: i="//assets/shield.png"; 
                            n="shield";
                            break;
                    case 2: i="//assets/laser.png"; 
                            n="laser";
                            break;
                    case 3: i="//assets/life.png"; 
                            n="life";
                            break;
                    case 4: i="//assets/bomb.png"; 
                            n="bomb";
                            break;
                }//switch
                
            try{
               
                Sprite f=new Sprite(Image.createImage(i));
                int xx= Math.abs(rand.nextInt(stageWidth))%stageWidth;
                int yy=0-rand.nextInt(50)-f.getHeight(); 
                f.setPosition(xx, yy);
                powerUp.addElement(new Thing(f,n));                    
                
            }catch(Exception e){}//exception
            cooldown=rand.nextInt(200)+200;
        }//if
        
    }//generate
    public void render(){
        for(int i=0;i<powerUp.size();i++){
            Thing t= (Thing) powerUp.elementAt(i);
            t.getSprite().setPosition(t.getSprite().getX(), t.getSprite().getY());
            t.getSprite().paint(g);
        }//for
    }//render
    
    public void animate(){
         for(int i=0;i<powerUp.size();i++){
            Thing t= (Thing) powerUp.elementAt(i);
            t.getSprite().setPosition(t.getSprite().getX(), t.getSprite().getY()+rand.nextInt(1)+1);
            t.getSprite().paint(g);
        }//for
    }//animate
    
    public void evaluateCollisions(){
        Sprite temp=p.getSprite();
        
        for(int i=0;i<powerUp.size();i++){
            Thing t= (Thing)powerUp.elementAt(i); 
            if(temp.collidesWith(t.getSprite(), true)){
                p.addPowerUp(t);
                powerUp.removeElementAt(i);
                audio.playMusic("powerup");
            }//if
        }//powerup
    }//evaluatecollisions
    
    public void Activate(String name){
        
        
      if(name.equals("shield")){
         
          p.setShield(true);
          int deg=0;
          for(int i=0;i<36;i++,deg+=10){
             int rx=(int)(40*Math.cos((deg*Math.PI)/180)+p.getSprite().getX()+p.getWidth()/2);
             int ry=(int)(40*Math.sin((deg*Math.PI)/180)+p.getSprite().getY()+p.getHeight()/2);
             try{
                Sprite s= new Sprite(Image.createImage("//assets/particle.png"));
                s.setPosition(rx, ry);
                s.paint(g);
                Thing t=new Thing(s,"particle");
                shield.addElement(t);
             }catch(Exception e){}
          }//for
      }else if(name.equals("bomb")){
            bombCooldown--;
            if(bombCooldown>0){
                try{
                    for(int i=0;i<10;i++){
                         Sprite s= new Sprite(Image.createImage("//assets/particle.png"));
                         int rx=Math.abs(rand.nextInt(stageWidth))+1;
                         int ry=Math.abs(rand.nextInt(50))+p.getSprite().getY()+1;
                         s.setPosition(rx, ry);
                         s.paint(g);
                         audio.playMusic("pew");
                         Thing t=new Thing(s,"particle");
                         bomb.addElement(t);
                         
                    }//for
                }catch(Exception e){}   //exception
            }else{
                bombCooldown=50;
                p.setBomb(false);
            }
      }else if(name.equals("laser")){
          p.setLaser(true);
            try{
                Sprite s= new Sprite(Image.createImage("//assets/bar.PNG"));
                s.setPosition(p.getSprite().getX()-s.getWidth()/2+p.getWidth()/2,p.getSprite().getY()+p.getHeight()/2);
                s.paint(g);
                audio.playMusic("laser");
                Thing t=new Thing(s,"laser");
                laser.addElement(t);
            }catch(Exception e){}
          
      }//else if
    }//activate
    
    public void renderPowerUps(){
        k++;
               int deg=0;
                for(int i=0;i<shield.size();i++,k+=10){
                  int rx=(int)(40*Math.cos((k*Math.PI)/180)+p.getSprite().getX()+p.getWidth()/2);
                  int ry=(int)(40*Math.sin((k*Math.PI)/180)+p.getSprite().getY()+p.getHeight()/2);
                    //int rx=p.getSprite().getX();
                    //int ry=p.getS
                   Thing t=(Thing)shield.elementAt(i);
                   t.getSprite().setPosition(rx,ry);
                   t.getSprite().paint(g);
                }//for
        
      //  if(p.getBomb()){
           
                 for(int i=0;i<bomb.size();i++){
                   Thing t=(Thing)bomb.elementAt(i);
                   t.getSprite().setPosition(t.getSprite().getX(),t.getSprite().getY());
                   t.getSprite().paint(g);
                }//for
            
           
     //   }
        
        //if(p.getLaser()){
            for(int i=0;i<laser.size();i++){
                Thing t=(Thing)laser.elementAt(i);
                t.getSprite().setPosition(t.getSprite().getX(),t.getSprite().getY());
                t.getSprite().paint(g);
            }//for
        //}//if
    }//render
    
    public void animatePowerups(){
        
        if(p.getShield()){
                shieldCooldown--;
            if(shieldCooldown<=0){
                shield.removeAllElements();
                p.setShield(false);
                shieldCooldown=400;

            }//inner if
        }//if naay shield
            
                for(int i=0;i<shield.size();i++,k+=10){
                  int rx=(int)(40*Math.cos((k*Math.PI)/180)+p.getSprite().getX()+p.getWidth()/2);
                  int ry=(int)(40*Math.sin((k*Math.PI)/180)+p.getSprite().getY()+p.getHeight()/2);
             
                   Thing t=(Thing)shield.elementAt(i);
                   t.getSprite().setPosition(rx,ry);
                   t.getSprite().paint(g);
                   
                  if(k==350){
                       k=0;
                   }
                }//for
            
  
        
        //bomb animate
        //if(p.getBomb()){
             for(int i=0;i<bomb.size();i++){
                 int speed=i+1;
                 Thing t=(Thing)bomb.elementAt(i);
                 t.getSprite().setPosition(t.getSprite().getX(),t.getSprite().getY()-speed);
                 t.getSprite().paint(g);
                   
                 if(t.getSprite().getY()<=0)
                    bomb.removeElementAt(i);
              }//for}
        //laser animate
             
             for(int i=0;i<laser.size();i++){
                 int speed=i+2; 
                 Thing t=(Thing)laser.elementAt(i);
                     t.getSprite().setPosition(t.getSprite().getX(),t.getSprite().getY()-speed);
                    t.getSprite().paint(g);
                   
                 if(t.getSprite().getY()<=0)
                    laser.removeElementAt(i);
             }//laser
    }//animate
}
