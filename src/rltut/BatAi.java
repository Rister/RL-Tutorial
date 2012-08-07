package rltut;

public class BatAi extends CreatureAi {
	
	/**
	 * New BatAi
	 * 
	 * @param creature Creature to Add the Ai To.
	 */
	public BatAi(Creature creature) {
		super(creature);
	}
	
	/**
	 * Take a turn.
	 * 
	 * For a bat, this means wander twice in a turn.
	 */
	@Override
	public void onUpdate() {
		wander();
		wander();
	}

}
