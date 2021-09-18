package wrapper.frostwrapper.xyz.discord;

import java.util.concurrent.TimeUnit;

import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class DiscordUtils {

	public static void setBotStatus(String status) {
		DiscordBot.jda.getPresence().setActivity(Activity.of(ActivityType.WATCHING, status));
		DiscordBot.jda.getPresence().setStatus(OnlineStatus.IDLE);
	}
	
	public static void sendMessage(String channelId, String message) {
		
		String guildId = DiscordBot.configuration.getGuildId();
		
		DiscordBot.jda.getGuildById(guildId).getTextChannelById(channelId).sendMessage(message).queue();
	}
	
	public static void sendMessage(String channelId, MessageEmbed embed) {
		
		String guildId = DiscordBot.configuration.getGuildId();
		
		DiscordBot.jda.getGuildById(guildId).getTextChannelById(channelId).sendMessage(embed).queue();
	}
	
	public static void sendTimedMessage(CommandEvent event, MessageEmbed embed, int ms, boolean isPrivate) {
		
		if (isPrivate) {
			event.getMember().getUser().openPrivateChannel().queue(channel -> {
				channel.sendMessage(embed).queue( m -> {
					m.delete().queueAfter(ms, TimeUnit.MICROSECONDS);
				});
			});
			
			return;
		}
		
		event.getChannel().sendMessage(embed).queue( m -> {
			m.delete().queueAfter(ms, TimeUnit.MILLISECONDS);
		});
	}
}
