package bilokhado.phonebook.entity;

public interface User {

	public int getId();

	public void setId(int id);

	public String getLogin();

	public void setLogin(String login);

	public String getPasswordHash();

	public void setPasswordHash(String passwordHash);

	public String getFullName();

	public void setFullName(String fullName);

}
