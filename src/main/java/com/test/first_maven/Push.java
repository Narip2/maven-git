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
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.util.FS;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import javax.swing.event.PopupMenuEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Push extends JFrame {

	private JPanel contentPane;
	private Push close_window;
	private Vector<String> from;
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
	public void SetProname(String proname) {
		repo_name = proname;
	}
	public void SetRepo(Repository repository) {
		repo = repository;
	}
	
	//初始化一些需要数据的窗口
	public void Init() {
		comboBox.setModel(new DefaultComboBoxModel(from));
		//默认第一个
		from_branch = from.get(0);
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
		comboBox.setBounds(149, 88, 99, 23);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Push分支:");
		lblNewLabel.setBounds(80, 92, 59, 15);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("确定");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Git git = new Git(repo);
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
				try {
					//先切换分支
					git.checkout().setCreateBranch(false).setName(from_branch).call();
					//将当前分支push上去
					//其实push只能是push当前分支到同样的分支上去，不能Push到另外不同的分支上面去
					git.push().setRemote("root@39.97.255.250:/root/"+login.username+"/"+repo_name).setTransportConfigCallback(new TransportConfigCallback() {
				            @Override
				            public void configure(Transport transport) {
				                SshTransport sshTransport = (SshTransport) transport;
				                sshTransport.setSshSessionFactory(sshSessionFactory);
				            }
				        }).call();
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
				close_window.dispose();
			}
		});
		btnNewButton.setBounds(155, 191, 93, 23);
		contentPane.add(btnNewButton);
	}
}
