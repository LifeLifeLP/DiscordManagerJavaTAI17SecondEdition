/**
 * 
 */
package lifelifelp.io;

/**
 * @author K.Schulz
 *
 */
public class SettingsCommon {
	private boolean headlessmode;
	private String botID;
	private String botName;
	private boolean botBansBot;
	/**
	 * 
	 */
	public SettingsCommon() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the headlessmode
	 */
	public boolean isHeadlessmode() {
		return headlessmode;
	}
	/**
	 * @param headlessmode the headlessmode to set
	 */
	public void setHeadlessmode(boolean headlessmode) {
		this.headlessmode = headlessmode;
	}
	/**
	 * @return the botID
	 */
	public String getBotID() {
		return botID;
	}
	/**
	 * @param botID the botID to set
	 */
	public void setBotID(String botID) {
		this.botID = botID;
	}

}