import java.util.ArrayList;

import ris.local.valueobjects.Land;
import ris.local.valueobjects.Player;

public class backup {
	public void angriffAuswerten(ArrayList<Integer> dice,Land def,Land att) {
		Player attacker= isOwner(att);
		int defNew= dice.get(0);
		if(def.getEinheiten()>dice.get(0)) {
			def.setEinheiten(defNew);
		}
		else {
			def.setFarbe(attacker.getFarbe());
		}
		att.setEinheiten(dice.get(1));
	}
	
	
	
	public Player isOwner(Land attacker) {
		for(Player p: playerMg.getPlayers())
				if (p.getBesitz().contains(attacker)) {
					return p;
				}
	}
}
