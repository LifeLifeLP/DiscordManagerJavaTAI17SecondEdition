/**
 * 
 */
package lifelifelp;

import lifelifelp.io.IOfunctions;
import sx.blah.discord.api.IDiscordClient;

/**
 * @author K.Schulz
 *
 */
public class Startup {

	/**
	 * 
	 */
	public Startup() {
		super();
	}
	
	public static void OnStartup(IDiscordClient iDiscordClient) {
		IOfunctions.updateUserDatabase(iDiscordClient);
		IOfunctions.loadUserDatabase(iDiscordClient);
	}
}
