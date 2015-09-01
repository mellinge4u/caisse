package caisse.listener;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class CloseListener implements ActionListener {

	protected Window window;
	
	public CloseListener(Window window) {
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		window.dispose();
	}

}
