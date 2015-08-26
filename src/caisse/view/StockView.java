package caisse.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import caisse.Model;

public class StockView extends JPanel implements Observer {

	protected Model model;
	
	public StockView(Model model) {
		this.model = model;
		model.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
