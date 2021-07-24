package Services;

import org.junit.*;
import static org.junit.Assert.* ;

import Result.ClearResponse;
import Result.Response;

public class ClearServiceTest {

    ClearService service = new ClearService();

    @Test
    public void clearTest(){
        Response response = service.clear();
        ClearResponse test = new ClearResponse();
        assertEquals(test.getClass(), response.getClass());
    }

}
