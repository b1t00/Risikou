package ris.common.interfaces;

import java.awt.Color;
import java.util.ArrayList;

import ris.common.valueobjects.Attack;
import ris.common.valueobjects.Land;
import ris.common.valueobjects.Player;
import ris.common.valueobjects.Risikokarte;
import ris.common.valueobjects.State;

public interface RisikoInterface {
	
	public State getCurrentState ();
	
	public boolean kannAngreifen(Player play);
	
	public Player gibAktivenPlayer();
	
	public void setNextState();
	
	public boolean kannVerschieben(Player play);
	
	public void setNextPlayer();
	
	public int errechneVerfuegbareEinheiten(Player play);
	
	public void setLandClickZeit(boolean obLandClickbar);
	
	public void setTauschZeit(boolean obTauschbar);
	
	public boolean zieheEinheitenkarte(Player playerHatGezogen);
	
	public void moveUnits(Land von, Land zu, int units);
	
	public Attack attack(Land angriff, Land defence, int unitsAngriff, int unitsDefend);
	
	public Player getGewinner();
	
	public boolean moveUnitsGueltig(Land von, Land zu, int units);
	
	public Player playerAnlegen(String name, String farbe, int iD);
	
	public void setColorArray(Color color); //TODO: kann wahrscheinlich auch in PlayerAnlegen
	
	public void verteileEinheiten();
	
	public void verteileMissionen();
	
	public void setzeAktivenPlayer();
	
	public ArrayList<Player> getPlayerArray();
	
	public ArrayList<Risikokarte> getRisikoKarten();
	
	public Land getLandById(int landId);
	
	public boolean allMissionsComplete();
	
	public boolean rundeMissionComplete(Player play);
	
	public void spielSpeichern(String name);
	
	public void spielLaden(String name);
	
	
	
}
