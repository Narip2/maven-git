package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import Demo.Test;

import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Process extends JFrame {

	private JPanel contentPane;
	private Process close_window;
	private JTable table;
	private DefaultTableModel model;
	private String prouser;
	private String proname;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private MouseEvent mevent;
	public static String desc;
	
	public void SetCloseWindow(Process window) {
		close_window = window;
	}
	
	
	/**
	 * Create the frame.
	 */
	public Process(final String prouser, final String proname) {
		this.prouser = prouser;
		this.proname = proname;
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
		//选中table中某一行之后才能点击删除 然后删除该行
		JButton btnNewButton = new JButton("删除");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int columnIndex = table.columnAtPoint(mevent.getPoint());
				int rowIndex = table.rowAtPoint(mevent.getPoint());
				Connection connect;
				try {
					connect = DriverManager.getConnection(  
					          "jdbc:mysql://localhost:3306/work_together?serverTimezone=UTC","root","123456");
					Statement stmt = connect.createStatement();
					//更新数据库 删除改行
					stmt.executeUpdate("delete from process where username = \'"+prouser+"\' and repo_name = \'"+proname+"\' and description = \'"+table.getValueAt(rowIndex, 1)+"\'");
					//刷新表格
					model.removeRow(rowIndex);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(573, 135, 93, 23);
		contentPane.add(btnNewButton);
		
		//显示table
		model = new DefaultTableModel();
		//每次重复使用model
		model.setRowCount(0);
		model.setColumnIdentifiers(new Object[] {"","description"});
		table = new JTable(model);
		TableColumn tc = table.getColumnModel().getColumn(0);
		//如果列是bool类型，会自动变成checkbox
		tc.setCellEditor(table.getDefaultEditor(Boolean.class));
		tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
		//连接数据库
		Connection connect;
		try {
			connect = DriverManager.getConnection(  
			          "jdbc:mysql://localhost:3306/work_together?serverTimezone=UTC","root","123456");
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery("select * from  process where username = \'"+prouser+"\' and repo_name = \'"+proname+"\'");
			while(rs.next()) {
				String temp = rs.getString("description");
				if(rs.getBoolean("isfinish")) {
					//该任务已经完成 checkbox显示一个勾
					model.addRow(new Object[] {new Boolean(true),temp});
				}else {
					//该任务没有完成 checkbox不显示勾
					model.addRow(new Object[] {new Boolean(false),temp});
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//记录每次e 用于删除操作使用
				mevent = e;
				
				int columnIndex = table.columnAtPoint(e.getPoint());
				int rowIndex = table.rowAtPoint(e.getPoint());
				String description = (String) model.getValueAt(rowIndex, 1);
				//第0列时才执行
				if(columnIndex == 0) {
					Statement stmt = null;
					try {
						Connection connect = DriverManager.getConnection(  
						          "jdbc:mysql://localhost:3306/work_together?serverTimezone=UTC","root","123456");
						stmt = connect.createStatement();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//如果是checkbox列
					if((boolean)model.getValueAt(rowIndex, 0) == false) {
						try {
							stmt.executeUpdate("update process set isfinish = 0 where username = \'"+prouser+"\' and repo_name = \'"+proname+"\' and description = \'"+description+"\'");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else {
						try {
							stmt.executeUpdate("update process set isfinish = 1 where username = \'"+prouser+"\' and repo_name = \'"+proname+"\' and description = \'"+description+"\'");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}else {
					//非checkbox 不执行操作
				}
			}
		});
		table.setBounds(133, 193, 611, 403);
		contentPane.add(table);
	
			
		btnNewButton_1 = new JButton("创建");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Process_Create window = new Process_Create("请输入描述进度的内容:");
				window.SetCloseWindow(window);
				window.SetProname(proname);
				window.SetProuser(prouser);
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
						//关闭新建窗口之后更新table
						//更新数据库
						Connection connect;
						try {
							connect = DriverManager.getConnection(  
							          "jdbc:mysql://localhost:3306/work_together?serverTimezone=UTC","root","123456");
							Statement stmt = connect.createStatement();
							stmt.executeUpdate("insert into process values(\'"+prouser+"\',\'"+proname+"\',\'"+desc+"\',0)");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//刷新table
						model.addRow(new Object[] {new Boolean(false),desc});
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
					}
				});
				window.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(133, 135, 93, 23);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("返回");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				project window = new project();
				//载入存放的信息
				window.LoadState();
				window.SetCloseWindow(window);
				close_window.dispose();
				window.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(10, 10, 93, 23);
		contentPane.add(btnNewButton_2);
		
		
	}
}
