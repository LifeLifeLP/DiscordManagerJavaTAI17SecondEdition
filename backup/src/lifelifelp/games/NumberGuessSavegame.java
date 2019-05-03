/**
 * 
 */
package lifelifelp.games;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

/**
 * @author lifel
 *
 */
public class NumberGuessSavegame {
	//Klassenattribute
	private IUser gameuser;
	private IChannel sourceChannel;
	private int trys;
	private int goal;
	private String name;

	
	/**
	 * 
	 */
	//Normale Konstruktor
	public NumberGuessSavegame() {
		super();
	}

	/**
	 * 
	 */
	//Erweiterter Konstruktor
	public NumberGuessSavegame(IUser Gameuser, IChannel Sourcechannel) {
		// TODO Auto-generated constructor stub
		this.gameuser = Gameuser;
		this.sourceChannel = Sourcechannel;
        this.trys = 0;
        this.goal = (int) (Math.random()*50);
	}

	/**
	 * @return the gameuser
	 */
	public IUser getGameuser() {
		return gameuser;
	}

	/**
	 * @param gameuser the gameuser to set
	 */
	public void setGameuser(IUser gameuser) {
		this.gameuser = gameuser;
	}

	/**
	 * @return the sourceChannel
	 */
	public IChannel getSourceChannel() {
		return sourceChannel;
	}

	/**
	 * @param sourceChannel the sourceChannel to set
	 */
	public void setSourceChannel(IChannel sourceChannel) {
		this.sourceChannel = sourceChannel;
	}

	/**
	 * @return the trys
	 */
	public int getTrys() {
		return trys;
	}

	/**
	 * @param trys the trys to set
	 */
	public void setTrys(int trys) {
		this.trys = trys;
	}

	/**
	 * @return the goal
	 */
	public int getGoal() {
		return goal;
	}

	/**
	 * @param goal the goal to set
	 */
	public void setGoal(int goal) {
		this.goal = goal;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
