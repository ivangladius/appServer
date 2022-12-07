import java.util.*;
import java.io.*;
import java.net.*;

import appServer.MessageObject;


class Client {

    private Socket clientSocket;
    private InetAddress address;
    private Integer port;

    private boolean status;

    // private PrintWriter output;
    // private BufferedReader input;
    private ObjectOutputStream out;
    private ObjectInputStream in;


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
	    // output = new PrintWriter(clientSocket.getOutputStream(), true);
	    out = new ObjectOutputStream(clientSocket.getOutputStream());
	    
	} catch(IOException e) {
	    System.out.println("COULD NOT CONNECT!!!");
	    status = false;
	} catch(NullPointerException e) {
	    e.printStackTrace();
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

	try {
	    in = new ObjectInputStream(clientSocket.getInputStream());

	    MessageObject message = new MessageObject(403, "getUsername", null);
	    MessageObject reply;

	    out.writeObject(message);

	    //output.println("max@gmail.com getUsername");

	    // System.out.println("sending succesful");

	    // ObjectInputStream in = new ObjectInputStream(cSocket.getInputStream());

	    // input = new BufferedReader
	    // 	(new InputStreamReader(clientSocket.getInputStream()));
	    // username = input.readLine();
	    reply = (MessageObject) in.readObject();
	    username = reply.getPayload();
	    reply.print();

	    in.close();
	    out.close();

	    // input.close();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (NullPointerException e) {
	    e.printStackTrace();
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}



	// System.out.println("reading succesful\n");

	// output.close();

	closeSocket();

	if (username != null)
	    return username;
	else
	    return null;
    }
} 

public class client {
    public static void main(String[] args) {

	Client client = new Client(args[0], Integer.parseInt(args[1]));

	try {
	    String username = client.getUsername();
	if (username != null)
	    System.out.println("\nUsername: " + username + "\n");
	else
	    System.out.println("\nUsername not found!");
	} catch (NullPointerException ignore) { }

    }
}

private class NetworkThreadInstance implements Runnable, Client {

    public NetworkThreadInstance(Client client) {
	
    }
}
