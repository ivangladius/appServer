import java.util.*;
import java.io.*;
import java.net.*;


class Client {

    private Socket clientSocket;
    private InetAddress address;
    private Integer port;

    private boolean status;

    private PrintWriter output;
    private BufferedReader input;

    public Client(String address, Integer port) {
	try {
	    this.address = InetAddress.getByName(address);
	} catch (UnknownHostException e) {
	    System.out.println("HOST NOT REACHABLE !!!");
	    status = false;
	    e.printStackTrace();
	}
	this.port = port;
	status = true;
    }

    private void connect() {
	try {
	    clientSocket = new Socket(this.address, this.port);
	    output = new PrintWriter(clientSocket.getOutputStream(), true);
	} catch(IOException e) {
	    System.out.println("COULD NOT CONNECT!!!");
	    status = false;
	}
	status = true;
    }

    private void closeSocket() {
	if (!clientSocket.isClosed()) {
	    try {
		clientSocket.close();
	    } catch (IOException ignore) {}
	}
    }

    public String getUsername() {

	String username = null;

	connect();

	output.println("getUsername");

	System.out.println("sending succesful");

	try {
	    input = new BufferedReader
		(new InputStreamReader(clientSocket.getInputStream()));
	    username = input.readLine();
	    input.close();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (NullPointerException e) {
	    e.printStackTrace();
	}

	System.out.println("reading succesful\n");

	output.close();

	closeSocket();

	if (username != null)
	    return username;
	else
	    return null;
    }

} 

public class client {
    public static void main(String[] args) {

	Client client = new Client("localhost", Integer.parseInt(args[0]));

	try {
	    String username = client.getUsername();
	if (username != null)
	    System.out.println("Username: " + username + "\n");
	else
	    System.out.println("Username not found!");
	} catch (NullPointerException ignore) { }

    }
}
