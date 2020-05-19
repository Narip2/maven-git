package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
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

import Demo.Test;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class download_path extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private static download_path close_window;
	private String download_project;
	private String download_user;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					download_path frame = new download_path();
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
	
	public void SetCloseWindow(download_path window) {
		close_window = window;
	}
	public void SetDownloadProject(String project) {
		download_project = project;
	}
	public void SetDownloadUser(String user) {
		download_user = user;
	}
	
	
	public download_path() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("输入文件路径：");
		label.setBounds(51, 93, 84, 15);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(145, 90, 142, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton button = new JButton("浏览");
		final JFileChooser jfile = new JFileChooser("");
		//设置模式变成既可以选择文件夹也可以选择单个文件
		jfile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int index = jfile.showSaveDialog(null);
				//表示无文件选取或正常选取文件
				if(index == jfile.APPROVE_OPTION) {
					File file = jfile.getSelectedFile();
					textField.setText(file.getAbsolutePath());
				}
//				else if(index == jfile.ERROR_OPTION) {//表示发生错误或对话框已被解除而退出对话框
//				}
			}
		});
		button.setBounds(297, 89, 64, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("确认");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
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
			        CloneCommand cloneCommand = Git.cloneRepository();
			        cloneCommand.setURI("root@39.97.255.250:/root/"+download_user+"/"+download_project);
					cloneCommand.setDirectory(new File(textField.getText()+"/"+download_project));
				    cloneCommand.setTransportConfigCallback(new TransportConfigCallback() {
				            @Override
				            public void configure(Transport transport) {
				                SshTransport sshTransport = (SshTransport) transport;
				                sshTransport.setSshSessionFactory(sshSessionFactory);
				            }
				        });
				        cloneCommand.call();
				        System.out.println("clone success");
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
				//下载完毕之后直接关闭本窗口
				close_window.dispose();
			}
		});
		button_1.setBounds(169, 176, 70, 23);
		contentPane.add(button_1);
	}
}
