package ris.local.domain;
import java.util.ArrayList;
import java.util.Collections;
public class dice {
public static void main(String[]args) {
	dice dice = new dice();
	dice.rollDice(2, 1);

}

	public void rollDice(int defUnits, int attUnits) {
	ArrayList<Integer> attDice= new ArrayList<Integer>();	
	ArrayList<Integer> defDice= new ArrayList<Integer>();		
	// 1 Angreifer, 1 Verteidiger//
	if(attUnits==1) {
		if(defUnits==1) {
			int def1 = (int)(Math.random() *6)+1;
			int att1 =  (int)(Math.random() *6)+1;
			System.out.println(att1);
			System.out.println(def1);
			if(def1>att1) {
				System.out.println("def wins");
			}
			else
				System.out.println("att wins");
		}
		if(defUnits==2) {
			int def1=(int)(Math.random() *6)+1;
			int def2=(int)(Math.random() *6)+1;
			int att1=(int)(Math.random() *6)+1;
			System.out.println(def1);
			System.out.println(def2);
			System.out.println(att1);
			if(def1>att1 || def2>att1) {
				System.out.println("def wins");
			}
			else
				System.out.println("att wins");
		}
		
		
		
	
		
	}
	
}
int defUnits = 1;
int attUnits = 1;

}
