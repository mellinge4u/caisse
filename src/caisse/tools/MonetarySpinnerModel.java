package caisse.tools;

import javax.swing.SpinnerNumberModel;

public class MonetarySpinnerModel extends SpinnerNumberModel {

	public MonetarySpinnerModel() {
		super(0.00, 0.00, null, 0.01);
	}
	
}
