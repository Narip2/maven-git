package Demo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.test.first_maven.SSH;

import javax.swing.JTree;

public class demo6 extends JFrame {

	private JPanel contentPane;
	private DefaultTreeModel tree_model;
	private DefaultMutableTreeNode Node;

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
		setBounds(100, 100, 758, 511);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		SSH ssh = new SSH();
		ssh.SetProName("demo");
		ssh.SetUserName("narip2");
		Node = ssh.GetAllFiles("demo", "master");
		tree_model = new DefaultTreeModel(Node);
		JTree tree = new JTree(tree_model);
		tree.setBounds(106, 141, 294, 220);
		contentPane.add(tree);
	}

}
