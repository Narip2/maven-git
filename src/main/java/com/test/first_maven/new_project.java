package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JCheckBox;

public class new_project extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	public String username = login.username;
	static new_project new_project_frame;
	private JCheckBox chckbxNewCheckBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
//		try {  
//		      Class.forName("com.mysql.jdbc.Driver");     //加载MYSQL JDBC驱动程序     
//		      //Class.forName("org.gjt.mm.mysql.Driver");  
//		     System.out.println("Success loading Mysql Driver!");  
//		    }  
//		    catch (Exception e) {  
//		      System.out.print("Error loading Mysql Driver!");  
//		      e.printStackTrace();  
//		    }
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new_project frame = new new_project();
					new_project_frame = frame;
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
	public new_project() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("新建项目名称:");
		label.setBounds(39, 55, 78, 15);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(127, 52, 125, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton button = new JButton("确定");
		button.addMouseListener(new MouseAdapter() {
			@Override
			//创建对应用户的新项目，修改数据库
			public void mouseClicked(MouseEvent e) {
				final String project_name = textField.getText();
				Connection connect = null;
				try {
				connect = DriverManager.getConnection(  
					          "jdbc:mysql://localhost:3306/work_together?serverTimezone=UTC","root","123456");
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				//需要用README初始化
				if(chckbxNewCheckBox.isSelected()) {
					
				}//不需要用README初始化
				else{
					try {
			
						Statement stmt = connect.createStatement();
						//插入数据库
						stmt.executeUpdate("insert into repo (username,repo_name,auth) values(\'"+username+"\',\'"+project_name+"\',1)");
						
						//在服务器上创建文件并初始化
						SSH ssh = new SSH();
						ssh.exec("mkdir "+username+"/"+project_name);
						ssh.exec("git init --bare "+username+"/"+project_name);
						
						//设置下面要跳转进去的repo的名字
						project.project_name = project_name;
						project.project_user = username;
						project window = new project();
						window.SetCloseWindow(window);
						new_project_frame.dispose();
						window.setVisible(true);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			
				}
					}
		});
		button.setBounds(127, 153, 93, 23);
		contentPane.add(button);
		
		JLabel lblNewLabel = new JLabel("创建初始化文件README");
		lblNewLabel.setBounds(126, 93, 126, 21);
		contentPane.add(lblNewLabel);
		
		chckbxNewCheckBox = new JCheckBox();
		chckbxNewCheckBox.setBounds(92, 93, 103, 23);
		contentPane.add(chckbxNewCheckBox);
	}
}
