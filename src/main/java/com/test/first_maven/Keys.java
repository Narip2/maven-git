package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Demo.Test;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTable;
import javax.swing.JScrollPane;

public class Keys extends JFrame {

	private JPanel contentPane;
	private Keys close_window;
	private JTable table;
	private DefaultTableModel model;
	private MouseEvent mevent;
	private String key_Create;
	private String path_Create;
	
	public void SetCloseWindow(Keys window) {
		close_window = window;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Keys frame = new Keys();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public Keys() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//控制软件大小，使得填充满整个屏幕
				Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
				setBounds(0,0,
						screensize.width,
						screensize.height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("返回");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//窗口跳转
				  after_login window = new after_login();
	    		  after_login.afterlogin_frame = window;
	    		  close_window.dispose();
	    		  window.setVisible(true);
			}
		});
		btnNewButton.setBounds(10, 10, 93, 23);
		contentPane.add(btnNewButton);
		
		//显示table
		model = new DefaultTableModel();
		//每次重复使用model
		model.setRowCount(0);
		model.setColumnIdentifiers(new Object[] {"public key","primary key path"});
		
		try {
			Statement stmt = login.connect.createStatement();
			ResultSet rs = stmt.executeQuery("select * from rsa where username = \'"+login.username+"\'");
			while(rs.next()) {
				model.addRow(new String[] {rs.getString("pub_key"),rs.getString("path")});
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(151, 163, 501, 500);
		contentPane.add(scrollPane);
		
		table = new JTable(model);
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mevent = e;
			}
		});
		
		JButton btnNewButton_1 = new JButton("添加key");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				final Keys_Create window = new Keys_Create();
				window.SetCloseWindow(window);
				window.setVisible(true);
				window.addWindowListener(new WindowListener() {
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
					}
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
					}
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						key_Create = window.GetKey();
						path_Create = window.GetPath();
						//远程的auth文件夹的尾部添加 public key
						SSH ssh = new SSH();
						ssh.exec("echo \'"+key_Create+"\' >> /root/.ssh/authorized_keys");
						//执行更新table操作
						model.addRow(new String[] {key_Create,path_Create});
					}
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
					}
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
					}
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
					}
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
					}
				});
			}
		});
		btnNewButton_1.setBounds(662, 159, 93, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("删除该key");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowindex = table.rowAtPoint(mevent.getPoint());
				try {
					Statement stmt = login.connect.createStatement();
					stmt.executeUpdate("delete from rsa where pub_key = \'"+table.getValueAt(rowindex, 0)+"\' and username = \'"+login.username+"\'");
					model.removeRow(rowindex);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(662, 192, 93, 23);
		contentPane.add(btnNewButton_2);
		
		
		final JFileChooser jfile = new JFileChooser("");
		jfile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		JButton btnNewButton_3 = new JButton("修改该path");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jfile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int index = jfile.showSaveDialog(null);
				//表示无文件选取或正常选取文件
				if(index == jfile.APPROVE_OPTION) {
					File file = jfile.getSelectedFile();
					int rowindex = table.rowAtPoint(mevent.getPoint());
					try {
						Statement stmt = login.connect.createStatement();
						stmt.executeUpdate("update rsa set path = \'"+file.getAbsolutePath()+"\' where pub_key = \'"+table.getValueAt(rowindex, 0)+"\' and username = \'"+login.username+"\'");
						table.setValueAt(file.getAbsoluteFile(), rowindex, 1);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton_3.setBounds(662, 225, 93, 23);
		contentPane.add(btnNewButton_3);
	}
}
