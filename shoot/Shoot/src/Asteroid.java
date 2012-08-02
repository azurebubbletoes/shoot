
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ceruleansea
 */
public class Asteroid extends Thing{
    int damage;
    int score;
    int speed;
    int hp;
    int count;
    int maxHp;
    boolean status;
    Graphics g;
    
    public Asteroid(Sprite s, String n,Graphics d){
        super(s,n);
        damage=4;
        score=10;
        speed=2;
        hp=20; 
        maxHp=20;
        d=g;
        status=false;
    }
    
    public void setDamage(int x){damage=x;}
    public void setScore(int x){score=x;}
    public void setSpeed(int x){speed=x;}
    public void setHp(int x){hp=x;}
    public void setMaxhp(int x){maxHp=x;}
    public void setStatus(boolean s){status=s;}
    
    public int getDamage(){return damage;}
    public int getScore(){return score;}
    public int getSpeed(){return speed;}
    public int getHp(){return hp;}
    public int getMaxhp(){return maxHp;}
    public boolean getStatus(){return status;}
    
    public Sprite getSprite(){return super.getSprite();}
    public void setPosition(int x,int y){getSprite().setPosition(x, y);}
    public int getHeight(){return getSprite().getHeight();}
    public int getWidth(){return getSprite().getWidth();}
    public int getX(){return getSprite().getX();}
    public int getY(){return getSprite().getY();}
    public String getName(){return super.getName();}
    public boolean collidesWith(Sprite s){return getSprite().collidesWith(s, true);}
}
