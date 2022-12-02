

public class Databse {

    public Databse() {
	
    }

    String queryUsername() {
	String username = QUERY("SELECT username from USERS where username = maximus");
	return username;
    }
    String queryEmail() {
	String username = QUERY("SELECT username from USERS where username = maximus");
	return username;
    }
    String queryMessage() {
	String username = QUERY("SELECT username from USERS where username = maximus");
	return (username != null) ? username : null;
    }


}
