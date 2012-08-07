package rltut;

import java.awt.Color;

/**
 * Generic item class.
 * 
 * Basic items don't __do__ much of anything
 * 
 * @author Jeremy Rist
 * 
 */
public class Item {

	private String name;
	private char glyph;
	private Color color;

	private int foodValue;

	/**
	 * Create a new item.
	 * 
	 * @param glyph
	 *            char for the item
	 * @param color
	 *            Color for the item
	 * @param name
	 *            String containing the item's name.
	 */
	public Item(char glyph, Color color, String name) {
		this.name = name;
		this.glyph = glyph;
		this.color = color;
	}

	public Color color() {
		return color;
	}

	public int foodValue() {
		return foodValue;
	}

	public char glyph() {
		return glyph;
	}

	/**
	 * Modify the food value of the item.
	 * 
	 * @param amount
	 *            amount to add to foodValue
	 */
	public void modifyFoodValue(int amount) {
		foodValue += amount;
	}

	public String name() {
		return name;
	}

}
