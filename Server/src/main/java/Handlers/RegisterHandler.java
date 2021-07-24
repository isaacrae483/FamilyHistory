package Handlers;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import com.google.gson.*;

import Data.Fnames;
import Data.Locations;
import Data.Mnames;
import Data.Snames;
import Requests.RegisterRequest;
import Result.Error;
import Result.Response;
import Result.RegisterResponse;
import Services.RegisterService;

public class RegisterHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;
        RegisterService service = new RegisterService();
        Response response = null;
        RegisterRequest request;

        try{
            //check request type
            if (exchange.getRequestMethod().toLowerCase().equals("post")){
                //get input data
                InputStreamReader reqBody = new InputStreamReader(exchange.getRequestBody());
                Gson gson = new Gson();
                request = gson.fromJson(reqBody, RegisterRequest.class);
                // call service
                Fnames fNames = loadFnames();
                Mnames mNames = loadMnames();
                Snames sNames = loadSnames();
                Locations locations = loadLocations();
                response = service.register(request, fNames, mNames, sNames, locations);
                //check for non error
                if(response instanceof RegisterResponse){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStreamWriter respBody = new OutputStreamWriter(exchange.getResponseBody());
                    gson = new GsonBuilder().setPrettyPrinting().create();
                    String jsonStr = gson.toJson(response);
                    respBody.write(jsonStr);
                    respBody.flush();
                    respBody.close();
                    success = true;
                }
                //return error
                else if(response instanceof Error){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                    OutputStreamWriter respBody = new OutputStreamWriter(exchange.getResponseBody());
                    gson = new GsonBuilder().setPrettyPrinting().create();
                    String jsonStr = gson.toJson(response);
                    respBody.write(jsonStr);
                    respBody.flush();
                    respBody.close();
                    success = true;
                }
            }
            //return bad input
            if(!success){
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
    Fnames loadFnames(){
        //load list of female names from file
        try{
            String fileName = "json/fnames.json";
            InputStreamReader reqBody = new InputStreamReader(new FileInputStream(new File(fileName)));
            Gson gson = new Gson();
            return gson.fromJson(reqBody, Fnames.class);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    Mnames loadMnames(){
        //load list of male names form file
        try{
            String fileName = "json/mnames.json";
            InputStreamReader reqBody = new InputStreamReader(new FileInputStream(new File(fileName)));
            Gson gson = new Gson();
            return gson.fromJson(reqBody, Mnames.class);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    Snames loadSnames(){
        //load list of last names from file
        try{
            String fileName = "json/snames.json";
            InputStreamReader reqBody = new InputStreamReader(new FileInputStream(new File(fileName)));
            Gson gson = new Gson();
            return gson.fromJson(reqBody, Snames.class);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    Locations loadLocations(){
        //load list of locations from file
        try{
            String fileName = "json/locations.json";
            InputStreamReader reqBody = new InputStreamReader(new FileInputStream(new File(fileName)));
            Gson gson = new Gson();
            return gson.fromJson(reqBody, Locations.class);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
