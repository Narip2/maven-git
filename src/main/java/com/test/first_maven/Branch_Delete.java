package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Label;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.event.PopupMenuListener;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CannotDeleteCurrentBranchException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NotMergedException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

import javax.swing.event.PopupMenuEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

public class Branch_Delete extends JFrame {

	private JPanel contentPane;
	private Branch_Delete close_window;
	private JComboBox comboBox_1;
	private JComboBox comboBox;
	private String current_branch;
	private Vector<String> branch;
	private Repository repo;
	private String repo_name;
	//flag为0表示origin flag为1表示local
	private int flag = 0;
	
	
	public void SetCloseWindow(Branch_Delete window) {
		close_window = window;
	}
	public void SetRepository(Repository repository) {
		repo = repository;
	}
	

	/**
	 * Create the frame.
	 */
	public Branch_Delete() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("选择要删除的分支:");
		lblNewLabel.setBounds(20, 104, 120, 15);
		contentPane.add(lblNewLabel);
		
		comboBox = new JComboBox();
		comboBox.setBounds(262, 100, 99, 23);
		contentPane.add(comboBox);
		
		JButton btnNewButton = new JButton("确认");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//删除分支操作
				if(flag == 0) {
					//origin 分支进行操作
					SSH ssh = new SSH();
					ssh.exec("cd /root/"+login.username+"/"+repo_name+" &&git branch -D "+branch.get(comboBox.getSelectedIndex()));
				}else {
					//local分支进行操作
					Git git = new Git(repo);
					try {
						git.branchDelete().setBranchNames(branch.get(comboBox.getSelectedIndex())).call();
					} catch (NotMergedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (CannotDeleteCurrentBranchException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (GitAPIException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				//关闭窗口
				close_window.dispose();
			}
		});
		btnNewButton.setBounds(151, 174, 93, 23);
		contentPane.add(btnNewButton);
		
		comboBox_1 = new JComboBox(new String[] {"origin","local"});
		comboBox_1.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				flag = comboBox_1.getSelectedIndex();
				if(flag == 0) {
					//对origin 分支进行删除操作
					//获取项目名称
					repo_name = repo.getDirectory().toString().split("\\\\")[repo.getDirectory().toString().split("\\\\").length-2];
					//在comboBox显示origin 分支
					SSH ssh = new SSH();
					branch = ssh.GetBranch(login.username, repo_name);
					comboBox.setModel(new DefaultComboBoxModel(branch));
				}else {
					//对local分支进行操作
					//在comboBox显示local分支
					Vector<String> branches = new Vector<String>();
					Git git = new Git(repo);
					List<Ref> call;
					Connection connect;
						try {
							call = git.branchList().call();
							for(Ref ref:call) {
								branches.add(ref.getName().split("/")[ref.getName().split("/").length-1]);
							}
						} catch (GitAPIException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					comboBox.setModel(new DefaultComboBoxModel(branches));
					branch = branches;
				}
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox_1.setBounds(134, 100, 99, 23);
		contentPane.add(comboBox_1);
		
		JLabel lblNewLabel_1 = new JLabel("/");
		lblNewLabel_1.setBounds(243, 100, 15, 23);
		contentPane.add(lblNewLabel_1);
	}
}
