/**
 * 
 */
package lifelifelp.ui;

import java.util.ArrayList;

import lifelifelp.io.IOfunctions;
import lifelifelp.io.SettingsUser;
import sx.blah.discord.api.IDiscordClient;

/**
 * @author K.Schulz
 *
 */
public class HeadlessMain {
	static ArrayList<SettingsUser> aSU = new ArrayList<SettingsUser>();
	/**
	 * 
	 */
	public HeadlessMain() {
	}
	public static void main(IDiscordClient iDiscordClient) {
		aSU = IOfunctions.loadUserDatabase(iDiscordClient);
		
	}

}
