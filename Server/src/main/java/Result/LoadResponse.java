package Result;

public class LoadResponse extends Response {
    String message;


    /**
     * loadResponse constructor
     */
    public LoadResponse(int numUsers, int numPersons, int numEvents){
        message = "Successfully added " +
                numUsers + " users, " +
                numPersons + " persons and " +
                numEvents + " events to the database";
    }
}
