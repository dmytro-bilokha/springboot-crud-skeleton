package bilokhado.phonebook.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "db.xml")
public class XmlStorageProperties {

	private String fileName;
	private boolean formatted;
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public boolean isFormatted() {
		return formatted;
	}
	
	public void setFormatted(boolean formatted) {
		this.formatted = formatted;
	}
		
}
