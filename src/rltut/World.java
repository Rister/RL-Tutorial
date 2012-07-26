package rltut;

import java.awt.Color;

public class World {
	private Tile[][] tiles;
	private int width;

	private int height;

	public World(Tile[][] tiles) {
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
	}

	public void addAtEmptyLocation(Creature creature) {
		int x;
		int y;

		do {
			x = (int) (Math.random() * width);
			y = (int) (Math.random() * height);
		} while (!tile(x, y).isGround());

		creature.x = x;
		creature.y = y;
	}

	public Color color(int x, int y) {
		return tile(x, y).color();
	}

	public void dig(int x, int y) {
		if (tile(x, y).isDiggable())
			tiles[x][y] = Tile.FLOOR;
	}

	public char glyph(int x, int y) {
		return tile(x, y).glyph();
	}

	public int height() {
		return height;
	}

	public Tile tile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			return Tile.BOUNDS;
		else
			return tiles[x][y];
	}

	public int width() {
		return width;
	}
}
