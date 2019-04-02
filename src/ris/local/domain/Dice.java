package ris.local.domain;

import java.util.ArrayList;
import java.util.Collections;

public class Dice {

	public static void main(String[] args) {
		Dice dice=new Dice();
		dice.rollDice(3, 3);
	}
	public void rollDice(int attUnits,int defUnits) {
		int lossDef=0;
		int lossAtt=0;
		ArrayList<Integer> aList= new ArrayList<Integer>();
		ArrayList<Integer> dList= new ArrayList<Integer>();
		for(int i=0;i<attUnits;i++) {
			aList.add((int)(Math.random() *6)+1);
			
		}
		for(int i=0;i<defUnits;i++) {
			dList.add((int)(Math.random() *6)+1);
			
		}
		Collections.sort(dList);
		Collections.sort(aList);
		Collections.reverse(dList);
		Collections.reverse(aList);
		System.out.println("d1" + " " + dList.get(0));
		System.out.println("d2" + " " + dList.get(1));
		System.out.println("d3" + " " + aList.get(2));
		System.out.println("a1" + " " + aList.get(0));
		System.out.println("a2" + " " + aList.get(1));
		System.out.println("a3" + " " + aList.get(2));
		if(aList.size()-dList.size()==2) {
			aList.remove(2);
			aList.remove(1);
			
		}
		if(aList.size()-dList.size()==1) {
			aList.remove(dList.size());
			}
		
		if(dList.size()-aList.size()==2) {
			dList.remove(2);
			dList.remove(1);
			
		}
		if(dList.size()-aList.size()==1) {
			dList.remove(aList.size());
			
		}
		
		if(dList.size()==1) {
			if(aList.get(0)>dList.get(0))
				lossDef= lossDef-1;
			else
				lossAtt= lossAtt-1;
		}
		if(dList.size()==2) {
			if(aList.get(0)>dList.get(0))
				lossDef= lossDef-1;
			else
				lossAtt= lossAtt-1;
			if(aList.get(1)>dList.get(1))
				lossDef= lossDef-1;
			else
				lossAtt= lossAtt-1;
		}
		if(dList.size()==3) {
			if(aList.get(0)>dList.get(0))
				lossDef= lossDef-1;
			else
				lossAtt= lossAtt-1;
			if(aList.get(1)>dList.get(1))
				lossDef= lossDef-1;
			else
				lossAtt= lossAtt-1;
			if(aList.get(2)>dList.get(2))
				lossDef= lossDef-1;
			else
				lossAtt= lossAtt-1;
		}
		
		System.out.println("Def:" + lossDef + " " + "Att" + lossAtt);
		
	
	}
}