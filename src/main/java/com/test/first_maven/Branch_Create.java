package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.lib.Repository;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Branch_Create extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private Branch_Create close_window;
	private Repository repo;

	public void SetCloseWindow(Branch_Create window) {
		close_window = window;
	}
	public void SetRepoName(Repository repository) {
		repo = repository;
	}
	
	/**
	 * Create the frame.
	 */
	public Branch_Create() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Branch名:");
		lblNewLabel.setBounds(41, 102, 54, 15);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(105, 99, 95, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("确定");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//创建分支
				Git git = new Git(repo);
				try {
					git.branchCreate().setName(textField.getText()).call();
				} catch (RefAlreadyExistsException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RefNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvalidRefNameException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (GitAPIException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//关闭窗口
				close_window.dispose();
			}
		});
		btnNewButton.setBounds(227, 98, 93, 23);
		contentPane.add(btnNewButton);
	}
}
