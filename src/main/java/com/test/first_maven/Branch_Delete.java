package com.test.first_maven;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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
	private JComboBox comboBox;
	private String current_branch;
	private Vector<String> branch;
	private Repository repo;
	
	public void SetCloseWindow(Branch_Delete window) {
		close_window = window;
	}
	public void Init() {
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
		comboBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				int index = comboBox.getSelectedIndex();
				current_branch = branch.get(index);
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox.setBounds(140, 100, 99, 23);
		contentPane.add(comboBox);
		
		JButton btnNewButton = new JButton("确认");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//删除分支操作
				Git git = new Git(repo);
				try {
					git.branchDelete().setBranchNames(current_branch).call();
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
				//关闭窗口
				close_window.dispose();
			}
		});
		btnNewButton.setBounds(249, 100, 93, 23);
		contentPane.add(btnNewButton);
	}
}
