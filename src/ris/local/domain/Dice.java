package ris.local.domain;

import java.util.ArrayList;
import java.util.Collections;

public class Dice {

	public static void main(String[] args) {
		Dice dice=new Dice();
		dice.rollDice(3, 3);
	}
	public ArrayList<Integer> rollDice(int attUnits,int defUnits) {
		int lossDef=0;
		int lossAtt=0;
		ArrayList<Integer> aList= new ArrayList<Integer>();
		ArrayList<Integer> defList= new ArrayList<Integer>();
		for(int i=0;i<attUnits;i++) {
			aList.add((int)(Math.random() *6)+1);
			
		}
		for(int j=0;j<defUnits;j++) {
			defList.add((int)(Math.random() *6)+1);
			
		}
		System.out.println("d1" + " " + defList.get(0));
		System.out.println("d2" + " " + defList.get(1));
		System.out.println("d3" + " " + defList.get(2));
		System.out.println("a1" + " " + aList.get(0));
		System.out.println("a2" + " " + aList.get(1));
		System.out.println("a3" + " " + aList.get(2));
		System.out.println(" ");
		Collections.sort(aList);
		Collections.sort(defList);
		Collections.reverse(aList);
		Collections.reverse(defList);
		System.out.println("d1" + " " + defList.get(0));
		System.out.println("d2" + " " + defList.get(1));
		System.out.println("d3" + " " + defList.get(2));
		System.out.println("a1" + " " + aList.get(0));
		System.out.println("a2" + " " + aList.get(1));
		System.out.println("a3" + " " + aList.get(2));
		if(aList.size()-defList.size()==2) {
			aList.remove(2);
			aList.remove(1);
			
		}
		if(aList.size()-defList.size()==1) {
			aList.remove(defList.size());
			}
		
		if(defList.size()-aList.size()==2) {
			defList.remove(2);
			defList.remove(1);
			
		}
		if(defList.size()-aList.size()==1) {
			defList.remove(aList.size());
			
		}
		
		if(defList.size()==1) {
			if(aList.get(0)>defList.get(0))
				lossDef= lossDef-1;
			else
				lossAtt= lossAtt-1;
		}
		if(defList.size()==2) {
			if(aList.get(0)>defList.get(0))
				lossDef= lossDef-1;
			else
				lossAtt= lossAtt-1;
			if(aList.get(1)>defList.get(1))
				lossDef= lossDef-1;
			else
				lossAtt= lossAtt-1;
		}
		if(defList.size()==3) {
			if(aList.get(0)>defList.get(0))
				lossDef= lossDef-1;
			else
				lossAtt= lossAtt-1;
			if(aList.get(1)>defList.get(1))
				lossDef= lossDef-1;
			else
				lossAtt= lossAtt-1;
			if(aList.get(2)>defList.get(2))
				lossDef= lossDef-1;
			else
				lossAtt= lossAtt-1;
		}
		ArrayList<Integer> unitLoss= new ArrayList<Integer>();
		unitLoss.add(lossDef);
		unitLoss.add(lossAtt);
		System.out.println("Def:" + lossDef + " " + "Att" + lossAtt);
		return unitLoss;
	
	}
}