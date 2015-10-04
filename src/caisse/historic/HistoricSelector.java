package caisse.historic;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.toedter.calendar.JDateChooser;

public class HistoricSelector extends JPanel {

	private IHistoricTableModel tableModel;
	private JComboBox<String> choice;
	private JDateChooser dateView;
	
	public HistoricSelector(IHistoricTableModel tableModel) {
		this.tableModel = tableModel;
		choice = new JComboBox<String>();
		dateView = new JDateChooser(new Date());
		choice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateTable();
			}
		});
		choice.addItem("Jour");
		choice.addItem("Semaine");
		choice.addItem("Mois");
		choice.addItem("Année");
		choice.addItem("Tout");
		dateView.setPreferredSize(new Dimension(150, 25));
		dateView.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				updateTable();
			}
		});
		
		this.add(new JLabel("Visualiser un(e) "));
		this.add(choice);
		this.add(new JLabel(" à partir du "));
		this.add(dateView);
	}
	
	private void updateTable() {
		tableModel.setDisplay(getDisplayDay(), getStartDate());
	}
	
	private int getDisplayDay() {
		switch (choice.getSelectedIndex()) {
		case 0:
			return 1;
		case 1:
			return 7;
		case 2:
			return 30;
		case 3:
			return 365;
		default:
			return Integer.MAX_VALUE;
		}
	}
	
	private Date getStartDate() {
		return dateView.getDate();
	}
	
}
