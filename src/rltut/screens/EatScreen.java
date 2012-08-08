package rltut.screens;

import rltut.Creature;
import rltut.Item;

/**
 * Selection screen for edible items.
 * 
 * @author Jeremy Rist
 *
 */
public class EatScreen extends InventoryBasedScreen {

	/**
	 * @param player The player object
	 */
	public EatScreen(Creature player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "eat";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return item.foodValue() != 0;
	}

	@Override
	protected Screen use(Item item) {
		player.eat(item);
		return null;
	}

}
