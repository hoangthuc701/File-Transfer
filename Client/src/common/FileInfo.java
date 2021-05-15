/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public void writeFile(String Despath) throws IOException{
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(Despath);
            fos.write(dataBytes, 0, dataBytes.length);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            fos.close();
        }
    }
    public void deleteFile(String Src){
        File myObj = new File(Src); 
        if (myObj.delete()) { 
            System.out.println("Deleted the file: " + myObj.getName());
          } else {
            System.out.println("Failed to delete the file.");
          } 
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
}
