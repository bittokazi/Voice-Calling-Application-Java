/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package voicechat1;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author Bitto
 */
public class VoiceChat1 extends client {
public static DataInputStream din;
public static DataOutputStream dout;
public static Socket soc;
public static int accp;
    /**
     *
     */
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws LineUnavailableException, IOException {
        
    
        
        
        AudioFormat format = new AudioFormat(8000, 16, 1, true, true);
TargetDataLine microphone;
try{
    soc=new Socket("127.0.0.1",5217);
    din=new DataInputStream(soc.getInputStream()); 
        dout=new DataOutputStream(soc.getOutputStream());
    
    
    
    microphone = AudioSystem.getTargetDataLine(format);

    DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
    microphone = (TargetDataLine)AudioSystem.getLine(info);
    microphone.open(format);

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int numBytesRead;
    byte[] data = new byte[microphone.getBufferSize() / 5];
    microphone.start();

    int bytesRead =0;

   
        
        
        

        int i=0;
         System.out.println("none");
        while(true){ //Just so I can test if recording my mic works...
             try{
            if(state==0) {
                 System.out.println("none");
                System.out.println(din.readUTF());
                System.out.println("none");
                accp=0;
                    client c=new client("Thread");
                    c.start();
                    
    int name=input.nextInt();
    dout.writeInt(name);
    accp=1;
    state=1;
    
            }
            else if(state==1) {
            i=0;
            numBytesRead = microphone.read(data, 0, data.length);
            i=bytesRead;
            bytesRead = bytesRead + numBytesRead;
           System.out.println(bytesRead);
            out.write(data, 0, numBytesRead);
           System.out.println(numBytesRead);
            dout.write(data);
            }
             }
             catch(Exception e){
        System.out.println(e);
    }
        }
        //System.out.println("out");
    
    
    //microphone.close();
}
    

catch(Exception e){
    e.printStackTrace();
}

        
        
        
        
        
    }
    
}








class client implements Runnable {
    
//public static Socket soc;



public static int state=0;
public static Scanner input = new Scanner(System.in);


public int i;
private Thread t;
private String s;

public client() {
        
}
public client(String s1) {
    s=s1;
}
public void run() {
    System.out.println("listening");
    AudioFormat format = new AudioFormat(8000, 16, 1, true, true);
        SourceDataLine speakers = null;
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
    try {
        speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
    } catch (LineUnavailableException ex) {
        
    }
    try {
        speakers.open(format);
    } catch (LineUnavailableException ex) {
        
    }
        speakers.start();
    i=0;
    int j=0;
    while (true) {
        try {
            byte[] b=new byte[1600];
                int i=0;
                while(i<1600) {
                    // System.out.println("listening "+j);
                     //System.out.println("CLICK 1 to accept");
                    b[i]=VoiceChat1.din.readByte();
                    i++;
                    //j++;
                    
                    //System.out.println("CLICK 1 to accept");
                }
                //System.out.println("listening "+j);
                //j++;
                    speakers.write(b, 0, 1600);
                    System.out.println(b);
                    //System.out.println(b[i]);
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