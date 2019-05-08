package ris.local.persistence;

import java.io.*;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.Serializable;

import ris.local.domain.Risiko;

public class FilePersistenceManager {
	
	public void speichern(Serializable risiko, String datei) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(datei + ".ser");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(risiko);
		}catch (IOException e) {}
		try {
			oos.close();
			fos.close();
		} catch (IOException e) {}
	}
		
	public Risiko laden(String datei) {
		Risiko risiko = null;
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(datei + ".ser"))){
			risiko = (Risiko) ois.readObject();
			ois.close();
		} catch (IOException e) {
			System.err.println("fehler");
		} catch (ClassNotFoundException e) {
			System.err.println("Fehler!");
		} 
		return risiko;
	}
}
