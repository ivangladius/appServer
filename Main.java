import appServer.Server2;

public class Main {
    public static void main(String[] args) {

	Server2 server = new Server2(Integer.parseInt(args[0]), 10);

	server.start();

	server.loop();

	server.stop();
    }
}
