package wrapper.frostwrapper.xyz.reader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import wrapper.frostwrapper.xyz.Launcher;
import wrapper.frostwrapper.xyz.commands.CommandList;
import wrapper.frostwrapper.xyz.commands.CommandStart;
import wrapper.frostwrapper.xyz.logger.Logger;
import wrapper.frostwrapper.xyz.logger.Logger.Level;

public class InputReader {
	
	ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	private final static Pattern regex = Pattern.compile("([^\"\\\\]*(?:\\\\.[^\"\\\\]*)*)\"|'([^'\\\\]*(?:\\\\.[^'\\\\]*)*)'|[^\\s]+");
	private final static Matcher regexMatcher = regex.matcher("");
	public static WrapperConfiguration configuration;
	
	public static final Map<String, Process> processes = new HashMap<>();
	
	public void start() {
		loadConfiguration();
		
		threadPool.setRejectedExecutionHandler(new RejectedExec());
		threadPool.setKeepAliveTime(30, TimeUnit.SECONDS);
		threadPool.allowCoreThreadTimeOut(true);
	
		Logger.log(Level.INFO, "Taking input...");
		Scanner sc = new Scanner(System.in);
		String input;
		
		while(!(input = sc.nextLine()).equals("exit")) {
			processInput(input);
			
		}
		
		sc.close();
		Logger.log(Level.WARN, "Shutting down...");
		
		killAllProcesses();
		System.exit(0);
	}
	
	public static void killAllProcesses() {
		for (String s: processes.keySet()) {
			processes.get(s).destroyForcibly();
		}
		
	}
	
	public static ArrayList<String> regexMatches(String input){
		ArrayList<String> matches = new ArrayList<>();
		regexMatcher.reset(input);
		
		while (regexMatcher.find()) {
		    if (regexMatcher.group(1) != null) {
		        // Add double-quoted string without the quotes
		        matches.add(regexMatcher.group(1));
		    } else if (regexMatcher.group(2) != null) {
		        // Add single-quoted string without the quotes
		        matches.add(regexMatcher.group(2));
		    } else {
		        // Add unquoted word
		        matches.add(regexMatcher.group());
		    }
		} 
		
		return matches;
	}
	
	private void processInput(String input) {
		List<String> matchList = regexMatches(input);
		
		
		if (matchList.isEmpty() && matchList.size() < 2) {
			Logger.log(Level.WARN, "Error: Command and or application name not provided.");
			return;
		}
		
		
		
		String type = matchList.get(0);
		matchList.remove(0);
		
		
		switch (type) {
			case "start" -> {
				WrapperApplication app = configuration.getApplications().get(matchList.get(0));
				matchList.remove(0);
				
				if (app == null) {
					Logger.log(Level.WARN, "Error: Application does not exist.");
					return;
				}
				
				CommandStart startCmd = new CommandStart(app);
				//if there are further arguments, add them to the command.
				if (matchList.size() > 0) {
					startCmd.setArgs(matchList.toArray(new String[matchList.size()]));
				}
				
				threadPool.execute(startCmd);
			}
			
			case "list" -> {
				threadPool.execute(new CommandList(matchList.toArray(new String[matchList.size()])));
			}
		}
		
		
	}
	
	/**
	 * Load the configuration file for the discord bot.
	 */
	public static void loadConfiguration() {
		File discordBotConfig = new File(Launcher.BOT_DIR + "configs" + File.separator + "wrapper.yaml");
		
		if (new File(Launcher.BOT_DIR + "configs").mkdirs()) {
			Logger.log(Level.WARN, "Generating configs directory...");
		}
		
		try {
    		if (!discordBotConfig.exists()) {
        		generateConfig();
        	}
        	
            InputStream is = new BufferedInputStream(new FileInputStream(discordBotConfig));
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            configuration = mapper.readValue(is, WrapperConfiguration.class);
            
            is.close();
        } catch (Exception e) {
            Logger.log(Level.FATAL, "Unable to load configuration... Exiting." + e.toString());
            e.printStackTrace();
            System.exit(1);
        }
		
	}
	
	 /**
     * Generates config file.
     */
    private static void generateConfig() {   	
    	
        try {
        	Logger.log(Level.WARN, "Missing wrapper.yaml. Generating new config...");
        	ClassLoader classloader = InputReader.class.getClassLoader();
        	
        	// copies twitchbot.yaml template to current working directory.
        	InputStream original = classloader.getResourceAsStream("wrapper.yaml");
            Path copy = Paths.get(new File(Launcher.BOT_DIR + "configs" + File.separator + "wrapper.yaml").toURI());
          
            Logger.log(Level.WARN, "Generating config at " + copy);
            Files.copy(original, copy);
            original.close();
		} catch (IOException e) {
			Logger.log(Level.ERROR, e.toString());
			Logger.log(Level.ERROR, "Failed to generate wrapper.yaml...");
		}
    }

}
