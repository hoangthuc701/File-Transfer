/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import common.FileInfo;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Sang
 */
public class SocketActions {
    public static void sendFile(String sourceFilePath, ObjectOutputStream oos) {
           try {
               FileInfo fileInfo = new FileInfo();
               fileInfo.getFileInfo(sourceFilePath);
               oos.writeObject(fileInfo);
               //System.out.println("Gui File");
               }
           catch (IOException ex) {
               ex.printStackTrace();
           }
       }
    public static FileInfo getFile(ObjectInputStream ois) {
            FileInfo fileInfo = null;
            try {
                fileInfo = (FileInfo) ois.readObject();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
                
                System.out.println(ex.getMessage());
            }catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } 
            return fileInfo;
        }
    public static void send(String str, DataOutputStream dos)
    {  
        try {
            dos.writeUTF(str);
            dos.flush();
            System.out.println("Send mess SS!");
        } catch (Exception e) {
             System.out.println("Send mess Fail!");
        }     
    }
    public static void closeStream(BufferedWriter bufferedWriter) {
        try {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void closeStream(ObjectOutputStream objectOutputStream) {
        try {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void closeStream(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void closeStream(DataOutputStream dataOutputStream) {
        try {
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
