package Result;

public class FillResponse extends Response {
    String message;

    /**
     * fillResponse constructor
     */
    public FillResponse(int numPersons, int numEvents){
        message = "Successfully added " +
                numPersons + " persons and " +
                numEvents + " events to the database";
    }
}
