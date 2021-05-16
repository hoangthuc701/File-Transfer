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
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import thread.*;
import util.PrintFactory;

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
		this.view = view;
		view.getBtnAdd().addActionListener(this);
		view.getBtnExt().addActionListener(this);
		view.getBtnConnect().addActionListener(this);
		view.getMenuItemDelete().addActionListener(this);
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
        else if (e.getActionCommand().equals(view.getBtnConnect().getActionCommand())) {
        	// check if is connect
        	if (view.getBtnConnect().getText().equals("Disconnect")) {
        		
        		SocketActions.send("QUIT", dos);
                SocketActions.closeStream(dos);
                SocketActions.closeStream(oos);
                service3Manage.kill();
                fileSever.closeSocket();
                
                PrintFactory.writeSuccess(view.getTextPaneLogs(), "Server is disconnect success.");
                
                view.getFieldIP().setEnabled(true);
        		view.getFieldPort().setEnabled(true);
        		view.getBtnConnect().setText("Connect");
        		return;
        	}
        	
        	String hostname_master_server = view.getFieldIP().getText();
        	int port_master_server = Integer.parseInt(view.getFieldPort().getText());
        	int current_file_server_port = getAvailablePort();
        	String current_filer_server_hostname = "127.0.0.1";
//        	try {
//				//current_filer_server_hostname = InetAddress.getLocalHost().getHostAddress().toString();
//			} catch (UnknownHostException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
        	
        	//set up server listening 
            service3Manage = new Service3Manage(current_file_server_port);
            service3Manage.start();
            
            PrintFactory.writeSuccess(view.getTextPaneLogs(), "Server is listening in "+current_filer_server_hostname+":"+String.valueOf(current_file_server_port));
            
            //connect to master server to send and receive data
            fileSever = new FileSever(hostname_master_server, port_master_server);
            fileSever.connectServer();
            try {
                oos = new ObjectOutputStream(fileSever.getSocket().getOutputStream());
                dos = new DataOutputStream(fileSever.getSocket().getOutputStream());  
            } catch (IOException ex) {
                Logger.getLogger(FileSeverTransferController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            view.getFieldIP().setEnabled(false);
    		view.getFieldPort().setEnabled(false);
    		view.getBtnConnect().setText("Disconnect");
            
            SocketActions.send("SEVER", dos);
            System.out.println(fileSever.getSocket().getPort());
            String info = current_filer_server_hostname + "_" + String.valueOf(current_file_server_port);
            SocketActions.send(info, dos);
            SocketActions.sendFile(sourcFolder + "/Data/data.txt", oos);
        }
    }

	private static int getAvailablePort() {
		ServerSocket s = null;
		try {
			s = new ServerSocket(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s.getLocalPort();
	}
}
