package ris.local.persistence;

//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import ris.common.valueobjects.GameObject;

public class FilePersistenceManager implements Serializable {
	
	public void speichern(Serializable gameObject, String datei) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			//System.getProperty für die Angabe der Pfade in verschiedenen Betriebssystemen (\\ bzw /)
			fos = new FileOutputStream("files" + System.getProperty("file.separator") + datei + ".ser");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(gameObject);
		}catch (IOException e) {}
		try {
			oos.close();
			fos.close();
		} catch (IOException e) {}
	}
		
	public GameObject laden(String datei) {
		GameObject game = null;
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("files" + System.getProperty("file.separator") + datei))){
			game = (GameObject) ois.readObject();
			ois.close();
			System.out.println("datei ist geladen");
		} catch (IOException e) {
			System.err.println("fehler");
		} catch (ClassNotFoundException e) {
			System.err.println("Fehler!");
		} 
		return game;
	}
}
