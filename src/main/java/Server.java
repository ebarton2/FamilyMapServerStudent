import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import handler.*;

public class Server {
    //private static final String DEFAULT_SERVER_PORT = "8080";
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    private void run(String portNumber) {
        System.out.println("Initializing HTTP Server");

        try {
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNumber)), MAX_WAITING_CONNECTIONS);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        server.createContext("/clear", new ClearHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/event", new EventHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/", new FileHandler());

        server.start();
        System.out.println("Server started on port: " + portNumber);
    }

    public static void main(String args[]) {
        String portNumber = args[0];
        new Server().run(portNumber);
    }


}
