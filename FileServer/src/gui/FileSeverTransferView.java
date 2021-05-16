/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sang
 */
public class FileSeverTransferView extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel Container;
    private DefaultTableModel model;
    private JTable jTable;
    private JButton btnAdd;
    private JButton btnExt;
    private JMenuItem Delete= new JMenuItem("Delete");
    String sourcFolder = null;
    JTextField tfIp;
    JTextField tfPort;
    JButton btnConnect;
    JTextPane taLog;
    public FileSeverTransferView(String srcDir){
        this.sourcFolder = srcDir;
        Container = new JPanel();
        Container.setLayout(new BoxLayout(Container, BoxLayout.Y_AXIS));
        JLabel lbTitle = new JLabel("File Sever");
        lbTitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
        JPanel Title = new JPanel();
        Title.add(lbTitle); 
        
        tfIp = new JTextField(10);
        tfPort = new JTextField(10);
        btnConnect = new JButton("Connect");
        btnConnect.setActionCommand("CONNECT");
        
        JPanel jpLayout = new JPanel();
        JLabel jlIp = new JLabel("IP:");
        JLabel jlPort = new JLabel("Port:");
        jpLayout.add(jlIp);
        jpLayout.add(tfIp);
        jpLayout.add(jlPort);
        jpLayout.add(tfPort);
        jpLayout.add(btnConnect);
        JPanel jpConnect = new JPanel();
        jpConnect.setLayout(new BorderLayout());
        jpConnect.add(jpLayout, BorderLayout.LINE_END);
        
        // Initializing the JTable
        model = new DefaultTableModel(); 
        jTable = new JTable(model);
        jTable.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            final JPopupMenu menu = new JPopupMenu("Menu");
            Delete.setActionCommand("DELETE");
            menu.add(Delete);
            menu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        // Add colums
        model.addColumn("File name");
        model.addColumn("Size");
        loadData();
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
        
        Border border = tfIp.getBorder();
        taLog = new JTextPane();
        taLog.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
        Container.add(Title);
        Container.add(jpConnect);
        Container.add(sp);
        Container.add(Actions);
        Container.add(taLog);
        Container.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(Container);
        setPreferredSize(new Dimension(450, 450));
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
    public JMenuItem getMenuItemDelete() {
        return Delete;
    }
    public JButton getBtnConnect() {
    	return btnConnect;
    }
    public JTextField getFieldIP() {
    	return tfIp;
    }
    public JTextField getFieldPort() {
    	return tfPort;
    }
    public JTextPane getTextPaneLogs() {
    	return taLog;
    }
    public void addFile(){
        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showDialog(this, null);
        if(response == JFileChooser.APPROVE_OPTION){
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            File newFile = new File(sourcFolder + "/" + fileChooser.getSelectedFile().getName());
            try {
                Files.copy(file.toPath(), newFile.toPath());
                JOptionPane.showOptionDialog(this,
                "Add file success!",
                "A Silly Question",
                JOptionPane.CLOSED_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                null,
                null);
                loadData();
            } catch (IOException ex) {

            }
        }
    }
    public void deleteFile(){
        File myObj = new File(sourcFolder + "/" + jTable.getValueAt(jTable.getSelectedRow(),0)); 
        if (myObj.delete()) { 
            JOptionPane.showMessageDialog(null, "Delete file Success!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(null, "Delete file Fail!");
        } 
    }
    public void loadData(){
        File folder = new File(sourcFolder + "/");
        File[] listOfFiles = folder.listFiles();
        model.setRowCount(0);
        ArrayList<String> files = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                String[] rows = {file.getName(), getFileSizeKiloBytes(file)};
                model.addRow(rows);
                files.add(file.getName());
            }
        }
        String srcData = sourcFolder + "/Data";
        File foderData = new File(srcData);
        foderData.mkdir();
        writeFile(srcData + "/data.txt", files);
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
    public static void writeFile(String path, ArrayList<String> files){
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
                fw = new FileWriter(path);
                bw = new BufferedWriter(fw);
                for(String i : files){
                    bw.write(i);
                    bw.newLine();
                }
                bw.close();
        } catch (IOException e) {
                e.printStackTrace();
                
        }
    }
}
