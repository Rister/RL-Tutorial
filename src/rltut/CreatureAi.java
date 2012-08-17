package rltut;

/**
 * AI Class for Creatures. Contains the decision making logic for Creatures.
 * 
 * @author Jeremy Rist
 * 
 */
public class CreatureAi {
	protected Creature creature;

	/**
	 * Adds a new AI to a creature class.
	 * 
	 * @param creature
	 *            Creature to add Ai to.
	 */
	public CreatureAi(Creature creature) {
		this.creature = creature;
		this.creature.setCreatureAi(this);
	}

	/**
	 * Check to see if the Creature can see a given tile.
	 * 
	 * @param wx
	 *            world x-coordinate
	 * @param wy
	 *            world y-coordinate
	 * @param wz
	 *            world z-coordinate
	 * @return if the Creature can see a given coordinate
	 */
	public boolean canSee(int wx, int wy, int wz) {
		if (creature.z != wz)
			return false;

		if ((creature.x - wx) * (creature.x - wx) + (creature.y - wy)
				* (creature.y - wy) > creature.visionRadius()
				* creature.visionRadius())
			return false;
		for (Point p : new Line(creature.x, creature.y, wx, wy)) {
			if (creature.tile(p.x, p.y, wz).isGround() || p.x == wx
					&& p.y == wy)
				continue;
			return false;
		}

		return true;
	}

	public void onEnter(int x, int y, int z, Tile tile) {
		if (tile.isGround()) {
			creature.x = x;
			creature.y = y;
			creature.z = z;
		} else {
			creature.doAction("bump into a wall");
		}
	}

	public void onNotify(String message) {
	}

	/**
	 * Take a turn.
	 */
	public void onUpdate() {
	}

	/**
	 * Move creature one tile in a random direction
	 */
	public void wander() {
		int mx = (int) (Math.random() * 3) - 1;
		int my = (int) (Math.random() * 3) - 1;

		Creature other = creature.creature(creature.x + mx, creature.y + my,
				creature.z);
		if (other != null && other.glyph() == creature.glyph())
			return;
		else
			creature.moveBy(mx, my, 0);
	}

	public void onGainLevel() {
		new LevelUpController().autoLevelUp(creature);
	}

	public Tile rememberedTile(int wx, int wy, int wz) {
		return Tile.UNKNOWN;
	}
}
