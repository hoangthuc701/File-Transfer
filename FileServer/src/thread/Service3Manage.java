/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class Service3Manage extends Thread {

	private int _port;
	private boolean isRunning= true;
	private static DatagramSocket socket = null;

	public Service3Manage(int port) {
		this._port = port;
		this.isRunning = true;
	}

	@Override
	public void run() {
		System.out.print("server is listening" + String.valueOf(this._port));
		ArrayList<Service3> listConect = new ArrayList<>();
		
		try {
			socket = new DatagramSocket(this._port);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while (true && this.isRunning) {
			String savedFileName = "";
			int client_port = 0;
			String client_host_name = "127.0.0.1";
			int server_port = 6023; // not use but please keep it =)))
			try {
				// recieve filename
				byte[] receiveFileNameChoice = new byte[1024];
				DatagramPacket receiveFileNameChoicePacket = new DatagramPacket(receiveFileNameChoice,
						receiveFileNameChoice.length);
				socket.receive(receiveFileNameChoicePacket);

				String decodedDataUsingUTF82 = null;
				try {
					decodedDataUsingUTF82 = new String(receiveFileNameChoice, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				savedFileName = decodedDataUsingUTF82.trim();
				// recieve port
				byte[] receivePort = new byte[1024];
				DatagramPacket receivePortPacket = new DatagramPacket(receivePort, receivePort.length);
				socket.receive(receivePortPacket);
				
				if (savedFileName.length()==0) {
					continue;
				}

				try {
					client_port = Integer.parseInt((new String(receivePort, "UTF-8").trim()));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Service3 connectThread = new Service3(client_host_name, client_port, savedFileName, server_port);
			connectThread.start();
			listConect.add(connectThread);
		}
	}

	public void kill() {
		socket.close();
		this.isRunning = false;
	}

}
