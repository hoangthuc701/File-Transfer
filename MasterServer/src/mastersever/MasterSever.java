package mastersever;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import thread.ConnectThread;

public class MasterSever {

	/**
	 * @param args the command line arguments
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		int port = 1234;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		ServerSocket masterServer = null;
		try {
			masterServer = new ServerSocket(port);
			System.out.println("Master server is listening in port " + String.valueOf(port) + "!!");
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

	public static void updateFileSever(ArrayList<ConnectThread> listConect) {
		for (ConnectThread connectThread : listConect) {
			if (connectThread.role.equals("CLIENT")) {
				connectThread.sendUpdate();
			}
		}
	}
}
