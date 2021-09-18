package wrapper.frostwrapper.xyz.commands;

import wrapper.frostwrapper.xyz.logger.Logger;
import wrapper.frostwrapper.xyz.logger.Logger.Level;
import wrapper.frostwrapper.xyz.reader.InputReader;

public class CommandList extends Command {

	public CommandList(String[] args) {
		this.args = args;
	}
	
	
	
	@Override
	public void run() {
		if (args.length <= 0) {
			String m = "";
			for (String p: InputReader.processes.keySet()) {
				m += p + "\n";
			}
			
			Logger.log(Level.SUCCESS, m);
		}
	}

}
