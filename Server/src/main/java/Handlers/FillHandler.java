package Handlers;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import com.google.gson.*;

import java.io.IOException;

import Data.Fnames;
import Data.Mnames;
import Data.Snames;
import Data.Locations;
import Result.Error;
import Result.FillResponse;
import Result.Response;
import Services.FillService;

import static java.lang.Integer.parseInt;

public class FillHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        FillService service = new FillService();
        Response response;
        String temp;
        String username = null;
        int generations = 4;

        try{
            //check request type
            if (exchange.getRequestMethod().toLowerCase().equals("post")){
                String urlPath = exchange.getRequestURI().getPath();
                //retrieve url
                temp = urlPath.substring(6, urlPath.length());
                //gather username and generations from url
                if(!temp.contains("/"))
                    username = temp;
                else{
                    int index = temp.indexOf("/");
                    username = temp.substring(0, index);
                    temp = temp.substring(index + 1, temp.length());
                    generations = parseInt(temp);
                }
                //gather sources to generate information
                Fnames fNames = loadFnames();
                Mnames mNames = loadMnames();
                Snames sNames = loadSnames();
                Locations locations = loadLocations();
                //call service
                response = service.fill(username, generations, fNames, mNames, sNames, locations);
                //check for non error response
                if(response instanceof FillResponse){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStreamWriter respBody = new OutputStreamWriter(exchange.getResponseBody());
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String jsonStr = gson.toJson(response);
                    respBody.write(jsonStr);
                    respBody.flush();
                    respBody.close();
                    success = true;
                }
                //return the error and error message
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
            //return on bad input
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
