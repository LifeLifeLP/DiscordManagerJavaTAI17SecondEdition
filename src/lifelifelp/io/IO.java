/**
 * 
 */
package lifelifelp.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * @author K.Schulz
 *
 */
public class IO {
	//Ungenutzt soll sp�ter genutzt werden um einen Headless-Betrieb auf einem Server zu erm�glichen
	@SuppressWarnings("unused")
	public static SettingsCommon loadSetting(){
		SettingsCommon settingsCommon = new SettingsCommon();
		try {
			File f = new File("src\\settings.txt");
			BufferedReader br = new BufferedReader(new FileReader(f));
			//BotID
			settingsCommon.setBotID(br.readLine());
			String headlessMode = br.readLine();
			if(headlessMode.equals("TRUE")) {
				settingsCommon.setHeadlessmode(true);
			}else {
				settingsCommon.setHeadlessmode(false);
			}
			String botNickname = br.readLine();
			String canBanBots = br.readLine();
			br.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, String.valueOf(e.toString()), String.valueOf(e.toString()), 0);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return settingsCommon;
	}

	public static SettingsCommon set() {
		// TODO Auto-generated method stub
		SettingsCommon s = new SettingsCommon();
		
		return s;
	}

	 protected static ArrayList<SettingsUser> readUsers() {
		ArrayList<SettingsUser> aSU = new ArrayList<SettingsUser>();
			try {
				File f = new File("src\\settingsUser.txt");
				BufferedReader br = new BufferedReader(new FileReader(f));
				while(true) {
					SettingsUser su = new SettingsUser();
					su.setUserID(br.readLine());
					su.setPersonalNickname(br.readLine());
					if(br.readLine().equals("####CUT####")) {
					}else {
						break;
					}
				}
				
				br.close();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, String.valueOf(e.toString()), String.valueOf(e.toString()), 0);
				e.printStackTrace();
				System.exit(1);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		// TODO Auto-generated method stub
		return null;
	}

}
