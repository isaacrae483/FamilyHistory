package Result;

public class RegisterResponse extends Response{
    String authToken;
    String userName;
    String id;

    /**
     * registerResponce constructor
     * @param a auth token
     * @param u user name
     * @param i id
     */

    public RegisterResponse(String a, String u, String i){
        authToken = a;
        userName = u;
        id = i;
    }
}