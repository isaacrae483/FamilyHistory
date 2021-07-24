package Handlers;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import com.google.gson.*;

import java.io.IOException;

import Result.Error;
import Result.PersonResponse;
import Result.Response;
import Services.PersonService;

public class PersonHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        PersonService service = new PersonService();
        Response response = null;
        String authToken;

        try{
            if (exchange.getRequestMethod().toLowerCase().equals("get")){
                // Get the HTTP request headers
                Headers reqHeaders = exchange.getRequestHeaders();
                // Check to see if an "Authorization" header is present
                if (reqHeaders.containsKey("Authorization")) {
                    authToken = reqHeaders.getFirst("Authorization");
                    //call service
                    response = service.personService(authToken);
                }
                //check for non error
                if(response instanceof PersonResponse){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStreamWriter respBody = new OutputStreamWriter(exchange.getResponseBody());
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String jsonStr = gson.toJson(response);
                    respBody.write(jsonStr);
                    respBody.flush();
                    respBody.close();
                    success = true;
                }
                //return the Error
                else if(response instanceof Error){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                    OutputStreamWriter respBody = new OutputStreamWriter(exchange.getResponseBody());
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String jsonStr = gson.toJson(response);
                    respBody.write(jsonStr);
                    respBody.flush();
                    respBody.close();
                    success = true;
                }
            }
            //returns for bad input
            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                response = new Error("wrong input");
                OutputStreamWriter respBody = new OutputStreamWriter(exchange.getResponseBody());
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String jsonStr = gson.toJson(response);
                respBody.write(jsonStr);
                respBody.flush();
                respBody.close();
            }

        }catch(IOException e){
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

}
