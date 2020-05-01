package Demo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JTree;

public class demo6 extends JFrame {

	private JPanel contentPane;
	private DefaultTreeModel tree_model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					demo6 frame = new demo6();
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
	public demo6() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DefaultMutableTreeNode parent = new DefaultMutableTreeNode("分支1");
		DefaultMutableTreeNode son1 = new DefaultMutableTreeNode("分支2");
		DefaultMutableTreeNode son2 = new DefaultMutableTreeNode("分支3");
		parent.add(son1);
		parent.add(son2);
		son2.add(son1);
		tree_model = new DefaultTreeModel(parent);
		JTree tree = new JTree(tree_model);
		tree.setBounds(76, 55, 165, 159);
		contentPane.add(tree);
	}
}
