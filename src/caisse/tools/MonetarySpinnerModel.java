package caisse.tools;

import javax.swing.SpinnerNumberModel;

public class MonetarySpinnerModel extends SpinnerNumberModel {

	public MonetarySpinnerModel() {
		super(1.0, 0.0, null, 0.5);
	}
	
}
