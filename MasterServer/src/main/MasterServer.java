/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import thread.ConnectThread;

/**
 *
 * @author Sang
 */
public class MasterServer {
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
    	ServerSocket masterServer = null;
        try {
            masterServer = new ServerSocket(1234);
            System.out.println("Successfully started the master server!");
            ArrayList<ConnectThread> listConnect = new ArrayList<>();
            while (true) {             
                Socket socket = masterServer.accept();
                ConnectThread connectThread = new ConnectThread(socket, listConnect);
                connectThread.start();
                listConnect.add(connectThread);
            }
        } catch (Exception e) {
        	
        } finally {
        	masterServer.close();
		}
    }
    public static void updateFileSever(ArrayList<ConnectThread> listConnect){
        for(ConnectThread connectThread : listConnect){
            if(connectThread.role.equals("CLIENT"))
            {
                connectThread.sendUpdate();
            }
        }
    }
}
