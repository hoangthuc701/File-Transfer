/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import common.FileInfo;
import file.FileActions;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Sang
 */
public class ConnectThread extends Thread{
    protected Socket socket;
    ArrayList<ConnectThread> listConect;
    public String role;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private String userDirectory = new File("").getAbsolutePath();
    public ConnectThread(Socket socket, ArrayList<ConnectThread> listConect) {
        this.listConect = listConect;
        this.socket = socket;
    }
    public void run(){
        try {
            String line = "";
            String[] info;
            System.out.println("Start");
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            line = dis.readUTF();
            if(line.equals("SEVER")){
                this.role = "SEVER";
                System.out.println("Sever IP:" + socket.getInetAddress().toString() + ", Port:" + socket.getPort() + " Connect!");
                line = dis.readUTF();
                info = line.split("_");
                String des = userDirectory + "/Data/files/" + info[0] + "_" + info[1] + "$.txt";
                //String des = "files" + socket.getInetAddress() + "_" + socket.getPort() + "$.txt";
                FileInfo fileInfo = SocketActions.getFile(ois);
                FileActions.write(des, fileInfo);
                FileActions.writeAllFile();
                mastersever.MasterSever.updateFileSever(listConect);
                try {
                    while ((line = dis.readUTF()) != null) {   
                        if (line.equals("QUIT"))
                        {
                            System.out.println("Sever IP:" + socket.getInetAddress().toString() + ", Port:" + socket.getPort() + " Disconnect!");
                            FileActions.delete(userDirectory + "/Data/files/" + info[0] + "_" + info[1] + "$.txt");
                            FileActions.writeAllFile();
                            mastersever.MasterSever.updateFileSever(listConect);
                            SocketActions.closeStream(oos);
                            SocketActions.closeStream(dos);
                            SocketActions.closeStream(dis);
                            SocketActions.closeStream(ois);
                            socket.close();
                            break;
                        }  
                        else if(line.equals("UPDATE")){
                            fileInfo = SocketActions.getFile(ois);
                            FileActions.write(des, fileInfo);
                            FileActions.writeAllFile();
                            mastersever.MasterSever.updateFileSever(listConect);
                        }
                    }
                } catch (IOException ex) {
                    System.out.println("Sever IP:" + socket.getInetAddress().toString() + ", Port:" + socket.getPort() + " Disconnect!");
                    FileActions.delete(userDirectory + "/Data/files" + socket.getInetAddress() + "_" + socket.getPort() + "$.txt");
                    FileActions.writeAllFile();
                    mastersever.MasterSever.updateFileSever(listConect);
                    //fileInfo.deleteFile("files" + socketOfServer.getInetAddress() + "_" + socketOfServer.getPort() + "$.txt");
                }     
            }
            else if(line.equals("CLIENT")){
                this.role = "CLIENT";
                System.out.println("Client IP:" + socket.getInetAddress().toString() + ", Port:" + socket.getPort() + " Connect!");
                SocketActions.sendFile(userDirectory + "/Data/data.txt", oos);
                try {
                    while ((line = dis.readUTF()) != null) {}
                } catch (Exception e) {
                    System.out.println("Client IP:" + socket.getInetAddress().toString() + ", Port:" + socket.getPort() + " Disconnect!");
                    SocketActions.closeStream(oos);
                    SocketActions.closeStream(dos);
                    SocketActions.closeStream(dis);
                    SocketActions.closeStream(ois);
                    socket.close();
                }
               
            }
        } catch (IOException ex) {    
            System.out.println(ex.getMessage());
        }
    }
    public void sendUpdate(){
        SocketActions.send("UPDATE", dos);
        SocketActions.sendFile(userDirectory + "/Data/data.txt", oos);
    }
}
        
        
       
        
       
