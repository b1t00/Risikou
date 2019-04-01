package ris.local.valueobjects;

import java.util.ArrayList;

public class Gamer {
private String name;
static ArrayList<Land> inBesitz = new ArrayList<Land>();
public Gamer(String name,ArrayList inBesitz){
	this.name=name;
	this.inBesitz=inBesitz;
}
public static void main(String[]args) {
	Land Frankreich = new Land("Frankreich",1);
	Gamer Tobi= new Gamer("Tobi",inBesitz);
	Tobi.inBesitz.add(Frankreich);
	System.out.println(inBesitz);
}
}
