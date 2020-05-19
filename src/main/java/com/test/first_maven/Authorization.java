package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Demo.Test;
import Demo.demo6;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Authorization extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private Authorization close_window;
	private JLabel label;
	private String project_name;
	private String project_user;
	Connection connect;

	
	public void SetProjectName(String proname) {
		project_name = proname;
	}
	public String GetProjectName() {
		return project_name;
	}
	public void SetProjectUser(String prouser) {
		project_user = prouser;
	}
	public String GetProjectUser() {
		return project_user;
	}
	public void SetCloseWindow(Authorization window) {
		close_window = window;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Authorization frame = new Authorization();
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
	public Authorization() {
		
		try {
			connect = DriverManager.getConnection(  
			          "jdbc:mysql://localhost:3306/work_together?serverTimezone=UTC","root","123456");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(121, 112, 136, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton button = new JButton("添加");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//更新数据库
				//首先查看是否存在这个人
				try {
					Statement stmt = connect.createStatement();
					ResultSet rs = stmt.executeQuery("select * from user where username = \'"+textField.getText()+"\'");
					if(rs.next()) {
						//存在此人
						//查询此人是否有同名项目
						rs = stmt.executeQuery("select * from repo where username = \'"+textField.getText()+"\' and repo_name = \'"+project_name+"\' and fork_from != \'"+login.username+"\'");
						if(rs.next()) {
							Error window = new Error("此人已经有同名项目!");
							window.SetCloseWindow(window);
							window.setVisible(true);
						}else {
							rs = stmt.executeQuery("select * from repo where username = \'"+textField.getText()+"\' and repo_name = \'"+project_name+"\'");
							if(rs.next()) {
								//项目是从这个人这里fork过去的
								stmt.executeUpdate("update repo set auth = 2 where username = \'"+textField.getText()+"\' and repo_name = \'"+project_name+"\'");
							}else {
								//没有同名项目 准备更新数据库 将auth设置为2，插入repo中 表明实际没有fork，等待fork
								stmt.executeUpdate("insert into repo values(\'"+textField.getText()+"\',\'"+project_name+"\',\'"+project_user+"\',2,0)");
								//关闭授权窗口
							}
						}
						close_window.dispose();
					}else {
						//不存在此人
						Error window = new Error("不存在此人，请重新输入!");
						window.SetCloseWindow(window);
						window.setVisible(true);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				//关闭窗口
				
			}
		});
		button.setBounds(289, 111, 93, 23);
		contentPane.add(button);
		
		label= new JLabel("请输入你想授权该项目的账号:");
		label.setBounds(76, 35, 257, 45);
		contentPane.add(label);
	}
}
