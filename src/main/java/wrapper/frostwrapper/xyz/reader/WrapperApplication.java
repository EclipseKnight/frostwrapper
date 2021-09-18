package wrapper.frostwrapper.xyz.reader;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WrapperApplication {

	private boolean enabled;
	private String name;
	private String startScript;
	private String runPrefix;
	
	@JsonCreator
	public WrapperApplication(
			@JsonProperty("enabled") boolean enabled,
			@JsonProperty("name") String name,
			@JsonProperty("start_script") String startScript,
			@JsonProperty("run_prefix") String runPrefix) {
		this.enabled = enabled;
		this.name = name;
		this.startScript = startScript;
		this.runPrefix = runPrefix;
		
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStartScript() {
		return startScript;
	}
	
	public void setStartScript(String startScript) {
		this.startScript = startScript;
	}
	
	public String getRunPrefix() {
		return runPrefix;
	}
	
	public void setRunPrefix(String runPrefix) {
		this.runPrefix = runPrefix;
	}
	
}
