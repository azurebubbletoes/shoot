
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ceruleansea
 */
public class Audio {
     Player sound;
     InputStream tune;
    Vector sounds;
     public Audio(){sounds=new Vector();}
    
    
    
    public void playMusic(String type){
        String f="";
        int loopCount=1;
        if(type.equals("boom")){
            f="//assets/boom.wav";
        }
        if(type.equals("background")){
            f="//assets/background.wav";
            loopCount=1000;
        }
        if(type.equals("laser")){
            f="//assets/laser.wav";
        }
        if(type.equals("pew")){
            f="//assets/pew.wav";
        }
        if(type.equals("powerup")){
            f="//assets/powerup.wav";
        }
        
        tune = this.getClass().getResourceAsStream(f);
        try{
            sound = Manager.createPlayer(tune, "audio/x-wav"); // create a wave object from the InputStream of the type wav file
            sound.prefetch(); //initialize the sound
            sound.setMediaTime(0L);
           
            sound.setLoopCount(loopCount);
            sound.start();
          
        
        }catch(Exception e){}

    }
    
    
    
    public void stopAll(){
        try{
            sound.stop();
        }catch(Exception e){}    
    }
    

    
    
  

    
}
