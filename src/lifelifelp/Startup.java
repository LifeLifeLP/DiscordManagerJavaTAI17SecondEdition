/**
 * 
 */
package lifelifelp;

import lifelifelp.io.IOfunctions;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;

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
		IOfunctions.updateUserDatabase(event.getClient());
		IOfunctions.loadUserDatabase(event.getClient());
	}

}
