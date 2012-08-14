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

	private int attackValue;
	private int defenseValue;

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

	public int attackValue() {
		return attackValue;
	}

	public Color color() {
		return color;
	}

	public int defenseValue() {
		return defenseValue;
	}

	public int foodValue() {
		return foodValue;
	}

	public char glyph() {
		return glyph;
	}

	public void modifyAttackValue(int amount) {
		attackValue += amount;
	}

	public void modifyDefenseValue(int amount) {
		defenseValue += amount;
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

	public String details() {
		String details = "";
		
		if (attackValue != 0)
			details += "     attack:" + attackValue;
		
		if (defenseValue != 0)
			details += "     defense:" + defenseValue;
		
		if (foodValue != 0)
			details += "     food:" + foodValue;
		
		return details;
	}

}
