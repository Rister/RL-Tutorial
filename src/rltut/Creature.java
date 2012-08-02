package rltut;

import java.awt.Color;

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

	private void attack(Creature other) {
		int amount = Math.max(0, attackValue() - other.defenseValue());

		amount = (int) (Math.random() * amount) + 1;

		other.modifyHp(-amount);

		doAction("attack the %s for %d damage", other.name, amount);
	}

	public int attackValue() {
		return attackValue;
	}

	public boolean canEnter(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz).isGround()
				&& world.creature(wx, wy, wz) == null;
	}

	public boolean canSee(int wx, int wy, int wz) {
		return ai.canSee(wx, wy, wz);
	}

	public Color color() {
		return color;
	}

	public Creature creature(int wx, int wy, int wz) {
		return world.creature(wx, wy, wz);
	}

	public int defenseValue() {
		return defenseValue;
	}

	public void dig(int wx, int wy, int wz) {
		world.dig(wx, wy, wz);
	}

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

	public char glyph() {
		return glyph;
	}

	public int hp() {
		return hp;
	}

	public Inventory inventory() {
		return inventory;
	}

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

	public int maxHp() {
		return maxHp;
	}

	public void modifyHp(int amount) {
		hp += amount;

		if (hp < 1) {
			world.remove(this);
			doAction("die");
		}
	}

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

	public String name() {
		return name;
	}

	public void notify(String message, Object... params) {
		ai.onNotify(String.format(message, params));
	}

	public void setCreatureAi(CreatureAi ai) {
		this.ai = ai;
	}

	public Tile tile(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz);
	}

	public void update() {
		ai.onUpdate();
	}

	public int visionRadius() {
		return visionRadius;
	}
	
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
	
	public void drop(Item item) {
		doAction("drop a " + item.name());
		inventory.remove(item);
		world.addAtEmptySpace(item, x, y, z);
	}

}
