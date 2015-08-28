package caisse.tools;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

import caisse.Model;

public class MonetarySpinner extends JSpinner {

	public MonetarySpinner() {
		super(new MonetarySpinnerModel());
		JSpinner.NumberEditor editor = new NumberEditor(this, "0.00");
		DecimalFormat format = editor.getFormat();
		Locale loc = new Locale("en");
		format.setDecimalFormatSymbols(new DecimalFormatSymbols(loc));
		this.setEditor(editor);
	}

}
