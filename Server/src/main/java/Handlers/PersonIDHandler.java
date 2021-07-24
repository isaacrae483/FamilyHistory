package Handlers;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import com.google.gson.*;

import java.io.IOException;

import Model.Person;
import Result.Error;
import Result.PersonIDResponse;
import Result.Response;
import Services.PersonIDService;

public class PersonIDHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        PersonIDService service = new PersonIDService();
        Response response = null;
        String authToken;
        String personId = null;

        try{
            //check request type
            if (exchange.getRequestMethod().toLowerCase().equals("get")){
                String urlPath = exchange.getRequestURI().getPath();
                //get person id from url
                personId = urlPath.substring(8, urlPath.length());
                // Get the HTTP request headers
                Headers reqHeaders = exchange.getRequestHeaders();
                // Check to see if an "Authorization" header is present
                if (reqHeaders.containsKey("Authorization")) {
                    authToken = reqHeaders.getFirst("Authorization");
                    response = service.getPerson(personId, authToken);
                }
                //check for non error response
                if(response instanceof PersonIDResponse){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStreamWriter respBody = new OutputStreamWriter(exchange.getResponseBody());
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    Person person = ((PersonIDResponse) response).getPerson();
                    String jsonStr = gson.toJson(person);
                    respBody.write(jsonStr);
                    respBody.flush();
                    respBody.close();
                    success = true;
                }
                //return error
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
            //return for bad input
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
