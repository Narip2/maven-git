package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Merge extends JFrame {

	private JPanel contentPane;
	private Merge close_window;
	private Repository repo;
	private int flag = 0;
	private Vector<String> branch = new Vector<String>();
	
	private JLabel lblNewLabel;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	
	public void SetCloseWindow(Merge window) {
		close_window = window;
	}
	public void SetRepository(Repository repository) {
		repo = repository;
	}
	//初始化
	public void Init() {
		try {
			//设置label显示内容
			lblNewLabel.setText("当前分支:"+repo.getBranch());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Create the frame.
	 */
	public Merge() {
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
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(104, 232, 604, 447);
		contentPane.add(textArea);
		
		lblNewLabel = new JLabel();
		lblNewLabel.setBounds(104, 173, 168, 30);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Merge分支:");
		lblNewLabel_1.setBounds(355, 173, 72, 30);
		contentPane.add(lblNewLabel_1);
		
		
		comboBox = new JComboBox(new String[]{"origin","local"});
		comboBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				
				//先清理branch 以免多次重复操作使得其中有很多累计变量
				branch.clear();
				
				flag = comboBox.getSelectedIndex();
				if(flag == 0) {
					//显示origin 分支
					//让comboBox显示出来
					SSH ssh = new SSH();
					String repo_name = repo.getDirectory().toString().split("\\\\")[repo.getDirectory().toString().split("\\\\").length-2];
					branch = ssh.GetBranch(login.username, repo_name);
					comboBox.setModel(new DefaultComboBoxModel(branch));
				}else {
					//显示local 分支
					//获取所有分支
					Git git = new Git(repo);
					String temp;
					List<Ref> call;
						try {
							call = git.branchList().call();
							for(Ref ref:call) {
								temp = ref.getName().split("/")[ref.getName().split("/").length-1];
								if(temp.equals(repo.getBranch())) {
									//已在该分支 不添加到branch vector中
								}else {
									branch.add(ref.getName().split("/")[ref.getName().split("/").length-1]);	
								}
							}
						} catch (GitAPIException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//初始化comboBox
						comboBox.setModel(new DefaultComboBoxModel(branch));
				}
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox.setBounds(438, 177, 99, 23);
		contentPane.add(comboBox);
		
		comboBox_1 = new JComboBox();
		comboBox_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(flag == 0) {
					//origin 分支
					//先fetch再merge
				}else {
					//local 分支
					//直接merge
				}
			}
		});
		comboBox_1.setBounds(558, 177, 99, 23);
		contentPane.add(comboBox_1);
		
		JButton btnNewButton = new JButton("确定");
		btnNewButton.setBounds(690, 177, 93, 23);
		contentPane.add(btnNewButton);
	}
}
