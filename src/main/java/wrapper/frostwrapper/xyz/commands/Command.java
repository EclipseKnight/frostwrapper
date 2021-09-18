package wrapper.frostwrapper.xyz.commands;

import wrapper.frostwrapper.xyz.reader.WrapperApplication;

public abstract class Command implements Runnable {

	protected String[] args = {};
	protected WrapperApplication application = null;
	protected String appName = null;
	
	public String[] getArgs() {
		return args;
	}
	
	public void setArgs(String[] args) {
		this.args = args;
	}
	
	public WrapperApplication getApplication() {
		return application;
	}
	
	public void setApplication(WrapperApplication application) {
		this.application = application;
	}
	
	public String getAppName() {
		return appName;
	}
	
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
}
