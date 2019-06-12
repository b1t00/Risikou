package ris.client.net;

import java.awt.Color;
import java.util.ArrayList;

import ris.common.interfaces.RisikoInterface;
import ris.common.valueobjects.Attack;
import ris.common.valueobjects.Land;
import ris.common.valueobjects.Player;
import ris.common.valueobjects.Risikokarte;
import ris.common.valueobjects.State;

public class RisikoFassade implements RisikoInterface {

	@Override
	public State getCurrentState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean kannAngreifen(Player play) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Player gibAktivenPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNextState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean kannVerschieben(Player play) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setNextPlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int errechneVerfuegbareEinheiten(Player play) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLandClickZeit(boolean obLandClickbar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTauschZeit(boolean obTauschbar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean zieheEinheitenkarte(Player playerHatGezogen) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void moveUnits(Land von, Land zu, int units) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Attack attack(Land angriff, Land defence, int unitsAngriff, int unitsDefend) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getGewinner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean moveUnitsGueltig(Land von, Land zu, int units) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Player playerAnlegen(String name, String farbe, int iD) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColorArray(Color color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void verteileEinheiten() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void verteileMissionen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setzeAktivenPlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Player> getPlayerArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Risikokarte> getRisikoKarten() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Land getLandById(int landId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean allMissionsComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rundeMissionComplete(Player play) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void spielSpeichern(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spielLaden(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getLandClickZeit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getTauschZeit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean attackLandGueltig(Land attacker) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean defenseLandGueltig(Land attacker, Land defender) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveFromLandGueltig(Land von) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveToLandGueltig(Land von, Land zu) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Color> getColorArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getFarbauswahl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setFarbeAuswaehlen(String farbe) {
		// TODO Auto-generated method stub
		return null;
	}

}
