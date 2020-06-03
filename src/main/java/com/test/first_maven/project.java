package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;

import com.jcraft.jsch.Session;

import Demo.Test;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JProgressBar;
import javax.swing.JTree;
import javax.swing.JComboBox;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class project extends JFrame {

	private JPanel contentPane;
	public String username = login.username;
	public static String project_name;
	public static String project_user;
	private static project close_window;
	private Connection connect;
	private DefaultTreeModel tree_model;
	private DefaultMutableTreeNode Node;
	private DefaultTableModel model;
	private Vector<String> combobranch;
	private JComboBox comboBox;
	private JTree tree;
	private JTable table;
	public static String comment_msg;
	
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
						stmt.executeUpdate("insert into repo values (\'"+username+"\',\'"+project_name+"\',\'"+project_user+"\',0,0)");
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
				Process window = new Process(project_user,project_name);
				window.SetCloseWindow(window);
				SaveState();
				close_window.dispose();
				window.setVisible(true);
			}
		});
		
		btnNewButton.setBounds(566, 69, 93, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("返回");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				  after_login window = new after_login();
	    		  after_login.afterlogin_frame = window;
	    		  close_window.dispose();
	    		  window.setVisible(true);
			}
		});
		
		btnNewButton_1.setBounds(10, 10, 93, 23);
		contentPane.add(btnNewButton_1);
		
		//用jtree 展示项目结构(对应分支)
		login.ssh.SetProName(project_name);
		login.ssh.SetUserName(project_user);
		Node = login.ssh.GetAllFiles(project_name, "master");
		tree_model = new DefaultTreeModel(Node);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(157, 276, 517, 421);
		contentPane.add(scrollPane);
		
		//设置默认的model 不然会显示jtree默认的内容 默认打开master的分支
		tree = new JTree(tree_model);
		scrollPane.setViewportView(tree);
		
		combobranch = login.ssh.GetBranch(project_user, project_name);
		comboBox = new JComboBox(combobranch);
		comboBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				int index = comboBox.getSelectedIndex();
				Node = login.ssh.GetAllFiles(project_name, combobranch.get(index));
				tree_model = new DefaultTreeModel(Node);
				tree.setModel(tree_model);
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		
		comboBox.setBounds(237, 243, 85, 23);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Branches:");
		lblNewLabel.setBounds(157, 247, 70, 15);
		contentPane.add(lblNewLabel);
		
		//显示table
		model = new DefaultTableModel();
		//每次重复使用model
		model.setRowCount(0);
		model.setColumnIdentifiers(new Object[] {"comment_id","comment","username"});
		try {
			Statement stmt = login.connect.createStatement();
			ResultSet rs = stmt.executeQuery("select * from comment where repo_name = \'"+project_name+"\' and username = \'"+project_user+"\'");
			while(rs.next()) {
				model.addRow(new String[]{rs.getInt("comment_id")+"",rs.getString("comment"),rs.getString("username")});
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		table = new JTable(model);
		table.setBounds(795, 276, 517, 421);
		contentPane.add(table);
		
		JButton btnNewButton_2 = new JButton("添加评论");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Comment_add window = new Comment_add();
				window.SetCloseWindow(window);
				window.setVisible(true);
				window.addWindowListener(new WindowListener() {
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
					}
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
					}
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
							try {
								Statement stmt = login.connect.createStatement();
								ResultSet rs = stmt.executeQuery("select * from repo where username = \'"+project_user+"\' and repo_name = \'"+project_name+"\'");
								if(rs.next()) {
									int temp = rs.getInt("comment_id");
									stmt.executeUpdate("update repo set comment_id = "+(temp+1)+" where username = \'"+project_user+"\' and repo_name = \'"+project_name+"\'");
									stmt.executeUpdate("insert into comment values(\'"+project_name+"\',\'"+comment_msg+"\',"+(temp+1)+",\'"+project_user+"\',\'"+login.username+"\')");
									model.addRow(new String[] {temp+1+"",comment_msg,login.username});
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
					}
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
					}
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
					}
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
					}});

			}
		});
		
		btnNewButton_2.setBounds(1221, 220, 93, 23);
		contentPane.add(btnNewButton_2);
		
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