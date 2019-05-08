package ris.local.valueobjects;

public class MissionTest extends Mission {

	private Land l;
	
	public MissionTest(Land l) {
		super("Erobern Sie das Land " + l.getName() + ".");
		this.l = l;
	}
	
	@Override
	public boolean missionComplete(Player aktiverSpieler) {
		// TODO Auto-generated method stub
		return false;
	}

}
