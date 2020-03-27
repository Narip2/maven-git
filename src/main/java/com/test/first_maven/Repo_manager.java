package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eclipse.jgit.lib.Repository;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JList;
import java.awt.GridLayout;
import javax.swing.JTextPane;
import javax.swing.JInternalFrame;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JProgressBar;

public class Repo_manager extends JFrame {
	
	private Repository repo;
	private JPanel contentPane;
	private Repo_manager close_window;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Repo_manager frame = new Repo_manager();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void SetCloseWindow(Repo_manager window) {
		close_window = window;
	}
	public void SetRepository(Repository repository) {
		repo = repository;
	}
	/**
	 * Create the frame.
	 */
	public Repo_manager() {
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
		
		JList list = new JList();
		list.setBounds(115, 10, 286, 347);
		contentPane.add(list);
		
		JList list_1 = new JList();
		list_1.setBounds(10, 446, 286, 273);
		contentPane.add(list_1);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(419, 10, 894, 347);
		contentPane.add(textPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(427, 446, 886, 273);
		contentPane.add(textArea);
		
		JButton button = new JButton("刷新");
		button.setBounds(10, 10, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("同步");
		button_1.setBounds(10, 32, 93, 23);
		contentPane.add(button_1);
		
		JButton btnCommit = new JButton("Commit");
		btnCommit.setBounds(10, 54, 93, 23);
		contentPane.add(btnCommit);
		
		JButton btnPush = new JButton("Push");
		btnPush.setBounds(10, 77, 93, 23);
		contentPane.add(btnPush);
				
	}
}
