/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import util.*;

public class Service3 extends Thread {
	private static int totalTransferred = 0;
	private static StartTime timer;
	private static String fileName = "";
	private static int file_server_port;
	private static String file_server_host;
	private static int client_port;
	private static String file_request;
	private static String file_output;

	public Service3(String _file_server_host, int _file_server_port, String _file_request, String _file_output) {
		file_server_port = _file_server_port;
		file_server_host = _file_server_host;
		file_request = _file_request;
		file_output = _file_output;
	}

	@Override
	public void run() {
		try {
			client_port = getAvailablePort();
			setUp(client_port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void setUp(int port_listen) throws IOException {
		System.out.print("Start listen port " + String.valueOf(port_listen));
		DatagramSocket client_socket_listen = new DatagramSocket(port_listen);
		DatagramSocket socket = new DatagramSocket();
		InetAddress address = InetAddress.getByName(file_server_host);

		// send request file
		String fileRequest = file_request;
		byte[] fileRequestData = fileRequest.getBytes();

		DatagramPacket fileStatPacket = new DatagramPacket(fileRequestData, fileRequestData.length, address,
				file_server_port);
		socket.send(fileStatPacket);

		// sent port listen
		String port_listen_str = String.valueOf(port_listen);
		byte[] port_listen_strData = port_listen_str.getBytes();

		DatagramPacket port_listen_strPacket = new DatagramPacket(port_listen_strData, port_listen_strData.length,
				address, file_server_port);
		socket.send(port_listen_strPacket);

		//
		String fileOutput = file_output;

		File file = new File(fileOutput);
		FileOutputStream outToFile = new FileOutputStream(file);

		acceptTransferOfFile(outToFile, client_socket_listen);

		byte[] finalStatData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(finalStatData, finalStatData.length);
		socket.receive(receivePacket);
		printFinalStatistics(finalStatData);

		socket.close();
		client_socket_listen.close();
	}

	private static void printFinalStatistics(byte[] finalStatData) {
		try {
			String decodedDataUsingUTF8 = new String(finalStatData, "UTF-8");
			PrintFactory.printSpace();
			PrintFactory.printSpace();
			System.out.println("Statistics of transfer");
			PrintFactory.printSeperator();
			System.out.println("File has been saved as: " + getFileName());
			System.out.println("Statistics of transfer");
			System.out.println("" + decodedDataUsingUTF8.trim());
			PrintFactory.printSeperator();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private static void acceptTransferOfFile(FileOutputStream outToFile, DatagramSocket socket) throws IOException {

		// last message flag
		boolean flag;
		int sequenceNumber = 0;
		int findLast = 0;

		while (true) {
			byte[] message = new byte[1024];
			byte[] fileByteArray = new byte[1021];

			// Receive packet and retrieve message
			DatagramPacket receivedPacket = new DatagramPacket(message, message.length);
			socket.setSoTimeout(0);
			socket.receive(receivedPacket);

			message = receivedPacket.getData();
			totalTransferred = receivedPacket.getLength() + totalTransferred;
			totalTransferred = Math.round(totalTransferred);

			// start the timer at the point transfer begins
			if (sequenceNumber == 0) {
				timer = new StartTime();
			}

			if (Math.round(totalTransferred / 1000) % 50 == 0) {
				double previousTimeElapsed = 0;
				int previousSize = 0;
				PrintFactory.printCurrentStatistics(totalTransferred, previousSize, timer, previousTimeElapsed);
			}
			// Get port and address for sending acknowledgment
			InetAddress address = receivedPacket.getAddress();
			int port = receivedPacket.getPort();

			// Retrieve sequence number
			sequenceNumber = ((message[0] & 0xff) << 8) + (message[1] & 0xff);
			// Retrieve the last message flag
			// a returned value of true means we have a problem
			flag = (message[2] & 0xff) == 1;
			// if sequence number is the last one +1, then it is correct
			// we get the data from the message and write the message
			// that it has been received correctly
			if (sequenceNumber == (findLast + 1)) {

				// set the last sequence number to be the one we just received
				findLast = sequenceNumber;

				// Retrieve data from message
				System.arraycopy(message, 3, fileByteArray, 0, 1021);

				// Write the message to the file and print received message
				outToFile.write(fileByteArray);
				System.out.println("Received: Sequence number:" + findLast);

				// Send acknowledgement
				sendAck(findLast, socket, address, port);
			} else {
				System.out.println("Expected sequence number: " + (findLast + 1) + " but received " + sequenceNumber
						+ ". DISCARDING");
				// Re send the acknowledgement
				sendAck(findLast, socket, address, port);
			}

			// Check for last message
			if (flag) {
				outToFile.close();
				break;
			}
		}
	}

	private static String getFileName() {
		return fileName;
	}

	private static void setFileName(String passed_file_name) {
		fileName = passed_file_name;
	}

	private static void sendAck(int findLast, DatagramSocket socket, InetAddress address, int port) throws IOException {
		// send acknowledgement
		byte[] ackPacket = new byte[2];
		ackPacket[0] = (byte) (findLast >> 8);
		ackPacket[1] = (byte) (findLast);
		// the datagram packet to be sent
		DatagramPacket acknowledgement = new DatagramPacket(ackPacket, ackPacket.length, address, port);
		socket.send(acknowledgement);
		System.out.println("Sent ack: Sequence Number = " + findLast);
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
