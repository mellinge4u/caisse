package caisse.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {

	private static String path = "caisse_BDD/";

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
