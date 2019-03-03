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
		ArrayList<SettingsUser> aSettingsUserFromHDD = IO.readUsers();
		ArrayList<SettingsUser> aSettingsUserToHDD = new ArrayList<SettingsUser>();
		for (SettingsUser su : aSettingsUserFromHDD) {
			for (IUser iu : iDiscordClient.getUsers()) {

				if (String.valueOf(su.getUserID()).equals(String.valueOf(iu.getLongID()))) {

				} else {
					if (aSettingsUserFromHDD
							.contains(new SettingsUser(String.valueOf(iu.getLongID()), String.valueOf(iu.getName())))) {
					} else {
						aSettingsUserToHDD
								.add(new SettingsUser(String.valueOf(iu.getLongID()), String.valueOf(iu.getName())));
						System.out.println("aSettingsUserToHDD Size:" + aSettingsUserToHDD.size());
					}

				}
			}
		}
		aSettingsUserFromHDD.addAll(aSettingsUserToHDD);
		IO.writeUsers(aSettingsUserFromHDD);

	}

	static ArrayList<SettingsUser> loadUserDatabase(IDiscordClient iDiscordClient) {
		return IO.readUsers();
	}

	static void updateServerDatabase(IDiscordClient iDiscordClient) {
		ArrayList<SettingsUser> aSettingsUserFromHDD = IO.readUsers();
		ArrayList<SettingsUser> aSettingsUserToHDD = new ArrayList<SettingsUser>();
		for (SettingsUser su : aSettingsUserFromHDD) {
			for (IUser iu : iDiscordClient.getUsers()) {
				if (!iu.isBot() && String.valueOf(su.getUserID()).equals(String.valueOf(iu.getLongID()))) {
				} else {
					IUser newIUser = iu;
					aSettingsUserToHDD.add(
							new SettingsUser(String.valueOf(newIUser.getLongID()), String.valueOf(newIUser.getName())));
				}
			}
		}
		aSettingsUserFromHDD.addAll(aSettingsUserToHDD);
		IO.writeUsers(aSettingsUserFromHDD);
	}

}
