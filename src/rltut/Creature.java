package rltut;

import java.awt.Color;

public class Creature {
	private World world;

	public int x;
	public int y;

	private char glyph;

	private Color color;

	private CreatureAi ai;

	public Creature(World world, char glyph, Color color) {
		this.world = world;
		this.glyph = glyph;
		this.color = color;
	}

	public Color color() {
		return color;
	}

	public char glyph() {
		return glyph;
	}

	public void setCreatureAi(CreatureAi ai) {
		this.ai = ai;
	}
	
	public void dig(int wx, int wy) {
		world.dig(wx, wy);
	}

}
