package Handlers;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import com.google.gson.*;

import java.io.IOException;

import Model.Event;
import Result.Error;
import Result.EventIDResponse;
import Result.Response;
import Services.EventIDService;

public class EventIDHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        EventIDService service = new EventIDService();
        Response response = null;
        String authToken;
        String eventId = null;

        try{
            if (exchange.getRequestMethod().toLowerCase().equals("get")){
                String urlPath = exchange.getRequestURI().getPath();
                //pull event ID from url
                eventId = urlPath.substring(7, urlPath.length());
                // Get the HTTP request headers
                Headers reqHeaders = exchange.getRequestHeaders();
                // Check to see if an "Authorization" header is present
                if (reqHeaders.containsKey("Authorization")) {
                    authToken = reqHeaders.getFirst("Authorization");
                    //call service
                    response = service.getEvent(eventId, authToken);
                }
                //check for non error response
                if(response instanceof EventIDResponse){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStreamWriter respBody = new OutputStreamWriter(exchange.getResponseBody());
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    Event event = ((EventIDResponse) response).getEvent();
                    //return response data
                    String jsonStr = gson.toJson(event);
                    respBody.write(jsonStr);
                    respBody.flush();
                    respBody.close();
                    success = true;
                }
                else if(response instanceof Error){
                    //send error
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                    OutputStreamWriter respBody = new OutputStreamWriter(exchange.getResponseBody());
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    //return error message
                    String jsonStr = gson.toJson(response);
                    respBody.write(jsonStr);
                    respBody.flush();
                    respBody.close();
                    success = true;
                }
            }
            if(!success){
                //return bad input error
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
