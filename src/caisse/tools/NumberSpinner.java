package caisse.tools;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import caisse.Model;

public class NumberSpinner extends JSpinner {

	public NumberSpinner(int start, int min, int max) {
		super(new SpinnerNumberModel(start, min, max, 1));
		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor)this.getEditor();
		JTextField textField = editor.getTextField();
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
