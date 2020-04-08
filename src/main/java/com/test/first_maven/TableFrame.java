package com.test.first_maven;



import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;




public class TableFrame extends JFrame{
	JTable t;
	public static void main(String[] args) {
		TableFrame tf = new TableFrame();
	}
	public TableFrame(){
		setBounds(100,100, 200, 200);
		JPanel pane = new JPanel();	
		
		 DefaultTableModel dm = new DefaultTableModel();
		    dm.setDataVector(new Object[][]{{"Button 1","foo"},
		                                    {"Button 2","bar"}},
		                     new Object[]{"Button","String"});
		                     
		    t = new JTable(dm);

		
		

		t.getColumn("Button").setCellRenderer(new ButtonRenderer());
		t.getColumn("Button").setCellEditor(new ButtonEditor(new JTextField("xhh")));
	
		pane.setLayout(new FlowLayout());
		pane.add(t);
		this.add(pane);
		setVisible(true);
	}
	
}
 class ButtonEditor extends DefaultCellEditor {
	  protected JButton button;
	  private String    label;
	  private boolean   isPushed;

	  public ButtonEditor(JTextField checkBox) {
	    super(checkBox);
	    this.setClickCountToStart(1);
	    button = new JButton();
	    button.setOpaque(true);
	    button.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        fireEditingStopped();
	      }
	    });
	    
	  }

	  public Component getTableCellEditorComponent(JTable table, Object value,
	                   boolean isSelected, int row, int column) {
	    if (isSelected) {
	      button.setForeground(table.getSelectionForeground());
	      button.setBackground(table.getSelectionBackground());
	    } else{
	      button.setForeground(table.getForeground());
	      button.setBackground(table.getBackground());
	    }
	    label = (value ==null) ? "" : value.toString();
	    button.setText( label );
	    button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("我是button的事件 ");
				
			}
		});
	    isPushed = true;
	    return button;
	  }
	 
	  public Object getCellEditorValue() {
	    if (isPushed)  {
	      // 
	      // 
	      JOptionPane.showMessageDialog(button ,label + ": Ouch!");
	      // System.out.println(label + ": Ouch!");
	    }
	    isPushed = false;
	    return new String( label ) ;
	  }
	  
	  public boolean stopCellEditing() {
	    isPushed = false;
	    return super.stopCellEditing();
	  }

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		System.out.println(1);
		return super.shouldSelectCell(anEvent);
	}
	  
	 
	}
 class ButtonRenderer extends JButton implements TableCellRenderer {

	  public ButtonRenderer() {
	    setOpaque(true);
	  }
	  
	  public Component getTableCellRendererComponent(JTable table, Object value,
	                   boolean isSelected, boolean hasFocus, int row, int column) {
	    if (isSelected) {
	      setForeground(table.getSelectionForeground());
	      setBackground(table.getSelectionBackground());
	    } else{
	      setForeground(table.getForeground());
	      setBackground(UIManager.getColor("UIManager"));
	    }
	    setText( (value ==null) ? "" : value.toString() );
	    return this;
	  }
	}