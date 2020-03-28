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

public class Error extends JFrame {
	private JPanel contentPane;
	private Error close_window;
	
	
	public void SetCloseWindow(Error window) {
		close_window = window;
	}

	/**
	 * Create the frame.
	 */
	public Error(String message) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel(message);
		label.setBounds(86, 79, 225, 71);
		contentPane.add(label);
		
		JButton button = new JButton("确定");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				close_window.dispose();
			}
		});
		button.setBounds(151, 195, 93, 23);
		contentPane.add(button);
		
	}
}
