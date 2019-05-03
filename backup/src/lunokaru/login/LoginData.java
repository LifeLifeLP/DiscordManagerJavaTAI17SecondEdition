package lunokaru.login;

import sx.blah.discord.api.IDiscordClient;

public class LoginData {
	//Klassenattribut
	private static IDiscordClient botID;

	//Klassenmethoden
	/**
	 * @return the botID
	 */
	public static IDiscordClient getBotID() {
		return botID;
	}

	/**
	 * @param botID the botID to set
	 */
	public static void setBotID(IDiscordClient botID) {
		LoginData.botID = botID;
	}

	/**
	 *Standardkonstruktor
	 */
	public LoginData() {
		super();
	}

	/**
	 * Erweiterter Konstruktor
	 * @param discordClient
	 */
	public LoginData(IDiscordClient discordClient) {
		super();
		LoginData.botID = discordClient;
	}
}
