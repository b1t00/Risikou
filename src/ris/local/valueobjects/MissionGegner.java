package ris.local.valueobjects;

public class MissionGegner extends MissionsVorlage
{
	private Player gegenspieler;

	//um zu gucken ob man nicht sich selbst als Gegner hat
	public Player getGegner()
	{
		return gegenspieler;
	}
	
	public MissionGegner(String missionstext, Player gegenspieler){
		super(missionstext);
		this.gegenspieler = gegenspieler;		
	}
	
	
	public boolean missionComplete(Player aktiverSpieler){
		if(gegenspieler.isDead())
			return true;
		else 
			return false;
	}
}