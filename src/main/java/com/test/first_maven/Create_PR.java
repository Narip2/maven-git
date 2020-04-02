package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Create_PR extends JFrame {

	private JPanel contentPane;
	private Create_PR close_window;
	private String from_user;
	private String repo_name;
	private String to_user;
	private JLabel lblNewLabel_1;
	
	public void SetCloseWindow(Create_PR window) {
		close_window = window;
	}
	public void SetFromUser(String user) {
		from_user = user;
	}
	public void SetRepoName(String repo) {
		repo_name = repo;
	}
	public void SetToUser(String user) {
		to_user = user;
	}
	public void RefreshLabel() {
		lblNewLabel_1.setText(to_user+"/");
	}
	
	
	/**
	 * Create the frame.
	 */
	public Create_PR() {
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
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(160, 83, 111, 23);
		contentPane.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(606, 83, 111, 23);
		contentPane.add(comboBox_1);
		
		JLabel lblNewLabel = new JLabel(login.username+"/");
		lblNewLabel.setBounds(39, 83, 111, 23);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setBounds(485, 83, 111, 23);
		contentPane.add(lblNewLabel_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(103, 208, 620, 419);
		contentPane.add(textArea);
		
		JButton btnNewButton = new JButton("确定");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//更新数据库
				Connection connect;
				try {
					connect = DriverManager.getConnection(  
					          "jdbc:mysql://localhost:3306/work_together?serverTimezone=UTC","root","123456");
					Statement stmt = connect.createStatement();
					stmt.executeUpdate("insert into pull_request values(\'"+from_user+"\',");
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(346, 666, 93, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("返回");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				project window =  new project();
				window.SetCloseWindow(window);
				window.LoadState();
				close_window.dispose();
				window.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(10, 10, 93, 23);
		contentPane.add(btnNewButton_1);
	}
}
