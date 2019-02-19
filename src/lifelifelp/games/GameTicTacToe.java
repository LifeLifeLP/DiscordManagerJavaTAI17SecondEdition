/**
 * 
 */
package lifelifelp.games;

import java.awt.Color;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;

import lifelifelp.botfuctions.BotFunctions;
import lifelifelp.tools.UnicodeEmoji;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.RequestBuffer;

/**
 * @author LifeLifeLP
 *
 */
/*
 * 
 * 
 *Dieses Spiel ist leider nicht fertig geworden, daher nicht funktionsfähig bzw. Testzustand 
 * 
 * 
 * 
 * 
 * 
 */
public class GameTicTacToe {
	public static ArrayList<TicTacToeSavegame> GameData = new ArrayList<TicTacToeSavegame>();

	/**
	 * 
	 */
	public GameTicTacToe() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean checkForGames(MessageReceivedEvent event) {
		for(TicTacToeSavegame ttts: GameData) {
			if(ttts.getGameuser().equals(event.getAuthor())) {
				return true;
			}else {
				//Nichts
			}
		}
		return false;
		
	}
	
	public static void start(MessageReceivedEvent event) {
		String channelname = StringUtils.replace(event.getAuthor().getName().toLowerCase() + "-gameroom", " ", "-");
		if (!checkForGames(event)) {
			Random rng = new Random();

			RequestBuffer.request(() -> {
				lifelifelp.botfuctions.BotFunctions.CreateChannel(event.getGuild(),
						event.getAuthor().getName() + "-gameroom", true, "Tic Tac und Toe", false, 0, 0, null, 0);
				RequestBuffer.request(() -> {
					TicTacToeSavegame ttts = new TicTacToeSavegame();
					ttts.setGameuser(event.getAuthor());
					ttts.setSourceChannel(event.getChannel());
					ttts.setGameChannel(event.getGuild().getChannelsByName(channelname).get(0));
					ArrayList<Integer> iAL = new ArrayList<Integer>();
					while (iAL.size() != 9) {
						//iAL.add(0);
						iAL.add(ThreadLocalRandom.current().nextInt(0, 2 + 1));
						System.out.println("iAL =" + iAL.size());
					}
					ttts.setPlayfield(iAL);
					GameData.add(ttts);
					RequestBuffer.request(() -> {
						ttts.getGameChannel().sendMessage("Ok, ich bin bereit!");
						RequestBuffer.request(() -> {
							event.getChannel().sendMessage("Wir sehen uns in <#"
									+ event.getGuild().getChannelsByName(channelname).get(0).getLongID() + "> !");
							RequestBuffer.request(() -> {
								BotFunctions.saveRole(event.getGuild(), channelname,
										new Color(rng.nextInt(255), rng.nextInt(255), rng.nextInt(255)), false, false,
										true, true, true, true, true, false, false, false, false, false, false, false,
										false, false, false, true, false, false, true, true, true, true, true, false,
										false, false, false, false);
								RequestBuffer.request(() -> {
									EnumSet<Permissions> noPermissions = EnumSet.noneOf(Permissions.class);
									EnumSet<Permissions> allPermissions = EnumSet.allOf(Permissions.class);
									event.getGuild().getChannelsByName(channelname).get(0).overrideRolePermissions(
											event.getGuild().getEveryoneRole(), noPermissions, allPermissions);
									RequestBuffer.request(() -> {
										event.getAuthor().addRole(event.getGuild().getRolesByName(channelname).get(0));
										RequestBuffer.request(() -> {
											event.getGuild().getChannelsByName(channelname).get(0)
													.overrideRolePermissions(
															event.getGuild().getRolesByName(channelname).get(0),
															allPermissions, noPermissions);
											printPlayfield(ttts);
											printPlayerChoice(ttts);
										});
									});
								});
							});
						});
					});
				});
			});
		} else {
			event.getChannel().sendMessage("Wir spielen doch schon zusammen <@" + event.getAuthor().getLongID()
					+ "> ! \n In <#" + event.getGuild().getChannelsByName(channelname).get(0).getLongID() + ">");
		}
	}

	private static void printPlayerChoice(TicTacToeSavegame ttts) {
		// TODO Auto-generated method stub
		ttts.getGameChannel().setTypingStatus(true);
		IMessage row1 = ttts.getGameChannel().sendMessage("Reihe:1");
		row1.addReaction(UnicodeEmoji.L);
		row1.addReaction(UnicodeEmoji.M);
		row1.addReaction(UnicodeEmoji.R);
		IMessage row2 = ttts.getGameChannel().sendMessage("Reihe:2");
		row2.addReaction(UnicodeEmoji.L);
		row2.addReaction(UnicodeEmoji.M);
		row2.addReaction(UnicodeEmoji.R);
		IMessage row3 = ttts.getGameChannel().sendMessage("Reihe:3");
		row3.addReaction(UnicodeEmoji.L);
		row3.addReaction(UnicodeEmoji.M);
		row3.addReaction(UnicodeEmoji.R);
	}

	private static void printPlayfield(TicTacToeSavegame ttts) {
		// TODO Auto-generated method stub
		ttts.getGameChannel().getFullMessageHistory().bulkDelete();
		
		StringBuilder sbPlayfield = new StringBuilder();
		
		{
			for(int i = 0; i != 3; i++) {
				switch (ttts.getPlayfield().get(i)) {
				case 0:
					sbPlayfield.append(UnicodeEmoji.TTTCLEAR);
					break;
				case 1:
					sbPlayfield.append(UnicodeEmoji.TTTX);
					break;
				case 2:
					sbPlayfield.append(UnicodeEmoji.TTTO);
					break;
				}
			}
			sbPlayfield.append("\n");
			for(int i = 3; i != 6; i++) {
				switch (ttts.getPlayfield().get(i)) {
				case 0:
					sbPlayfield.append(UnicodeEmoji.TTTCLEAR);
					break;
				case 1:
					sbPlayfield.append(UnicodeEmoji.TTTX);
					break;
				case 2:
					sbPlayfield.append(UnicodeEmoji.TTTO);
					break;
				}
			}
			sbPlayfield.append("\n");
			for(int i = 6; i != 9; i++) {
				switch (ttts.getPlayfield().get(i)) {
				case 0:
					sbPlayfield.append(UnicodeEmoji.TTTCLEAR);
					break;
				case 1:
					sbPlayfield.append(UnicodeEmoji.TTTX);
					break;
				case 2:
					sbPlayfield.append(UnicodeEmoji.TTTO);
					break;
				}
			}
		}
		ttts.getGameChannel().sendMessage(sbPlayfield.toString());
	}
	
	
	
	
	
	
}
