package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.util.FS;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import Demo.Test;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		String repo_name = repo.getDirectory().toString().split("\\\\")[repo.getDirectory().toString().split("\\\\").length-2];
		branch = login.ssh.GetBranch(login.username, repo_name);
		comboBox.setModel(new DefaultComboBoxModel(branch));
	}

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fetch frame = new Fetch();
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
	public Fetch() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
				final SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
		            @Override
		            protected void configure(OpenSshConfig.Host host, Session session) {
		            	//第一次访问服务器不用输入yes
		                session.setConfig("StrictHostKeyChecking", "no");
		            }
		            @Override
		            protected JSch getJSch(final OpenSshConfig.Host hc, FS fs) throws JSchException {

		                JSch jsch = super.getJSch(hc, fs);
		                jsch.removeAllIdentity();
		            	try {
							Statement stmt = login.connect.createStatement();
							ResultSet rs = stmt.executeQuery("select * from rsa where mac = \'"+login.Mac+"\'");
							if(rs.next()) {
								jsch.addIdentity(rs.getString("path"));
							}else {
								System.out.println("本机没有key上传");
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		                return jsch;
		            }
		            
		        };
				
				int index = comboBox.getSelectedIndex();
				Git git = new Git(repo);
				try {
					git.fetch().setRefSpecs("refs/heads/"+branch.get(index)).setTransportConfigCallback(new TransportConfigCallback() {

						@Override
						public void configure(Transport transport) {
							// TODO Auto-generated method stub
							SshTransport sshTransport = (SshTransport) transport;
			                sshTransport.setSshSessionFactory(sshSessionFactory);
						}}).call();
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
