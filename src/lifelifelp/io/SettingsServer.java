/**
 * 
 */
package lifelifelp.io;

/**
 * @author K.Schulz
 *
 */
public class SettingsServer {
	private String serverID;
	private Boolean BotBansBot;
	
	/**
	 * 
	 */
	public SettingsServer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the serverID
	 */
	public String getServerID() {
		return serverID;
	}

	/**
	 * @param serverID the serverID to set
	 */
	public void setServerID(String serverID) {
		this.serverID = serverID;
	}

	/**
	 * @return the botBansBot
	 */
	public Boolean getBotBansBot() {
		return BotBansBot;
	}

	/**
	 * @param botBansBot the botBansBot to set
	 */
	public void setBotBansBot(Boolean botBansBot) {
		BotBansBot = botBansBot;
	}

}
