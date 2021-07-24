package Result;

public class ClearResponse extends Response{
    String message = "Clear succeeded";

    public ClearResponse(){
        message = "Clear succeeded";
    }

    public String getMessage() {
        return message;
    }

}
