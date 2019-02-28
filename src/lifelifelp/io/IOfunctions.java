/**
 * 
 */
package lifelifelp.io;

import java.util.ArrayList;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IUser;

/**
 * @author K.Schulz
 *
 */
public interface IOfunctions {

	static void updateUserDatabase(IDiscordClient iDiscordClient) {
		// Diese Funktion wird für alle User die der Bot erreichen kann einen SettinngsUser-Eintrag erstellen
		ArrayList<SettingsUser> aSettingsUserFromHDD = IO.readUsers();
		
		for(IUser iu: iDiscordClient.getUsers()) {
			if(!iu.isBot()) {
				
			}
		}
	}

}
