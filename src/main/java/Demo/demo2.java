package Demo;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class demo2 extends JFrame {

	private JPanel contentPane;
	private DefaultListModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					demo2 frame = new demo2();
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
	public demo2() {
		model = new DefaultListModel();
		
		FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
		try {
			Repository repository = repositoryBuilder.setGitDir(new File("D:\\Git\\learngit\\.git"))
			        .readEnvironment() // scan environment GIT_* variables
			        .findGitDir() // scan up the file system tree
			        .setMustExist(true)
			        .build();
			Git git = new Git(repository);
			git.reset().setMode(ResetType.HARD).setRef("832cb2292f472c528a47d8f62e4f0a216295f2fb").call();
			Iterable<RevCommit> log = git.log().call();
			for(RevCommit rev:log) {
				System.out.println(rev.getName());
				model.addElement(rev.getFullMessage());
				//832cb2292f472c528a47d8f62e4f0a216295f2fb
			}
			String proname = repository.getDirectory().toString().split("\\\\")[repository.getDirectory().toString().split("\\\\").length-2];
			System.out.println(proname);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoHeadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 733, 497);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(133, 136, 453, 266);
		contentPane.add(scrollPane);
		
		
		JList list = new JList(model);
		scrollPane.setViewportView(list);
	}

}
