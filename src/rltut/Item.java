package rltut;

import java.awt.Color;

public class Item {
	
	private String name;
	private char glyph;
	private Color color;
	
	public String name() { return name; }
	public char glyph() {return glyph;}
	public Color color() {return color;}
	
	public Item(char glyph, Color color, String name) {
		this.name = name;
		this.glyph = glyph;
		this.color = color;
	}

}
