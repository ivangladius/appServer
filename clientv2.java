import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import appServer.MessageObject;


class Client {

    private Socket clientSocket;
    private InetAddress address;
    private Integer port;

    private ExecutorService executor;

    // private PrintWriter output;
    // private BufferedReader input;
    private ObjectOutputStream out;
    private ObjectInputStream in;


    public Client(String address, Integer port) {
	try {
	    this.address = InetAddress.getByName(address);
	} catch (UnknownHostException e) {
	    System.out.println("HOST NOT REACHABLE !!!");
	    e.printStackTrace();
	}
	this.port = port;
    }

    private void connect() {
	try {
	    clientSocket = new Socket(this.address, this.port);
	    // output = new PrintWriter(clientSocket.getOutputStream(), true);
	    out = new ObjectOutputStream(clientSocket.getOutputStream());
	    
	} catch(IOException e) {
	    System.out.println("COULD NOT CONNECT!!!");
	} catch(NullPointerException e) {
	    e.printStackTrace();
	}
    }

    private void closeSocket() {
	if (!clientSocket.isClosed()) {
	    try {
		clientSocket.close();
	    } catch (IOException ignore) {}
	}
    }

    public String requestData(String operation) {

	String username = null;

	executor = Executors.newSingleThreadExecutor();
	Future<String> requestFuture;

	try {

		requestFuture = executor.submit(() -> {

			MessageObject requestMessage = new MessageObject(403, operation, null);
			MessageObject serverReply = null;

			connect();

			try {
				in = new ObjectInputStream(clientSocket.getInputStream());

				out.writeObject(requestMessage);

				// output.println("max@gmail.com getUsername");

				// System.out.println("sending succesful");

				// ObjectInputStream in = new ObjectInputStream(cSocket.getInputStream());

				// input = new BufferedReader
				// (new InputStreamReader(clientSocket.getInputStream()));
				// username = input.readLine();
				serverReply = (MessageObject) in.readObject();
				serverReply.print();

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

			closeSocket();

			return serverReply.getPayload();
		});

		username = requestFuture.get(5L, TimeUnit.SECONDS);
		executor.shutdown();

	} catch (TimeoutException e) {
	    e.printStackTrace();
	} catch (InterruptedException e) {
		e.printStackTrace();
	} catch (ExecutionException e) {
		e.printStackTrace();
	}

	return username;
    }
    /*
	public String getUsername() {

		executor = Executors.newSingleThreadExecutor();
		Future<String> usernameFuture;
		String username = null;

		try {
			MessageObject reply;
			usernameFuture = executor.submit(() -> {

				connect();

				try {
					in = new ObjectInputStream(clientSocket.getInputStream());

					MessageObject message = new MessageObject(403, "getUsername", null);

					out.writeObject(message);

					// output.println("max@gmail.com getUsername");

					// System.out.println("sending succesful");

					// ObjectInputStream in = new ObjectInputStream(cSocket.getInputStream());

					// input = new BufferedReader
					// (new InputStreamReader(clientSocket.getInputStream()));
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

				return "hello my friend";

			});
			while (!usernameFuture.isDone()) {
				System.out.println("waiting...");
				Thread.sleep(300);
			}
			username = usernameFuture.get(2L, TimeUnit.SECONDS);
			executor.shutdown();

		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return username;
	}
    */
}

// public class clientv2 {
//     public static void main(String[] args) {

// 	 Client client = new Client(args[0], Integer.parseInt(args[1]));

// 	try {
// 	    String username = client.requestData("getUsername");
// 	if (username != null)
// 	    System.out.println("\nUsername: " + username + "\n");
// 	else
// 	    System.out.println("\nUsername not found!");
// 	} catch (NullPointerException ignore) { }

// 	System.out.println("main done");

//     }
// }

