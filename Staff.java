package mini;

public class Staff {
	String email_id;
	private String password;
	public Staff(String email, String pwd) {
	this.email_id = email;
	this.password = pwd;
	}
	public int verifyPassword(String pwd) {
	return this.password.equals(pwd) ? 1 : 0;
	}
	}
