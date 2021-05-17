/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesever;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 *
 * @author Sang
 */
public class FileSever {
    private Socket socket;
    private String host;
    private int port;
    public FileSever(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public Socket getSocket() {
        return socket;
    }
    public void connectServer() throws UnknownHostException, IOException {
            socket = new Socket(host, port);
    }
    public void closeSocket() {
       try {
           if (socket != null) {
               socket.close();
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
    }
}
