/**
 * 
 */
package lifelifelp;

import javax.swing.JOptionPane;

import lifelifelp.io.IOfunctions;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IUser;

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
	
	@EventSubscriber
    public void onReadyEvent(ReadyEvent event){
		JOptionPane.showMessageDialog(null, null, null, 0);
		IOfunctions.updateUserDatabase(event.getClient());
		IOfunctions.loadUserDatabase(event.getClient());
		System.out.println("Startup done!");
	}

}
