package ris.common.valueobjects;

import java.io.Serializable;
import java.util.Comparator;

public class SymbolComparator_test implements Comparator<Risikokarte>, Serializable {

	@Override
	public int compare(Risikokarte karte1, Risikokarte karte2) {
		return karte1.getSymbol().compareTo(karte2.getSymbol());
	}
	
	
	
}
