package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Demo.Test;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Search extends JFrame {

	private JPanel contentPane;
	private Search close_window;
	private JTable table;
	private DefaultTableModel model;
	private Class test;
	
	
	public void SetCloseWindow(Search window) {
		close_window = window;
	}
	
	
	/**
	 * Create the frame.
	 */
	public Search(String search_text) {
		//处理一下search_text
		StringBuffer text = new StringBuffer(search_text);
		for(int i = 0; i < search_text.length(); i++) {
			text.insert(2*i + 1, "%");
		}
		text.insert(0, "%");
		
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(147, 168, 454, 464);
		contentPane.add(scrollPane);
		
		
		Connection connect;
		//配置tablemodel
		model = new DefaultTableModel();
		model.setRowCount(0);
		model.setColumnIdentifiers(new Object[] {"repo_name","username"});
		try {
			connect = DriverManager.getConnection(  
			          "jdbc:mysql://localhost:3306/work_together?serverTimezone=UTC","root","123456");
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery("select * from repo where repo_name like \'"+text+"\'");
			while(rs.next()) {
				model.addRow(new String[] {rs.getString("repo_name"),rs.getString("username")});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table = new JTable(model);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row_index = table.rowAtPoint(e.getPoint());
				String repo_name = (String) table.getValueAt(row_index, 0);
				String username = (String) table.getValueAt(row_index, 1);
				project.project_name = repo_name;
				project.project_user = username;
				project window = new project();
				window.SetCloseWindow(window);
				close_window.dispose();
				window.setVisible(true);
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("返回");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//界面跳转 返回after_login界面
				after_login window = new after_login();
				after_login.afterlogin_frame = window;
				close_window.dispose();
				window.setVisible(true);
			}
		});
		btnNewButton.setBounds(10, 10, 93, 23);
		contentPane.add(btnNewButton);
	}
}
