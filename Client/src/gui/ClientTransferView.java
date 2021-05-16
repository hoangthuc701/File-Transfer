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
import java.io.File;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import object.RowTable;

/**
 *
 * @author Sang
 */
public class ClientTransferView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel Container;
	private DefaultTableModel model;
	private JTable jTable;
	private JButton btnAdd;
	private JButton btnExt;
	private JMenuItem Download = new JMenuItem("Download");
	private JTextField tfIp;
	private JTextField tfPort;
	private JButton btnConnect;
	private JTextPane taLog;
	private String srcDir;

	public ClientTransferView(String srcDir) {
		this.srcDir = srcDir;
		Container = new JPanel();
		Container.setLayout(new BoxLayout(Container, BoxLayout.Y_AXIS));
		JLabel lbTitle = new JLabel("Client");
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
				Download.setActionCommand("DOWNLOAD");
				menu.add(Download);
				menu.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		// Add colums
		model.addColumn("File name");
		model.addColumn("Path");
		model.addColumn("Ip");
		model.addColumn("Port");

		// loadData();
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

	public TableModel getModel() {
		return jTable.getModel();
	}

	public JMenuItem getMenuItemInstall() {
		return Download;
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

	public void loadData() {
		ArrayList<RowTable> rowTables = FileActions.read(srcDir + "/Data/data.txt");
		model.setRowCount(0);
		for (RowTable rowTable : rowTables) {
			String[] rows = { rowTable.getName(), rowTable.getPath(), rowTable.getIp(), rowTable.getPort() };
			model.addRow(rows);
		}
	}

	public static String getFileSizeKiloBytes(File file) {
		Double size = (double) file.length() / 1024;
		return Math.round(size) + " KB";
	}

	public String getExtension(File file) {
		String fileName = file.toString();
		String extension = "";
		int index = fileName.lastIndexOf('.');
		if (index > 0) {
			extension = fileName.substring(index + 1);
		}
		return extension;
	}
}
