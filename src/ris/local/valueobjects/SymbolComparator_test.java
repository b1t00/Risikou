package ris.local.valueobjects;

import java.util.Comparator;

public class SymbolComparator_test implements Comparator<Einheitenkarte> {

	@Override
	public int compare(Einheitenkarte karte1, Einheitenkarte karte2) {
		return karte1.getSymbol().compareTo(karte2.getSymbol());
	}
	
	
	
}
