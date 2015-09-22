package caisse;

import java.text.ParseException;
import java.util.Date;

import caisse.file.ReadFile;
import caisse.view.MainView;

public class Launcher {

	public static void main(String[] args) {
		Model model = new Model();

		model.addReadUser(0, "...", "...", true, new Date(), "...", "...", "...", "...", "...", "...", false);
		try {
			model.addReadUser(-1, "asso", "CENS", false, Model.dateFormatSimple.parse("01/09/2004"), "00", "...", "Fac de science", "54500", "Vandoeuvre", "...", false);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ReadFile.readAll(model);

		new MainView(model);
		model.update();
	}

}
