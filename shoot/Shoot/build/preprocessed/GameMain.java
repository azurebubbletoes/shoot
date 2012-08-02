/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.*;

/**
 * @author ceruleansea
 */
public class GameMain extends MIDlet {//implements CommandListener {
    Engine g;
   // Command start;
   // Alert a;
    Display screen;
    
    
    public GameMain(){
        g = new Engine(this);
         
    }
    
    public void startApp() {
        screen = Display.getDisplay(this);
        screen.setCurrent(g);

       
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }

  /*  public void commandAction(Command c, Displayable d) {
       if(c==start){
             g = new Engine(this);
             display.setCurrent(g);
       
       }//start
    }*/
}
