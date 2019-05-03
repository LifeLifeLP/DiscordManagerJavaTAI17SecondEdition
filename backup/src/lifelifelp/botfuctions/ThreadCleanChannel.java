package lifelifelp.botfuctions;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.RequestBuffer;

public class ThreadCleanChannel extends Thread{
	MessageReceivedEvent event;
	
	public ThreadCleanChannel(MessageReceivedEvent event) {
		this.event = event;
	}
	
	public void run() {
		String input = StringUtils.replace(event.getMessage().getContent(), "p!clean ", "");
		List<IRole> roles = event.getAuthor().getRolesForGuild(event.getGuild());
		Permissions ADMINISTRATOR = Permissions.ADMINISTRATOR;
		for(IRole ir: roles) {
			if(ir.getPermissions().contains(ADMINISTRATOR)) {
				int toremove = Integer.valueOf(input);
	    		if(toremove > 99) {
	    			List<IMessage> lim = event.getChannel().getMessageHistory(toremove);
	    			for(IMessage im: lim) {
	    				RequestBuffer.request(() -> {
	        				im.delete();
	                	});
	    			}
	    			RequestBuffer.request(() -> {
	    				event.getMessage().delete();
	            	});
	    		}else {
	    			RequestBuffer.request(() -> {
	    				event.getChannel().bulkDelete(event.getChannel().getMessageHistory(toremove));
	    				RequestBuffer.request(() -> {
	    					event.getMessage().delete();
	                	});
	            	});
	    		}
				break;
			}
		}		
	}
}
