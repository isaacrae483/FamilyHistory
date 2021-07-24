package Services;

import com.google.gson.Gson;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import Data.Fnames;
import Data.Locations;
import Data.Mnames;
import Data.Snames;
import Requests.RegisterRequest;
import Result.FillResponse;
import Result.Response;

import static org.junit.Assert.assertEquals;

public class FillServiceTest {

    FillService fillService = new FillService();
    RegisterService registerService = new RegisterService();
    RegisterRequest registerRequest = new RegisterRequest("userName", "password", "email@gmail.com", "Irish", "Model", "f" );

    @Test
    public void fillTest(){
        registerService.register(registerRequest, loadFnames(), loadMnames(), loadSnames(), loadLocations());
        Response response = fillService.fill("userName", 4, loadFnames(), loadMnames(), loadSnames(), loadLocations());
        FillResponse test = new FillResponse(0, 0);
        assertEquals(test.getClass(), response.getClass());
    }

    Fnames loadFnames(){

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
