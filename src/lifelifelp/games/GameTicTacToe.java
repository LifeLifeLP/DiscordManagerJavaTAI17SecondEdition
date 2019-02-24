/**
 * 
 */
package lifelifelp.games;

import java.awt.Color;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import lifelifelp.botfuctions.BotFunctions;
import lifelifelp.tools.UnicodeEmoji;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;
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
						iAL.add(0);
						//iAL.add(ThreadLocalRandom.current().nextInt(0, 2 + 1));
						System.out.println("iAL =" + iAL.size());
					}
					ttts.setPlayfield(iAL);
					RequestBuffer.request(() -> {
						ttts.getGameChannel().sendMessage("Ok, ich bin bereit!");
						RequestBuffer.request(() -> {
							event.getChannel().sendMessage("Wir sehen uns in <#"
									+ event.getGuild().getChannelsByName(channelname).get(0).getLongID() + "> !");
							RequestBuffer.request(() -> {
								BotFunctions.saveRole(event.getGuild(), channelname,
										new Color(rng.nextInt(255), rng.nextInt(255), rng.nextInt(255)), false, false,
										false, false, false, false, false, false, false, false, false, false, false, false,
										false, false, false, false, false, false, false, false, false, false, false, false,
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
											ttts.setPlayfieldMessage(printPlayfield(ttts));
											printPlayerChoice(ttts);
											GameData.add(ttts);
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
		// Reihen in Array pack und dann die Reaction en-mass adden
		RequestBuffer.request(() -> {
			IMessage row1 = ttts.getGameChannel().sendMessage("Reihe:1");
			RequestBuffer.request(() -> {
				row1.addReaction(UnicodeEmoji.L);
				RequestBuffer.request(() -> {
					row1.addReaction(UnicodeEmoji.M);
					RequestBuffer.request(() -> {
						row1.addReaction(UnicodeEmoji.R);
						ttts.setRow1(row1);
						RequestBuffer.request(() -> {
							IMessage row2 = ttts.getGameChannel().sendMessage("Reihe:2");
							RequestBuffer.request(() -> {
								row2.addReaction(UnicodeEmoji.L);
								RequestBuffer.request(() -> {
									row2.addReaction(UnicodeEmoji.M);
									RequestBuffer.request(() -> {
										row2.addReaction(UnicodeEmoji.R);
										ttts.setRow2(row2);
										row3(ttts);
									});
								});
							});
						});
					});
				});
			});
		});
	}

	private static void row3(TicTacToeSavegame ttts) {
		// TODO Auto-generated method stub
		RequestBuffer.request(() -> {
			IMessage row3 = ttts.getGameChannel().sendMessage("Reihe:3");
			RequestBuffer.request(() -> {
				row3.addReaction(UnicodeEmoji.L);
				RequestBuffer.request(() -> {
					row3.addReaction(UnicodeEmoji.M);
					RequestBuffer.request(() -> {
						row3.addReaction(UnicodeEmoji.R);
						ttts.setRow3(row3);
					});
				});
			});
		});
	}

	private static IMessage printPlayfield(TicTacToeSavegame ttts) {
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
		IMessage lel = ttts.getGameChannel().sendMessage(sbPlayfield.toString());
		return lel;
	}

	public static void playerMadeMove(TicTacToeSavegame ttts, ReactionAddEvent event) {
		// TODO Auto-generated method stub
		ttts.setActivePlayer(true);
		if(ttts.getActivePlayer()) {
			System.out.println("Der Spieler macht einen Move!");
			List<IReaction> lir = event.getMessage().getReactions();
			for(IReaction ir: lir) {
				System.out.println(ir.getEmoji().getName()+" = "+ir.getCount());
			}
		}else {
			//Der Bot ist dran!
		}
	}

	public static void reloadPlayfield(TicTacToeSavegame ttts) {
		// TODO Auto-generated method stub
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
		ttts.getPlayfieldMessage().edit(sbPlayfield.toString());
	}

	public static void botMadeMove(TicTacToeSavegame ttts) {
		// TODO Auto-generated method stub
		System.out.println("Der Bot ist dran!");
		Random rdm = new Random();
		int ziel = rdm.nextInt(9);
		int unendschieden = 0;
		if(ttts.getPlayfield().get(ziel) == 0) {
			ttts.getPlayfield().set(ziel, 2);
			ttts.setActivePlayer(true);
			GameTicTacToe.reloadPlayfield(ttts);
			//Test ob das Game over ist
		}else {
			unendschieden++;
			if(unendschieden > 99) {
				GameTicTacToe.end(ttts, unendschieden);
				return;
			}
			if(GameTicTacToe.checkForWin(ttts)) {
				return;
			}else {
				botMadeMove(ttts);
			}
		}
	}

	private static boolean checkForWin(TicTacToeSavegame ttts) {
		// TODO Auto-generated method stub
		//Spieler_Horizontal
		if(ttts.getPlayfield().get(0).equals(1) && ttts.getPlayfield().get(1).equals(1) && ttts.getPlayfield().get(2).equals(1)) {
			GameTicTacToe.end(ttts, 0);
			return true;
		}
		if(ttts.getPlayfield().get(3).equals(1) && ttts.getPlayfield().get(4).equals(1) && ttts.getPlayfield().get(5).equals(1)) {
			GameTicTacToe.end(ttts, 0);
			return true;
		}
		if(ttts.getPlayfield().get(6).equals(1) && ttts.getPlayfield().get(7).equals(1) && ttts.getPlayfield().get(8).equals(1)) {
			GameTicTacToe.end(ttts, 0);
			return true;
		}
		//Spieler_Vertikal
		if(ttts.getPlayfield().get(0).equals(1) && ttts.getPlayfield().get(3).equals(1) && ttts.getPlayfield().get(6).equals(1)) {
			GameTicTacToe.end(ttts, 0);
			return true;
		}
		if(ttts.getPlayfield().get(1).equals(1) && ttts.getPlayfield().get(4).equals(1) && ttts.getPlayfield().get(7).equals(1)) {
			GameTicTacToe.end(ttts, 0);
			return true;
		}
		if(ttts.getPlayfield().get(2).equals(1) && ttts.getPlayfield().get(1).equals(5) && ttts.getPlayfield().get(8).equals(1)) {
			GameTicTacToe.end(ttts, 0);
			return true;
		}
		//Spieler_X
		if(ttts.getPlayfield().get(0).equals(1) && ttts.getPlayfield().get(4).equals(1) && ttts.getPlayfield().get(8).equals(1)) {
			GameTicTacToe.end(ttts, 0);
			return true;
		}
		if(ttts.getPlayfield().get(2).equals(1) && ttts.getPlayfield().get(4).equals(1) && ttts.getPlayfield().get(6).equals(1)) {
			GameTicTacToe.end(ttts, 0);
			return true;
		}
		//////////////////////////////////////////////////////////////////
		//Bot_Horizontal
		if(ttts.getPlayfield().get(0).equals(2) && ttts.getPlayfield().get(1).equals(2) && ttts.getPlayfield().get(2).equals(2)) {
			GameTicTacToe.end(ttts, 1);
			return true;
		}
		if(ttts.getPlayfield().get(3).equals(2) && ttts.getPlayfield().get(4).equals(2) && ttts.getPlayfield().get(5).equals(2)) {
			GameTicTacToe.end(ttts, 1);
			return true;
		}
		if(ttts.getPlayfield().get(6).equals(2) && ttts.getPlayfield().get(7).equals(2) && ttts.getPlayfield().get(8).equals(2)) {
			GameTicTacToe.end(ttts, 1);
			return true;
		}
		//Bot_Vertikal
		if(ttts.getPlayfield().get(0).equals(2) && ttts.getPlayfield().get(3).equals(2) && ttts.getPlayfield().get(6).equals(2)) {
			GameTicTacToe.end(ttts, 1);
			return true;
		}
		if(ttts.getPlayfield().get(1).equals(2) && ttts.getPlayfield().get(4).equals(2) && ttts.getPlayfield().get(7).equals(2)) {
			GameTicTacToe.end(ttts, 1);
			return true;
		}
		if(ttts.getPlayfield().get(2).equals(2) && ttts.getPlayfield().get(1).equals(5) && ttts.getPlayfield().get(8).equals(2)) {
			GameTicTacToe.end(ttts, 1);
			return true;
		}
		//Bot_X
		if(ttts.getPlayfield().get(0).equals(2) && ttts.getPlayfield().get(4).equals(2) && ttts.getPlayfield().get(8).equals(2)) {
			GameTicTacToe.end(ttts, 1);
			return true;
		}
		if(ttts.getPlayfield().get(2).equals(2) && ttts.getPlayfield().get(4).equals(2) && ttts.getPlayfield().get(6).equals(2)) {
			GameTicTacToe.end(ttts, 1);
			return true;
		}
		return false;
	}

	private static void end(TicTacToeSavegame ttts, int mode) {
		// TODO Auto-generated method stub
		//Muss unterscheiden ob "Unendschieden", "Sieg" und "Niederlage"
		//Unendschieden: 100
		//Sieg: 0
		//Niederlage: 1
		if(mode == 100) {
			ttts.getGameChannel().sendMessage("Das Spiel ist vorbei! \nUNENDSCHIEDEN!");
		}else {
			if(mode == 0) {
				ttts.getGameChannel().sendMessage("Das Spiel ist vorbei! \nDU HAST GEWONNEN!");
			}else {
				if(mode == 1) {
					ttts.getGameChannel().sendMessage("Das Spiel ist vorbei! \nDU HAST VERLOREN!");
				}
			}
		}
	}

	public static void done(MessageReceivedEvent event) {
		if(checkForGames(event)) {
			int tmp = 0;
			for(TicTacToeSavegame ttts : GameData) {
				if(event.getAuthor().equals(ttts.getGameuser()) && event.getChannel().getName().equals(ttts.getGameChannel().getName())){
					event.getChannel().delete();
					event.getGuild().getRolesByName(ttts.getGameuser().getName().toLowerCase()+"-gameroom").get(0).delete();
					GameData.remove(tmp);
					break;
				}else {
					tmp++;
				}
			}
		}
	}
	
	
	
	
	
	
}
//End