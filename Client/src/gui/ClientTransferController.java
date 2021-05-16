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
import client.SocketActions;

import java.io.IOException;
import java.net.Socket;
import file.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import thread.*;

public class ClientTransferController implements ActionListener {
	private ClientTransferView view;
	Client client;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	private String srcDir;
	private static Thread listenFromMasterServer = null;

	public ClientTransferController(ClientTransferView view, String srcDir) {
		this.srcDir = srcDir;
		this.view = view;
		view.getBtnExt().addActionListener(this);
		view.getMenuItemInstall().addActionListener(this);
		view.getBtnConnect().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(view.getBtnExt().getActionCommand())) {
			// FileActions.clear("data.txt");
			view.dispose();
			SocketActions.closeStream(oos);
			SocketActions.closeStream(ois);
			SocketActions.closeStream(dos);
			SocketActions.closeStream(dis);
			listenFromMasterServer.interrupt();
			client.closeSocket();

		} else if (e.getActionCommand().equals(view.getMenuItemInstall().getActionCommand())) {

			String file_server_host = (String) view.getTable().getValueAt(view.getTable().getSelectedRow(), 1);
			int file_server_port = Integer
					.parseInt((String) view.getTable().getValueAt(view.getTable().getSelectedRow(), 0));
			String file_request = "G:\\Bai Tap\\Hoc ki 8\\Mang may tinh nang cao\\Java-UDP-File-Transfer-Made-Reliable\\love.jpg";
			String file_output = "something.png";
			new Service3(file_server_host, file_server_port, file_request, file_output).start();

		} else if (e.getActionCommand().equals(view.getBtnConnect().getActionCommand())) {
			if (view.getBtnConnect().getText().equals("Disconnect")) {

				listenFromMasterServer.interrupt();

				SocketActions.closeStream(oos);
				SocketActions.closeStream(dos);
				client.closeSocket();

				view.getFieldIP().setEnabled(true);
				view.getFieldPort().setEnabled(true);
				view.getBtnConnect().setText("Connect");

				return;
			}

			String hostname_master_server = view.getFieldIP().getText();
			int port_master_server = Integer.parseInt(view.getFieldPort().getText());

			listenFromMasterServer = new Thread() {
				public void run() {
					client = new Client(hostname_master_server, port_master_server);
					client.connectServer();
					Socket socket = client.getSocket();
					try {
						dis = new DataInputStream(socket.getInputStream());
						dos = new DataOutputStream(socket.getOutputStream());
						oos = new ObjectOutputStream(socket.getOutputStream());
						ois = new ObjectInputStream(socket.getInputStream());
						SocketActions.send("CLIENT", dos);
						FileInfo fileInfo = SocketActions.getFile(ois);
						String des = srcDir + "/Data/data.txt";
						FileActions.write(des, fileInfo);
						view.loadData();
						String line;
						while ((line = dis.readUTF()) != null) {
							if (line.equals("UPDATE")) {
								fileInfo = SocketActions.getFile(ois);
								FileActions.write(des, fileInfo);
								view.loadData();
							}
						}
					} catch (IOException ex) {
						System.out.println("Disconnect!");
					} finally {
						SocketActions.closeStream(oos);
						SocketActions.closeStream(ois);
						SocketActions.closeStream(dos);
						SocketActions.closeStream(dis);
					}
				}
			};

			listenFromMasterServer.start();

			view.getFieldIP().setEnabled(false);
			view.getFieldPort().setEnabled(false);
			view.getBtnConnect().setText("Disconnect");
		}
	}
}
