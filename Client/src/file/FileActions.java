/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import common.FileInfo;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import client.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import object.RowTable;

/**
 *
 * @author Sang
 */
public class FileActions {
    public static void clear(String Src){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File(Src));
            fileWriter.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileInfo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(FileActions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void write(String Despath, FileInfo file) throws IOException{
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(Despath);
            fos.write(file.getDataBytes(), 0, file.getDataBytes().length);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            fos.close();
        }
    }
    public static ArrayList<RowTable> read(String Srcpath) {
        ArrayList<RowTable> al = new ArrayList<>();
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new FileReader(Srcpath));
            line = br.readLine();
            while (line != null && !line.isEmpty()) {
                System.out.println(line);
                String [] rowitem = line.split("_");
                RowTable rowTable = new RowTable(rowitem[0], rowitem[1], rowitem[2]);
                al.add(rowTable);
                line = br.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(FileActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(FileActions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return al;
    }
}
