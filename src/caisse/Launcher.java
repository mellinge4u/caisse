package caisse;

import java.text.ParseException;
import java.util.Date;

import caisse.file.ReadFile;
import caisse.user.User;
import caisse.view.MainView;

public class Launcher {

	public static void main(String[] args) {
		Model model = new Model();

		ReadFile.readAll(model);
		
		model.addReadUser(0, "...", "...", true, new Date(), "", "", "", "", "", "", false);
		model.addReadUser(-1, "CENS", "", true, new Date(), "", "", "", "", "", "", false);

		new MainView(model);
		model.update();
	}

}
