package rltut;

public class FungusAi extends CreatureAi {
	private StuffFactory factory;
	private int spreadcount;

	public FungusAi(Creature creature, StuffFactory factory) {
		super(creature);
		this.factory = factory;
	}

	@Override
	public void onUpdate() {
		if (spreadcount < 2 && Math.random() < 0.01)
			spread();
	}

	private void spread() {
		int x = creature.x + (int) (Math.random() * 11) - 5;
		int y = creature.y + (int) (Math.random() * 11) - 5;
		int z = creature.z;

		if (!creature.canEnter(x, y, z))
			return;

		Creature child = factory.newFungus(z);
		child.x = x;
		child.y = y;
		child.z = z;
		spreadcount++;
		creature.doAction("spawn a child");
	}
}
