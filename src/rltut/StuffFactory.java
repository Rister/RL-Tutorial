package rltut;

import java.util.List;

import asciiPanel.AsciiPanel;

/**
 * Factory class to build new Creatures and Items.
 * 
 * @author Jeremy Rist
 * 
 */
public class StuffFactory {
	private World world;

	public StuffFactory(World world) {
		this.world = world;
	}

	public Creature newBat(int depth) {
		Creature bat = new Creature(world, "Scary Bat", 'b', AsciiPanel.yellow,
				15, 5, 0);
		world.addAtEmptyLocation(bat, depth);
		new BatAi(bat);
		return bat;
	}

	public Item newBuinessCard(int depth) {
		Item item = new Item('_', AsciiPanel.brightCyan, "spare business card");
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newDagger(int depth) {
		Item item = new Item(')', AsciiPanel.white, "dagger");
		item.modifyAttackValue(5);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newFriedFish(int depth) {
		Item item = new Item('%', AsciiPanel.cyan, "greasy fried fish");
		world.addAtEmptyLocation(item, depth);
		item.modifyFoodValue(500);
		return item;
	}

	public Item newFries(int depth) {
		Item item = new Item('%', AsciiPanel.brightYellow, "box of stale fries");
		world.addAtEmptyLocation(item, depth);
		item.modifyFoodValue(125);
		return item;
	}

	public Creature newFungus(int depth) {
		Creature fungus = new Creature(world, "Green Stuff", 'f',
				AsciiPanel.green, 10, 0, 0);
		world.addAtEmptyLocation(fungus, depth);
		new FungusAi(fungus, this);
		return fungus;
	}

	public Item newHeavyArmor(int depth) {
		Item item = new Item('[', AsciiPanel.brightWhite, "platemail");
		item.modifyDefenseValue(6);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newLightArmor(int depth) {
		Item item = new Item('[', AsciiPanel.green, "tunic");
		item.modifyDefenseValue(2);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newMediumArmor(int depth) {
		Item item = new Item('[', AsciiPanel.white, "chainmaille");
		item.modifyDefenseValue(4);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Creature newPlayer(List<String> messages, FieldOfView fov) {
		Creature player = new Creature(world, "Player", '@',
				AsciiPanel.brightWhite, 100, 20, 5);
		world.addAtEmptyLocation(player, 0);
		new PlayerAi(player, messages, fov);
		return player;
	}

	public Item newRock(int depth) {
		Item rock = new Item(',', AsciiPanel.yellow, "rock");
		world.addAtEmptyLocation(rock, depth);
		return rock;
	}

	public Item newStaff(int depth) {
		Item item = new Item(')', AsciiPanel.yellow, "staff");
		item.modifyAttackValue(5);
		item.modifyDefenseValue(3);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newSword(int depth) {
		Item item = new Item(')', AsciiPanel.brightWhite, "sword");
		item.modifyAttackValue(10);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newVaccuumCleaner(int depth) {
		Item item = new Item('&', AsciiPanel.brightCyan, "old hoover vaccuum");
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newVictoryItem(int depth) {
		Item item = new Item('*', AsciiPanel.brightWhite, "teddy bear");
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item randomArmor(int depth) {
		switch ((int) (Math.random() * 3)) {
		case 0:
			return newLightArmor(depth);
		case 1:
			return newMediumArmor(depth);
		default:
			return newHeavyArmor(depth);
		}
	}

	public Item randomWeapon(int depth) {
		switch ((int) (Math.random() * 3)) {
		case 0:
			return newDagger(depth);
		case 1:
			return newSword(depth);
		default:
			return newStaff(depth);
		}
	}

	public Item newEdibleWeapon(int depth) {
		Item item = new Item(')', AsciiPanel.yellow, "baguette");
		item.modifyAttackValue(3);
		item.modifyFoodValue(50);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Creature newZombie(int depth, Creature player) {
		Creature zombie = new Creature(world, "rotting zombie", 'z',
				AsciiPanel.white, 50, 10, 10);
		world.addAtEmptyLocation(zombie, depth);
		new ZombieAi(zombie, player);
		return zombie;
	}
}
