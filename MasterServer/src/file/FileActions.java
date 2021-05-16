/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import common.FileInfo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sang
 */
public class FileActions {
    public static void delete(String Src){
        File myObj = new File(Src); 
        if (myObj.delete()) { 
            //System.out.println("Deleted the file: " + myObj.getName());
        } else {
            //System.out.println("Failed to delete the file.");
        } 
    }
    public static void write(String Despath, FileInfo file) throws IOException{
        FileOutputStream fos = null;
        String userDirectory = new File("").getAbsolutePath();
        File folderData = new File(userDirectory + "/Data");
        File folderFiles = new File(userDirectory + "/Data/files");
        folderData.mkdir();
        folderFiles.mkdirs();
        
        File newFile = new File(Despath);
        newFile.createNewFile(); 
        try {
            fos = new FileOutputStream(newFile);
            fos.write(file.getDataBytes(), 0, file.getDataBytes().length);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            fos.close();
        }
    }
    public static void write(File file, BufferedWriter bw){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file.getAbsolutePath()));
            String line;
            while ((line = br.readLine()) != null) {
                String name = file.getName();
                String[] fullname = name.split("\\$");
                bw.write(line + "_" + fullname[0]);
                bw.newLine();
           }
        } catch (IOException e) {
                e.printStackTrace();
        }
        finally{
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(FileActions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void writeAllFile(){
        String userDirectory = new File("").getAbsolutePath();
        System.out.println(userDirectory);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(userDirectory + "/Data/data.txt"));
            File folder = new File(userDirectory + "/Data/files/");
            File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    write(file, bw);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FileActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(FileActions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
