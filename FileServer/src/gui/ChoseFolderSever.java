/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Sang
 */
public class ChoseFolderSever{
    public static String chose(){
        String srcDir = "";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = fileChooser.showOpenDialog(null);
        if(option == JFileChooser.APPROVE_OPTION){
           File file = fileChooser.getSelectedFile();
           System.out.println("Folder Selected: " + file.getAbsolutePath());
           srcDir = file.getAbsolutePath();
        }else{
           System.out.print("Open command canceled");
        }
        return srcDir;
    }
}
