package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Branch_CheckOut extends JFrame {

	private JPanel contentPane;
	private Branch_CheckOut close_window;
	private Repository repo;
	private JLabel lblNewLabel;
	private JComboBox comboBox;
	private Vector<String> branches = new Vector<String>();
	private String Choose_Branch;
	
	public void SetCloseWindow(Branch_CheckOut window) {
		close_window = window;
	}
	public void Init() {
		Git git = new Git(repo);
		try {
			lblNewLabel.setText("当前分支:"+repo.getBranch());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取所有分支
		List<Ref> call;
			try {
				call = git.branchList().call();
				for(Ref ref:call) {
					branches.add(ref.getName().split("/")[ref.getName().split("/").length-1]);
				}
			} catch (GitAPIException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//初始化comboBox
			comboBox.setModel(new DefaultComboBoxModel(branches));
			//初始化变量
			Choose_Branch = branches.get(0);
	}
	public void SetRepository(Repository repository) {
		repo = repository;
	}

	/**
	 * Create the frame.
	 */
	public Branch_CheckOut() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel();
		lblNewLabel.setBounds(68, 46, 192, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("切换到分支:");
		lblNewLabel_1.setBounds(68, 89, 88, 15);
		contentPane.add(lblNewLabel_1);
		
		comboBox = new JComboBox();
		comboBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				int index = comboBox.getSelectedIndex();
				Choose_Branch = branches.get(index);
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox.setBounds(166, 85, 94, 23);
		contentPane.add(comboBox);
		
		JButton btnNewButton = new JButton("确定");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//切换分支
				Git git = new Git(repo);
				try {
					git.checkout().setName(Choose_Branch).setCreateBranch(false).call();
				} catch (RefAlreadyExistsException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RefNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvalidRefNameException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (CheckoutConflictException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (GitAPIException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				close_window.dispose();
			}
		});
		btnNewButton.setBounds(291, 85, 93, 23);
		contentPane.add(btnNewButton);
	}

}
