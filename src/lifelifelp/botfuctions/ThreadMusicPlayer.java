package lifelifelp.botfuctions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IMessage.Attachment;
import sx.blah.discord.util.audio.AudioPlayer;

public class ThreadMusicPlayer extends Thread{

	MessageReceivedEvent event;
	IChannel i;
	IMessage m;
	
	public ThreadMusicPlayer(MessageReceivedEvent event) {
		this.event = event;
	}
	
	//run() Methode, wird ausgeführt nach Starten des Threads
	public void run() {
		try {
			System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0");
			Attachment song = event.getMessage().getAttachments().get(0);
			URL url = new URL(song.getUrl());
			i = event.getChannel();
			m = i.sendMessage("opening connection");
			InputStream in = url.openStream();
			File F = new File("music_"+event.getAuthor().getLongID()+".mp3");//LOL
			FileOutputStream fos = new FileOutputStream(F);
			m.edit("reading file...");
			int length = -1;
			byte[] buffer = new byte[1024];
			while ((length = in.read(buffer)) > -1) {
			    fos.write(buffer, 0, length);
			}
			fos.close();
			in.close();
			m.edit("file was downloaded");
			AudioInputStream stream = AudioSystem.getAudioInputStream(F);
			AudioPlayer audioP = AudioPlayer.getAudioPlayerForGuild(event.getGuild());
			audioP.queue(stream);
			m.edit("Viel Spaß beim Anhöhren " + event.getAuthor().getDisplayName(event.getGuild()));
		} catch (IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
}
