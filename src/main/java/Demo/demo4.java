package Demo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class demo4 extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					demo4 frame = new demo4();
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
	public demo4() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		model = new DefaultTableModel();
		model.setRowCount(0);
		model.setColumnIdentifiers(new Object[] {"","编号","姓名"});
		table = new JTable(model);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int columnIndex = table.columnAtPoint(e.getPoint());
				int rowIndex = table.rowAtPoint(e.getPoint());
				//第0列时才执行
				if(columnIndex == 0) {
					//如果是checkbox列
					if((boolean)model.getValueAt(rowIndex, 0) == false) {
						System.out.println(rowIndex+"false");
					}else {
						System.out.println(rowIndex+"true");
					}
				}else {
				}
			}
		});
		
		TableColumn tc = table.getColumnModel().getColumn(0);
		tc.setCellEditor(table.getDefaultEditor(Boolean.class));
		tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
		for(int i = 0; i < 3; i++) {
			//false未勾选 true表示勾选
			model.addRow(new Object[] {new Boolean(false),"编号"+i,"姓名"+i});
		}
		table.setBounds(74, 50, 249, 145);
		contentPane.add(table);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				model.addRow(new Object[] {new Boolean(false),"编号"+3,"姓名"+3});
			}
		});
		btnNewButton.setBounds(141, 228, 93, 23);
		contentPane.add(btnNewButton);
	}
}


