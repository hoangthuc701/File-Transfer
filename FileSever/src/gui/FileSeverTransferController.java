/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import filesever.*;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import thread.*;
/**
 *
 * @author Sang
 */
public class FileSeverTransferController implements ActionListener {
    private FileSeverTransferView view;
    FileSever fileSever;
    ObjectOutputStream oos;
    DataOutputStream dos;
    Service3Manage service3Manage;
    String sourcFolder = null;
    public FileSeverTransferController(FileSeverTransferView view, String srcDir) {
        System.out.print("thuc run");
    	this.sourcFolder = srcDir;
        service3Manage = new Service3Manage(2345);
        service3Manage.start();
        this.view = view;
        view.getBtnAdd().addActionListener(this);
        view.getBtnExt().addActionListener(this);
        view.getMenuItemDelete().addActionListener(this);  
        fileSever = new FileSever("127.0.0.1", 1234);
        fileSever.connectServer();
        try {
            oos = new ObjectOutputStream(fileSever.getSocket().getOutputStream());
            dos = new DataOutputStream(fileSever.getSocket().getOutputStream());  
        } catch (IOException ex) {
            Logger.getLogger(FileSeverTransferController.class.getName()).log(Level.SEVERE, null, ex);
        }
        SocketActions.send("SEVER", dos);
        System.out.println(fileSever.getSocket().getPort());
        String info = "127.0.0.1" + "_" + "2345";
        SocketActions.send(info, dos);
        SocketActions.sendFile(srcDir + "/Data/data.txt", oos);
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(view.getBtnAdd().getActionCommand())) {
            view.addFile();
            SocketActions.send("UPDATE", dos);
            SocketActions.sendFile("data.txt", oos);
        }
        else if(e.getActionCommand().equals(view.getBtnExt().getActionCommand())){
            SocketActions.send("QUIT", dos);
            SocketActions.closeStream(dos);
            SocketActions.closeStream(oos);
            service3Manage.kill();
            fileSever.closeSocket();
            view.dispose();
        }
        else if(e.getActionCommand().equals(view.getMenuItemDelete().getActionCommand())){
            view.deleteFile();
            SocketActions.send("UPDATE", dos);
            SocketActions.sendFile("data.txt", oos);
        }
    }
}
