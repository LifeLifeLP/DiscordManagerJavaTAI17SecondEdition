/**
 * 
 */
package lifelifelp.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author K.Schulz
 *
 */
public class IO {
	//Ungenutzt soll sp�ter genutzt werden um einen Headless-Betrieb auf einem Server zu erm�glichen
	@SuppressWarnings("unused")
	public static boolean loadSetting(){
		boolean ret = false;
		try {
			File f = new File("src\\settings.txt");
			BufferedReader br = new BufferedReader(new FileReader(f));
			String botID = br.readLine();
			String botNickname = br.readLine();
			String canBanBots = br.readLine();
			String headlessMode = br.readLine();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
			return ret;
		}
		return ret;
	}

}
