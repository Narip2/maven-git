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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.event.PopupMenuListener;

import Demo.Test;

import javax.swing.event.PopupMenuEvent;

public class Create_PR extends JFrame {

	private JPanel contentPane;
	private Create_PR close_window;
	
	private String from_user;
	private String repo_name;
	private String to_user;
	private String from_branch;
	private String to_branch;
	
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JTextArea textArea;
	//from 和 to 不需要分配空间，因为不涉及vector的修改操作
	private Vector<String> from;
	private Vector<String> to;
	
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
	public void Init() {
		//初始化branch
		from_branch = from.get(0);
		to_branch = to.get(0);
		//初始化标签显示数据
		lblNewLabel_1.setText(to_user+"/"+repo_name+"/");
		lblNewLabel.setText(from_user+"/"+repo_name+"/");
		comboBox.setModel(new DefaultComboBoxModel(from));
		comboBox_1.setModel(new DefaultComboBoxModel(from));
		ShowDiff();
		
	}
	public void SetFromBranch(Vector<String> f_v) {
		from = f_v;
	}
	public void SetToBranch(Vector<String> t_v) {
		to = t_v;
	}
	public void ShowDiff() {
		SSH ssh = new SSH();
		Vector<String> str = new Vector<String>();
		ssh.exec("cd /root/"+to_user+"/"+repo_name+" &&git fetch root@39.97.255.250:/root/"+from_user+"/"+repo_name+" "+from_branch+" &&git diff "+to_branch+" FETCH_HEAD");
		str = ssh.GetOutput();
		String show = "";
//		for(int i = 0; i < str.size(); i++) {
//			str.set(i, str.get(i)+"\n");
//		}
		for(String temp:str) {
			show += temp;
			show += "\n";
		}
		textArea.setText(show);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Create_PR frame = new Create_PR();
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
		
		comboBox = new JComboBox();
		comboBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				int index = comboBox.getSelectedIndex();
				from_branch = from.get(index);
				ShowDiff();
			}
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		});
		comboBox.setBounds(160, 83, 111, 23);
		contentPane.add(comboBox);
		
		comboBox_1 = new JComboBox();
		comboBox_1.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				int index = comboBox_1.getSelectedIndex();
				to_branch = to.get(index);
				ShowDiff();
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox_1.setBounds(606, 83, 111, 23);
		contentPane.add(comboBox_1);
		
		lblNewLabel = new JLabel();
		lblNewLabel.setBounds(39, 83, 111, 23);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setBounds(485, 83, 111, 23);
		contentPane.add(lblNewLabel_1);
		
		textArea = new JTextArea();
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
					ResultSet rs = stmt.executeQuery("select * from repo where username = \'"+from_user+"\' and repo_name = \'"+repo_name+"\'");
					//由于rs必须.next()一下才能继续，所以干脆在这里加了一个if判断
					if(rs.next()) {
						if(rs.getInt("auth") == 1) {
							//有权限，直接push
							SSH ssh = new SSH();
							ssh.exec("cd /"+from_user+"/"+repo_name
									+" &&git push root@39.97.255.250:/root/"+to_user+"/"+repo_name+" "+from_branch+":"+to_branch);
						}else {
							//没有权限，执行正常pull request操作
							//flag 位0表示未回应，1表示同意，-1表示拒绝
							stmt.executeUpdate("insert into pull_request values(\'"+from_user+"\',\'"+from_branch+"\',\'"+repo_name+"\',\'"+to_user+"\',\'"+to_branch+"\',0)");
						}
						//和返回按钮执行一样的功能
						project window =  new project();
						window.SetCloseWindow(window);
						window.LoadState();
						close_window.dispose();
						window.setVisible(true);
					}

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
