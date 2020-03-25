package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class download_path extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private static download_path close_window;
	private String download_project;
	private String download_user;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					download_path frame = new download_path();
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
	
	public void SetCloseWindow(download_path window) {
		close_window = window;
	}
	public void SetDownloadProject(String project) {
		download_project = project;
	}
	public void SetDownloadUser(String user) {
		download_user = user;
	}
	
	
	public download_path() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("输入文件路径：");
		label.setBounds(51, 93, 84, 15);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(145, 90, 142, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton button = new JButton("浏览");
		final JFileChooser jfile = new JFileChooser("");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int index = jfile.showSaveDialog(null);
				//表示无文件选取或正常选取文件
				if(index == jfile.APPROVE_OPTION) {
					File file = jfile.getSelectedFile();
					textField.setText(file.getAbsolutePath());
				}
//				else if(index == jfile.ERROR_OPTION) {//表示发生错误或对话框已被解除而退出对话框
//				}
			}
		});
		button.setBounds(297, 89, 64, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("确认");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		button_1.setBounds(169, 176, 70, 23);
		contentPane.add(button_1);
	}
}
