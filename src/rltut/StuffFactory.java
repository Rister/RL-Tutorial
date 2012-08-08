package rltut;

import java.util.List;

import asciiPanel.AsciiPanel;

public class StuffFactory {
	private World world;

	public StuffFactory(World world) {
		this.world = world;
	}

	public Creature newFungus(int depth) {
		Creature fungus = new Creature(world, "Green Stuff", 'f',
				AsciiPanel.green, 10, 0, 0);
		world.addAtEmptyLocation(fungus, depth);
		new FungusAi(fungus, this);
		return fungus;
	}

	public Creature newPlayer(List<String> messages, FieldOfView fov) {
		Creature player = new Creature(world, "Player", '@',
				AsciiPanel.brightWhite, 100, 20, 5);
		world.addAtEmptyLocation(player, 0);
		new PlayerAi(player, messages, fov);
		return player;
	}

	public Creature newBat(int depth) {
		Creature bat = new Creature(world, "Scary Bat", 'b', AsciiPanel.yellow,
				15, 5, 0);
		world.addAtEmptyLocation(bat, depth);
		new BatAi(bat);
		return bat;
	}

	public Item newRock(int depth) {
		Item rock = new Item(',', AsciiPanel.yellow, "rock");
		world.addAtEmptyLocation(rock, depth);
		return rock;
	}

	public Item newVictoryItem(int depth) {
		Item item = new Item('*', AsciiPanel.brightWhite, "teddy bear");
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newBuinessCard(int depth) {
		Item item = new Item('_', AsciiPanel.brightCyan, "spare business card");
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newVaccuumCleaner(int depth) {
		Item item = new Item('&', AsciiPanel.brightCyan, "old hoover vaccuum");
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newFries(int depth) {
		Item item = new Item('%', AsciiPanel.brightYellow, "box of stale fries");
		world.addAtEmptyLocation(item, depth);
		item.modifyFoodValue(125);
		return item;
	}

	public Item newFriedFish(int depth) {
		Item item = new Item('%', AsciiPanel.cyan, "greasy fried fish");
		world.addAtEmptyLocation(item, depth);
		item.modifyFoodValue(500);
		return item;
	}

}
