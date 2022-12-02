
package appServer;

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

import appServer.MessageObject;

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
	    // PrintWriter out = new PrintWriter(cSocket.getOutputStream(), true);
	    // BufferedReader in = new BufferedReader
	    // 	(new InputStreamReader(cSocket.getInputStream()));

	    //	    String operation = in.readLine();

	    ObjectOutputStream out = new ObjectOutputStream(cSocket.getOutputStream());
	    ObjectInputStream in = new ObjectInputStream(cSocket.getInputStream());

	    MessageObject message = (MessageObject) in.readObject();

	    if (message.getOperation().equals("getUsername"))
		replyUsername(out, message.getKey());

	    message.print();

	    // String[] query = operation.split(" ");

	    // String email = query[0];
	    // String op = query[1];

	    // System.out.println("EMAIL : " + email + " OP: " + op);

	    // if (operation != null) {
	    // 	if (operation.equals("getUsername"))
	    // 	    replyUsername(out);
	    // } else {
	    // 	replyError(out);
	    // }

	    out.close();
	    in.close();
	    closeClient(cSocket);

	} catch(UnknownHostException e) {
	    closeClient(cSocket);
	    e.printStackTrace();
	} catch(IOException e) {
	    closeClient(cSocket);
	    e.printStackTrace();
	} catch(ClassNotFoundException e) {
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

    public void replyUsername(ObjectOutputStream out, int key) {
	try {
	    MessageObject replyMessage = new MessageObject(key, null, "hans peter gustav");
	    out.writeObject(replyMessage);
	} catch (IOException e) {
	    // ignore
	}
	System.out.println("Username requested\n");
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
