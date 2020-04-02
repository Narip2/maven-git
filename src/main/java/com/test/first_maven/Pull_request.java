package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class Pull_request extends JFrame {

	private JPanel contentPane;
	private Pull_request close_window;
	private JTextArea textArea;
	
	
	public void SetCloseWindow(Pull_request window) {
		close_window = window;
	}
	//刷新textarea 窗口 用于一打开该窗口就能显示
	public void FreshList() {
		
	}
	
	
	/**
	 * Create the frame.
	 */
	public Pull_request() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//控制软件大小，使得填充满整个屏幕
				Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
				setBounds(0,0,
						screensize.width,
						screensize.height);
//		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("返回");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//界面跳转，返回到Message窗口
				Message window = new Message();
				window.SetCloseWindow(window);
				close_window.dispose();
				window.setVisible(true);
			}
		});
		btnNewButton.setBounds(31, 24, 93, 23);
		contentPane.add(btnNewButton);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(114, 133, 579, 392);
		contentPane.add(textArea);
		
		JButton btnNewButton_1 = new JButton("合并");
		btnNewButton_1.setBounds(600, 573, 93, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("拒绝合并");
		btnNewButton_2.setBounds(114, 573, 93, 23);
		contentPane.add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("不同之处:");
		lblNewLabel.setBounds(112, 108, 54, 15);
		contentPane.add(lblNewLabel);
	}
}
