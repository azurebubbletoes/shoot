

import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.rms.RecordComparator;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ceruleansea
 */
public class DB implements RecordComparator{
    RecordStore rs;
     int[] s;
     int count;
    
    public DB() {
        try{
            rs=RecordStore.openRecordStore("scores", true);
            count=0;
           
           // insert("12");
            
   
        }catch(Exception e){}
    }//DB
    
    
    public void insert(String outputData){
        
        try{
            byte[] byteOutputData = outputData.getBytes();
            rs.addRecord(byteOutputData, 0, byteOutputData.length);
       }catch(Exception e){}
    }
   
    
    
    
    public void display(Graphics g){
        
        
        int y=100;
         try{
           int[] v=new int[rs.getNumRecords()];
            RecordEnumeration enumeration=rs.enumerateRecords(null, this, false);
            while(enumeration.hasNextElement()){
             
                v[count++]=Integer.parseInt(new String(enumeration.nextRecord()));
               
              //  y+=15;

            }
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            int length=v.length>5?5:v.length;
            for(int i=0;i<length;i++,y+=15){
                g.drawString(""+v[i], 40, y, Graphics.TOP|Graphics.LEFT);
            }
          
            
            
            
        
        }catch(Exception e){e.printStackTrace();}
         close();

    };
    
    public void close(){
        try{
            rs.closeRecordStore();
        }catch(Exception e){}
     }//close

    public int compare(byte[] rec1, byte[] rec2) {
        int r1 = Integer.parseInt(new String(rec1));
        int r2 = Integer.parseInt(new String(rec2));
        
        if(r1 > r2) {
            return RecordComparator.PRECEDES;
        } else if(r1 < r2) {
            return RecordComparator.FOLLOWS;
        } else {
            return RecordComparator.EQUIVALENT;
        }
        
    }
}
