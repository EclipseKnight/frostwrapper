package wrapper.frostwrapper.xyz.reader;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import wrapper.frostwrapper.xyz.logger.Logger;
import wrapper.frostwrapper.xyz.logger.Logger.Level;

public class RejectedExec implements RejectedExecutionHandler{

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		Logger.log(Level.ERROR, "Unable to execute task. Queue Size: " + executor.getQueue().size());
	}

}
