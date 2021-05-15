/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sang
 */
public class Service3Manage extends Thread{
    private volatile boolean isRunning = true;
    private DatagramSocket datagramSocket= null;   
    private String ip = "127.0.0.1";
    Random rand = new Random();
    int ranNum = rand.nextInt(2000)+1000;
    private int port = ranNum;
    public String getIp(){
        return ip;
    }
    public int getPort(){
        return port;
    }
    @Override
    public void run() {
        try {
            
            InetAddress inetAddress = InetAddress.getByName(ip);
            
            datagramSocket = new DatagramSocket(port, inetAddress);
            
            byte[] buf = new byte[1024];
            
            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
            
            datagramSocket.receive(datagramPacket);
            //while (true) {                
              //  Sevice3 sevice3 = new Sevice3(datagramPacket);
                //sevice3.start();
            //}
            
            //String str = new String(datagramPacket.getData(), 0, buf.length);
            //while (isRunning) {                
              //  str = new String(datagramPacket.getData(), 0, buf.length);
            //}
            //System.out.println(str);
        } catch (IOException ex) {
            System.out.println("File Sever Disconnect!");
        }  
        finally{
            datagramSocket.close();
        }
    }//To change body of generated methods, choose Tools | Templates.
    public void kill() {
       datagramSocket.close();
       isRunning = false;
   }
}
