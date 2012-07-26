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

	public void dig(int wx, int wy) {
		world.dig(wx, wy);
	}

	public char glyph() {
		return glyph;
	}

	public void moveBy(int mx, int my) {
		ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
	}

	public void setCreatureAi(CreatureAi ai) {
		this.ai = ai;
	}

}
