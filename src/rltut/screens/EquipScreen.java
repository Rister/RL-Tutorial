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
public class EquipScreen extends InventoryBasedScreen {

	public EquipScreen(Creature player) {
		super(player);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rltut.screens.InventoryBasedScreen#getVerb()
	 */
	@Override
	protected String getVerb() {
		return "wear or weild";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rltut.screens.InventoryBasedScreen#isAcceptable(rltut.Item)
	 */
	@Override
	protected boolean isAcceptable(Item item) {
		return item.attackValue() > 0 || item.defenseValue() > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rltut.screens.InventoryBasedScreen#use(rltut.Item)
	 */
	@Override
	protected Screen use(Item item) {
		player.equals(item);
		return null;
	}

}
