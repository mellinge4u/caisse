package caisse;

import java.util.Date;

import caisse.file.ReadFile;
import caisse.view.MainView;

public class Launcher {

	public static void main(String[] args) {
		Model model = new Model();

		model.addReadUser(0, "...", "...", true, new Date(), "", "", "", "", "", "", false);
		model.addReadUser(-1, "CENS", "", true, new Date(), "", "", "", "", "", "", false);
		
		ReadFile.readAll(model);

		new MainView(model);
		model.update();
	}

}
