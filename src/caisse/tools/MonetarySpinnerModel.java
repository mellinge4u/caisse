package caisse.tools;

import javax.swing.SpinnerNumberModel;

public class MonetarySpinnerModel extends SpinnerNumberModel {

	public MonetarySpinnerModel(double decal) {
		super(0.00, 0.00, null, decal);
	}

}
