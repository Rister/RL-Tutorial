/**
 * 
 */
package rltut.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

/**
 * @author Jeremy Rist
 * 
 */
public class HelpScreen implements Screen {

	/*
	 * (non-Javadoc)
	 * 
	 * @see rltut.screens.Screen#displayOutput(asciiPanel.AsciiPanel)
	 */
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.clear();
		terminal.writeCenter("roguelike help", 1);
		terminal.write(
				"Descend the Caves Of Slight Danger, find the lost Teddy Bear, and return to",
				1, 3);
		terminal.write("the surface to win. use what you find to avoid dying.",
				1, 4);

		int y = 6;
		terminal.write("[g] or [,] to pick up", 2, y++);
		terminal.write("[d] to drop", 2, y++);
		terminal.write("[e] to eat", 2, y++);
		terminal.write("[w] to wear or weild", 2, y++);
		terminal.write("[?] for help", 2, y++);
		terminal.write("[x] to examine your items", 2, y++);
		terminal.write("[;] to look arond", 2, y++);

		terminal.writeCenter("-- press any key to continue --", 22);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rltut.screens.Screen#respondToUserInput(java.awt.event.KeyEvent)
	 */
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return null;
	}

}
