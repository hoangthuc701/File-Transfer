/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesever;

import common.FileInfo;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import gui.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
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
    public void connectServer() {
        try {
            socket = new Socket(host, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
