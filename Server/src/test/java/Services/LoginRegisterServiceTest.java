package Services;

import com.google.gson.Gson;

import org.junit.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import Data.Fnames;
import Data.Locations;
import Data.Mnames;
import Data.Snames;
import Requests.LoginRequest;
import Requests.RegisterRequest;
import Result.Error;
import Result.LoginResponse;
import Result.RegisterResponse;
import Result.Response;

import static org.junit.Assert.assertEquals;

public class LoginRegisterServiceTest {
    ClearService clearService = new ClearService();
    LoginService loginService = new LoginService();
    RegisterService registerService = new RegisterService();
    LoginRequest loginRequest = new LoginRequest("userName", "password");
    RegisterRequest registerRequest = new RegisterRequest("userName", "password", "email@gmail.com", "Irish", "Model", "f" );

    @Test
    public void registerTest(){
        clearService.clear();
        Response response = registerService.register(registerRequest, loadFnames(), loadMnames(), loadSnames(), loadLocations());
        RegisterResponse test = new RegisterResponse(null, null, null);
        assertEquals(test.getClass(), response.getClass());
    }
    @Test
    public void registerFailTest(){
        clearService.clear();
        registerService.register(registerRequest, loadFnames(), loadMnames(), loadSnames(), loadLocations());
        Response response = registerService.register(registerRequest, loadFnames(), loadMnames(), loadSnames(), loadLocations());
        if(response instanceof Error)
            assertEquals(true, true);
        else
            assertEquals(true, false);
    }
    @Test
    public void loginTest(){
        registerService.register(registerRequest, loadFnames(), loadMnames(), loadSnames(), loadLocations());
        Response response = loginService.login(loginRequest);
        LoginResponse test = new LoginResponse(null, null, null);
        assertEquals(test.getClass(), response.getClass());
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
