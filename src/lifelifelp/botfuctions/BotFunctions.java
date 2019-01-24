/**
 * 
 */
package lifelifelp.botfuctions;

import java.awt.Color;
import java.util.EnumSet;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
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
	//auf dem Server erstellt, am Ende gibt es noch einen Test ob das erstllen erfolgreich war.
	public static boolean saveRole(IGuild iguild, String Rolename, Color Rolecolor, Boolean isHoist, Boolean isMentionable, Boolean canReadMessages, Boolean canMentionEveryone, Boolean canUseExternalEmojis, Boolean canAddReaction, Boolean canChangeNickname, 
			Boolean isAdministrator, Boolean canViewAuditLog, Boolean canManageServer, Boolean canManageRoles, Boolean canKick, Boolean canBan, Boolean canCreateInvite, Boolean canManageNicknames, Boolean canManageEmojis, Boolean canManageWebhooks, Boolean canSendText, 
			Boolean canSendTTSText, Boolean canManageMesages, Boolean CanEmbedLinks, Boolean canAddFile, Boolean CanReadHistory, Boolean CanVoiceConnect, Boolean CanVoiceSpeak, Boolean CanMuteMembers, Boolean CanDeafMembers, Boolean CanMoveMembers, 
			Boolean CanUseVoiceAuto, Boolean CanManageChannels) {
		EnumSet<Permissions> newRolePermissions = EnumSet.noneOf(Permissions.class);
		RoleBuilder roleBuilderForNewRole = new RoleBuilder(iguild);
		System.out.println("###");
		System.out.println("NewRole: "+Rolename);
		/////////////////////////////////////////////////////////////////
		if(isHoist) {
			System.out.println("HOIST:"+isHoist);
			roleBuilderForNewRole.setHoist(true);
		}else {
			System.out.println("HOIST:"+isHoist);
		}
		/////////////////////////////////////////////////////////////////
		if(isMentionable) {
			System.out.println("MENTIONABLE:"+isMentionable);
			roleBuilderForNewRole.setMentionable(true);
		}else {
			System.out.println("MENTIONABLE:"+isMentionable);
		}
		/////////////////////////////////////////////////////////////////
		if(canReadMessages) {
			System.out.println("READ_MESSAGES:"+canReadMessages);
			Permissions READ_MESSAGES = Permissions.READ_MESSAGES;
			newRolePermissions.add(READ_MESSAGES);
		}else {
			System.out.println("READ_MESSAGES:"+canReadMessages);
		}
		////////////////////////////////////////////////////////////////
		if(canMentionEveryone) {
			System.out.println("MENTION_EVERYONE:"+canMentionEveryone);
			Permissions MENTION_EVERYONE = Permissions.MENTION_EVERYONE;
			newRolePermissions.add(MENTION_EVERYONE);
		}else {
			System.out.println("MENTION_EVERYONE:"+canMentionEveryone);
		}
		/////////////////////////////////////////////////////////////////
		if(canUseExternalEmojis) {
			System.out.println("USER_EXTERNAL_EMOJIS:"+canUseExternalEmojis);
			Permissions USE_EXTERNAL_EMOJIS = Permissions.USE_EXTERNAL_EMOJIS;
			newRolePermissions.add(USE_EXTERNAL_EMOJIS);
		}else {
			System.out.println("USER_EXTERNAL_EMOJIS:"+canUseExternalEmojis);
		}
		/////////////////////////////////////////////////////////////////
		if(canAddReaction) {
			System.out.println("ADD_REACTION:"+canAddReaction);
			Permissions ADD_REACTIONS = Permissions.ADD_REACTIONS;
			newRolePermissions.add(ADD_REACTIONS);
		}else {
			System.out.println("ADD_REACTION:"+canAddReaction);
		}
		//////////////////////////////////////////////////////////////////
		if(canChangeNickname) {
			System.out.println("CANGE_NICKNAME:"+canChangeNickname);
			Permissions CHANGE_NICKNAME = Permissions.CHANGE_NICKNAME;
			newRolePermissions.add(CHANGE_NICKNAME);
		}else {
			System.out.println("CANGE_NICKNAME:"+canChangeNickname);
		}
		///////////////////////////////////////////////////////////////
		if(isAdministrator) {
			System.out.println("ADMIN:"+isAdministrator);
			Permissions ADMINISTRATOR = Permissions.ADMINISTRATOR;
			newRolePermissions.add(ADMINISTRATOR);
		}else {
			System.out.println("ADMIN:"+isAdministrator);
		}
		//////////////////////////////////////////////////////////
		if(canViewAuditLog) {
			System.out.println("VIEW_AUDIT_LOG:"+canViewAuditLog);
			Permissions VIEW_AUDIT_LOG = Permissions.VIEW_AUDIT_LOG;
			newRolePermissions.add(VIEW_AUDIT_LOG);
		}else {
			System.out.println("VIEW_AUDIT_LOG:"+canViewAuditLog);
		}
		//////////////////////////////////////////////////////////
		if(canManageServer) {
			System.out.println("MANAGE_SERVER:"+canManageServer);
			Permissions MANAGE_SERVER = Permissions.MANAGE_SERVER;
			newRolePermissions.add(MANAGE_SERVER);
		}else {
			System.out.println("MANAGE_SERVER:"+canManageServer);
		}
		///////////////////////////////////////////////////////////
		if(canManageRoles) {
			System.out.println("MANAGE_ROLES"+canManageRoles);
			Permissions MANAGE_ROLES = Permissions.MANAGE_ROLES;
			newRolePermissions.add(MANAGE_ROLES);
		}else {
			System.out.println("MANAGE_ROLES"+canManageRoles);
		}
		/////////////////////////////////////////////////////
		if(canKick) {
			System.out.println("CAN_KICK"+canKick);
			Permissions KICK = Permissions.KICK;
			newRolePermissions.add(KICK);
		}else {
			System.out.println("CAN_KICK"+canKick);
		}
		////////////////////////////////////////////////////////
		if(canBan) {
			System.out.println("CAN_BAN:"+canBan);
			Permissions BAN = Permissions.BAN;
			newRolePermissions.add(BAN);
		}else {
			System.out.println("CAN_BAN:"+canBan);
		}
		///////////////////////////////////////////////////////////
		if(canCreateInvite) {
			System.out.println("CREATE_INVITE:"+canCreateInvite);
			Permissions CREATE_INVITE = Permissions.CREATE_INVITE;
			newRolePermissions.add(CREATE_INVITE);
		}else {
			System.out.println("CREATE_INVITE:"+canCreateInvite);
		}
		////////////////////////////////////////////////
		if(canChangeNickname) {
			System.out.println("CHANGE_NICKNAME:"+canChangeNickname);
			Permissions MANAGE_NICKNAMES = Permissions.MANAGE_NICKNAMES;
			newRolePermissions.add(MANAGE_NICKNAMES);
		}else {
			System.out.println("CHANGE_NICKNAME:"+canChangeNickname);
		}
		
		if(canManageEmojis) {
			System.out.println("MANGE_EMOJIS:"+canManageEmojis);
			Permissions MANAGE_EMOJIS = Permissions.MANAGE_EMOJIS;
			newRolePermissions.add(MANAGE_EMOJIS);
		}else {
			System.out.println("MANGE_EMOJIS:"+canManageEmojis);
		}
		if(canManageWebhooks) {
			System.out.println("MANGE_WEBHOOKS:"+canManageWebhooks);
			Permissions MANAGE_WEBHOOKS = Permissions.MANAGE_WEBHOOKS;
			newRolePermissions.add(MANAGE_WEBHOOKS);
		}else {
			System.out.println("MANGE_WEBHOOKS:"+canManageWebhooks);
		}
		
		if(canSendText) {
			System.out.println("SEND_TEXT:"+canSendText);
			Permissions SEND_MESSAGES = Permissions.SEND_MESSAGES;
			newRolePermissions.add(SEND_MESSAGES);
		}else {
			System.out.println("SEND_TEXT:"+canSendText);
		}
		
		if(canSendTTSText) {
			System.out.println("SEND_TTS_TEXT:"+canSendTTSText);
			Permissions SEND_TTS_MESSAGES = Permissions.SEND_TTS_MESSAGES;
			newRolePermissions.add(SEND_TTS_MESSAGES);
		}else {
			System.out.println("SEND_TTS_TEXT:"+canSendTTSText);
		}
		
		if(canManageMesages) {
			System.out.println("MANAGE_MESAGES:"+canManageMesages);
			Permissions MANAGE_MESSAGES = Permissions.MANAGE_MESSAGES;
			newRolePermissions.add(MANAGE_MESSAGES);
		}else {
			System.out.println("MANAGE_MESAGES:"+canManageMesages);
		}
		
		if(CanEmbedLinks) {
			System.out.println("EMBED_LINKS:"+CanEmbedLinks);
			Permissions EMBED_LINKS = Permissions.EMBED_LINKS;
			newRolePermissions.add(EMBED_LINKS);
		}else {
			System.out.println("EMBED_LINKS:"+CanEmbedLinks);
		}
		
		if(canAddFile) {
			System.out.println("ADD_FIEL:"+canAddFile);
			Permissions ATTACH_FILES = Permissions.ATTACH_FILES;
			newRolePermissions.add(ATTACH_FILES);
		}else {
			System.out.println("ADD_FIEL:"+canAddFile);
		}
		
		if(CanReadHistory) {
			System.out.println("READ_HISTORY:"+CanReadHistory);
			Permissions READ_MESSAGE_HISTORY = Permissions.READ_MESSAGE_HISTORY;
			newRolePermissions.add(READ_MESSAGE_HISTORY);
		}else {
			System.out.println("READ_HISTORY:"+CanReadHistory);
		}
		
		if(CanVoiceConnect) {
			System.out.println("VOICE_CONNECT:"+CanVoiceConnect);
			Permissions VOICE_CONNECT = Permissions.VOICE_CONNECT;
			newRolePermissions.add(VOICE_CONNECT);
		}else {
			System.out.println("VOICE_CONNECT:"+CanVoiceConnect);
		}
		
		if(CanVoiceSpeak) {
			System.out.println("VOICE_SPEAK:"+CanVoiceSpeak);
			Permissions VOICE_SPEAK = Permissions.VOICE_SPEAK;
			newRolePermissions.add(VOICE_SPEAK);
		}else {
			System.out.println("VOICE_SPEAK:"+CanVoiceSpeak);
		}
		
		if(CanMuteMembers) {
			System.out.println("VOICE_MUTE:"+CanVoiceSpeak);
			Permissions VOICE_MUTE_MEMBERS = Permissions.VOICE_MUTE_MEMBERS;
			newRolePermissions.add(VOICE_MUTE_MEMBERS);
		}else {
			System.out.println("VOICE_MUTE:"+CanVoiceSpeak);
		}
		
		if(CanDeafMembers) {
			System.out.println("VOICE_DEAF:"+CanDeafMembers);
			Permissions VOICE_DEAFEN_MEMBERS = Permissions.VOICE_DEAFEN_MEMBERS;
			newRolePermissions.add(VOICE_DEAFEN_MEMBERS);
		}else {
			System.out.println("VOICE_DEAF:"+CanDeafMembers);
		}
		
		if(CanMoveMembers) {
			System.out.println("MOVE_MEMBERS:"+CanMoveMembers);
			Permissions VOICE_MOVE_MEMBERS = Permissions.VOICE_MOVE_MEMBERS;
			newRolePermissions.add(VOICE_MOVE_MEMBERS);
		}else {
			System.out.println("MOVE_MEMBERS:"+CanMoveMembers);
		}
		
		if(CanUseVoiceAuto) {
			System.out.println("VOICE_AUTO:"+CanUseVoiceAuto);
			Permissions VOICE_USE_VAD = Permissions.VOICE_USE_VAD;
			newRolePermissions.add(VOICE_USE_VAD);
		}else {
			System.out.println("VOICE_AUTO:"+CanUseVoiceAuto);
		}
		if(CanManageChannels) {
			System.out.println("MANAGE:CHANNELS:"+CanManageChannels);
			Permissions MANAGE_CHANNELS = Permissions.MANAGE_CHANNELS;
			newRolePermissions.add(MANAGE_CHANNELS);
		}else {
			System.out.println("MANAGE:CHANNELS:"+CanManageChannels);
		}
		
		roleBuilderForNewRole.withPermissions(newRolePermissions);
		roleBuilderForNewRole.withName(Rolename);
		roleBuilderForNewRole.withColor(Rolecolor);
		if(roleBuilderForNewRole.build() != null) {
			return true;
		}else {
			return false;
		}
	}
}
