package ris.common.interfaces;

import java.awt.Color;
import java.util.ArrayList;

import ris.common.exceptions.LandExistiertNichtException;
import ris.common.exceptions.LandInBesitzException;
import ris.common.exceptions.LandNichtInBesitzException;
import ris.common.exceptions.SpielerNameExistiertBereitsException;
import ris.common.exceptions.UngueltigeAnzahlEinheitenException;
import ris.common.exceptions.UngueltigeAnzahlSpielerException;
import ris.common.valueobjects.Attack;
import ris.common.valueobjects.GameObject;
import ris.common.valueobjects.Kontinent;
import ris.common.valueobjects.Land;
import ris.common.valueobjects.Player;
import ris.common.valueobjects.Risikokarte;
import ris.common.valueobjects.State;

public interface RisikoInterface {

	public State getCurrentState();

	public boolean kannAngreifen();

	public Player gibAktivenPlayer();

	public void setNextState();

	public boolean kannVerschieben(Player play);

	public void setNextPlayer();

	public int errechneVerfuegbareEinheiten();

	public boolean getLandClickZeit();

	public void setLandClickZeit(boolean obLandClickbar);

	public boolean getTauschZeit();

	public void setTauschZeit(boolean obTauschbar);

	public boolean zieheEinheitenkarte();

	public void moveUnits(Land von, Land zu, int units)
			throws LandExistiertNichtException, UngueltigeAnzahlEinheitenException;

	public boolean attackLandGueltig(Land attacker);

	public boolean defenseLandGueltig(Land attacker, Land defender);

	public void attackStart(Land attLand, Land defLand, int attUnits)
			throws LandNichtInBesitzException, LandInBesitzException;

	public int getDefLandUnits();

	public Attack attackFinal(int defUnits);

	public Player getGewinner();

	public boolean moveFromLandGueltig(Land von);

	public boolean moveToLandGueltig(Land von, Land zu);

	public boolean moveUnitsGueltig(Land von, Land zu, int units);

	public void playerAnlegen(String name, String farbe, int iD) throws SpielerNameExistiertBereitsException;

	public ArrayList<Color> getColorArray();

	public ArrayList<String> getFarbauswahl();

	public void setFarbeAuswaehlen(String farbe);

	public ArrayList<Player> getPlayerArray();

	public ArrayList<Risikokarte> getRisikoKarten();

	public Land getLandById(int landId) throws LandExistiertNichtException;

	public boolean allMissionsComplete();

	public boolean rundeMissionComplete();

	public void spielSpeichern(String name);

	public String[] getSpielladeDateien();

	public void spielLaden(String name) throws SpielerNameExistiertBereitsException, LandExistiertNichtException;

	public void spielAufbau();

	public void setEinheiten(Land land, int units) throws UngueltigeAnzahlEinheitenException;

	public void setSpielerAnzahl(int spielerAnzahl) throws UngueltigeAnzahlSpielerException;

	public int getSpielerAnzahl();

	public void allUpdate(String ereignis);

	public Player getPlayerById(int iD);

	public void aksForServerListenerNr();

	public void aktiveClientAskHowMany(int sListenerNr);

	public void pressBackButn(int sListenerNr);

	public void spielEintreitenBtn(int sListenerNr);

	public void removeRisikoKarten(ArrayList<Integer> risikokartenWahl);

	public GameObject gameObjectLaden(String datei);

	public GameObject getGameDatei();

	boolean spielWurdeGeladen();

	public Object getLaender();

	public void spielLadenTrue();

	public GameObject getGeladenesSpiel();

	public int wieVieleSpielerImGame();

	public void spielerWurdeGeladen();

	public Kontinent getKontinentVonLand(Land defLand);

	public void setSpeicherButtonDisable();

	public boolean gameNotReady();

	public void spielNotReady();

	public void setSpielNotReady();

	public void setSpielReady();

}
