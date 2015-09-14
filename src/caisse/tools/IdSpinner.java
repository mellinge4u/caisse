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

public class IdSpinner extends JSpinner {

	public IdSpinner() {
		super(new SpinnerNumberModel(0, 0, null, 1));
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

	@Override
	public Object getValue() {
		int id = (int) super.getValue();
		
		if (id > 0 && id < 10000) {
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH);
			int add = cal.get(Calendar.YEAR) - 2000;
			if (month < 8) {
				add--;
			}
			add *= 10000;
			id += add;
		}
		
		return id;
	}
	
}
