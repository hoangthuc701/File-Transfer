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
        ClientTransferView view = new ClientTransferView();
        new ClientTransferController(view);
    }
}
