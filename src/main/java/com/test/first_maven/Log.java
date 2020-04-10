package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;

import com.jcraft.jsch.Session;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Log extends JFrame {

	private JPanel contentPane;
	private Log close_window;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private MouseEvent mevent = null;
	
	public void SetCloseWindow(Log window) {
		close_window = window;
	}

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public Log(String prouser,final String proname,final Repository repo) {
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
		
		
		//显示table
		model = new DefaultTableModel();
		//每次重复使用model
		model.setRowCount(0);
		model.setColumnIdentifiers(new Object[] {"Message","SHA1"});
		Git git = new Git(repo);
		try {
			Iterable<RevCommit> log = git.log().call();
			for(RevCommit rev:log) {
				model.addRow(new String[] {rev.getFullMessage(),rev.getName()});//message,sha-1
			}
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mevent = e;
			}
		});
		scrollPane.setBounds(138, 190, 620, 454);
		contentPane.add(scrollPane);
		
		table = new JTable(model);
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("确认回到该版本");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//获取上次点击table的地方
				if(mevent == null) {
					System.out.println("error 没有点击");
				}else {
					int rowIndex = table.rowAtPoint(mevent.getPoint());
					//获取要回退版本的SHA-1
					String SHA1 = (String) table.getValueAt(rowIndex, 1);
					String branch;
					Git git = new Git(repo);
					try {
						branch = repo.getBranch();
						//相当于 git reset --hard <sha-1>
						git.reset().setMode(ResetType.HARD).setRef(SHA1).call();
					} catch (GitAPIException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					//然后强制传输到远程服务器上 用于更新
					//git push -f origin branch
					SshSessionFactory.setInstance( new JschConfigSessionFactory() {
					    @Override
					    protected void configure( Host host, Session session ) {
					      session.setPassword( "a1b2c3d4E5" );
					    }
					} );
					try {
						//将当前分支push上去
						git.push().setForce(true).setRemote("root@39.97.255.250:/root/"+login.username+"/"+proname).call();
					} catch (GitAPIException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
		btnNewButton.setBounds(138, 674, 126, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("浏览版本");
		btnNewButton_1.setBounds(665, 674, 93, 23);
		contentPane.add(btnNewButton_1);
	}
}
