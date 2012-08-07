package rltut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The World factory
 * 
 * This class will put a world together and spit it out.
 * 
 * @author Jeremy Rist
 * 
 */
public class WorldBuilder {
	private int width;
	private int height;
	private int depth;
	private Tile[][][] tiles;
	private int[][][] regions;
	private int nextRegion;

	/**
	 * @param width
	 *            Width or X-dimension of the world
	 * @param height
	 *            Height or Y-dimension of the world
	 * @param depth
	 *            depth or Z-dimension of the world
	 */
	public WorldBuilder(int width, int height, int depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.tiles = new Tile[width][height][depth];
		this.regions = new int[width][height][depth];
		this.nextRegion = 1;
	}

	/**
	 * Final method to return a completed World object.
	 * 
	 * @return A new world
	 */
	public World build() {
		return new World(tiles);
	}

	/**
	 * Cycles through the z-levels and connects each one with the level below
	 * it.
	 * 
	 * @return WorldBuilder with downward stairs added.
	 */
	public WorldBuilder connectRegions() {
		for (int z = 0; z < depth - 1; z++) {
			connectRegionsDown(z);
		}
		return this;
	}

	/**
	 * Connects a given z-level with the z-level below it.
	 * 
	 * @param z
	 *            z-level to connect with the one below it.
	 */
	private void connectRegionsDown(int z) {
		List<String> connected = new ArrayList<String>();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				String region = regions[x][y][z] + "," + regions[x][y][z + 1];
				if (tiles[x][y][z] == Tile.FLOOR
						&& tiles[x][y][z + 1] == Tile.FLOOR
						&& !connected.contains(region)) {
					connected.add(region);
					connectRegionsDown(z, regions[x][y][z],
							regions[x][y][z + 1]);
				}
			}
		}
	}

	/**
	 * Connects a z-level with the z-level below it by placing stairs between
	 * two specified regions.
	 * 
	 * @param z
	 *            z-level to connect to the one below it.
	 * @param r1
	 *            region to connect to lower level.
	 * @param r2
	 *            region on lower level to connect to
	 */
	private void connectRegionsDown(int z, int r1, int r2) {
		List<Point> candidates = findRegionOverlaps(z, r1, r2);

		int stairs = 0;
		do {
			Point p = candidates.remove(0);
			tiles[p.x][p.y][z] = Tile.STAIRS_DOWN;
			tiles[p.x][p.y][z + 1] = Tile.STAIRS_UP;
			stairs++;
		} while (candidates.size() / stairs > 250);
	}

	/**
	 * Looks through the world and identifies contiguous regions. If the region
	 * is too small, it will remove them, otherwise it will mark them with a
	 * region ID number.
	 * 
	 * @return a WorldBuilder with the contiguous regions marked
	 */
	private WorldBuilder createRegions() {
		regions = new int[width][height][depth];

		for (int z = 0; z < depth; z++) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					if (tiles[x][y][z] != Tile.WALL && regions[x][y][z] == 0) {
						int size = fillRegion(nextRegion++, x, y, z);

						if (size < 25)
							removeRegion(nextRegion - 1, z);
					}
				}
			}
		}
		return this;
	}

	/**
	 * Assigns a region number to all the tiles in the region containing a given
	 * tile.
	 * 
	 * @param region
	 *            number to assign to the region
	 * @param x
	 *            x-coordinate of a tile in the region
	 * @param y
	 *            y-coordinate of a tile in the region
	 * @param z
	 *            z-coordinate of a tile in the region
	 * @return number of tiles in the region
	 */
	private int fillRegion(int region, int x, int y, int z) {
		int size = 1;
		ArrayList<Point> open = new ArrayList<Point>();
		open.add(new Point(x, y, z));
		regions[x][y][z] = region;

		while (!open.isEmpty()) {
			Point p = open.remove(0);

			for (Point neighbor : p.neighbors8()) {
				if (neighbor.x < 0 || neighbor.y < 0 || neighbor.x >= width
						|| neighbor.y >= height)
					continue;

				if (regions[neighbor.x][neighbor.y][neighbor.z] > 0
						|| tiles[neighbor.x][neighbor.y][neighbor.z] == Tile.WALL)
					continue;

				size++;
				regions[neighbor.x][neighbor.y][neighbor.z] = region;
				open.add(neighbor);
			}
		}
		return size;
	}

	/**
	 * Finds the points where the selected regions overlap.
	 * 
	 * @param z
	 *            base z-level
	 * @param r1
	 *            region on base z-level
	 * @param r2
	 *            region below base z-level
	 * @return List of points where the regions overlap
	 */
	private List<Point> findRegionOverlaps(int z, int r1, int r2) {
		ArrayList<Point> candidates = new ArrayList<Point>();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (tiles[x][y][z] == Tile.FLOOR
						&& tiles[x][y][z + 1] == Tile.FLOOR
						&& regions[x][y][z] == r1 && regions[x][y][z + 1] == r2) {
					candidates.add(new Point(x, y, z));
				}
			}
		}

		Collections.shuffle(candidates);
		return candidates;
	}

	/**
	 * Creates a new factory and goes through the steps to create a randomized
	 * dungeon in it.
	 * 
	 * @return A factory containing a randomized world.
	 */
	public WorldBuilder makeCaves() {
		return randomizeTiles().smooth(8).createRegions().connectRegions()
				.addExitStairs();
	}

	/**
	 * Fills the world with random Floor and Wall tiles.
	 * 
	 * @return WorldBuilder containing a world with random tiles.
	 */
	private WorldBuilder randomizeTiles() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < depth; z++) {
					tiles[x][y][z] = Math.random() < 0.5 ? Tile.FLOOR
							: Tile.WALL;
				}
			}
		}
		return this;
	}

	/**
	 * Removes the specified region by filling it in with WALL tiles.
	 * 
	 * @param region
	 *            number of the region to remove
	 * @param z
	 *            z-level of the region to remove
	 */
	private void removeRegion(int region, int z) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (regions[x][y][z] == region) {
					regions[x][y][z] = 0;
					tiles[x][y][z] = Tile.WALL;
				}
			}
		}
	}

	/**
	 * Smoothes the world using a simple form of cellular automata
	 * 
	 * @param times
	 *            Number of times to smooth the world
	 * @return WorldBuilder with the maps smoothed
	 */
	private WorldBuilder smooth(int times) {
		Tile[][][] tiles2 = new Tile[width][height][depth];
		for (int time = 0; time < times; time++) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					for (int z = 0; z < depth; z++) {
						int floors = 0;
						int rocks = 0;

						for (int ox = -1; ox < 2; ox++) {
							for (int oy = -1; oy < 2; oy++) {
								if (x + ox < 0 || x + ox >= width || y + oy < 0
										|| y + oy >= height)
									continue;
								if (tiles[x + ox][y + oy][z] == Tile.FLOOR)
									floors++;
								else
									rocks++;
							}
						}
						tiles2[x][y][z] = floors >= rocks ? Tile.FLOOR
								: Tile.WALL;
					}
				}
			}
			tiles = tiles2;
		}
		return this;
	}

	/**
	 * Adds an exit staircase to the top z-level
	 * 
	 * @return WorldBuilder with exit stairs added.
	 */
	private WorldBuilder addExitStairs() {
		int x = -1;
		int y = -1;
		do {
			x = (int) (Math.random() * width);
			y = (int) (Math.random() * height);
		} while (tiles[x][y][0] != Tile.FLOOR);

		tiles[x][y][0] = Tile.STAIRS_UP;
		return this;
	}
}
