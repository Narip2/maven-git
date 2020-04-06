package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Fetch extends JFrame {

	private JPanel contentPane;
	private Fetch close_window;
	private Repository repo;
	private Vector<String> branch = new Vector<String>();
	
	private JComboBox comboBox;
	
	public void SetCloseWindow(Fetch window) {
		close_window = window;
	}
	public void SetRepository(Repository repository) {
		repo = repository;
	}
	public void Init() {
		//让comboBox显示出来
		SSH ssh = new SSH();
		String repo_name = repo.getDirectory().toString().split("\\\\")[repo.getDirectory().toString().split("\\\\").length-2];
		branch = ssh.GetBranch(login.username, repo_name);
		comboBox.setModel(new DefaultComboBoxModel(branch));
	}

	/**
	 * Create the frame.
	 */
	public Fetch() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setBounds(164, 90, 110, 23);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("选择fetch分支:");
		lblNewLabel.setBounds(65, 94, 89, 15);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("确定");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = comboBox.getSelectedIndex();
				Git git = new Git(repo);
				try {
					git.fetch().setRefSpecs("refs/heads/"+branch.get(index)).call();
				} catch (InvalidRemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TransportException e1) {
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
		btnNewButton.setBounds(284, 90, 93, 23);
		contentPane.add(btnNewButton);
	}

}
