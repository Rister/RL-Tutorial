/**
 * 
 */
package rltut.screens;

import rltut.Creature;
import rltut.Item;

/**
 * @author Jeremy Rist
 *
 */
public class ExamineScreen extends InventoryBasedScreen {

	/**
	 * @param player
	 */
	public ExamineScreen(Creature player) {
		super(player);
	}

	/* (non-Javadoc)
	 * @see rltut.screens.InventoryBasedScreen#getVerb()
	 */
	@Override
	protected String getVerb() {
		return "examine";
	}

	/* (non-Javadoc)
	 * @see rltut.screens.InventoryBasedScreen#isAcceptable(rltut.Item)
	 */
	@Override
	protected boolean isAcceptable(Item item) {
		return true;
	}

	/* (non-Javadoc)
	 * @see rltut.screens.InventoryBasedScreen#use(rltut.Item)
	 */
	@Override
	protected Screen use(Item item) {
		String article = "aeiou".contains(item.name().subSequence(0, 1)) ? "an " : "a ";
		player.notify("It's " + article + item.name() + "." + item.details());
		return null;
	}

}
