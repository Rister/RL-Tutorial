package rltut.screens;

import java.awt.event.KeyEvent;

import rltut.Creature;
import rltut.CreatureFactory;
import rltut.World;
import rltut.WorldBuilder;
import asciiPanel.AsciiPanel;

public class PlayScreen implements Screen {
	private World world;
	private int screenWidth;
	private int screenHeight;
	private Creature player;

	public PlayScreen() {
		screenWidth = 80;
		screenHeight = 21;
		createWorld();
		CreatureFactory creatureFactory = new CreatureFactory(world);
		player = creatureFactory.newPlayer();
	}

	private void createWorld() {
		world = new WorldBuilder(874, 344).makeCaves().build();
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		int left = getScrollX();
		int top = getScrollY();

		displayTiles(terminal, left, top);
		terminal.write(player.glyph(), player.x - left, player.y - top,
				player.color());
	}

	private void displayTiles(AsciiPanel terminal, int left, int top) {
		for (int x = 0; x < screenWidth; x++) {
			for (int y = 0; y < screenHeight; y++) {
				int wx = x + left;
				int wy = y + top;

				Creature creature = world.creature(wx, wy);
				if (creature != null)
					terminal.write(creature.glyph(), creature.x - left,
							creature.y - top, creature.color());
				else
					terminal.write(world.glyph(wx, wy), x, y,
							world.color(wx, wy));
			}
		}
	}

	public int getScrollX() {
		return Math.max(
				0,
				Math.min(player.x - screenWidth / 2, world.width()
						- screenWidth));
	}

	public int getScrollY() {
		return Math.max(
				0,
				Math.min(player.y - screenHeight / 2, world.height()
						- screenHeight));
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch (key.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			return new LoseScreen();
		case KeyEvent.VK_ENTER:
			return new WinScreen();
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_H:
			player.moveBy(-1, 0);
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_L:
			player.moveBy(1, 0);
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_K:
			player.moveBy(0, -1);
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_J:
			player.moveBy(0, 1);
			break;
		case KeyEvent.VK_Y:
			player.moveBy(-1, -1);
			break;
		case KeyEvent.VK_U:
			player.moveBy(1, -1);
			break;
		case KeyEvent.VK_B:
			player.moveBy(-1, 1);
			break;
		case KeyEvent.VK_N:
			player.moveBy(1, 1);
			break;
		}

		return this;
	}
}
