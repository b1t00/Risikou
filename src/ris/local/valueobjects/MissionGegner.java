package ris.local.valueobjects;

public class MissionGegner extends Mission
{
	private Player gegenspieler;
	
	public MissionGegner(String missionstext, Player gegenspieler){
		super(missionstext);
		this.gegenspieler = gegenspieler;		
	}
	
	//um zu gucken ob man nicht sich selbst als Gegner hat
	public Player getGegner()
	{
		return gegenspieler;
	}

	public boolean missionComplete(Player aktiverSpieler){
		if(gegenspieler.isDead())
			return true;
		else 
			return false;
	}
}