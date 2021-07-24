package Services;

import com.google.gson.Gson;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import Requests.LoadRequest;

import Result.LoadResponse;
import Result.Response;

import static org.junit.Assert.assertEquals;

public class LoadServiceTest {

    LoadService service = new LoadService();
    LoadRequest request;


    @Test
    public void clearTest(){
        try{
            String fileName = "json/example.json";
            InputStreamReader reqBody = new InputStreamReader(new FileInputStream(new File(fileName)));
            Gson gson = new Gson();
            request = gson.fromJson(reqBody, LoadRequest.class);
        }catch(IOException e){
            e.printStackTrace();
        }
        Response response = service.load(request);
        LoadResponse test = new LoadResponse(0, 0, 0);
        assertEquals(test.getClass(), response.getClass());
    }

}
