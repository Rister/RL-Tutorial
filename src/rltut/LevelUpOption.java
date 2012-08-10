/**
 * 
 */
package rltut;

/**
 * @author Jeremy Rist
 * 
 */
public abstract class LevelUpOption {
	private String name;

	public LevelUpOption(String name) {
		this.name = name;
	}

	public abstract void invoke(Creature creature);

	public String name() {
		return name;
	}

}
