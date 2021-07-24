import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import Handlers.ClearHandler;
import Handlers.EventHandler;
import Handlers.EventIDHandler;
import Handlers.FileHandler;
import Handlers.FillHandler;
import Handlers.LoadHandler;
import Handlers.LoginHandler;
import Handlers.PersonHandler;
import Handlers.PersonIDHandler;
import Handlers.RegisterHandler;

public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    //most of the work
    void run(String portNumber){

        //start the server
        System.out.println("Initializing HTTP Server");

        try{

            //Create new HTTP Server object
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }catch(IOException e){
            e.printStackTrace();
            return;
        }

        //using default "executor"
        server.setExecutor(null);

        System.out.println("Creating contexts");

        // create and install the HTTP handler for the given URL path
        server.createContext("/clear", new ClearHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/event/", new EventIDHandler());
        server.createContext("/event", new EventHandler());
        server.createContext("/person/", new PersonIDHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/", new FileHandler());


        System.out.println("Starting server");
        server.start();

    }


//main method
    public static void main(String args[]){
        String portNumber = args[0];
        new Server().run(portNumber);

    }
}
