package Handlers;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import com.google.gson.*;

import Result.Error;
import Result.Response;
import Result.ClearResponse;
import Services.ClearService;

public class ClearHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;
        //initialize service
        ClearService service = new ClearService();
        Response response = null;

        try{
            //check request method
            if (exchange.getRequestMethod().toLowerCase().equals("post")){
                //call service
                response = service.clear();
                //check response for error
                if(response instanceof ClearResponse){
                    //return OK
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStreamWriter respBody = new OutputStreamWriter(exchange.getResponseBody());
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    //return given response body
                    String jsonStr = gson.toJson(response);
                    respBody.write(jsonStr);
                    respBody.flush();
                    respBody.close();
                    success = true;
                }
                else if(response instanceof Error){
                    //return internal error
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
                //return for bad input
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStreamWriter respBody = new OutputStreamWriter(exchange.getResponseBody());
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                response = new Error("Bad Input");
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