package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Process_Create extends JFrame {

	private JPanel contentPane;
	private Process_Create close_window;
	private String description;
	private JTextArea textArea;
	private String proname;
	private String prouser;
	
	public void SetCloseWindow(Process_Create window) {
		close_window = window; 
	}
	public String GetDescription() {
		return description;
	}
	public void SetProname(String proname) {
		this.proname = proname;
	}
	public void SetProuser(String prouser) {
		this.prouser = prouser;
	}

	/**
	 * Create the frame.
	 */
	public Process_Create(String msg) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(msg);
		lblNewLabel.setBounds(87, 56, 205, 30);
		contentPane.add(lblNewLabel);
		
		textArea = new JTextArea();
		textArea.setBounds(88, 96, 260, 93);
		contentPane.add(textArea);
		
		JButton btnNewButton = new JButton("确定");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Process.desc = textArea.getText();
				//关闭该窗口
				close_window.dispose();
			}
		});
		btnNewButton.setBounds(255, 199, 93, 23);
		contentPane.add(btnNewButton);
	}
}
