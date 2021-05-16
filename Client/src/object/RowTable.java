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
    private String path;
    private String ip;
    private String port;
    public RowTable(String name, String path, String ip, String port){
        this.name = name;
        this.path = path;
        this.ip = ip;
        this.port = port;
    }
    public String getName(){
        return name;
    }
    public String getPath(){ return path; }
    public String getIp(){
        return ip;
    }
    public String getPort(){
        return port;
    }
}
