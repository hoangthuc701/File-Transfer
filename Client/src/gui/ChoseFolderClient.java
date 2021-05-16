/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Sang
 */
public class ChoseFolderClient {
    public static String chose(){
        String srcDir = "";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = fileChooser.showOpenDialog(null);
        if(option == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            srcDir = file.getAbsolutePath();
            String srcData = srcDir + "/Data";
            File foderData = new File(srcData);
            foderData.mkdir();
            File fileData = new File(srcData + "/data.txt");
            try {
                fileData.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ChoseFolderClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
           System.out.print("Open command canceled");
        }
        return srcDir;
    }
}
