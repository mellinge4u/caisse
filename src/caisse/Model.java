package caisse;

import java.util.Observable;

public class Model extends Observable {

	public Model() {
		// TODO Auto-generated constructor stub
	}

	private void update() {
		setChanged();
		notifyObservers();
	}
	
}
