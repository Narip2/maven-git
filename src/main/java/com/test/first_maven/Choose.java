package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Choose extends JFrame {

	private JPanel contentPane;
	private Choose close_window;
	private int choice;
	
	
	public void SetCloseWindow(Choose window) {
		close_window = window;
	}
	//用于返回在这个窗口点击了哪个按钮
	public int GetBtnChoice() {
		return choice;
	}
	//用于设置在这个窗口点击了哪个按钮 1表示左 2表示右
	public void SetBtnChoice(int num) {
		choice = num;
	}
	
	/**
	 * Create the frame.
	 */
	public Choose(String label_msg,String btn1_msg,String btn2_msg) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(label_msg,JLabel.CENTER);
		lblNewLabel.setBounds(79, 72, 254, 50);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton(btn1_msg);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//左边按钮被点击返回1 并且窗口关闭
				SetBtnChoice(1);
				close_window.dispose();
			}
		});
		btnNewButton.setBounds(43, 166, 93, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton(btn2_msg);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//右边按钮被点击返回2并且窗口关闭
				SetBtnChoice(2);
				close_window.dispose();
			}
		});
		btnNewButton_1.setBounds(273, 166, 93, 23);
		contentPane.add(btnNewButton_1);
	}
}
