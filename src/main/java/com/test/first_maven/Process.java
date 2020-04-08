package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.JTable;

public class Process extends JFrame {

	private JPanel contentPane;
	private Process close_window;
	private JTable table;
	
	public void SetCloseWindow(Process window) {
		close_window = window;
	}
	


	/**
	 * Create the frame.
	 */
	public Process() {
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
		
		JCheckBox jcx1 = new JCheckBox();
		
		table = new JTable();
		table.setBounds(133, 193, 611, 403);
		contentPane.add(table);
		table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(jcx1));
		
	}
	
	public class MyTable extends AbstractTableModel{

		
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	//https://blog.csdn.net/paullinjie/article/details/51899390
}
