package server;

import com.sun.net.httpserver.HttpServer;
import handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    static final int port = 8080;

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.startServer(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startServer(int port) throws IOException {
        InetSocketAddress serverAddress = new InetSocketAddress(port);
        HttpServer server = HttpServer.create(serverAddress, 10);
        registerHandlers(server);
        server.start();
        System.out.println("FamilyMapServer listening on port " + port);
    }

    private void registerHandlers(HttpServer server) {
        server.createContext("/", new FileRequestHandler());
        server.createContext("/user/register", new RegisterRequestHandler());
        server.createContext("/clear", new ClearRequestHandler());
        server.createContext("/user/login", new LoginRequestHandler());
        server.createContext("/event/", new EventRequestHandler());
        server.createContext("/event", new EventsRequestHandler());
        server.createContext("/person/", new PersonRequestHandler());
        server.createContext("/person", new PersonsRequestHandler());
        server.createContext("/load", new LoadRequestHandler());
        server.createContext("/fill/", new FillRequestHandler());


    }
}
