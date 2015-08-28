package caisse.tools;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

import caisse.Model;

public class CurrencySpinner extends JSpinner {

	public CurrencySpinner(Model model) {
		super(new MonetarySpinnerModel());
		JSpinner.NumberEditor editor = new NumberEditor(this, "0.00");
		this.setEditor(editor);
	}

}
