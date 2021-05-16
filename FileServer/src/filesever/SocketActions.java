/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesever;

import common.FileInfo;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Sang
 */
public class SocketActions {
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
    public static void sendFile(String sourceFilePath, ObjectOutputStream oos) {
        try {
            FileInfo fileInfo = new FileInfo();
            fileInfo.getFileInfo(sourceFilePath);
            oos.writeObject(fileInfo);
            oos.flush();
            System.out.println("Send File SS!");
            }
        catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Send File Fail!");
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
    public static void closeStream(DataOutputStream dataOutputStream) {
        try {
            if (dataOutputStream != null) {
                dataOutputStream.close();
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
}
