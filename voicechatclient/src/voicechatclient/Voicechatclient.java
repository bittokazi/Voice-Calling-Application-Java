/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package voicechatclient;

import static com.sun.jndi.toolkit.dir.SearchFilter.format;
import static com.sun.org.apache.bcel.internal.classfile.Utility.format;
import static java.awt.PageAttributes.MediaType.B;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import static java.lang.String.format;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author Bitto
 */
public class Voicechatclient {
    public static byte[][] data=new byte[100000][1600];
public static int[] incall=new int[10000000];
public static DataInputStream[] din=new DataInputStream[100000];
public static DataOutputStream[] dout=new DataOutputStream[100000];
public static int[] call=new int[100000];
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, LineUnavailableException {
        // TODO code application logic here

    System.out.println("ok");    

        int id=0;
        while(true)
        {
           
            
            try
            {
               System.out.println("waiting for client "+id);
               if(id==0) {
                   client c=new client("Thread"+id,id);
                   id++;
                c.getConnection();
                c.start();
               }
               else {
                   client c=new client("Thread"+id);
                   id++;
                c.getConnection();
                c.start();
               }
                
                
                
            }
            catch(Exception ex)
            {
                //ex.printStackTrace();
            }
        }
    }
    
}



class client implements Runnable {

public static ServerSocket soc;    
private Socket ClientSocket;


public static int id;
//public static byte[][] data=new byte[100000][1600];
//public static int[] incall=new int[10000000];

private int i;
public int me;
public int callto;
private Thread t;
private String s;

private int co=0;
public client() {
    
}
public client(String s1) {
    s=s1;
}
public client(String s1,int i) {
    try {
        soc=new ServerSocket(5217);
    } catch (IOException ex) {
         //System.out.println(ex);
    }
    s=s1;
    id=i;
}
public void getConnection() {
    while (true) {
    try { 
            //soc=new ServerSocket(5217);
        ClientSocket=soc.accept();
        Voicechatclient.din[id]=new DataInputStream(ClientSocket.getInputStream());
    Voicechatclient.dout[id]=new DataOutputStream(ClientSocket.getOutputStream());
        Voicechatclient.dout[id].writeUTF("Your id is: "+id+" Please Give an id:");
        break;
    } catch (IOException ex) {
        System.out.println(ex);
    }
    }
}
public void run() {
    try {
    //Socket ClientSocket = Csoc;
    
    
    
    
    me=id;
    Voicechatclient.incall[id]=0;
    Voicechatclient.call[id]=0;
    id++;
    
    }
    catch(Exception ex) {
        
    }
    i=0;
    int state=0;
    while (true) {
        try {
           if (Voicechatclient.incall[me]==0) {
               //System.out.println("id "+me+" "+Voicechatclient.incall[me]);
               
               if(Voicechatclient.din[me].available()>0) {
                   callto=Voicechatclient.din[me].readInt();
                   System.out.println(callto);
                   Voicechatclient.data[callto]="1".getBytes();
                   Voicechatclient.data[me]=null;
 //System.out.println("id "+callto+" "+Voicechatclient.incall[callto]);
                   //Voicechatclient.incall[callto]=1;
                   //System.out.println("id "+callto+" "+Voicechatclient.incall[callto]);
                   Voicechatclient.incall[me]=1;
                   
                   Voicechatclient.call[me]=callto;
                   Voicechatclient.call[callto]=me;
                   System.out.println("call "+me+" "+Voicechatclient.call[me]);
                   System.out.println("call "+callto+" "+Voicechatclient.call[callto]);
                   
               
                  //Voicechatclient.incall[callto]=1;
                   //clienttome a=new clienttome("Threadme"+me,me);
            //a.start();
            co=1;
                   state=1;
               }
               
            }
           else if(Voicechatclient.incall[me]==1) {
               if(co==0) {
                   Voicechatclient.dout[me].write(Voicechatclient.data[me]);
                   System.out.println("incall id "+me+" "+Voicechatclient.incall[me]);
                   //clienttome a=new clienttome("Threadme"+me,me);
            //a.start();
            co=1;
               }
               //dout.write(Voicechatclient.data[me]);
                //System.out.println("incall id "+me+" "+Voicechatclient.incall[me]);
               i=0;
               //Thread.sleep(2000);
               byte[] b=new byte[1600];
               while(i<1600) {
                    b[i]=Voicechatclient.din[me].readByte();
                   //Voicechatclient.data[Voicechatclient.call[me]][i]=Voicechatclient.din[me].readByte();
                    i++;
               }
                Voicechatclient.dout[Voicechatclient.call[me]].write(b); 
           }
        } 
        catch (Exception ex) {
            //break;
            //System.out.println(ex);
        }
    }
    
    
    
}
public void start ()
   {
       t=new Thread(this,s);
       t.start();
   }
}




class clienttome extends client implements Runnable {
private Thread t;
private String s;
private int mee;
public clienttome() {
        
}
public clienttome(String s1,int i) {
    s=s1;
    mee=i;
}
public void run() {
    System.out.println("incall id "+mee+" "+Voicechatclient.incall[mee]);
    while (true) {
    try {
        
        //Voicechatclient.dout[Voicechatclient.call[mee]].write(Voicechatclient.data[Voicechatclient.call[mee]]);
       
    } catch (Exception ex) {
        //System.out.println(ex);
    }
    }
} 
public void start ()
   {
       t=new Thread(this,s);
       t.start();
   }
}