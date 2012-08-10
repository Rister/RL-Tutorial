/**
 * 
 */
package rltut;

import java.util.List;

/**
 * AI class for zombies
 * 
 * @author Jeremy Rist
 * 
 */
public class ZombieAi extends CreatureAi {
	private Creature player;;

	/**
	 * @param creature
	 * @param player
	 */
	public ZombieAi(Creature creature, Creature player) {
		super(creature);
		this.player = player;
	}

	/**
	 * Checks if Creature can see the player. If so, moves towards the player.
	 * 
	 * @see rltut.CreatureAi#onUpdate()
	 */
	@Override
	public void onUpdate() {
		if (Math.random() < 0.2)
			return;

		if (creature.canSee(player.x, player.y, player.z))
			hunt(player);
		else
			wander();
	}

	/**
	 * Moves towards a target creature.
	 * 
	 * @param target
	 *            Creature object to hunt.
	 */
	public void hunt(Creature target) {
		List<Point> points = new Path(creature, target.x, target.y).points();

		int mx = points.get(0).x - creature.x;
		int my = points.get(0).y - creature.y;

		creature.moveBy(mx, my, 0);
	}

}
