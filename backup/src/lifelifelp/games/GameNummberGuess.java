/**
 * 
 */
package lifelifelp.games;

import java.awt.Color;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import lifelifelp.botfuctions.BotFunctions;
import lifelifelp.tools.UnicodeEmoji;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.RequestBuffer;

/**
 * @author K.Schulz
 *
 */
public class GameNummberGuess {
	//Spielstandspeicher, jedes Objet im Array ist ein Spielstand, f�r den Inhalt sie NumberGuessSavegame.java
	public static ArrayList<NumberGuessSavegame> GameData = new ArrayList<NumberGuessSavegame>();
	/**
	 * 
	 */
	public GameNummberGuess() {
		//Leere Main Metode
	}
	
	
	//Schaut ob der erhaltenden User bereits ein Spiel spielt
	public static boolean checkForGames(MessageReceivedEvent event) {
		for(NumberGuessSavegame ngs: GameData) {
			if(ngs.getGameuser().equals(event.getAuthor())) {
				return true;
			}else {
				//Nichts
			}
		}
		return false;
		
	}
	
	//Bereitet das Spiel vor und l�dt den Spieler im Anschluss ein
	public static void start(MessageReceivedEvent event) {
		String channelname = StringUtils.replace(event.getAuthor().getName().toLowerCase()+"-gameroom", " ", "-");
		if(!checkForGames(event)) {
			Random rng = new Random();
			
			RequestBuffer.request(() -> {
			
			//Erstellt einen einfachen TextChannel
			lifelifelp.botfuctions.BotFunctions.CreateChannel(event.getGuild(), event.getAuthor().getName() + "-gameroom", true, "Rate mal?", false,0, 0, null, 0);
				
			RequestBuffer.request(() -> {
			
			//Erstellung eines Savegames mit Spieler und Spielchannels
			NumberGuessSavegame ngsSTART = new NumberGuessSavegame(event.getAuthor(), event.getChannel());
			
			//Schreibt die L�sung in die Konsole
			System.out.println("ngsSTART =" + ngsSTART.getGoal());
			
			//Gibt dem Savegame einen Namen
			ngsSTART.setName(channelname);
			
			//Schreibt den Spielstand in den Spielstandsspeicher
			GameData.add(ngsSTART);
			
			RequestBuffer.request(() -> {
			
			//Gibt den User bescheidt das es losgehen kann
			event.getGuild().getChannelsByName(channelname).get(0).sendMessage("Ok, ich bin bereit! :)");
			
			RequestBuffer.request(() -> {
			
			//Gibt den User im Startchanenel einen Link zu seinem Gameroom wo er mit dem Bot spielen wird
			event.getChannel().sendMessage("Wir sehen uns in <#" + event.getGuild().getChannelsByName(channelname).get(0).getLongID() + "> !");
			
			RequestBuffer.request(() -> {
			
			//Der Bot erstellt eine Rolle mit Standard User-Rechten mir Namen des Spielers
			BotFunctions.saveRole(event.getGuild(), channelname,new Color(rng.nextInt(255), rng.nextInt(255), rng.nextInt(255)), false, false, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, true, false, false, true, true, true, true, true, false, false, false, false, false);
			
			RequestBuffer.request(() -> {
			
			//Vorbereitung: Erstellt ein EnumSet ohne Rechte zu irgentwas
			EnumSet<Permissions> noPermissions = EnumSet.noneOf(Permissions.class);
			
			//Vorbereitung: Erstellt ein EnumSet mit allen Rechten
			EnumSet<Permissions> allPermissions = EnumSet.allOf(Permissions.class);
			
			//Entfernt f�r die Everyone Rolle alle Recht im Gamechanenl, so das niemand au�er Administratoren den Chanenl sehen k�nnen
			event.getGuild().getChannelsByName(channelname).get(0).overrideRolePermissions(event.getGuild().getEveryoneRole(), noPermissions, allPermissions);
			
			RequestBuffer.request(() -> {
			
			//Gibt der Gruppe die f�r den Spieler erstellt wurde alle Rechte zu diesem Channel
			event.getGuild().getChannelsByName(channelname).get(0).overrideRolePermissions(event.getGuild().getRolesByName(channelname).get(0), allPermissions, noPermissions);
			
			RequestBuffer.request(() -> {
			
			//Gibt den Spieler die zuvor erstellte Rolle wodurch er seinen Gameroom nun sehen kann um dort mit dem Bot schreiben zu k�nnen ohne das jemand davon gest�rt wird
			event.getAuthor().addRole(event.getGuild().getRolesByName(channelname).get(0));
			});
			});
			});
			});
			});
			});
			});
			});
		}else {
			event.getChannel().sendMessage("Wir spielen doch schon zusammen <@"+event.getAuthor().getLongID()+"> ! \n In <#"+event.getGuild().getChannelsByName(channelname).get(0).getLongID()+">");
		}
		
	}
	
	//Verarbeit die geratene Zahl des Spielers
	public static void guess(MessageReceivedEvent event) {
		//Schaut ob der User mit dem Bot spielt, dann werdne die Startnachrichten entfernt falls sie da sind, gleiches gilt f�r den Gameroom
		if(checkForGames(event)) {
			for(NumberGuessSavegame ngs: GameData) {
				if(event.getChannel().getName().equals(ngs.getName())) {
					if(ngs.getGameuser().equals(event.getAuthor())) {
						event.getChannel().setTypingStatus(true);
						for(IMessage tmp: event.getChannel().getFullMessageHistory()) {
							if(event.getChannel().getFullMessageHistory().size() != 0) {
								tmp.delete();
							}
						}
						for(IMessage tmp: ngs.getSourceChannel().getMessageHistory()) {
							if(tmp.getAuthor().equals(ngs.getGameuser()) && tmp.getContent().equals("p!startguessgame")) {
								tmp.delete();
							}
							if(tmp.getAuthor().equals(event.getClient().getOurUser()) && StringUtils.startsWith(tmp.getContent(), "Wir sehen uns in <#"+event.getChannel().getLongID()+">")) {
								tmp.delete();
							}
						}
						//Spiellogik, Simpel ist die Zahl zu gro� wird das dem User gesagt und der Versuchscouter um einen Punkt hochgez�hlt
						if(ngs.getGoal() > Integer.parseInt(StringUtils.replace(event.getMessage().getContent(), "p!guess ", ""))) {
							event.getChannel().sendMessage("<@"+event.getAuthor().getLongID()+"> die Zahl "+StringUtils.replace(event.getMessage().getContent(), "p!guess ", "")+" \n ist zu klein!");
							event.getMessage().delete();
							ngs.setTrys(ngs.getTrys()+1);
						}else {
							if(ngs.getGoal() < Integer.parseInt(StringUtils.replace(event.getMessage().getContent(), "p!guess ", ""))) {
								event.getChannel().sendMessage("<@"+event.getAuthor().getLongID()+"> die Zahl "+StringUtils.replace(event.getMessage().getContent(), "p!guess ", "")+" \n ist zu gross!");
								event.getMessage().delete();
								ngs.setTrys(ngs.getTrys()+1);
							}else {
								if(ngs.getGoal() == Integer.parseInt(StringUtils.replace(event.getMessage().getContent(), "p!guess ", ""))) {
									event.getChannel().sendMessage("<@"+event.getAuthor().getLongID()+"> die Zahl "+StringUtils.replace(event.getMessage().getContent(), "p!guess ", "")+" \n ist RICHTIG!");
									event.getChannel().getMessageHistory().getLatestMessage().addReaction(UnicodeEmoji.TADA);
									event.getMessage().delete();
									showWin(event, ngs);
								}else {
									//Nichts
								}
							}
						}
					}
				}
			}
		}else {
			event.getMessage().delete();
		}
	}
	
	//Informiert den Spieler das er die Zalh erraten hat
	private static void showWin(MessageReceivedEvent event, NumberGuessSavegame ngs) {
		event.getChannel().sendMessage("Du hast "+ngs.getTrys()+" Versuche gebraucht(Die Zahl lautete:"+ngs.getGoal()+") \nBitte schreibe noch p!gamedone !");
		event.getChannel().setTypingStatus(false);
	}
	
	
	//Nach Spielende wird der Channel,die Rolle und das Savegame gel�scht
	public static void done(MessageReceivedEvent event) {
		if(checkForGames(event)) {
			int tmp = 0;
			for(NumberGuessSavegame ngs : GameData) {
				if(event.getAuthor().equals(ngs.getGameuser()) && event.getChannel().getName().equals(ngs.getName()) && event.getGuild().getRolesByName(ngs.getName()).get(0).getName().equals(ngs.getName())){
					event.getChannel().delete();
					event.getGuild().getRolesByName(ngs.getName()).get(0).delete();
					GameData.remove(tmp);
					break;
				}else {
					tmp++;
				}
			}
		}
	}
}

