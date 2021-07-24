package Result;

/**
 * error
 */

public class Error extends Response {
    String message;

    /**
     * constructor
     * @param e     String containing specific error
     */

    public Error(String e){
        message = e;
    }

}
