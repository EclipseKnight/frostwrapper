package wrapper.frostwrapper.xyz.commands;

import java.io.IOException;
import java.util.List;

import wrapper.frostwrapper.xyz.logger.Logger;
import wrapper.frostwrapper.xyz.logger.Logger.Level;
import wrapper.frostwrapper.xyz.reader.InputReader;
import wrapper.frostwrapper.xyz.reader.WrapperApplication;

public class CommandStart extends Command {
	
	public CommandStart(String[] args) {
		this.args = args;
	}
	
	public CommandStart(WrapperApplication application) {
		this.application = application;
	}
	
	public CommandStart(String appName) {
		this.appName = appName;
	}
	
	
	@Override
	public void run() {
		
		if (application != null) {
			
			ProcessBuilder builder = new ProcessBuilder();
			if (!application.getRunPrefix().isBlank()) {
				List<String> runPrefix = InputReader.regexMatches(application.getRunPrefix());
				runPrefix.add(application.getStartScript());
				
				builder.command(runPrefix);
			} else {
				builder.command(application.getStartScript());
			}
			
			try {
				builder.inheritIO();
				Process p = builder.start();
				
				String pName = application.getName();
				if (args.length > 0) {
					pName = args[0];
				}
				
				int i = 1;
				while (InputReader.processes.containsKey(pName)) {
					pName = pName.concat("(" + i + ")");
					i++;
				}
				
				InputReader.processes.put(pName, p);
				// Wait for process to terminate.
				p.waitFor();
				Logger.log(Level.SUCCESS, pName + " terminated.");
				//Remove from map.
				InputReader.processes.remove(pName);
				
			} catch (IOException | InterruptedException e) {
				Logger.log(Level.ERROR, "Exception occurred: " + e.toString());
				e.printStackTrace();
			}
			
			
			
		} else {
			Logger.log(Level.ERROR, "No arguments or application name provided.");
			return;
		}
		
	}

}
