/**
 * 
 */
package lifelifelp.games;

import java.util.ArrayList;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

/**
 * @author K.Schulz
 * @see Dies ist ein Save fuer ein TicTacToe-Spiel
 */
public class TicTacToeSavegame {
	private IUser gameuser;
	private IChannel sourceChannel;
	private IChannel gameChannel;
	private ArrayList<Integer> playfield;
	/**
	 * @return the gameuser
	 */
	public synchronized IUser getGameuser() {
		return gameuser;
	}
	/**
	 * @param gameuser the gameuser to set
	 */
	public synchronized void setGameuser(IUser gameuser) {
		this.gameuser = gameuser;
	}
	/**
	 * @return the sourceChannel
	 */
	public synchronized IChannel getSourceChannel() {
		return sourceChannel;
	}
	/**
	 * @param sourceChannel the sourceChannel to set
	 */
	public synchronized void setSourceChannel(IChannel sourceChannel) {
		this.sourceChannel = sourceChannel;
	}
	/**
	 * @return the gameChannel
	 */
	public synchronized IChannel getGameChannel() {
		return gameChannel;
	}
	/**
	 * @param gameChannel the gameChannel to set
	 */
	public synchronized void setGameChannel(IChannel gameChannel) {
		this.gameChannel = gameChannel;
	}
	/**
	 * @return the playfield
	 */
	public synchronized ArrayList<Integer> getPlayfield() {
		return playfield;
	}
	/**
	 * @param playfield the playfield to set
	 */
	public synchronized void setPlayfield(ArrayList<Integer> playfield) {
		this.playfield = playfield;
	}
}
