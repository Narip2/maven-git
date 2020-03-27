package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eclipse.jgit.lib.Repository;

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
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
