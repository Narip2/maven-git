package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;

public class Keys_Create extends JFrame {

	private JPanel contentPane;
	private Keys_Create close_window;
	private JTextField textField;
	private JTextArea textArea;
	private String key_Create;
	private String path_Create;
	
	public void SetCloseWindow(Keys_Create window) {
		close_window = window;
	}
	
	public String GetKey() {
		return key_Create;
	}
	
	public String GetPath() {
		return path_Create;
	}

	/**
	 * Create the frame.
	 */
	public Keys_Create() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(36, 40, 334, 116);
		contentPane.add(textArea);
		
		textField = new JTextField();
		textField.setBounds(70, 180, 197, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		final JFileChooser jfile = new JFileChooser("");
		jfile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		JButton btnNewButton = new JButton("浏览");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jfile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int index = jfile.showSaveDialog(null);
				//表示无文件选取或正常选取文件
				if(index == jfile.APPROVE_OPTION) {
					File file = jfile.getSelectedFile();
					textField.setText(file.getAbsolutePath());
				}
			}
		});
		btnNewButton.setBounds(277, 179, 93, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("确定");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Statement stmt = login.connect.createStatement();
					key_Create = textArea.getText();
					path_Create = textField.getText().replace("\\", "\\\\");
					stmt.executeUpdate("insert into rsa values(\'"+key_Create+"\',\'"+login.username+"\',\'"+path_Create+"\',\'"+login.Mac+"\')");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//关闭窗口
				close_window.dispose();
			}
		});
		btnNewButton_1.setBounds(174, 228, 93, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("Public Key:");
		lblNewLabel.setBounds(10, 10, 109, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("文件路径:");
		lblNewLabel_1.setBounds(10, 183, 62, 15);
		contentPane.add(lblNewLabel_1);
	}
}
