package caisse.view.historic;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import caisse.Model;

public class HistoricView extends JPanel implements Observer {

	protected Model model;

	public HistoricView(Model model) {
		this.model = model;
		model.addObserver(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
