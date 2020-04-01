package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JTable;

public class Message extends JFrame {
	private JPanel contentPane;
	private Message Close_Window;
	private JList list;
	private Connection connect = null;
	private Statement stmt;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Message frame = new Message();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public void SetCloseWindow(Message window) {
		Close_Window = window;
	}
	
	public void RefreshList(JList jlist) {
		//设置list填充内容
		Vector<String> msg = new Vector<String>();
		try {
			//先查询有合作请求的
			ResultSet rs = stmt.executeQuery("select * from repo where auth = 2");
			while(rs.next()) {
				msg.add(rs.getString("fork_from")+"邀请您一起和ta合作"+rs.getString("repo_name"));
			}
			//查询有没有pull request的请求
			rs = stmt.executeQuery("select * from repo where auth = 3");
			while(rs.next()) {
				msg.add(rs.getString("username")+"针对你的"+rs.getString("repo_name")+"项目发起了pull request，点击查看详情");
			}
			
			//查询同意合作的
			rs = stmt.executeQuery("select * from repo where auth = 4");
			while(rs.next()) {
				msg.add(rs.getString("username")+"同意了你对"+rs.getString("repo_name")+"项目的合作请求");
			}
			
			//查询拒绝合作的
			rs = stmt.executeQuery("select * from repo where auth = -4");
			while(rs.next()) {
				msg.add(rs.getString("username")+"拒绝了你对"+rs.getString("repo_name")+"项目的合作请求");
			}
			
			//查询同意pull request的
			rs = stmt.executeQuery("select * from repo where auth = 5");
			while(rs.next()) {
				msg.add(rs.getString("fork_from")+"同意了你对"+rs.getString("repo_name")+"项目的pull request请求");	
			}
			//查询拒绝pull request的
			rs = stmt.executeQuery("select * from repo where auth = -5");
			while(rs.next()) {
				msg.add(rs.getString("fork_from")+"拒绝了你对"+rs.getString("repo_name")+"项目的pull request请求");	
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		list.setListData(msg);
	}
	/**
	 * Create the frame.
	 */
	public Message() {
		try {
			connect = DriverManager.getConnection(  
			          "jdbc:mysql://localhost:3306/work_together?serverTimezone=UTC","root","123456");
			stmt = connect.createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//控制软件大小，使得填充满整个屏幕
				Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
				
				setBounds(0,0,
						screensize.width,
						screensize.height);
//		setBounds(100, 100, 450, 300);
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
				window.afterlogin_frame = window;
				Close_Window.dispose();
				window.setVisible(true);
			}
		});
		btnNewButton.setBounds(10, 10, 93, 23);
		contentPane.add(btnNewButton);
		
		list = new JList();
		
		list.setBounds(10, 84, 836, 413);
		contentPane.add(list);

		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = list.getSelectedIndex();
				
			}
		});		

		
	}
}

