package caisse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {

	private static String path = "caisse_BDD/";

	// TODO encore des choes à faire
	public static void writeFile(String name) {

		// TODO mettre le texte dans ce sb
		StringBuilder sb = new StringBuilder();

		try {
			// création d'un dossier pour la bdd
			new File("caisse_BDD").mkdir();

			File file = new File(name + ".txt");
			file.createNewFile();
			try {
				// ouverture du fichier à l'écriture
				java.io.FileOutputStream fileFlux = new java.io.FileOutputStream(
						file);
				java.io.FileWriter fw = new FileWriter(file);
				fw.write(sb.toString());
				try {
					// Si on ouvre à l'écriture il faut pas oublier de fermer
					// !
					fileFlux.close();
					fw.close();
				} catch (IOException t) {
					t.printStackTrace(System.err);
				}
			} catch (IOException t) {
				t.printStackTrace(System.err);
			}
		} catch (IOException t) {
			t.printStackTrace(System.err);

		}
	}

	public static void writeFile(String fileName, String data) {
		try {
			new File("caisse_BDD").mkdir();
			File file = new File(path + fileName + ".txt");
			file.createNewFile();
			try {
				java.io.FileOutputStream fileFlux = new java.io.FileOutputStream(
						file);
				java.io.FileWriter fw = new FileWriter(file);
				fw.write(data);
				try {
					fileFlux.close();
					fw.close();
				} catch (IOException t) {
					t.printStackTrace(System.err);
				}
			} catch (IOException t) {
				t.printStackTrace(System.err);
			}
		} catch (IOException t) {
			t.printStackTrace(System.err);
		}
	}

}
