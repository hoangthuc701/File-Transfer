/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.net.DatagramPacket;

/**
 *
 * @author Sang
 */
public class Sevice3 extends Thread{
    public Sevice3(DatagramPacket datagramPacket){
        System.out.println(datagramPacket.getPort());
    }
}
