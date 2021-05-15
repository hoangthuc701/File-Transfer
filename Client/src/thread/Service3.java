/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sang
 */
public class Service3 extends Thread{

    @Override
    public void run() {
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket();
            InetAddress ip = InetAddress.getByName("localhost");
            String str = "hello";
            DatagramPacket datagramPacket = new DatagramPacket(str.getBytes(), str.length(), ip, 3000);
            datagramSocket.send(datagramPacket);
        }   catch (SocketException ex) {
            Logger.getLogger(Service3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Service3.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            datagramSocket.close();
        }
         //To change body of generated methods, choose Tools | Templates.
    }
}
