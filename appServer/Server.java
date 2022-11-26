
package appServer;

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {

    private int port;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

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
		clientSocket = serverSocket.accept();
		executor.execute(new MyRunnable(this));
	    } catch(IOException ignore) {}
	}
    }

    public void handle_connection(Socket cSocket) {

	try {
	    
	    out = new PrintWriter(clientSocket.getOutputStream(), true);
	    in = new BufferedReader
		(new InputStreamReader(cSocket.getInputStream()));

	    String msg = in.readLine();
	    System.out.println("Client send: " + msg);

	    close_connection(cSocket);

	} catch(UnknownHostException e) {
	    close_connection(cSocket);
	    e.printStackTrace();
	} catch(IOException e) {
	    close_connection(cSocket);
	    e.printStackTrace();
	}
    }

    private void close_connection(Socket cSocket) {
	try {
	    in.close();
	    out.close();
	    cSocket.close();
	} catch(IOException ignore) {} 
    }

    public void stop() {
	try {
	    close_connection(clientSocket);
	    serverSocket.close();
	} catch(IOException ignore) { }
    }

    public ServerSocket getServerSocket() { return this.serverSocket; }
    public Socket getClientSocket() { return this.clientSocket; }
}

class MyRunnable implements Runnable {

    private Server server;

    public MyRunnable(Server server) {
	this.server = server;
    }
    public void run() {
	server.handle_connection(server.getClientSocket());
	long threadId = Thread.currentThread().getId();
	System.out.print("ID: " + threadId + " ");
    }
}
