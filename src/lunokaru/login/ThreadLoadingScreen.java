package lunokaru.login;

import org.slf4j.LoggerFactory;

import lifelifelp.botfuctions.DiscordEvents;
import sx.blah.discord.api.IDiscordClient;

public class ThreadLoadingScreen extends Thread {
	//Klassenattribute
	private org.slf4j.Logger logger;
	private static IDiscordClient discordClient;
		
	//run() Methode, wird ausgeführt nach starten des Threads
	public void run() {
		logger = LoggerFactory.getLogger(LoginMain.class);
		discordClient = LoginData.getBotID();
		discordClient.getDispatcher().registerListener(new DiscordEvents());
	    discordClient.login();
	    logger.info(discordClient.getApplicationName()+ " is back. Let's get going!");
		lunokaru.ui.GuiMain.main(null);
	}
}