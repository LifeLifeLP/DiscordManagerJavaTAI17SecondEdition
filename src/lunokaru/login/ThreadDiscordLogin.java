package lunokaru.login;

import org.slf4j.LoggerFactory;

import lifelifelp.botfuctions.CommonEvents;
import sx.blah.discord.api.IDiscordClient;

public class ThreadDiscordLogin extends Thread {
	private org.slf4j.Logger logger;
	private static IDiscordClient discordClient;

	public void run() {
		logger = LoggerFactory.getLogger(LoginMain.class);
		discordClient = LoginData.getBotID();
		discordClient.getDispatcher().registerListener(new CommonEvents());
		discordClient.login();
		logger.info(discordClient.getApplicationName() + " is back. Let's get going!");
		lunokaru.ui.GuiMain.main(null);
	}
}
