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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JTextArea;
import javax.swing.JLabel;

public class Pull_request extends JFrame {

	private JPanel contentPane;
	private Pull_request close_window;
	private JTextArea textArea;
	private String from_user;
	private String to_user = login.username;
	private String repo_name;
	private String from_branch;
	private String to_branch;
	private Connection connect;
	
	public void SetCloseWindow(Pull_request window) {
		close_window = window;
	}
	
	public void SetRepoName(String repo) { 
		repo_name = repo;
	}
	public void SetFromUser(String user) {
		from_user = user;
	}
	public void SetFromBranch(String branch) {
		from_branch = branch;
	}
	public void SetToBranch(String branch) {
		to_branch = branch;
	}
	//刷新textarea 窗口 用于一打开该窗口就能显示
	public void FreshList() {
		Vector<String> str = new Vector<String>();
		SSH ssh = new SSH();
		ssh.exec("cd /root/"+to_user+"/"+repo_name
				+" &&git fetch root@39.97.255.250:/root/"+from_user+"/"+repo_name+" "+from_branch
				+" &&git diff "+to_branch+" FETCH_HEAD");
		str = ssh.GetOutput();
		String show = "";
		for(String temp:str) {
			show += temp;
			show += "\n";
		}
		textArea.setText(show);
	}
	public void BackToMsg() {
		Message window = new Message();
		window.SetCloseWindow(window);
		close_window.dispose();
		window.setVisible(true);
	}
	
	/**
	 * Create the frame.
	 */
	public Pull_request() {
		try {
			connect = DriverManager.getConnection(  
			          "jdbc:mysql://localhost:3306/work_together?serverTimezone=UTC","root","123456");
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
				//界面跳转，返回到Message窗口
				Message window = new Message();
				window.SetCloseWindow(window);
				close_window.dispose();
				window.setVisible(true);
			}
		});
		btnNewButton.setBounds(31, 24, 93, 23);
		contentPane.add(btnNewButton);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(114, 133, 579, 392);
		contentPane.add(textArea);
		
		JButton btnNewButton_1 = new JButton("合并");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//在远程服务器实行merge操作
				//但是由于远程仓库都为裸仓库，所以需要在远程服务器上利用clone再push的方法实现merge
				SSH ssh = new SSH();
				ssh.exec("mkdir .init && cd .init &&git clone root@39.97.255.250:/root/"+from_user+"/"+repo_name
						+" &&git push root@39.97.255.250:/root/");
				//更新数据库
				//返回到message窗口
				BackToMsg();
			}
		});
		btnNewButton_1.setBounds(600, 573, 93, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("拒绝合并");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//如果拒绝，就要在数据库中修改pull_request的flag位为-1
				try {
					Statement stmt = connect.createStatement();
					stmt.executeUpdate("update pull_request set flag = -1 where from_user = \'"
					+from_user+"\' and from_branch = \'"+from_branch+"\' and repo_name = \'"+repo_name+"\'");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//然后执行窗口返回操作
				BackToMsg();
			}
		});
		btnNewButton_2.setBounds(114, 573, 93, 23);
		contentPane.add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("不同之处:");
		lblNewLabel.setBounds(112, 108, 54, 15);
		contentPane.add(lblNewLabel);
	}
}
