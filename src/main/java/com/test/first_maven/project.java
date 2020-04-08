package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;

import com.jcraft.jsch.Session;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JProgressBar;

public class project extends JFrame {

	private JPanel contentPane;
	public String username = login.username;
	public static String project_name;
	public static String project_user;
	private static project close_window;
	private Connection connect;
	
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
	public void SaveState() {
		login.temp_pro_name = project_name;
		login.temp_pro_user = project_user;
	}
	public void LoadState() {
		project_name = login.temp_pro_name;
		project_user = login.temp_pro_user;
	}
	//用于设置和得到当前项目的username
//	public void SetUser(String username) {
//		project_user = username;
//	}
//	public String GetUser()
//	{
//		return project_user;
//	}
	//用于更新和设置project_name
//	public void SetProjectName(String proname) {
//		project_name = proname;
//	}
//	public String GetProjectName() {
//		return project_name;
//	}
	
	
	/**
	 * Create the frame.
	 */
	public project() {
		try {
			connect = DriverManager.getConnection(  
			          "jdbc:mysql://localhost:3306/work_together?serverTimezone=UTC","root","123456");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
					Statement stmt = connect.createStatement();
					ResultSet rs = stmt.executeQuery("select * from repo where username = \'" + username + "\' and repo_name = \'"+project_name+"\'");
					if(rs.next()) {
						Error window = new Error("仓库已经存在，请更改名字");
						window.SetCloseWindow(window);
						window.setVisible(true);
					}else {
						//没有重名项目，进行Fork
						//使用clone的方式实现fork
						//服务器自己克隆到服务器中
						//Clone 裸仓库 clone --bare
						//在服务器上项目名称
						SSH ssh = new SSH();
						ssh.exec("git clone --bare root@39.97.255.250:/root/"+project_user+"/"+project_name+" /root/"+username+"/"+project_name);
						//更新table repo
						stmt.executeUpdate("insert into repo values (\'"+username+"\',\'"+project_name+"\',\'"+project_user+"\',0)");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnFork.setBounds(313, 69, 93, 23);
		contentPane.add(btnFork);
		JButton btnPullRequest = new JButton("+ Pull Request");
		btnPullRequest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//查询数据库获取一定信息
				try {
					Statement stmt = connect.createStatement();
					ResultSet rs  = stmt.executeQuery("select * from repo where username = \'"+project_user+"\' and repo_name = \'"+project_name+"\'");
					if(rs.next()) {
						//跳转到create_PR 界面
						Create_PR window = new Create_PR();
						SaveState();
						window.SetCloseWindow(window);
						window.SetFromUser(project_user);
						window.SetRepoName(project_name);
						String fk_from = rs.getString("fork_from");
						window.SetToUser(fk_from);
						SSH ssh = new SSH();
						window.SetFromBranch(ssh.GetBranch(project_user, project_name));
						window.SetToBranch(ssh.GetBranch(fk_from, project_name));
						
						
						window.Init();
						close_window.dispose();
						window.setVisible(true);		
					}else {
						System.out.println("查询为空 error");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
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
		button.setBounds(438, 69, 93, 23);
		contentPane.add(button);
		
		JButton btnNewButton = new JButton("项目进度");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Process window = new Process();
				window.SetCloseWindow(window);
				SaveState();
				close_window.dispose();
				
				window.setVisible(true);
			}
		});
		btnNewButton.setBounds(566, 69, 93, 23);
		contentPane.add(btnNewButton);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(546, 127, 146, 14);
		
		contentPane.add(progressBar);
		if(username.equals(project_user)) {
		//用于添加一些项目合作人可以不用通过pull request， 而可以直接上传代码之类的
			JButton button_1 = new JButton("授权");
			button_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					Authorization window = new Authorization();
					window.SetCloseWindow(window);
					window.SetProjectName(project_name);
					window.SetProjectUser(project_user);
					window.setVisible(true);
				}
			});
			button_1.setBounds(1144, 80, 93, 23);
			contentPane.add(button_1);
		}
	}
}





/* 注意
 * 1.如果有Bug考虑一下project中是否每次进来都更新正确了project_name 和 project_user这两个变量
 *
 * 
 */