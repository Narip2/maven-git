package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class project extends JFrame {

	private JPanel contentPane;
	public String username = login.username;
	private String project_name;
	private String project_user;
	private static project close_window;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					project frame = new project();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public void SetCloseWindow(project window) {
		close_window = window;
	}
	//用于设置和得到当前项目的username
	public void SetUser(String username) {
		project_user = username;
	}
	public String GetUser()
	{
		return project_user;
	}
	//用于更新和设置project_name
	public void SetProjectName(String proname) {
		project_name = proname;
	}
	public String GetProjectName() {
		return project_name;
	}
	
	
	/**
	 * Create the frame.
	 */
	public project() {
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
		
		JButton btnFork = new JButton("Fork");
		//Fork之后的操作
		btnFork.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//首先检查数据库中没有重名项目
				try {
					Connection connect = DriverManager.getConnection(  
					          "jdbc:mysql://localhost:3306/work_together?serverTimezone=UTC","root","123456");
					Statement stmt = connect.createStatement();
					ResultSet rs = stmt.executeQuery("select * from repo where username = \'" + username + "\' and repo_name = \'"+project_name+"\'");
					if(rs.next()) {
						Error window = new Error("仓库已经存在，请更改名字");
						window.SetCloseWindow(window);
						window.setVisible(true);
					}else {
						//没有重名项目，进行Fork
						//更新服务器目录
						SSH ssh = new SSH();
						ssh.exec("mkdir /root/"+username+"/"+project_name);
						ssh.exec("git init /root/"+username+"/"+project_name);
						//更新table repo
						stmt.executeUpdate("insert into repo values (\'"+username+"\',\'"+project_name+"\',\'"+project_user+"\')");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//更新数据库repo内容
				
			}
		});
		btnFork.setBounds(313, 69, 93, 23);
		contentPane.add(btnFork);
		
		JButton btnPullRequest = new JButton("Pull Request");
		btnPullRequest.setBounds(157, 69, 126, 23);
		contentPane.add(btnPullRequest);
		
		JButton button = new JButton("Clone");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//打开download_path界面，但是不跳转
				download_path window = new download_path();
				window.SetCloseWindow(window);
				window.SetDownloadProject(project_name);
				window.SetDownloadUser(project_user);
				window.setVisible(true);
			}
		});
		button.setBounds(455, 69, 93, 23);
		contentPane.add(button);
	}
}





/* 注意
 * 1.如果有Bug考虑一下project中是否每次进来都更新正确了project_name 和 project_user这两个变量
 *
 * 
 */