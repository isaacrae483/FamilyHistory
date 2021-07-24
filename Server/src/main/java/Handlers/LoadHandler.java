package Handlers;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import com.google.gson.*;

import Requests.LoadRequest;
import Result.Error;
import Result.LoadResponse;
import Result.Response;
import Services.LoadService;

public class LoadHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;
        LoadService service = new LoadService();
        Response response = null;
        LoadRequest request;

        try{
            //check request type
            if (exchange.getRequestMethod().toLowerCase().equals("post")){
                //get request data
                InputStreamReader reqBody = new InputStreamReader(exchange.getRequestBody());
                Gson gson = new Gson();
                request = gson.fromJson(reqBody, LoadRequest.class);
                //call service
                response = service.load(request);
                //check for non error
                if(response instanceof LoadResponse){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStreamWriter respBody = new OutputStreamWriter(exchange.getResponseBody());
                    gson = new GsonBuilder().setPrettyPrinting().create();
                    String jsonStr = gson.toJson(response);
                    respBody.write(jsonStr);
                    respBody.flush();
                    respBody.close();
                    success = true;
                }
                //returns error
                else if(response instanceof Error){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                    OutputStreamWriter respBody = new OutputStreamWriter(exchange.getResponseBody());
                    gson = new GsonBuilder().setPrettyPrinting().create();
                    //description of error
                    response = new Error("Bad Input");
                    String jsonStr = gson.toJson(response);
                    respBody.write(jsonStr);
                    respBody.flush();
                    respBody.close();
                    success = true;
                }
            }
            // return on bad input
            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
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
