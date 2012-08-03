package rltut;

import java.awt.Color;

import asciiPanel.AsciiPanel;

/**
 * Class for a dungeon tile.
 * 
 * @author Jeremy Rist
 * 
 */
public enum Tile {
	FLOOR((char) 250, AsciiPanel.yellow), WALL((char) 177, AsciiPanel.yellow), BOUNDS(
			'x', AsciiPanel.brightBlack), STAIRS_DOWN('>', AsciiPanel.white), STAIRS_UP(
			'<', AsciiPanel.white), UNKNOWN(' ', AsciiPanel.white);

	private char glyph;

	private Color color;

	/**
	 * Create a new tile
	 * 
	 * @param glyph
	 *            char to display
	 * @param color
	 *            color for the tile
	 */
	Tile(char glyph, Color color) {
		this.glyph = glyph;
		this.color = color;
	}

	/**
	 * @return Color of the tile
	 */
	public Color color() {
		return color;
	}

	/**
	 * @return The tile's char
	 */
	public char glyph() {
		return glyph;
	}

	/**
	 * @return whether the player can dig in the tile
	 */
	public boolean isDiggable() {
		return this == Tile.WALL;
	}

	/**
	 * @return whether things can be placed on the tile
	 */
	public boolean isGround() {
		return this != WALL && this != BOUNDS;
	}
}
