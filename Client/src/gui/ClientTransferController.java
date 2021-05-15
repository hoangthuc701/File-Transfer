/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import common.FileInfo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import client.*;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import file.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import thread.*;
/**
 *
 * @author Sang
 */
public class ClientTransferController implements ActionListener{
    private ClientTransferView view;
    Client client;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    public ClientTransferController(ClientTransferView view){
        this.view = view;
        view.getBtnExt().addActionListener(this);
        view.getMenuItemInstall().addActionListener(this); 
        client = new Client("127.0.0.1", 1234);
        client.connectServer();
        Socket socket = client.getSocket();
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            SocketActions.send("CLIENT", dos);
            FileInfo fileInfo = SocketActions.getFile(ois);
            String des = "data.txt";
            FileActions.write(des, fileInfo);
            view.loadData();
            String line;
            while ((line = dis.readUTF()) != null) {
                if(line.equals("UPDATE"))
                {
                    fileInfo = SocketActions.getFile(ois);
                    FileActions.write(des, fileInfo);
                    view.loadData();
                }
            }
        } catch (IOException ex) {
            System.out.println("Disconnect!");
        }
        finally{
            SocketActions.closeStream(oos);
            SocketActions.closeStream(ois);
            SocketActions.closeStream(dos);
            SocketActions.closeStream(dis);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(view.getBtnExt().getActionCommand())) {
            //FileActions.clear("data.txt");
            view.dispose();
            SocketActions.closeStream(dos);
            SocketActions.closeStream(oos);
            client.closeSocket();
            
        }
        else if(e.getActionCommand().equals(view.getMenuItemInstall().getActionCommand())){
           new Service3().start();
        }
    }
}
