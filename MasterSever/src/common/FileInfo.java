/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import thread.SocketActions;

/**
 *
 * @author Sang
 */
public class FileInfo implements Serializable{
    private static final long serialVersionUID = 1L;
 
    private String filename;
    private long fileSize;
    private String sourceFile;
    private byte[] dataBytes;
    public byte [] getDataBytes()
    {
        return dataBytes;
    }
    public String getSourceFile(){
        return sourceFile;
    }
    public String getFilename(){
        return filename;
    }
    public void setFilename(String filename){
        this.filename = filename;
    }
    public void setFileSize(long fileSize){
        this.fileSize = fileSize;
    }
    public void setDataBytes(byte [] dataBytes){
        this.dataBytes = dataBytes;
    }
    public void setSourceFile(String sourceFile){
        this.sourceFile = sourceFile;
    }
    public String toString(){
        return filename + fileSize + sourceFile;
    }
    public void getFileInfo(String sourceFilePath) {
        BufferedInputStream bis = null;
        try {
            File file = new File(sourceFilePath);
            bis = new BufferedInputStream(new FileInputStream(file));
            byte[] fileBytes = new byte[(int) file.length()];
            bis.read(fileBytes, 0, fileBytes.length);
            filename = file.getName();
            dataBytes = fileBytes;
            sourceFile = sourceFilePath;
            fileSize = (long) sourceFile.length();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            SocketActions.closeStream(bis);
        }
    }
}
