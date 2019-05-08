package ris.local.valueobjects;

import java.util.Comparator;

public class SymbolComparator_test implements Comparator<Risikokarte> {

	@Override
	public int compare(Risikokarte karte1, Risikokarte karte2) {
		return karte1.getSymbol().compareTo(karte2.getSymbol());
	}
	
	
	
}
