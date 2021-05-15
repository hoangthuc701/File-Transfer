package object;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sang
 */
public class RowTable {
    private String name;
    private String ip;
    private String port;
    public RowTable(String name, String ip, String port){
        this.name = name;
        this.ip = ip;
        this.port = port;
    }
    public String getName(){
        return name;
    }
    public String getIp(){
        return ip;
    }
    public String getPort(){
        return port;
    }
}
