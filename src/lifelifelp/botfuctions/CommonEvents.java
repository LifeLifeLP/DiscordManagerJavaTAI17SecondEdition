/**
 * 
 */
package lifelifelp.botfuctions;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import lifelifelp.games.GameNummberGuess;
import lifelifelp.games.GameTicTacToe;
import lifelifelp.io.IOfunctions;
import lifelifelp.tools.UnicodeEmoji;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IEmoji;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;
import sx.blah.discord.util.audio.AudioPlayer;

public class CommonEvents{
	// Das Präfix einer Nachricht, wenn eine Nachricht damit beginnt wird der Bot sie beachten
	static String BOT_PREFIX = "p!";
	
	//Die @Anbindung wird ben�tig damit die Funktion bei einem Event ausgef�hrt wird
	
	
	@EventSubscriber
    public void onReactionAdd(ReactionAddEvent event){
		GameTicTacToe.move(event);
	}
	
	
	
    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event){ //Dies ist der EventReceiver f�r alle Nachricht erhalten Events, dies wird bei JEDER erhalten Nachricht ausgef�hrt
    	if(event.getMessage().getContent().startsWith(BotFunctions.BOT_PREFIX + "meme")){
    		InputStream input = null;
			try {
				input = new URL("http://www.stackoverflow.com").openStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	Document document = new Tidy().parseDOM(input, null);
        	NodeList imgs = document.getElementsByTagName("img");
        	List<String> srcs = new ArrayList<String>();

        	for (int i = 0; i < imgs.getLength(); i++) {
        	    srcs.add(imgs.item(i).getAttributes().getNamedItem("src").getNodeValue());
        	}

        	for (String src: srcs) {
        	    System.out.println(src);
        	}
		}
    	
    	if(event.getMessage().getContent().startsWith(BotFunctions.BOT_PREFIX + "fuckmax")){
    		BotFunctions.saveRole(event.getGuild(), "MUTEMAX", new Color(255, 255, 255), false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
    		EnumSet<Permissions> none = EnumSet.noneOf(Permissions.class);
    		EnumSet<Permissions> all = EnumSet.allOf(Permissions.class);
    		for(IChannel ic: event.getGuild().getChannels()) {
    			System.out.println("Gruppe erstellt für Channel: "+ic.getName() );
    			RequestBuffer.request(() -> {
    				ic.overrideRolePermissions(event.getGuild().getRolesByName("MUTEMAX").get(0), none, all);
    			});
    			RequestBuffer.request(() -> {
    				ic.removePermissionsOverride(event.getGuild().getUserByID(Long.parseLong("124227286575742978")));
    			});
    		}
    	}
    	
    	//Funktion um dem Bot einen Nachricht in einem privaten Chat ausrichten zu lassen
    	if(event.getMessage().getContent().startsWith(BotFunctions.BOT_PREFIX + "pm")){
			String input = StringUtils.replace(event.getMessage().getContent(), "p!pm ", "");
			String user = StringUtils.substring(input, 3, 21);
			IUser toBeCalled = event.getClient().getUserByID(Long.valueOf(user));
			IPrivateChannel ipc = toBeCalled.getOrCreatePMChannel();
			input = StringUtils.replace(input, user, "");
			String tosend = input;
			RequestBuffer.request(() -> {
				try {
					
					ipc.sendMessage("Ich soll dir von <@"+event.getAuthor().getLongID()+"> ausrichten: "+tosend);
				} catch (DiscordException e) {
					System.err.println("Message could not be sent with error: ");
					e.printStackTrace();
				}
			});
		}
    	
    	//Funktion um eine Anzahl an Nachrichten aus einem Channel zu loeschen, sofern es weniger als 99 �berlassen wird Discord die Arbeit(.bulkDelete) andernfalls macht es der Bot selbst
    	//f�r jede Nachricht was aber um einiges langsamer ist, jedoch gr��ere Mengen schaft
    	if(event.getMessage().getContent().startsWith(BotFunctions.BOT_PREFIX + "clean")){
    		ThreadCleanChannel tch = new ThreadCleanChannel(event);
    		tch.start();
    	}
    	
    	//Verbannt einen Benutzer vom Server wenn der Benutzer den den Befehl schreibt das Recht Administrator hat
    	if(event.getMessage().getContent().startsWith(BotFunctions.BOT_PREFIX.toLowerCase() + "ban".toLowerCase())) {
    		for(IRole ir: event.getAuthor().getRolesForGuild(event.getGuild())) {
    			EnumSet<Permissions> es = ir.getPermissions();
    			if(es.contains(Permissions.BAN)) {
    				String input = StringUtils.replace(event.getMessage().getContent(), "p!ban ", "");
    				String user = StringUtils.substring(input, 0, 23);
    				IUser toBeBanned = event.getGuild().getUserByID(Long.valueOf(user));
    				input = StringUtils.replace(input, user, "");
    				if(toBeBanned.isBot()) {
    					event.getChannel().sendMessage("Ich kann keine anderen Bots bannen!");
    					break;
    				}
    				event.getGuild().banUser(toBeBanned, input);
    				break;
    			}
    		}
    		event.getMessage().delete();
   		 }
    	
    	//Start das Spiel Zahlenraten
    	 if(event.getMessage().getContent().startsWith(BotFunctions.BOT_PREFIX.toLowerCase() + "startguessgame".toLowerCase())) {
    		 GameNummberGuess.start(event);
    	 }
    	 
    	 //Nimmt die vom User geratene Zahl entgegen
    	 if(event.getMessage().getContent().startsWith(BotFunctions.BOT_PREFIX.toLowerCase() + "guess".toLowerCase())){
    		 GameNummberGuess.guess(event);
    	 }
    	 //Beendet das Spiel mit dem User
    	 if(event.getMessage().getContent().startsWith(BotFunctions.BOT_PREFIX.toLowerCase() + "guessgamedone".toLowerCase())){
    		 GameNummberGuess.done(event);
    	 }
    	 
    	 if(event.getMessage().getContent().startsWith(BotFunctions.BOT_PREFIX.toLowerCase() + "starttictactoe".toLowerCase())) {
    		 GameTicTacToe.start(event);
    	 }
    	 if(event.getMessage().getContent().startsWith(BotFunctions.BOT_PREFIX.toLowerCase() + "tttdone".toLowerCase())) {
    		 GameTicTacToe.done(event);
    	 }
    	 if(event.getMessage().getContent().startsWith(BotFunctions.BOT_PREFIX.toLowerCase() + "scannow".toLowerCase())) {
    		 IOfunctions.updateUserDatabase(event.getClient());
    	 }
    	 
    	 //Schreibt unter jeder Nachrichte die Max Schr�der schreibt seinen Namen und das Server Emoj "MAX_WHEELCHAIR"
        if(event.getMessage().getAuthor().getLongID() == Long.parseLong("124227286575742978")) {
        	RequestBuffer.request(() -> {
        		event.getMessage().addReaction(UnicodeEmoji.M);
        		RequestBuffer.request(() -> {
            		event.getMessage().addReaction(UnicodeEmoji.A);
            		RequestBuffer.request(() -> {
                		event.getMessage().addReaction(UnicodeEmoji.X);
                		IEmoji maxwheelchair;
                		maxwheelchair = event.getClient().getGuildByID(Long.parseLong("491582161489625120")).getEmojiByName("MAX_WHEELCHAIR");
                		event.getMessage().addReaction(maxwheelchair);
                	});
            	});
        	});
        }
        
        //Wenn der User in einem VoiceChannel ist wird der Bot dem VoiceChannel ebenfalls betretenm sofern den Bot den Channel sehen kann und auch Rechte zum beitreten hat
        //Zu beachten ist das der Bot auf einem Server immer nur in einem Channel gleichzeitg sein kann
        //So kann der Bot auch zwischen den Channels hin und her bewegt werden
        if(event.getMessage().getContent().startsWith(BotFunctions.BOT_PREFIX + "join")){
        	boolean foundUser = false;
        	BotFunctions.sendMessage(event.getChannel(), "Einen Moment!");
        	List<IVoiceChannel> tmp = event.getGuild().getVoiceChannels();
        	for(IVoiceChannel ic: tmp) {
        		List<IUser> tmp2 =ic.getUsersHere();
        		for(IUser iu: tmp2) {
        			if(event.getAuthor().equals(iu)) {
        				ic.join();
        				foundUser = true;
        				break;
        			}
        		}
        		if(foundUser) {
        			break;
        		}
        	}
        }
        
        //L�dt die an die Nachricht angeh�gte Musikdatei von Discord,s Servern runter und gibt sie im Voicechannel wieder
        if(event.getMessage().getContent().startsWith(BotFunctions.BOT_PREFIX + "play")){
        	ThreadMusicPlayer threadMP = new ThreadMusicPlayer(event);
        	threadMP.start();
        }
        	
        //Stopt die Musikwiedergabe im Channel und entfernt die Musik aus der Wiedergabe
        if(event.getMessage().getContent().startsWith(BotFunctions.BOT_PREFIX + "stop")){
        	AudioPlayer audioP = AudioPlayer.getAudioPlayerForGuild(event.getGuild());
            audioP.clean();
            audioP.clear();
        }
        
        //Bringt den Bot dazu den VoiceChannel zu verlassen
        if(event.getMessage().getContent().startsWith(BotFunctions.BOT_PREFIX + "leave")){
        	boolean foundUser = false;
        	BotFunctions.sendMessage(event.getChannel(), "Einen Moment!");
        	List<IVoiceChannel> tmp = event.getGuild().getVoiceChannels();
        	for(IVoiceChannel ic: tmp) {
        		List<IUser> tmp2 =ic.getUsersHere();
        		for(IUser iu: tmp2) {
        			if(event.getAuthor().equals(iu)) {
        				ic.leave();
        				foundUser = true;
        				break;
        			}
        		}
        		if(foundUser) {
        			break;
        			//End
        		}
        	}
        }
    		}
        }
        
        
        
    
