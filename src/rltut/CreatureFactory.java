package rltut;

import java.util.List;

import asciiPanel.AsciiPanel;

public class CreatureFactory {
	private World world;

	public CreatureFactory(World world) {
		this.world = world;
	}

	public Creature newFungus(int depth) {
		Creature fungus = new Creature(world, "Green Stuff", 'f', AsciiPanel.green, 10, 0, 0);
		world.addAtEmptyLocation(fungus, depth);
		new FungusAi(fungus, this);
		return fungus;
	}

	public Creature newPlayer(List<String> messages, FieldOfView fov) {
		Creature player = new Creature(world, "Player", '@', AsciiPanel.brightWhite, 100,
				20, 5);
		world.addAtEmptyLocation(player, 0);
		new PlayerAi(player, messages, fov);
		return player;
	}
	
	public Creature newBat(int depth) {
		Creature bat = new Creature(world, "Scary Bat", 'b', AsciiPanel.yellow, 15, 5, 0);
		world.addAtEmptyLocation(bat, depth);
		new BatAi(bat);
		return bat;
	}

}
