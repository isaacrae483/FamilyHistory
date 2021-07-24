package Result;

public class LoginResponse extends Response{
    String authToken;
    String userName;
    String id;

    /**
     * loginResponse constructor
     * @param a auth token
     * @param u username
     * @param i id
     */
    public LoginResponse(String a, String u, String i){
        authToken = a;
        userName = u;
        id = i;
    }
}