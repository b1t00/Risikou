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

import ris.local.valueobjects.GameObject;

public class FilePersistenceManager {
	
	public void speichern(Serializable gameObject, String datei) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(datei + ".ser");
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
				new FileInputStream(datei + ".ser"))){
			game = (GameObject) ois.readObject();
			ois.close();
		} catch (IOException e) {
			System.err.println("fehler");
		} catch (ClassNotFoundException e) {
			System.err.println("Fehler!");
		} 
		return game;
	}
}
