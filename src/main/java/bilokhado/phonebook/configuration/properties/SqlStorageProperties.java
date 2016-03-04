package bilokhado.phonebook.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "db.sql")
public class SqlStorageProperties {

	private String driver;
	private String url;
	private String user;
	private String password;

	public String getDriver() {
		System.out.println("driver=" + driver);
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		System.out.println("url=" + url);
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		System.out.println("user=" + user);
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		System.out.println("password=" + password);
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
