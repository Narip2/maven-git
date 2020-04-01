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
	private Vector<String> msg = new Vector<String>();
	private String login_user = login.username;
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
		msg.clear();
		try {
			//先查询有合作请求的
			ResultSet rs = stmt.executeQuery("select * from repo where auth = 2 and username = \'"+login_user+"\'");
			while(rs.next()) {
				msg.add(rs.getString("fork_from")+"邀请您一起和ta合作"+rs.getString("repo_name")+"项目");
			}
			//查询有没有pull request的请求
			rs = stmt.executeQuery("select * from repo where auth = 3 and fork_from = \'"+login_user+"\'");
			while(rs.next()) {
				msg.add(rs.getString("username")+"针对你的"+rs.getString("repo_name")+"项目发起了pull request，点击查看详情");
			}
			
			//查询同意合作的
			rs = stmt.executeQuery("select * from repo where auth = 4 and fork_from = \'"+login_user+"\'");
			while(rs.next()) {
				msg.add(rs.getString("username")+"同意了你对"+rs.getString("repo_name")+"项目的合作请求");
			}
			
			//查询拒绝合作的
			rs = stmt.executeQuery("select * from repo where auth = -4 and fork_from = \'"+login_user+"\'");
			while(rs.next()) {
				msg.add(rs.getString("username")+"拒绝了你对"+rs.getString("repo_name")+"项目的合作请求");
			}
			
			//查询同意pull request的
			rs = stmt.executeQuery("select * from repo where auth = 5 and username = \'"+login_user+"\'");
			while(rs.next()) {
				msg.add(rs.getString("fork_from")+"同意了你对"+rs.getString("repo_name")+"项目的pull request请求");	
			}
			//查询拒绝pull request的
			rs = stmt.executeQuery("select * from repo where auth = -5 and username = \'"+login_user+"\'");
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
		//展示list
		RefreshList(list);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = list.getSelectedIndex();
				String str = msg.elementAt(index);
				//有合作请求的
				if(str.contains("邀")) {
					//如果同意需要直接fork到用户仓库 默认执行的fork(clone)操作
					//打开choose窗口进行选择
					Choose cs = new Choose("请选择是否接受这个邀请","拒绝","接受");
					cs.setVisible(true);
					String username = str.substring(0,str.indexOf("邀"));
					String proname = str.substring(str.indexOf("合作")+1,str.indexOf("项目"));
					//2表示是 接受1表示拒绝
					if(cs.GetBtnChoice() == 2) {
						//执行SSH的fork(clone)操作
						SSH ssh = new SSH();
						ssh.exec("git clone --bare root@39.97.255.250:/root/"+username+"/"+proname+" /root/"+login_user+"/"+proname);
						//执行数据库的修改操作
						try {
							stmt.executeUpdate("update repo set auth = 1 where auth = 2 and fork_from = \'"+username+"\' and repo_name = \'"+proname+"\'");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//刷新JList
						RefreshList(list);
					}else {
						//修改数据库中的auth为-4表示拒绝
						//执行数据库修改操作
						try {
							stmt.executeUpdate("update repo set auth = -4 where auth = 2 and fork_from = \'"+username+"\' and repo_name = \'"+proname+"\'");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//更新list
						RefreshList(list);
					}
				}//发起pull request
				else if(str.contains("针")) {
					//跳转到Pull_request窗口，查看pull requst的不同
					//注意修改构造函数，好跳转之后不用刷新直接显示内容
					
					
				}//同意合作
				else if(str.contains("同意")&&str.contains("合作")) {
					//修改数据库 执行消除消息操作
					String username = str.substring(0, str.indexOf("同意"));
					String proname = str.substring(str.indexOf("对")+1, str.indexOf("项目"));
					//修改repo中的auth变成1
					try {
						stmt.executeUpdate("update repo set auth = 1 where auth = 4 and username = \'"+username+"\' and repo_name = \'"+proname+"\'");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//刷新list
					RefreshList(list);
				}//拒绝合作
				else if(str.contains("拒绝")&&str.contains("合作")) {
					//修改数据库执行删除消息操作
					String username = str.substring(0,str.indexOf("拒绝"));
					String proname = str.substring(str.indexOf("对")+1,str.indexOf("项目"));
					try {
						//直接从数据库中删除该行
						stmt.executeUpdate("delete from repo where auth = -4 and username = \'"+username+"\' and repo_name = \'"+proname+"\'");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//刷新list
					RefreshList(list);
				}//同意pull request
				else if(str.contains("同意")&&str.contains("的pull")) {//加上中文 '的' 防止项目名称含有pull的混入
					//修改数据库执行删除消息操作
					String username = str.substring(0,str.indexOf("同意"));
					String proname = str.substring(str.indexOf("对")+1, str.indexOf("项目"));
					//修改数据库
					try {
						//默认修改auth = 0. 因为既然用了pull request，就没有权限直接去
						stmt.executeUpdate("update repo set auth = 0 where auth = 5 and fork_from = \'"+username+"\' and repo_name = \'"+proname+"\'");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//刷新list
					RefreshList(list);
				}//拒绝pull request
				else if(str.contains("拒绝")&&str.contains("的pull")) {
					//修改数据库执行删除消息操作
					String username = str.substring(0,str.indexOf("拒绝"));
					String proname = str.substring(str.indexOf("对")+1, str.indexOf("项目"));
					try {
						stmt.executeUpdate("update repo set auth = 0 where auth = -5 and fork_from = \'"+username+"\' and repo_name = \'"+proname+"\'");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});		

		
	}
}