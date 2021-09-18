package wrapper.frostwrapper.xyz;

import java.io.File;

import org.fusesource.jansi.AnsiConsole;

import wrapper.frostwrapper.xyz.discord.DiscordBot;
import wrapper.frostwrapper.xyz.logger.Logger;
import wrapper.frostwrapper.xyz.logger.Logger.Level;
import wrapper.frostwrapper.xyz.reader.InputReader;

public class Launcher {

	public static final String UWD = System.getProperty("user.dir");
	public static final String BOT_DIR = System.getProperty("user.dir") + File.separator + "frostwrapper" + File.separator;
	
	public static DiscordBot discordBot;
	
	public static void main(String[] args) {
		// allows ANSI escape sequences to format console output. For loggers. aka PRETTY COLORS
		AnsiConsole.systemInstall();
		
		Logger.log(Level.INFO, "Starting Discord bot...");
		discordBot = new DiscordBot();
		
		
		Logger.log(Level.INFO, "Starting FrostWrapper...");
		InputReader ir = new InputReader();
		ir.start();
		
	}
	
}
