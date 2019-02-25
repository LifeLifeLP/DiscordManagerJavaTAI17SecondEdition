/**
 * 
 */
package lifelifelp.botfuctions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import lifelifelp.games.GameNummberGuess;
import lifelifelp.games.GameTicTacToe;
import lifelifelp.games.TicTacToeSavegame;
import lifelifelp.tools.UnicodeEmoji;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;
import sx.blah.discord.handle.obj.IEmoji;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IMessage.Attachment;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;
import sx.blah.discord.util.audio.AudioPlayer;

public class DiscordEvents{
	// Das Präfix einer Nachricht, wenn eine Nachricht damit beginnt wird der Bot sie beachten
	static String BOT_PREFIX = "p!";
	
	//Die @Anbindung wird ben�tig damit die Funktion bei einem Event ausgef�hrt wird
	
	
	@EventSubscriber
    public void onReactionAdd(ReactionAddEvent event){
		System.out.println(event.getReaction().getEmoji().getName());
		//Bot reacted auf Reactions
		if(!event.getUser().isBot()) {
			for(TicTacToeSavegame ttts: GameTicTacToe.GameData) {
				if(String.valueOf(event.getChannel().getLongID()).equals(String.valueOf(ttts.getGameChannel().getLongID()))) {
					//TTTS ist der aktuelle Spielstand
					ttts.setActivePlayer(true);
					if(ttts.getActivePlayer()) {
						//Reihe1
						for(IReaction ir: ttts.getRow1().getReactions()) {
							if(ir.getUserReacted(ttts.getGameuser())) {
								switch(ir.getEmoji().getName()){
						        case "🇱":
						        	if(ttts.getPlayfield().get(0) == 0) {
						        		ttts.getPlayfield().set(0, 1);//Spieler setzen
						        		ttts.getRow1().removeReaction(ttts.getGameuser(), ir.getEmoji());
						        		GameTicTacToe.reloadPlayfield(ttts);
						        		ttts.setActivePlayer(false);
						        		GameTicTacToe.botMadeMove(ttts);
						        	}else {
						        		
						        		//Platz ist voll vom Spieler/Bot
						        	}
						            break;
						        case "🇲":
						        	if(ttts.getPlayfield().get(1) == 0) {
						        		ttts.getPlayfield().set(1, 1);//Spieler setzen
						        		ttts.getRow1().removeReaction(ttts.getGameuser(), ir.getEmoji());
						        		GameTicTacToe.reloadPlayfield(ttts);
						        		ttts.setActivePlayer(false);
						        		GameTicTacToe.botMadeMove(ttts);
						        	}else {
						        		
						        		//Platz ist voll vom Spieler/Bot
						        	}
						            break;
						        case "🇷":
						        	if(ttts.getPlayfield().get(2) == 0) {
						        		ttts.getPlayfield().set(2, 1);//Spieler setzen
						        		ttts.getRow1().removeReaction(ttts.getGameuser(), ir.getEmoji());
						        		GameTicTacToe.reloadPlayfield(ttts);
						        		ttts.setActivePlayer(false);
						        		GameTicTacToe.botMadeMove(ttts);
						        	}else {
						        		
						        		//Platz ist voll vom Spieler/Bot
						        	}
						            break;
						        default:
						            //Nichts
						        } 
							}
						}
						//Reihe1
						
						//Reihe2
						for(IReaction ir: ttts.getRow2().getReactions()) {
							if(ir.getUserReacted(ttts.getGameuser())) {
								switch(ir.getEmoji().getName()){
						        case "🇱":
						        	if(ttts.getPlayfield().get(3) == 0) {
						        		ttts.getPlayfield().set(3, 1);//Spieler setzen
						        		ttts.getRow2().removeReaction(ttts.getGameuser(), ir.getEmoji());
						        		GameTicTacToe.reloadPlayfield(ttts);
						        		ttts.setActivePlayer(false);
						        		GameTicTacToe.botMadeMove(ttts);
						        	}else {
						        		
						        		//Platz ist voll vom Spieler/Bot
						        	}
						            break;
						        case "🇲":
						        	if(ttts.getPlayfield().get(4) == 0) {
						        		ttts.getPlayfield().set(4, 1);//Spieler setzen
						        		ttts.getRow2().removeReaction(ttts.getGameuser(), ir.getEmoji());
						        		GameTicTacToe.reloadPlayfield(ttts);
						        		ttts.setActivePlayer(false);
						        		GameTicTacToe.botMadeMove(ttts);
						        	}else {
						        		
						        		//Platz ist voll vom Spieler/Bot
						        	}
						            break;
						        case "🇷":
						        	if(ttts.getPlayfield().get(5) == 0) {
						        		ttts.getPlayfield().set(5, 1);//Spieler setzen
						        		ttts.getRow2().removeReaction(ttts.getGameuser(), ir.getEmoji());
						        		GameTicTacToe.reloadPlayfield(ttts);
						        		ttts.setActivePlayer(false);
						        		GameTicTacToe.botMadeMove(ttts);
						        	}else {
						        		
						        		//Platz ist voll vom Spieler/Bot
						        	}
						            break;
						        default:
						            //Nichts
						        } 
							}
						}
						//Reihe2
						
						//Reihe3
						for(IReaction ir: ttts.getRow3().getReactions()) {
							if(ir.getUserReacted(ttts.getGameuser())) {
								switch(ir.getEmoji().getName()){
						        case "🇱":
						        	if(ttts.getPlayfield().get(6) == 0) {
						        		ttts.getPlayfield().set(6, 1);//Spieler setzen
						        		ttts.getRow3().removeReaction(ttts.getGameuser(), ir.getEmoji());
						        		GameTicTacToe.reloadPlayfield(ttts);
						        		ttts.setActivePlayer(false);
						        		GameTicTacToe.botMadeMove(ttts);
						        	}else {
						        		
						        		//Platz ist voll vom Spieler/Bot
						        	}
						            break;
						        case "🇲":
						        	if(ttts.getPlayfield().get(7) == 0) {
						        		ttts.getPlayfield().set(7, 1);//Spieler setzen
						        		ttts.getRow3().removeReaction(ttts.getGameuser(), ir.getEmoji());
						        		GameTicTacToe.reloadPlayfield(ttts);
						        		ttts.setActivePlayer(false);
						        		GameTicTacToe.botMadeMove(ttts);
						        	}else {
						        		
						        		//Platz ist voll vom Spieler/Bot
						        	}
						            break;
						        case "🇷":
						        	if(ttts.getPlayfield().get(8) == 0) {
						        		ttts.getPlayfield().set(8, 1);//Spieler setzen
						        		ttts.getRow3().removeReaction(ttts.getGameuser(), ir.getEmoji());
						        		GameTicTacToe.reloadPlayfield(ttts);
						        		ttts.setActivePlayer(false);
						        		GameTicTacToe.botMadeMove(ttts);
						        	}else {
						        		
						        		//Platz ist voll vom Spieler/Bot
						        	}
						            break;
						        } 
							}
						}
						//Reihe3
					}else {
						//Nichts
					}
				}
			}
		}else {
			//Nichts
		}
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
    		String input = StringUtils.replace(event.getMessage().getContent(), "p!clean ", "");
    		List<IRole> roles = event.getAuthor().getRolesForGuild(event.getGuild());
    		Permissions ADMINISTRATOR = Permissions.ADMINISTRATOR;
    		for(IRole ir: roles) {
    			System.out.println(ir.getName());
    			if(ir.getPermissions().contains(ADMINISTRATOR)) {
    				int toremove = Integer.valueOf(input)+1;
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
    	 if(event.getMessage().getContent().startsWith(BotFunctions.BOT_PREFIX.toLowerCase() + "gamedone".toLowerCase())){
    		 GameNummberGuess.done(event);
    		 GameTicTacToe.done(event);
    	 }
    	 
    	 if(event.getMessage().getContent().startsWith(BotFunctions.BOT_PREFIX.toLowerCase() + "starttictactoe".toLowerCase())) {
    		 GameTicTacToe.start(event);
    	 }
    	 if(event.getMessage().getContent().startsWith(BotFunctions.BOT_PREFIX.toLowerCase() + "tttdone".toLowerCase())) {
    		 GameTicTacToe.done(event);
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
        	ThreadMusicPlayer threadMP = new ThreadMusicPlayer().run(event);
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
        
        
        
    
