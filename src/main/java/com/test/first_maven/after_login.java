package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JTree;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JDesktopPane;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;

public class after_login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	static after_login afterlogin_frame;
	private JTable table;
	private JButton button_2;
	private JButton btnSshKey;
	private JButton button_3;
	private int auth_num = 0;
	private int pull_num = 0;
	Connection connect;
	private JButton button_4;
	private JTable table_1;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					after_login frame = new after_login();
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
	public after_login() {
		
		Vector column_name = new Vector();
		final Vector row = new Vector();
		try {
			connect = DriverManager.getConnection(  
			          "jdbc:mysql://localhost:3306/work_together?serverTimezone=UTC","root","123456");
			Statement stmt = connect.createStatement();
			//准备填充表格的数据，用于展示数据库中所有的项目
			ResultSet rs = stmt.executeQuery("select * from repo");
			column_name.add("repo_name");
			column_name.add("username");
			while(rs.next()) 
			{
				Vector rowdata = new Vector();
				rowdata.add(rs.getString("repo_name"));
				rowdata.add(rs.getString("username"));
				row.add(rowdata);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//控制软件大小，使得填充满整个屏幕
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		
		setBounds(0,0,
				screensize.width,
				screensize.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 50, 1050, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//创建搜索框
		textField = new JTextField();
		textField.setBounds(728, 23, 200, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton button = new JButton("搜索");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		button.setBounds(931, 22, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("创建仓库");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//界面跳转
				new_project window = new new_project();
				new_project.new_project_frame = window;
				afterlogin_frame.dispose();
				window.setVisible(true);
			}
		});
		button_1.setBounds(10, 22, 111, 23);
		contentPane.add(button_1);

//		Vector vector = new Vector();
//		vector.add("ddd");
//		 Vector vector1 = new Vector();
//		vector1.add("ss");
//		Vector vector2 = new Vector();
//		vector2.add(vector);
//		vector2.add(vector);
//		table = new JTable(vector2,vector1);
		table = new JTable(row,column_name);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int index = table.getSelectedRow();
				project.project_user = ((Vector) row.elementAt(index)).lastElement().toString();
				project.project_name = ((Vector) row.elementAt(index)).firstElement().toString();
				project window = new project();
				//设置项目名称和所属用户
				afterlogin_frame.dispose();
				//将window传到project里面，之后关闭的时候好关闭
				window.SetCloseWindow(window);
				window.setVisible(true);
			}
		});
		table.setBounds(115, 186, 431, 338);
		contentPane.add(table);
		
		button_2 = new JButton("打开本地仓库");
		final JFileChooser jfile = new JFileChooser("");
		jfile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				jfile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int index = jfile.showSaveDialog(null);
				//表示无文件选取或正常选取文件
				if(index == jfile.APPROVE_OPTION) {
					File file = jfile.getSelectedFile();
					
					//打开本地仓库
					FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
					try {
						Repository repository = repositoryBuilder.setGitDir(new File(file.getAbsolutePath()))
						                .readEnvironment() // scan environment GIT_* variables
						                .findGitDir() // scan up the file system tree
						                .setMustExist(true)
						                .build();
						Repo_manager window = new Repo_manager();
						window.SetCloseWindow(window);
						window.SetRepository(repository);
						afterlogin_frame.dispose();
						window.setVisible(true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		});
		button_2.setBounds(10, 55, 111, 23);
		contentPane.add(button_2);
		
		btnSshKey = new JButton("SSH Key管理");
		btnSshKey.setBounds(10, 88, 111, 23);
		contentPane.add(btnSshKey);
		
		try {
			Statement stmt = connect.createStatement();
			//查询得到授权邀请信息条数
			ResultSet rs = stmt.executeQuery("select * from repo where username = \'"+login.username+"\' and auth = 2");
			rs.last();
			auth_num += rs.getRow();
			
			//查询拒绝授权邀请的条数
			rs = stmt.executeQuery("select * from repo where fork_from = \'"+login.username+"\' and auth = -4");
			rs.last();
			auth_num += rs.getRow();
			
			//查询得到收到的pull request条数
			rs = stmt.executeQuery("select * from pull_request where flag = 0 and to_user = \'"+login.username+"\'");
			rs.last();
			pull_num += rs.getRow();
			
			//查询同意pull request条数
			rs = stmt.executeQuery("select * from pull_request where flag = 1 and from_user = \'"+login.username+"\'");
			rs.last();
			pull_num += rs.getRow();
			
			//查询拒绝pull request条数
			rs = stmt.executeQuery("select * from pull_request where flag = -1 and from_user = \'"+login.username+"\'");
			rs.last();
			pull_num += rs.getRow();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		button_3 = new JButton("消息"+"("+(auth_num+pull_num)+")");
		button_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//点击之后跳转到详细消息页面，其中有pull request 和 授权邀请信息
				//注意如果拒绝邀请，需要删除数据库中这一项，如果接受邀请，需要将数据库中这一项的auth修改成1
				//pull request如果接受需要将auth这一项从3修改成0,如果拒绝也需要将其中的3修改成0，同时进行拉取操作
				//界面跳转
				Message window = new Message();
				window.SetCloseWindow(window);
				afterlogin_frame.dispose();
				window.setVisible(true);
			}
		});
		button_3.setBounds(10, 124, 111, 23);
		contentPane.add(button_3);
		
		table_1 = new JTable();
		table_1.setBounds(688, 186, 431, 338);
		contentPane.add(table_1);
		
	}
}





/*注释
 * 1.在table 点击之后注意将project中project_name变量名称设置一下成当前名称
 *  并且project_user也需要改变一下
 * 
 * 
 *
 */