/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import file.FileActions;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import object.RowTable;

/**
 *
 * @author Sang
 */
public class ClientTransferView extends JFrame{
    private JPanel Container;
    private DefaultTableModel model;
    private JTable jTable;
    private JButton btnAdd;
    private JButton btnExt;
    private JMenuItem Install = new JMenuItem("Install");
    private String srcDir;
    public ClientTransferView(String srcDir){
        this.srcDir = srcDir;
        Container = new JPanel();
        Container.setLayout(new BoxLayout(Container, BoxLayout.Y_AXIS));
        JLabel lbTitle = new JLabel("Client");
        lbTitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
        JPanel Title = new JPanel();
        Title.add(lbTitle); 
        // Initializing the JTable
        model = new DefaultTableModel(); 
        jTable = new JTable(model);
        jTable.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            final JPopupMenu menu = new JPopupMenu("Menu");
            Install.setActionCommand("INSTALL");
            menu.add(Install);
            menu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        // Add colums
        model.addColumn("File name");
        model.addColumn("Ip");
        model.addColumn("Port");
        //loadData();
        JScrollPane sp = new JScrollPane(jTable);
        
        btnAdd = new JButton("Add");
        btnAdd.setActionCommand("ADD");
        btnExt = new JButton("Ext");
        btnExt.setActionCommand("EXT");
        JPanel Jp1 = new JPanel();
        Jp1.add(btnAdd);
        Jp1.add(btnExt);
        
        JPanel Actions = new JPanel();
        Actions.setLayout(new BorderLayout());
        Actions.add(Jp1, BorderLayout.LINE_END);
        
        Container.add(Title);
        Container.add(sp);
        Container.add(Actions);
        Container.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(Container);
        setPreferredSize(new Dimension(600, 450));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public JButton getBtnAdd() {
        return btnAdd;
    }
    public JButton getBtnExt() {
        return btnExt;
    }
    public JTable getTable() {
        return jTable;
    }
    public JMenuItem getMenuItemInstall() {
        return Install;
    }
    public void loadData(){
        ArrayList<RowTable> rowTables = FileActions.read(srcDir + "/Data/data.txt");
        model.setRowCount(0);
        for (RowTable rowTable : rowTables) {
            String[] rows = {rowTable.getName(), rowTable.getIp(), rowTable.getPort()};
            model.addRow(rows);
        }
    }
    public static String getFileSizeKiloBytes(File file) {
        Double size = (double) file.length() / 1024;
        return Math.round(size) + " KB";
    }
    public String getExtension(File file){
        String fileName = file.toString();
        String extension = "";
        int index = fileName.lastIndexOf('.');
        if(index > 0) {
          extension = fileName.substring(index + 1);
        }
        return extension;
    }
}
