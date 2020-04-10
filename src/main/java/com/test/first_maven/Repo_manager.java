package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JList;
import java.awt.GridLayout;
import javax.swing.JTextPane;
import javax.swing.JInternalFrame;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JProgressBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;

public class Repo_manager extends JFrame {
	
	private Repository repo;
	private JPanel contentPane;
	private Repo_manager close_window;
	private JList list;
	private JList list_1;
	private JTextArea textArea;
	private String proname;

	public void SetCloseWindow(Repo_manager window) {
		close_window = window;
	}
	public void SetRepository(Repository repository) {
		repo = repository;
		//获取项目名称
		proname = repo.getDirectory().toString().split("\\\\")[repo.getDirectory().toString().split("\\\\").length-2];
	}
	
	public void RefreshLocal(JList jlist) {
		Git git = new Git(repo);
		try {
			Status status = git.status().call();
			Vector<String> vector = new Vector<String>();
			for(String set:status.getUntracked()) {
				vector.add(set);
			}
			for(String set:status.getMissing()) {
				vector.add(set);
			}
			for(String set:status.getModified()) {
				vector.add(set);
			}
			jlist.setListData(vector);
			//getMissing getModified
		} catch (NoWorkTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void RefreshCache(JList jlist) {
		Git git = new Git(repo);
		try {
			Status status = git.status().call();
			Vector<String> vector = new Vector<String>();
			for(String set:status.getChanged()) {
				vector.add(set);
			}
			for(String set:status.getRemoved()) {
				vector.add(set);
			}
			for(String set:status.getAdded()) {
				vector.add(set);
			}
			jlist.setListData(vector);
			//getMissing getModified
		} catch (NoWorkTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		list = new JList();
		list.setBounds(115, 36, 286, 321);
		contentPane.add(list);
		
		list_1 = new JList();
		list_1.setBounds(115, 469, 286, 250);
		contentPane.add(list_1);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(419, 10, 894, 347);
		contentPane.add(textPane);
		
		textArea = new JTextArea();
		textArea.setBounds(427, 469, 886, 250);
		contentPane.add(textArea);
		
		JButton button = new JButton("刷新");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				RefreshLocal(list);
				RefreshCache(list_1);
			}
		});
		button.setBounds(10, 10, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("同步");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Git git = new Git(repo);
				try {
					Status status = git.status().call();
					git.add().addFilepattern(".").call();
					for(String set:status.getMissing()) {
						git.rm().addFilepattern(set).call();
					}
				} catch (NoFilepatternException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (GitAPIException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_1.setBounds(10, 32, 93, 23);
		contentPane.add(button_1);
		
		JButton btnCommit = new JButton("Commit");
		btnCommit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Git git = new Git(repo);
				if(!textArea.getText().equals("")) {
					try {
						git.commit().setMessage(textArea.getText()).call();
					} catch (NoHeadException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoMessageException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnmergedPathsException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ConcurrentRefUpdateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (WrongRepositoryStateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (AbortedByHookException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (GitAPIException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					Error window = new Error("请输入commit message");
					window.SetCloseWindow(window);
					window.setVisible(true);
				}
			}
		});
		btnCommit.setBounds(10, 54, 93, 23);
		contentPane.add(btnCommit);
		
		JButton btnPush = new JButton("Push");
		btnPush.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//获取其中的分支
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
					
					//展示Push窗口
					Push window = new Push();
					window.SetCloseWindow(window);
					window.SetFromBranch(branches);
					window.SetProname(proname);
					window.SetRepo(repo);
					window.setVisible(true);
			}
		});
		btnPush.setBounds(10, 77, 93, 23);
		contentPane.add(btnPush);
		
		JLabel lbladd = new JLabel("未Add");
		lbladd.setBounds(115, 10, 286, 23);
		contentPane.add(lbladd);
		
		JLabel lblcommit = new JLabel("未Commit");
		lblcommit.setBounds(115, 444, 286, 23);
		contentPane.add(lblcommit);
		
		JLabel lblCommitMessage = new JLabel("Commit Message");
		lblCommitMessage.setBounds(427, 448, 433, 15);
		contentPane.add(lblCommitMessage);
		
		JButton btnNewButton = new JButton("分支");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//界面跳转
				Branch_Create window = new Branch_Create();
				window.SetCloseWindow(window);
				window.SetRepoName(repo);
				window.setVisible(true);
			}
		});
		btnNewButton.setBounds(10, 99, 93, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("删除分支");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//弹出新窗口
				Branch_Delete window = new Branch_Delete();
				window.SetRepository(repo);
				window.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(10, 120, 93, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Fetch");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//窗口跳转
				Fetch window = new Fetch();
				window.SetRepository(repo);
				window.SetCloseWindow(window);
				window.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(10, 166, 93, 23);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("切换分支");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Branch_CheckOut window = new Branch_CheckOut();
				window.SetCloseWindow(window);
				window.SetRepository(repo);
				window.Init();
				window.setVisible(true);
			}
		});
		btnNewButton_3.setBounds(10, 143, 93, 23);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("合并分支");
		btnNewButton_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Merge window = new Merge();
				window.SetCloseWindow(window);
				window.SetRepository(repo);
				
				window.Init();
				window.setVisible(true);
			}
		});
		btnNewButton_4.setBounds(10, 190, 93, 23);
		contentPane.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("历史版本");
		btnNewButton_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Log window = new Log(login.username,proname,repo);
				window.SetCloseWindow(window);
				
				close_window.dispose();
				window.setVisible(true);
			}
		});
		btnNewButton_5.setBounds(10, 213, 93, 23);
		contentPane.add(btnNewButton_5);
				
	}
}
