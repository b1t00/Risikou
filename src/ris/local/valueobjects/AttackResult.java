package ris.local.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;

public class AttackResult implements Serializable{
	private int lossAtt;
	private int lossDef;
	private ArrayList<Integer> dicesAtt;
	private ArrayList<Integer> dicesDef;
	
	public AttackResult(int lossAtt, int lossDef, ArrayList<Integer> dicesAtt, ArrayList<Integer> dicesDef) {
		this.lossAtt = lossAtt;
		this.lossDef = lossDef;
		this.dicesAtt = dicesAtt;
		this.dicesDef = dicesDef;
	}
	
	public int getLossAtt() {
		return lossAtt;
	}
	
	public int getLossDef() {
		return lossDef;
	}
	
	public ArrayList<Integer> getDicesAtt() {
		return dicesAtt;
	}
	
	public ArrayList<Integer> getDicesDef() {
		return dicesDef;
	}
}
