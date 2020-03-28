package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class beta2 extends JFrame {

	private JPanel contentPane;
	public JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					beta2 frame = new beta2();
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
	public beta2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		textArea = new JTextArea();
		textArea.setBounds(58, 81, 155, 78);
		contentPane.add(textArea);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(textArea.getText() != null) {
					System.out.println("not null");
				}
				if(textArea.getText() == null) {
					System.out.println("null");
				}
				if(textArea.getText() == "")
				{
					System.out.println(1);
				}
				if(textArea.getText().equals("")) {
					System.out.println(2);
				}				
			}
		});
		btnNewButton.setBounds(89, 185, 93, 23);
		contentPane.add(btnNewButton);
	}
}
