package Handlers;

import java.io.*;
import java.net.*;
import java.nio.file.Files;

import com.sun.net.httpserver.*;

public class FileHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        boolean success = false;
        try{
            //check request method
            if(!exchange.getRequestMethod().toLowerCase().equals("get")){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }

            //get url path
            String urlPath = exchange.getRequestURI().getPath();
            //return index.html as default
            if(urlPath.length() == 0 || urlPath.equals("/"))
                urlPath = "/index.html";

            String filePath = "web" + urlPath;
            //retrieve file
            File file = new File(filePath);
            //return file
            if(file.exists() && file.canRead()){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                Files.copy(file.toPath(), respBody);
                respBody.close();
                success = true;
            }
            //return bad input
            filePath = "web/HTML/404.html";
            file = new File(filePath);
            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream respBody = exchange.getResponseBody();
                Files.copy(file.toPath(), respBody);
                respBody.close();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
