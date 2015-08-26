package caisse.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import caisse.Model;

public class UserView extends JPanel implements Observer{

	protected Model modlel;
	
	public UserView(Model model) {
		this.modlel = model;
		model.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
