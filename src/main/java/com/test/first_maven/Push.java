package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.event.PopupMenuListener;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;

import com.jcraft.jsch.Session;

import javax.swing.event.PopupMenuEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Push extends JFrame {

	private JPanel contentPane;
	private Push close_window;
	private Vector<String> from;
	private Vector<String> to;
	private String from_branch;
	private Repository repo;
	private String repo_name;
	
	private JComboBox comboBox;
	
	
	public void SetCloseWindow(Push window) {
		close_window = window;
	}
	public void SetFromBranch(Vector<String> f_b) {
		from = f_b;
	}
	public void SetToBranch(Vector<String> t_b) {
		to = t_b;
	}
	public void SetProname(String proname) {
		repo_name = proname;
	}
	public void SetRepo(Repository repository) {
		repo = repository;
	}
	
	//初始化一些需要数据的窗口
	public void Init() {
		comboBox.setModel(new DefaultComboBoxModel(from));
	}

	/**
	 * Create the frame.
	 */
	public Push() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				int index = comboBox.getSelectedIndex();
				from_branch = from.get(index);
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox.setBounds(70, 88, 69, 23);
		contentPane.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(286, 88, 69, 23);
		contentPane.add(comboBox_1);
		
		JLabel lblNewLabel = new JLabel("origin/");
		lblNewLabel.setBounds(232, 92, 54, 15);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("确定");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Git git = new Git(repo);
				SshSessionFactory.setInstance( new JschConfigSessionFactory() {
				    @Override
				    protected void configure( Host host, Session session ) {
				      session.setPassword( "a1b2c3d4E5" );
				    }
				} );
				try {
					//先切换分支
					git.checkout().setCreateBranch(false).setName(from_branch).call();
					//将当前分支push上去
					git.push().setRemote("root@39.97.255.250:/root/"+login.username+"/"+repo_name).call();
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
			}
		});
		btnNewButton.setBounds(155, 191, 93, 23);
		contentPane.add(btnNewButton);
	}
}
