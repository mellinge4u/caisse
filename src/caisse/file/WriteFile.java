package caisse.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import caisse.Model;

public class WriteFile {

	public static void writeFile(String fileName, String data) {
		try {
			new File("caisse_BDD").mkdir();
			File file = new File(Model.repository + "/" + fileName + "." + Model.extension);
			file.createNewFile();
			java.io.FileOutputStream fileFlux = new java.io.FileOutputStream(file);
			java.io.FileWriter fw = new FileWriter(file);
			fw.write(data);
			fileFlux.close();
			fw.close();
		} catch (IOException t) {
			t.printStackTrace(System.err);
		}
	}

	public static void addFile(String fileName, String data) {
		File file = new File(Model.repository + "/" + fileName + "." + Model.extension);
		try {
			if (!file.exists()) {
				new File("caisse_BDD").mkdir();
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(data);
			bw.close();
		} catch (IOException t) {
			t.printStackTrace(System.err);
		}
	}

}
