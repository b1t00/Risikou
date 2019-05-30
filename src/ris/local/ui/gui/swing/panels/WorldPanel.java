package ris.local.ui.gui.swing.panels;

import javax.swing.JPanel;

public class WorldPanel extends JPanel{
	
	private WorldListener listener;

	public interface WorldListener {
		
	}
	public WorldPanel (WorldListener wl) {
		listener = wl;
	}
}
