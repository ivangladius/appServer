import appServer.Server;

public class Main {
    public static void main(String[] args) {

	Server server = new Server(Integer.parseInt(args[0]), 10);

	server.start();

	server.loop();

	server.stop();
    }
}
