package caisse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {

	//TODO encore des choes à faire
	public static void writeFile(String name){
		
		
		//TODO mettre le texte dans ce sb
		StringBuilder sb = new StringBuilder();
		
		try {
			//création d'un dossier pour la bdd
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
					// Si on ouvre à l'écriture il faut pas oublier de fermer !
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
