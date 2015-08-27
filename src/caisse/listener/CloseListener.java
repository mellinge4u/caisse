package caisse.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class CloseListener implements ActionListener {

	protected JFrame frame;
	
	public CloseListener(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame.dispose();
	}

}
