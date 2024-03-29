package ris.common.valueobjects;

import java.io.Serializable;

public class MissionGegner extends Mission implements Serializable {
	private Player gegenspieler;

	public MissionGegner(Player gegenspieler) {
		super("loesche " + gegenspieler.getName() + " (" + gegenspieler.getFarbe() + ")" + " aus!");
		this.gegenspieler = gegenspieler;
	}

	// um zu gucken ob man nicht sich selbst als Gegner hat
	public Player getGegner() {
		return gegenspieler;
	}

	public boolean missionComplete(Player aktiverSpieler) {
		if (gegenspieler.isDead())
			return true;
		else
			return false;
	}
}