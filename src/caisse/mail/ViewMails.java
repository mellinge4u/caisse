package caisse.mail;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import caisse.Model;
import caisse.tools.CellRender;

public class ViewMails extends JPanel implements Observer {

	protected Model model;
	protected JTextArea mails;
	
	public ViewMails(final Model model, final JFrame parent) {
		this.model = model;
		mails = new JTextArea(model.getMailList());
		mails.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(mails);
		
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		
		model.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		mails.setText(model.getMailList());
	}

}
