package ris.common.interfaces;

import java.awt.Color;
import java.util.ArrayList;

import ris.common.exceptions.LandExistiertNichtException;
import ris.common.exceptions.SpielerNameExistiertBereitsException;
import ris.common.exceptions.ZuWenigEinheitenException;
import ris.common.exceptions.ZuWenigEinheitenNichtMoeglichExeption;
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
	
	public boolean getLandClickZeit();
	
	public void setLandClickZeit(boolean obLandClickbar);
	
	public boolean getTauschZeit();
	
	public void setTauschZeit(boolean obTauschbar);
	
	public boolean zieheEinheitenkarte(Player playerHatGezogen);
	
	public void moveUnits(Land von, Land zu, int units) throws ZuWenigEinheitenException, LandExistiertNichtException;
	
	public boolean attackLandGueltig(Land attacker);

	public boolean defenseLandGueltig(Land attacker, Land defender);
	
	public Attack attack(Land angriff, Land defence, int unitsAngriff, int unitsDefend) throws ZuWenigEinheitenNichtMoeglichExeption, ZuWenigEinheitenException;

	public Player getGewinner();
	
	public boolean moveFromLandGueltig(Land von);

	public boolean moveToLandGueltig(Land von, Land zu);
	
	public boolean moveUnitsGueltig(Land von, Land zu, int units);
	
	public void playerAnlegen(String name, String farbe, int iD) throws SpielerNameExistiertBereitsException;
	
	public void setColorArray(Color color); //TODO: kann wahrscheinlich auch in PlayerAnlegen
	
	public ArrayList<Color> getColorArray();
	
	public ArrayList<String> getFarbauswahl();
	
	public String setFarbeAuswaehlen(String farbe);
	
//	public void verteileEinheiten();
//	
//	public void verteileMissionen();
//	
//	public void setzeAktivenPlayer();
	
	public ArrayList<Player> getPlayerArray();
	
	public ArrayList<Risikokarte> getRisikoKarten();
	
	public Land getLandById(int landId) throws LandExistiertNichtException;
	
	public boolean allMissionsComplete();
	
	public boolean rundeMissionComplete(Player play);
	
	public void spielSpeichern(String name);
	
	public void spielLaden(String name) throws SpielerNameExistiertBereitsException, ZuWenigEinheitenException;
	
	public void spielAufbau();
	
	
}
