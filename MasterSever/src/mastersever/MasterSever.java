/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mastersever;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import thread.ConnectThread;

/**
 *
 * @author Sang
 */
public class MasterSever {

    /**
     * @param args the command line arguments
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
    	ServerSocket masterServer = null;
        try {
            masterServer = new ServerSocket(1234);
            System.out.println("Start MasterSever Success!!");
            ArrayList<ConnectThread> listConect = new ArrayList<>();
            while (true) {             
                Socket socket = masterServer.accept();
                ConnectThread connectThread = new ConnectThread(socket, listConect);
                connectThread.start();
                listConect.add(connectThread);
            }
        } catch (Exception e) {
        	
        } finally {
        	masterServer.close();
		}
    }
    public static void updateFileSever(ArrayList<ConnectThread> listConect){
        for(ConnectThread connectThread : listConect){
            if(connectThread.role.equals("CLIENT"))
            {
                connectThread.sendUpdate();
            }
        }
    }
}
