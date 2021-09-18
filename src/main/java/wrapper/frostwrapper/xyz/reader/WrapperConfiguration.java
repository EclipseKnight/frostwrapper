package wrapper.frostwrapper.xyz.reader;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperConfiguration {

	private Map<String, WrapperApplication> applications;
	
	
	public Map<String, WrapperApplication> getApplications() {
		return applications;
	}
	
	public void setApplications(Map<String, WrapperApplication> applications) {
		this.applications = applications;
	}
	
}
