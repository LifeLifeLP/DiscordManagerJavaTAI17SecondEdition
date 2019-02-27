/**
 * 
 */
package lifelifelp.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * @author K.Schulz
 *
 */
public class IO {
	//Ungenutzt soll sp�ter genutzt werden um einen Headless-Betrieb auf einem Server zu erm�glichen
	@SuppressWarnings("unused")
	public static Settings loadSetting(){
		Settings settings = new Settings();
		try {
			File f = new File("src\\settings.txt");
			BufferedReader br = new BufferedReader(new FileReader(f));
			//BotID
			settings.setBotID(br.readLine());
			String headlessMode = br.readLine();
			if(headlessMode.equals("TRUE")) {
				settings.setHeadlessmode(true);
			}else {
				settings.setHeadlessmode(false);
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
		return settings;
	}

	public static Settings set() {
		// TODO Auto-generated method stub
		Settings s = new Settings();
		
		return s;
	}

}
