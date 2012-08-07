package rltut;

import java.awt.Color;

/**
 * Base class for a creature
 * 
 * @author Jeremy Rist
 * 
 */
public class Creature {
	private World world;
	public int x;
	public int y;
	public int z;

	private String name;
	private char glyph;
	private Color color;

	private CreatureAi ai;

	private Inventory inventory;

	private int maxHp;
	private int hp;
	private int attackValue;
	private int defenseValue;

	private int visionRadius;

	/**
	 * @param world
	 *            World where the creature lives
	 * @param name
	 *            Name of the creature
	 * @param glyph
	 *            char glyph of the creature
	 * @param color
	 *            Color of the creature
	 * @param maxHp
	 *            Max Hit points of the creature
	 * @param attack
	 *            Attack value of the creature
	 * @param defense
	 *            defense value of the creature
	 */
	public Creature(World world, String name, char glyph, Color color,
			int maxHp, int attack, int defense) {
		this.world = world;

		this.name = name;
		this.glyph = glyph;
		this.color = color;

		this.inventory = new Inventory(20);

		this.maxHp = maxHp;
		this.hp = maxHp;
		this.attackValue = attack;
		this.defenseValue = defense;

		this.visionRadius = 9;
	}

	/**
	 * Attacks another creature and removes hit points from it.
	 * 
	 * @param other
	 *            Creature being attacked
	 */
	private void attack(Creature other) {
		int amount = Math.max(0, attackValue() - other.defenseValue());

		amount = (int) (Math.random() * amount) + 1;

		other.modifyHp(-amount);

		doAction("attack the %s for %d damage", other.name, amount);
	}

	/**
	 * A public getter. Probably ought to be getAttackValue()
	 * 
	 * @return attackValue
	 */
	public int attackValue() {
		return attackValue;
	}

	/**
	 * Check if the creature can enter a given coordinate
	 * 
	 * @param wx
	 *            world x-coordinate
	 * @param wy
	 *            world y-coordinate
	 * @param wz
	 *            world z-coordinate
	 * @return if the creature can enter given coordinate
	 */
	public boolean canEnter(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz).isGround()
				&& world.creature(wx, wy, wz) == null;
	}

	/**
	 * Checks with the creature's AI to see if the creature can see into a given
	 * coordinate.
	 * 
	 * @param wx
	 *            world x-coordinate
	 * @param wy
	 *            world y-coordinate
	 * @param wz
	 *            world z-coordinate
	 * @return if the Creature can see the given coordinate
	 */
	public boolean canSee(int wx, int wy, int wz) {
		return ai.canSee(wx, wy, wz);
	}

	/**
	 * Returns the creature's Color
	 * 
	 * @return the creture's Color
	 */
	public Color color() {
		return color;
	}

	/**
	 * Finds another creature at the given coordinates in the same world as the
	 * Creature.
	 * 
	 * @param wx
	 *            world x-coordinate
	 * @param wy
	 *            world y-coordinate
	 * @param wz
	 *            world z-coordinate
	 * @return Creature at given coordinate
	 */
	public Creature creature(int wx, int wy, int wz) {
		return world.creature(wx, wy, wz);
	}

	/**
	 * Returns the defenseValue of the creature.
	 * 
	 * Probably ought to be getDefenseValue()
	 * 
	 * @return defenseValue
	 */
	public int defenseValue() {
		return defenseValue;
	}

	/**
	 * Tell the creature to dig in a given coordinate.
	 * 
	 * @param wx
	 *            world x-coordinate
	 * @param wy
	 *            world y-coordinate
	 * @param wz
	 *            world z-coordinate
	 */
	public void dig(int wx, int wy, int wz) {
		world.dig(wx, wy, wz);
	}

	/**
	 * Push a message from the Creature on to the message queue
	 * 
	 * @param message
	 *            String to display
	 * @param params
	 *            params for the string
	 */
	public void doAction(String message, Object... params) {
		int r = 9;
		for (int ox = -r; ox < r + 1; ox++) {
			for (int oy = -r; oy < r + 1; oy++) {
				if (ox * ox + oy * oy > r * r)
					continue;

				Creature other = world.creature(x + ox, y + oy, z);

				if (other == null)
					continue;

				if (other == this)
					other.notify("You " + message + ".", params);
				else if (other.canSee(x, y, z))
					other.notify(String.format("The %s %s.", name,
							makeSecondPerson(message)), params);
			}
		}
	}

	/**
	 * @return the creature's glyph
	 */
	public char glyph() {
		return glyph;
	}

	/**
	 * @return The crature's current HP
	 */
	public int hp() {
		return hp;
	}

	/**
	 * @return The creature's Inventory object
	 */
	public Inventory inventory() {
		return inventory;
	}

	/**
	 * Converts a given string to second person parlance.
	 * 
	 * @param text
	 *            String to convert to second person
	 * @return Second Personized String
	 */
	private String makeSecondPerson(String text) {
		// TODO String and Grammar parser in Creature class: Yuck.
		String[] words = text.split(" ");
		words[0] = words[0] + "s";

		StringBuilder builder = new StringBuilder();
		for (String word : words) {
			builder.append(" ");
			builder.append(word);
		}

		return builder.toString().trim();
	}

	/**
	 * @return the creature's Max HP
	 */
	public int maxHp() {
		return maxHp;
	}

	/**
	 * Adds hit points to a creature's hit points. If the creature's hit points
	 * are reduced below 1, the creature is removed from the world.
	 * 
	 * @param amount
	 *            Amount of HP to add.
	 */
	public void modifyHp(int amount) {
		hp += amount;

		if (hp < 1) {
			world.remove(this);
			doAction("die");
		}
	}

	/**
	 * Moves the Creature by a given amount
	 * 
	 * @param mx
	 *            x-axis movement
	 * @param my
	 *            y-axis movement
	 * @param mz
	 *            z-axis movement
	 */
	public void moveBy(int mx, int my, int mz) {
		if (mx == 0 && my == 0 && mz == 0)
			return;

		Tile tile = world.tile(x + mx, y + my, z + mz);

		if (mz == -1) {
			if (tile == Tile.STAIRS_DOWN) {
				doAction("walk up the stairs to level %d", z + mz + 1);
			} else {
				doAction("try to go up but are stopped by the cave ceiling");
				return;
			}
		} else if (mz == 1) {
			if (tile == Tile.STAIRS_UP) {
				doAction("walk down the stairs to level %d", z + mz + 1);
			} else {
				doAction("try to go down but are stopped by the cave floor");
				return;
			}
		}

		Creature other = world.creature(x + mx, y + my, z + mz);

		if (other == null)
			ai.onEnter(x + mx, y + my, z + mz, tile);
		else
			attack(other);
	}

	/**
	 * @return name of creature
	 */
	public String name() {
		return name;
	}

	public void notify(String message, Object... params) {
		ai.onNotify(String.format(message, params));
	}

	/**
	 * Set the creature's AI.
	 * 
	 * @param ai
	 *            Ai object to give the creature
	 */
	public void setCreatureAi(CreatureAi ai) {
		this.ai = ai;
	}

	/**
	 * Get a tile from the creature's world
	 * 
	 * @param wx
	 *            world x-coordinate
	 * @param wy
	 *            world y-coordinate
	 * @param wz
	 *            world z-coordinate
	 * @return Tile object from the given coordinate in the Creature's world
	 */
	public Tile tile(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz);
	}

	/**
	 * Run the creature's update routine.
	 * 
	 * Take a turn.
	 */
	public void update() {
		ai.onUpdate();
	}

	/**
	 * @return visionRadius of the Creature
	 */
	public int visionRadius() {
		return visionRadius;
	}

	/**
	 * Pick up the item at the Creature's current location in the world and add
	 * to the Creature's inventory.
	 */
	public void pickup() {
		Item item = world.item(x, y, z);

		if (inventory.isFull() || item == null) {
			doAction("grab at the ground");
		} else {
			doAction("pickup a %s", item.name());
			world.remove(x, y, z);
			inventory.add(item);
		}
	}

	/**
	 * Remove an Item from the Creature's Inventory and place at or near the
	 * Creature's current location in the World.
	 * 
	 * @param item
	 *            Item to drop from the Creature's Inventory
	 */
	public void drop(Item item) {
		doAction("drop a " + item.name());
		inventory.remove(item);
		world.addAtEmptySpace(item, x, y, z);
	}

}
