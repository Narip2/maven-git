package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JEditorPane;
import javax.swing.JTree;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
		Connection connect;
		
		Vector column_name = new Vector();
		Vector row = new Vector();
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
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button_1.setBounds(115, 80, 93, 23);
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
		table.setBounds(115, 186, 431, 338);
		contentPane.add(table);
	}
}
