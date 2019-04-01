package ris.local.valueobjects;

import java.util.ArrayList;

public class Gamer {
private String name;
ArrayList<Land> inBesitz = new ArrayList<Land>();
public Gamer(String name,ArrayList inBesitz){
	this.name=name;
	this.inBesitz= inBesitz;

}
}
