/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;
import gui.*;
/**
 *
 * @author Sang
 */
public class ClientTransferMain {
    public static void main(String[] args) {
        String srcDir = ChoseFolderClient.chose();
        if(!srcDir.equals("")){
            ClientTransferView view = new ClientTransferView(srcDir);
            new ClientTransferController(view,srcDir);
        } 
    }
}
