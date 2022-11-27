
package appServer;

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {

    private int port;

    private ServerSocket serverSocket;

    private ThreadPoolExecutor executor;

    public Server(int port, int threadPoolSize) {

	this.port = port;

	executor = (ThreadPoolExecutor)
	    Executors.newFixedThreadPool(threadPoolSize);
    }

    public void start() {
	try {
	    serverSocket = new ServerSocket(port);
	} catch(IOException e) {
	    stop();
	    e.printStackTrace();
	}
    }

    public void loop() {
	for (;;) {
	    try {
		Socket clientSocket = serverSocket.accept();
		executor.execute(new MyRunnable(this, clientSocket));
	    } catch(IOException ignore) {}
	}
    }

    public void handle_connection(Socket cSocket) {

	System.out.println("\nClient connected: "
			   + cSocket.getRemoteSocketAddress().toString());


	try {
	    
	    // PrintWriter out = new PrintWriter(cSocket.getOutputStream(), true);
	    PrintWriter out = new PrintWriter(cSocket.getOutputStream(), true);
	    BufferedReader in = new BufferedReader
		(new InputStreamReader(cSocket.getInputStream()));

	    String operation = in.readLine();

	    if (operation != null) {
		if (operation.equals("getUsername"))
		    replyUsername(out);
	    } else {
		replyError(out);
	    }

	    out.close();
	    in.close();
	    closeClient(cSocket);

	} catch(UnknownHostException e) {
	    closeClient(cSocket);
	    e.printStackTrace();
	} catch(IOException e) {
	    closeClient(cSocket);
	    e.printStackTrace();
	}
    }

    private void closeClient(Socket cSocket) {
	try {
	    cSocket.close();
	} catch(IOException ignore) {} 
    }

    public void stop() {
	try {
	    serverSocket.close();
	} catch(IOException ignore) { }
    }

    public void replyUsername(PrintWriter out) {
	out.print("Maximus Ivan Gladius");
	System.out.println("Username requested");

    }

    public void replyError(PrintWriter out) {
	out.print("Error");
    }
}

class MyRunnable implements Runnable {

    private Server server;
    private Socket client;

    // passing original server object to this constructor
    public MyRunnable(Server server, Socket client) {
	this.server = server;
	this.client = client;
    }

    public void run() {
	this.server.handle_connection(this.client);
	// long threadId = Thread.currentThread().getId();
	// System.out.print("ID: " + threadId + " ");
    }
}
