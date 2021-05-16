/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.sun.media.jfxmedia.MetadataParser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import filesever.*;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
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
        this.sourcFolder = srcDir;
        service3Manage = new Service3Manage();
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
        String info = service3Manage.getIp() + "_" + service3Manage.getPort();
        SocketActions.send(info, dos);
        SocketActions.sendFile(sourcFolder + "/Data/data.txt", oos);
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(view.getBtnAdd().getActionCommand())) {
            view.addFile();
            SocketActions.send("UPDATE", dos);
            SocketActions.sendFile(sourcFolder + "/Data/data.txt", oos);
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
            SocketActions.sendFile(sourcFolder + "/Data/data.txt", oos);
        }
    }
}
