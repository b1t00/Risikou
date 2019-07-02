package ris.common.interfaces;

import ris.common.valueobjects.Attack;

// methoden, auf die der client reagiert
public interface ServerListener {
	
	public void handleEvent(String e);
	public void schickeObjekt(Attack aO);
	public void schickeReinesObject(Object o);
	public int getListenerNr();
}
