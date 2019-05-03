/**
 * 
 */
package lifelifelp.games;

import java.util.ArrayList;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * @author K.Schulz
 * @see Dies ist ein Save fuer ein TicTacToe-Spiel
 */
public class TicTacToeSavegame {
	private IUser gameuser;
	private IChannel sourceChannel;
	private IChannel gameChannel;
	private IMessage playfieldMessage;
	private IMessage row1;
	private IMessage row2;
	private IMessage row3;
	private ArrayList<Integer> playfield;
	private Boolean activePlayer;
	/**
	 * @return the gameuser
	 */
	public  IUser getGameuser() {
		return gameuser;
	}
	/**
	 * @param gameuser the gameuser to set
	 */
	public  void setGameuser(IUser gameuser) {
		this.gameuser = gameuser;
	}
	/**
	 * @return the sourceChannel
	 */
	public  IChannel getSourceChannel() {
		return sourceChannel;
	}
	/**
	 * @param sourceChannel the sourceChannel to set
	 */
	public  void setSourceChannel(IChannel sourceChannel) {
		this.sourceChannel = sourceChannel;
	}
	/**
	 * @return the gameChannel
	 */
	public  IChannel getGameChannel() {
		return gameChannel;
	}
	/**
	 * @param gameChannel the gameChannel to set
	 */
	public  void setGameChannel(IChannel gameChannel) {
		this.gameChannel = gameChannel;
	}
	/**
	 * @return the playfieldMessage
	 */
	public  IMessage getPlayfieldMessage() {
		return playfieldMessage;
	}
	/**
	 * @param playfieldMessage the playfieldMessage to set
	 */
	public  void setPlayfieldMessage(IMessage playfieldMessage) {
		this.playfieldMessage = playfieldMessage;
	}
	/**
	 * @return the row1
	 */
	public  IMessage getRow1() {
		return row1;
	}
	/**
	 * @param row1 the row1 to set
	 */
	public  void setRow1(IMessage row1) {
		this.row1 = row1;
	}
	/**
	 * @return the row2
	 */
	public  IMessage getRow2() {
		return row2;
	}
	/**
	 * @param row2 the row2 to set
	 */
	public  void setRow2(IMessage row2) {
		this.row2 = row2;
	}
	/**
	 * @return the row3
	 */
	public  IMessage getRow3() {
		return row3;
	}
	/**
	 * @param row3 the row3 to set
	 */
	public  void setRow3(IMessage row3) {
		this.row3 = row3;
	}
	/**
	 * @return the playfield
	 */
	public  ArrayList<Integer> getPlayfield() {
		return playfield;
	}
	/**
	 * @param playfield the playfield to set
	 */
	public  void setPlayfield(ArrayList<Integer> playfield) {
		this.playfield = playfield;
	}
	/**
	 * @return the activePlayer
	 */
	public Boolean getActivePlayer() {
		return activePlayer;
	}
	/**
	 * @param activePlayer the activePlayer to set
	 */
	public void setActivePlayer(Boolean activePlayer) {
		this.activePlayer = activePlayer;
	}
}
