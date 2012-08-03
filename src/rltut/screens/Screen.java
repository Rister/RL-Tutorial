package rltut.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

/**
 * This screen interface will serve as an abstract base for other screen
 * classes, introducing polymorphism into the application.
 * 
 * @author Jeremy Rist
 * 
 */
public interface Screen {
	/**
	 * Writes content to the terminal.
	 * 
	 * @param terminal
	 *            Virtual terminal instance.
	 */
	public void displayOutput(AsciiPanel terminal);

	/**
	 * Takes a key event and figures out what this screen needs to accomplish
	 * based on the received input. It will then return the next screen required
	 * for the application.
	 * 
	 * @param key
	 *            Received input.
	 * @return Next screen requred for program to run.
	 */
	public Screen respondToUserInput(KeyEvent key);
}
