package com.test.first_maven;
//
//import java.awt.BorderLayout;
//import java.awt.EventQueue;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;
//
//public class login extends JFrame {
//
//	private JPanel contentPane;
//
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					login frame = new login();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the frame.
//	 */
//	public login() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 300);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
//		setContentPane(contentPane);
//	}
//
//}


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	public static String username;
	//temp_pro_name用于表示现在打开或者处理的项目名称，直到打开下一个项目为止不更新
	//temp_pro_user用于表示现在打开或者处理的项目创建者 直到打开下一个项目为止不更新
	public static String temp_pro_name;
	public static String temp_pro_user;
	public String passwd;
	static login login_frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//First connnect to database
		try {  
		      Class.forName("com.mysql.jdbc.Driver");     //加载MYSQL JDBC驱动程序     
		      //Class.forName("org.gjt.mm.mysql.Driver");  
		     System.out.println("Success loading Mysql Driver!");  
		    }  
		    catch (Exception e) {  
		      System.out.print("Error loading Mysql Driver!");  
		      e.printStackTrace();  
		    }
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					login_frame = frame;
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
	public login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		textField = new JTextField();
		textField.setBounds(138, 38, 159, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(138, 69, 159, 21);
		contentPane.add(passwordField);
		
		final JLabel label_2 = new JLabel("");
		label_2.setBounds(111, 175, 203, 48);
		contentPane.add(label_2);
		
		
		//注册
				JButton btnNewButton = new JButton("注册");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						username = textField.getText();
						passwd = passwordField.getText();
						try {
							Connection connect = DriverManager.getConnection(  
							          "jdbc:mysql://localhost:3306/work_together?serverTimezone=UTC","root","123456");
							Statement stmt = connect.createStatement();
							if(username == ""||passwd == "")
							{
								label_2.setText("请将账号密码输入完整!");
							}
							else
							{
								if(username.matches("[a-zA-Z0-9_]+"))
								{
									ResultSet rs = stmt.executeQuery("select * from user where username = \'"+username+"\'");
									if(rs.next())
									{
										label_2.setText("账号已存在，请直接登录!");
									}else {
										stmt.executeUpdate("insert into user values(\'"+username+"\',\'"+passwd+"\')");
										SSH ssh = new SSH();
										ssh.exec("mkdir "+username);
										label_2.setText("账号创建成功，请登录!");
									}
								}else{
									label_2.setText("名称不符合规范，只能输入字母或数字或下划线");
								}
							}
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
					}
				});
				btnNewButton.setBounds(70, 125, 93, 23);
				contentPane.add(btnNewButton);
		
		//登录
		JButton btnNewButton_1 = new JButton("登录");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				username = textField.getText();
				passwd = passwordField.getText();
				System.out.println(passwd);
				if(username == ""||passwd == "") {
					//Warning
					label_2.setText("账号或密码未输入!");
				}
				try {  
					Connection connect = DriverManager.getConnection(  
					          "jdbc:mysql://localhost:3306/work_together?serverTimezone=UTC","root","123456");  
					           //连接URL为   jdbc:mysql//服务器地址/数据库名  ，后面的2个参数分别是登陆用户名和密码
				      Statement stmt = connect.createStatement();
				      ResultSet rs = stmt.executeQuery("select * from user where username = \'" + username + "\'");
				      if(!rs.next()) {
				    	  label_2.setText("账号不存在，请先注册!");
				      }else {
				    	  if(rs.getString(2).equals(passwd))
				    	  {
				    		  //界面跳转
				    		  after_login window = new after_login();
				    		  after_login.afterlogin_frame = window;
				    		  login_frame.dispose();
//				    		  contentPane.setVisible(false);
				    		  window.setVisible(true);
//				    		  System.exit(0);
				    		  
				    	  }else {
				    		  label_2.setText("密码错误!");
				    	  }
				      }
				    }  
				    catch (Exception e) {  
				      System.out.print("get data error!");  
				      e.printStackTrace();  
				    }  
			}
		});
		btnNewButton_1.setBounds(245, 125, 93, 23);
		contentPane.add(btnNewButton_1);
		

		JLabel label = new JLabel("\u8D26\u53F7:");
		label.setBounds(74, 41, 54, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u5BC6\u7801:");
		label_1.setBounds(74, 72, 54, 15);
		contentPane.add(label_1);
		

	}
}
