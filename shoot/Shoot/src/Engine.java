
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;
import javax.microedition.midlet.MIDlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ceruleansea
 */
public class Engine extends GameCanvas implements Runnable {
    MIDlet m;
    
    //GameMain gm;
    GameDesign design;
    Thread thread=new Thread(this);
    boolean stopper=true;
    Graphics g;
    Graphics f;
    int stageWidth,stageHeight;
    int playerHeight,playerWidth;
    Dude player;
    int playerx,playery;
    Sprite background;
    Asteroids asteroids;
    PowerUp powerup;
    int chosen,choicegap;
    int puCooldown;
    Audio audio;
    DB db;
    Damager damager;
    boolean gameover,isBoss,start,cut;
   // Cutscene cutscene;
    public int left,center,right;
    int cutcounter,cutIndex;
    Vector cutscene;
    Sprite dummy;//,yuno;
    Boss boss;
    int bossTimer,yunotimer;
    int tileRun;
    TiledLayer tl;
    
    
    public Engine(MIDlet ga){
        
        super(true);
        this.setFullScreenMode(true);
        g=this.getGraphics();
        f=this.getGraphics();
        start=true;
        m=ga;
        cutcounter=100;
        gameover=false;
        left=127;
        center=142;
        right=157;
        chosen=0; //chosen powerup
        puCooldown=5; //choice cooldown.
        choicegap=20;
        audio=new Audio();
        cutIndex=1;
        yunotimer=1;
        
        
        
        cut=true;
        isBoss=false;
        bossTimer=2000;
        stageHeight=this.getHeight();
        stageWidth=this.getWidth();
        cutscene=new Vector();
        tileRun=5;
        f.setColor(255, 255, 255);
    
        
        
        
        thread.start();
        
    }

    public void render(){
        //if(!gameover){
            bossTimer--;
            
           // background.setPosition(0, 0);
            //background.paint(g);
            tl.paint(g);

            player.setPosition(player.getX(),player.getY());
            player.getSprite().paint(g);
            //g.setColor(0, 255, 0);
            getStats();
           // g.fillRect(player.getX(),player.getY()-4,player.getHpbar(), 2); 

            player.renderBullets();
            asteroids.render();
            powerup.render();
            powerup.renderPowerUps();
            damager.render();
            
            if(bossTimer<=0){
                isBoss=true;
            }
            
            if(isBoss){
                boss.renderBoss();
                boss.renderBullets();
            }
         
        
        this.flushGraphics();
    }
    public void animate(){
        tileRun--;
       // if(!gameover){
        if(tileRun<=0){
             int arr[] = new int[tl.getColumns()];

           for(int i = 0 ; i < tl.getColumns() ; i++)
               arr[i] = tl.getCell(i, tl.getRows()-1);
           for(int i = 0 ; i < tl.getColumns() ; i++)
                for(int j = tl.getRows() - 1 ; j > 0 ; j--)
                    tl.setCell(i, j, tl.getCell(i, j-1));
           for(int i = 0 ; i < tl.getColumns() ; i++)
               tl.setCell(i, 0, arr[i]);
        tileRun=5;
        }
       
        
            player.animateBullets();
            asteroids.animate();
            powerup.animate();
            powerup.animatePowerups();
            damager.animate();
            
            if(isBoss){
               
             boss.animateBoss();
             boss.animateBullets();
            }
            //design.animateExplosions();
    }
    public void input(){
        int keystate=this.getKeyStates();
        if((keystate&this.UP_PRESSED) !=0) {
          
           player.setY(player.getY()-player.getSpeed());
        }
        if((keystate&this.DOWN_PRESSED) !=0) {
           
            player.setY(player.getY()+player.getSpeed());
        }
        if((keystate&this.LEFT_PRESSED) !=0) {
             
           player.setX(player.getX()-player.getSpeed());
        }
        if((keystate&this.RIGHT_PRESSED) !=0) {
             
            player.setX(player.getX()+player.getSpeed());
        }
        if ((keystate&this.FIRE_PRESSED)!=0) {
            if(!player.getBomb()){
                player.fire();
            }else{
                powerup.Activate("bomb");
            }
           
        }
        
        //no 1
        if((keystate&this.GAME_A_PRESSED)!=0){ 
          puCooldown--;
          if(puCooldown<=0){
           if(chosen==1 && player.getPowerup().size()>0){
                chosen=player.getPowerup().size();
            }else{
                chosen--;
            } //else
           puCooldown=5;
          }//puCooldown
        }// no1
        
        //no 3
        if((keystate&this.GAME_B_PRESSED)!=0){ 
          puCooldown--;
          if(puCooldown<=0){
            if(chosen==player.getPowerup().size() && player.getPowerup().size()>0){
                chosen=1;
            }else{
                chosen++;
            }//else
            puCooldown=5;
          }//puCooldown 
        }//no3
        
        if((keystate&this.GAME_C_PRESSED)!=0){ 
          
            
          if(choicegap<=0){
               if(chosen>0){     

                   Thing t=(Thing) player.getPowerup().elementAt(chosen-1);
                   String name=t.getName();
                   powerup.Activate(t.getName());

                   if(name.equals("shield")){
                       player.setShield(true);
                   }
                   if(name.equals("bomb")){
                       player.setBomb(true);
                   }
                   if(name.equals("laser")){
                       player.setLaser(true);
                   }
                   player.removePowerUp(chosen-1);
         
                   //if(chosen==1){
                       chosen=0;
                 //  }else{
                   //    chosen=1;
                   //}
               }//if
               choicegap=20;
          }//if choicegap
        }//no3
        
        
    };
    public void analyze(){
        setBounds();
        asteroids.evaluateCollisions();
        powerup.evaluateCollisions();
        damager.evaluateCollisions();
        if(isBoss){boss.evaluateCollisions();}
        
        if(player.getCurhp()<=0){
            if( player.getLivesleft()>0){
                player.setLivesleft(player.getLivesleft()-1);
                player.setCurhp(player.getMaxlife());
            }//if
            else{
              
                gameover=true;
                
                db.insert(player.getScore()+"");
             
          
            }//else
        }//if
            
        if(boss.curHP<=0){
            isBoss=false;
            bossTimer=2000;
            boss.curHP=boss.maxHP;
        }
           
       
    };
    
   public void setBounds(){
        if(player.getX()<=0){
             player.setX(0);
        }
        if(player.getX()>stageWidth-playerWidth){
            player.setX(stageWidth-playerWidth);    
        }
        if(player.getY()<=0){
             player.setY(0);
        }
        if(player.getY()>stageHeight-playerHeight){
            player.setY(stageHeight-playerHeight);    
        }
        
        //boss
    
        if(isBoss){
           if(boss.sprite.getX()<=0){
               boss.setX(0);
                 boss.goRight=true;
            }
            if(boss.sprite.getX()>stageWidth-boss.sprite.getWidth()){
                   boss.setX(stageWidth-boss.sprite.getWidth());
              
                boss.goRight=false;
            }
            if(boss.sprite.getY()<=0){
               
                boss.setY(20);
            }
            if(boss.sprite.getY()>stageHeight-boss.sprite.getHeight()){
                    boss.setY(stageHeight-boss.sprite.getHeight());
               
            }
        }
    }
   
   public void getStats(){
       //hp
       choicegap--;
       
       int cur=player.getCurhp();
       
       if(cur>=75){
        g.setColor(0, 255, 0);
       }
       if(cur<=74 && cur>=50){
         g.setColor(255, 255, 128);
       }
       if(cur<=49&& cur>=25){
            g.setColor(255, 184, 149);
       }
       if(cur<24){
           g.setColor(255,0,0);
       }
        //25+100+15+
       g.fillRect(25, 7, player.getHpbar(), 4);
       
       f.drawString("HP: ", 0, 1,Graphics.TOP|Graphics.LEFT);
         
       try{
           g.drawImage(Image.createImage("//assets/heart.png"), 85, 2, Graphics.TOP|Graphics.LEFT);
           f.drawString("x", 102, 1,Graphics.TOP|Graphics.LEFT);
           f.drawString(""+player.getLivesleft(), 112, 1,Graphics.TOP|Graphics.LEFT);
           //f.drawString(null, UP, UP, cur);
           
           int size=player.getPowerup().size();
           
           String s="//assets/powerupmenu.png";
           
           switch(chosen){
               case 1:s="//assets/powerupmenu_left.png";
                      break;
               case 2:s="//assets/powerupmenu_center.png";
                      break;
               case 3:s="//assets/powerupmenu_right.png";
                      break;
           }
           
           
           if(chosen==0||size==0){
             f.drawImage(Image.createImage("//assets/powerupmenu.png"),125, 2, Graphics.TOP|Graphics.LEFT);
           }else{
             f.drawImage(Image.createImage(s),125, 2, Graphics.TOP|Graphics.LEFT);  
           }
             f.drawImage(Image.createImage("//assets/coin.png"), 180, 2, Graphics.TOP|Graphics.LEFT);
             f.drawString(""+player.getScore(), 195, 2,Graphics.TOP|Graphics.LEFT);
           
           //populate
           int j=127;
           for(int i=0;i<size;i++,j+=15){
               Thing t=(Thing) player.getPowerup().elementAt(i);
               
               if(t.getName().equals("shield")){
                   s="//assets/pushield.png";
               }else if(t.getName().equals("bomb")){
                   s="//assets/publast.png";
               }else if(t.getName().equals("laser")){
                   s="//assets/pulaser.png";
               }else{
                   s="//assets/pulife.png";
               }
               
               f.drawImage(Image.createImage(s), j, 2, Graphics.TOP|Graphics.LEFT);
                
           }
       }catch(Exception e){}
       
       
   }
    
    public void run(){
        while(stopper){
            try {
               Thread.sleep(20);
            } catch (InterruptedException interruptedException) {}
        if(!start){
                if(!gameover){    
                    
                   if(!isBoss){ 
                    
                    asteroids.create();
                   }
                   
                   if(isBoss){
                       boss.createBullet();
                   }
                   damager.create();
                   powerup.generate();
                    input();
                    analyze();
                    animate(); 
                    render();



                }else{
                    Image ii=null;
                    try{
                        ii=Image.createImage("//assets/gameover.png");

                     }catch(Exception e){}
                         f.drawImage
                                 (ii, 0, 0, Graphics.TOP|Graphics.LEFT);
                        db.display(g);
                        this.flushGraphics();
                        boolean zz=true;
                     while(zz){   
                        int key=this.getKeyStates();
                        if((key&this.FIRE_PRESSED) !=0) {
                            ii=null;
                            this.flushGraphics();
                            zz=false;
                            Reset();
                            gameover=false;
                            break;
                        }//if
                     }
                }//else
            }//if !start
            else{
                cutcounter--;
                cutscene.removeAllElements();
                Image img=null;
             //   dummy=
                try {
                    img = Image.createImage("//assets/"+cutIndex+".png");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                dummy=new Sprite(img);
                cutscene.addElement(dummy);
                dummy.setPosition(0, 0);
                dummy.paint(g);
                this.flushGraphics();
                
                if(cutcounter<=0){cutcounter=100;cutIndex++;}
                
             if(cutIndex>6){
                 cut=false;
                     Sprite ii=null;
                      Sprite yuno=null;
                        try{

                            ii=new Sprite(Image.createImage("//assets/splash.png"));
                           
                            

                         }catch(Exception e){}
                             //f.drawImage(ii, 0, 0, Graphics.TOP|Graphics.LEFT);
                          try{   
                             yuno=new Sprite(Image.createImage("//assets/yuno.png"));
                          }catch(Exception e){}
                          ii.setPosition(0, 0);
                          ii.paint(g);
                          yuno.setPosition(60, yunotimer==1?260:280);
                          yuno.paint(g);
                          this.flushGraphics();
                                     
                                                          
                            
                            boolean zz=true;
                         while(zz){ 
                             ii.setPosition(0, 0);
                             ii.paint(g);
                             
                             yuno.setPosition(60, yunotimer==1?260:280);
                             yuno.paint(g);
                             
                             this.flushGraphics();
                          
                             
                            // yuno.setPosition(60, yunotimer==1?260:280);
                             //yuno.paint(g);
                             //this.flushGraphics();
                             
                             int key=this.getKeyStates();
                             
                             if((key&this.UP_PRESSED) !=0) {
                                
                                yunotimer=(yunotimer==1?2:1);
                             }//if
                             
                              if((key&this.DOWN_PRESSED) !=0) {
                                  
                                  yunotimer=(yunotimer==2?1:2);
                                  
                                
                            }//if
                              
                            
                            if((key&this.FIRE_PRESSED) !=0) {
                                
                                if(yunotimer==1){
                                    ii=null;
                                    this.flushGraphics();
                                    zz=false;
                                    Reset();
                                    start=false;
                                    break;
                                }else{
                                    
                                
                                }
                            }//if
                            
                            
                         }
             }
             
             
            }//!start nga else
        }//while
        
    }//run
    
    
    
    public void Reset(){
        this.setFullScreenMode(true);
        g=this.getGraphics();
        f=this.getGraphics();
        gameover=false;
        left=127;
        center=142;
        right=157;
        
        chosen=0; //chosen powerup
        puCooldown=5; //choice cooldown.
        choicegap=20;
        audio=new Audio();
        db=new DB();
        
//        recordInserted=false;
        try{
            //confusing.. hehe
            player=new Dude(new Sprite(Image.createImage("//assets/player.png")),"player",g);
        //    background=new Sprite(Image.createImage("//assets/background.png"));
           
            
        }catch(Exception e){}
        
         player.setScore(0);
        player.setCurhp(100);
         player.setLivesleft(3);
        
        playerHeight=player.getHeight();
        playerWidth=player.getWidth();
        
        playery=stageHeight/2;
        playerx=stageWidth/2;
        
        player.setX(playerx);
        player.setY(playery);
        isBoss=false;
        bossTimer=2000;
        tileRun=5;
        f.setColor(255, 255, 255);
       
        design=new GameDesign(g,player);
        damager=new Damager(stageWidth,stageHeight,player,g);
        powerup=new PowerUp(g,stageWidth,stageHeight,player);
        asteroids=new Asteroids(g,stageWidth,stageHeight,player,powerup,damager,design);
         boss=new Boss(this);
        try {
            tl=design.getLebackground();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        audio.playMusic("background");
    }
}

