package caisse.tools;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SwingUtilities;

import caisse.Model;

public class MonetarySpinner extends JSpinner {

	public MonetarySpinner(double decal) {
		super(new MonetarySpinnerModel(decal));
		JSpinner.NumberEditor numEditor = new NumberEditor(this, "0.00");
		DecimalFormat format = numEditor.getFormat();
		Locale loc = new Locale("en");
		format.setDecimalFormatSymbols(new DecimalFormatSymbols(loc));
		this.setEditor(numEditor);
		JSpinner.DefaultEditor textEditor = (JSpinner.DefaultEditor)this.getEditor();
		JTextField textField = textEditor.getTextField();
		textField.addFocusListener( new FocusAdapter()
		{
		    public void focusGained(final FocusEvent e)
		    {
		        SwingUtilities.invokeLater(new Runnable()
		        {
		            @Override
		            public void run()
		            {
		                JTextField tf = (JTextField)e.getSource();
		                tf.selectAll();
		            }
		        });
		    }
		});
	}

}
