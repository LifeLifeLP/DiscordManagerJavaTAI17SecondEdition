/**
 *
 */
package lifelifelp.botfuctions;

import java.awt.Color;
import java.util.EnumSet;

import org.apache.commons.lang3.StringUtils;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.ICategory;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;
import sx.blah.discord.util.RoleBuilder;

public class BotFunctions {
	// Ansprech Pr�fix f�r den Bot, wenn die Nachricht damit beginnt soll er reagieren
	public static String BOT_PREFIX = "p!";

	// Erstellt einen DiscordClient, dieser ist die Zentrale Verbidung zu Discord selbst
	public static IDiscordClient getBuiltDiscordClient(String token) {
		//Das Token stellt die Bot-ID dar weleche man unter https://discordapp.com/developers/applications/ erhalten hat
		//Der Clientbuilder erzeugt dann das Objekt mit unserem Token und der von Discord empfohlenen Shard-Menge (Verbindungen zu Discord selbst)
		return new ClientBuilder().withToken(token).withRecommendedShardCount().build();

	}
	// Beispiel Funktion zum senden von Nachrichten an einen Channel + RequestBuffer sollte ein Rate-Limit eintreten(Zu viele Anfragen gleichzeitig)
	public static void sendMessage(IChannel channel, String message) {
		RequestBuffer.request(() -> {
			try {
				channel.sendMessage(message);
			} catch (DiscordException e) {
				System.err.println("Message could not be sent with error: ");
				e.printStackTrace();
			}
		});
	}
	
	//Funktion um alle Nachrichten eines Users auf einem Server zu entfernen, da Discord selbst nur Nachrichten im einem Zeitraum von maximal 7 Tagen l�scht.
	public static void afterBanCleanup(IUser user, IGuild guild){
		for(IChannel ic: guild.getChannels()) {
			for(IMessage im: ic.getFullMessageHistory()) {
				if(im.getAuthor().equals(user)) {
					im.delete();
				}
			}
		}
	}
	
	//Erstellt eine Rolle, indem ein Server(iguild) der Rolename sowie die Farbe und dann die f�r jede veef�gbare Berechtigung ein Boolean Wert �bergeben wird
	//daraus ensteht dann ein EnumSet welches die gewollten Permissions Objekte enth�lt, welches im letzten Schritt dem RoleBuilder �bergeben wird der dann die Rolle
	//auf dem Server erstellt, am Ende gibt es noch einen Test ob das erstllen erfolgreich war. test
	public static boolean saveRole(
			IGuild iguild,
			String Rolename,
			Color Rolecolor,
			Boolean isHoist,
			Boolean isMentionable,
			Boolean isAdministrator,
			Boolean canViewAuditLog,
			Boolean canManageServer,
			Boolean canManageRoles,
			Boolean CanManageChannels,
			Boolean canKick,
			Boolean canBan,
			Boolean canCreateInvite,
			Boolean canChangeNickname,
			Boolean canManageNicknames,
			Boolean canManageEmojis,
			Boolean canManageWebhooks,
			Boolean canReadMessages,
			Boolean canSendText,
			Boolean canSendTTSText,
			Boolean canManageMesages,
			Boolean CanEmbedLinks,
			Boolean canAddFile,
			Boolean CanReadHistory,
			Boolean canMentionEveryone,
			Boolean canUseExternalEmojis,
			Boolean canAddReaction,
			Boolean CanVoiceConnect,
			Boolean CanVoiceSpeak,
			Boolean CanMuteMembers,
			Boolean CanDeafMembers,
			Boolean CanMoveMembers,
			Boolean CanUseVoiceAuto) {
		EnumSet<Permissions> newRolePermissions = EnumSet.noneOf(Permissions.class);
		RoleBuilder roleBuilderForNewRole = new RoleBuilder(iguild);
		if (isHoist) {
			roleBuilderForNewRole.setHoist(true);
		}
		if (isMentionable) {
			roleBuilderForNewRole.setMentionable(true);
		}
		if (canReadMessages) {
			Permissions READ_MESSAGES = Permissions.READ_MESSAGES;
			newRolePermissions.add(READ_MESSAGES);
		}
		if (canMentionEveryone) {
			Permissions MENTION_EVERYONE = Permissions.MENTION_EVERYONE;
			newRolePermissions.add(MENTION_EVERYONE);
		}
		if (canUseExternalEmojis) {
			Permissions USE_EXTERNAL_EMOJIS = Permissions.USE_EXTERNAL_EMOJIS;
			newRolePermissions.add(USE_EXTERNAL_EMOJIS);
		}
		if (canAddReaction) {
			Permissions ADD_REACTIONS = Permissions.ADD_REACTIONS;
			newRolePermissions.add(ADD_REACTIONS);
		}
		if (canChangeNickname) {
			Permissions CHANGE_NICKNAME = Permissions.CHANGE_NICKNAME;
			newRolePermissions.add(CHANGE_NICKNAME);
		}
		if (isAdministrator) {
			Permissions ADMINISTRATOR = Permissions.ADMINISTRATOR;
			newRolePermissions.add(ADMINISTRATOR);
		}
		if (canViewAuditLog) {
			Permissions VIEW_AUDIT_LOG = Permissions.VIEW_AUDIT_LOG;
			newRolePermissions.add(VIEW_AUDIT_LOG);
		}
		if (canManageServer) {
			Permissions MANAGE_SERVER = Permissions.MANAGE_SERVER;
			newRolePermissions.add(MANAGE_SERVER);
		}
		if (canManageRoles) {
			Permissions MANAGE_ROLES = Permissions.MANAGE_ROLES;
			newRolePermissions.add(MANAGE_ROLES);
		}
		if (canKick) {
			Permissions KICK = Permissions.KICK;
			newRolePermissions.add(KICK);
		}
		if (canBan) {
			Permissions BAN = Permissions.BAN;
			newRolePermissions.add(BAN);
		}
		if (canCreateInvite) {
			Permissions CREATE_INVITE = Permissions.CREATE_INVITE;
			newRolePermissions.add(CREATE_INVITE);
		}
		if (canChangeNickname) {
			Permissions MANAGE_NICKNAMES = Permissions.MANAGE_NICKNAMES;
			newRolePermissions.add(MANAGE_NICKNAMES);
		}
		if (canManageEmojis) {
			Permissions MANAGE_EMOJIS = Permissions.MANAGE_EMOJIS;
			newRolePermissions.add(MANAGE_EMOJIS);
		}
		if (canManageWebhooks) {
			Permissions MANAGE_WEBHOOKS = Permissions.MANAGE_WEBHOOKS;
			newRolePermissions.add(MANAGE_WEBHOOKS);
		}
		if (canSendText) {
			Permissions SEND_MESSAGES = Permissions.SEND_MESSAGES;
			newRolePermissions.add(SEND_MESSAGES);
		}
		if (canSendTTSText) {
			Permissions SEND_TTS_MESSAGES = Permissions.SEND_TTS_MESSAGES;
			newRolePermissions.add(SEND_TTS_MESSAGES);
		}
		if (canManageMesages) {
			Permissions MANAGE_MESSAGES = Permissions.MANAGE_MESSAGES;
			newRolePermissions.add(MANAGE_MESSAGES);
		}
		if (CanEmbedLinks) {
			Permissions EMBED_LINKS = Permissions.EMBED_LINKS;
			newRolePermissions.add(EMBED_LINKS);
		}
		if (canAddFile) {
			Permissions ATTACH_FILES = Permissions.ATTACH_FILES;
			newRolePermissions.add(ATTACH_FILES);
		} 
		if (CanReadHistory) {
			Permissions READ_MESSAGE_HISTORY = Permissions.READ_MESSAGE_HISTORY;
			newRolePermissions.add(READ_MESSAGE_HISTORY);
		}
		if (CanVoiceConnect) {
			Permissions VOICE_CONNECT = Permissions.VOICE_CONNECT;
			newRolePermissions.add(VOICE_CONNECT);
		}
		if (CanVoiceSpeak) {
			Permissions VOICE_SPEAK = Permissions.VOICE_SPEAK;
			newRolePermissions.add(VOICE_SPEAK);
		}
		if (CanMuteMembers) {
			Permissions VOICE_MUTE_MEMBERS = Permissions.VOICE_MUTE_MEMBERS;
			newRolePermissions.add(VOICE_MUTE_MEMBERS);
		}
		if (CanDeafMembers) {
			Permissions VOICE_DEAFEN_MEMBERS = Permissions.VOICE_DEAFEN_MEMBERS;
			newRolePermissions.add(VOICE_DEAFEN_MEMBERS);
		}
		if (CanMoveMembers) {
			Permissions VOICE_MOVE_MEMBERS = Permissions.VOICE_MOVE_MEMBERS;
			newRolePermissions.add(VOICE_MOVE_MEMBERS);
		}
		if (CanUseVoiceAuto) {
			Permissions VOICE_USE_VAD = Permissions.VOICE_USE_VAD;
			newRolePermissions.add(VOICE_USE_VAD);
		}
		if (CanManageChannels) {
			Permissions MANAGE_CHANNELS = Permissions.MANAGE_CHANNELS;
			newRolePermissions.add(MANAGE_CHANNELS);
		}
		roleBuilderForNewRole.withPermissions(newRolePermissions);
		roleBuilderForNewRole.withName(Rolename);
		roleBuilderForNewRole.withColor(Rolecolor);
		if (roleBuilderForNewRole.build() != null) {
			return true;
		} else {
			return false;
		}
	}
	//Erstellt einen Voice oder Text-Channel mit dem gew�nschten Einstellungen
		public static boolean CreateChannel(IGuild iguild, String name, Boolean channeltyp, String channeltopic,
				Boolean NSFW, int bitrate, int userlimit, ICategory category, int position) {
			Long channelID;
			String channelname = StringUtils.replace(name.toLowerCase(), " ", "-");
			if (channeltyp) {
				channelID = iguild.createChannel(channelname).getLongID();
				IChannel toeditchannel = iguild.getChannelByID(channelID);
				toeditchannel.changeTopic(channeltopic);
				if (NSFW) {
					toeditchannel.changeNSFW(true);
				}
				toeditchannel.changeCategory(category);
				toeditchannel.changePosition(position);
				IChannel totest = iguild.getChannelByID(channelID);
				if (totest.equals(toeditchannel)) {
					return true;
				} else {
					return false;
				}
			} else {
				channelID = iguild.createVoiceChannel(channelname).getLongID();
				IVoiceChannel toeditvoicechannel = iguild.getVoiceChannelByID(channelID);
				//toeditvoicechannel.changeBitrate(bitrate); Nicht nutzbar da dies ein Problem in Discord4J ist welches wir nicht umgehen k�nnen zur Zeit der Abgabe ist kein Bugfix 
				toeditvoicechannel.changeUserLimit(userlimit);
				toeditvoicechannel.changeCategory(category);
				toeditvoicechannel.changePosition(position);
				IVoiceChannel totest = iguild.getVoiceChannelByID(channelID);
				if (totest.equals(toeditvoicechannel)) {
					return true;
				} else {
					return false;
				}
			}

		}
}
