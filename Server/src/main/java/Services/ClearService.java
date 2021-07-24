package Services;

import DAO.ParentDAO;
import Result.Error;
import Result.Response;
import Result.ClearResponse;

/**
 * clearService
 */

public class ClearService {
    /**
     * clear
     * @return response fail/succeed
     */
    public Response clear() {
        ParentDAO dao = new ParentDAO();
        dao.openConnection();
        //deletes old tables and creates new tables for the database
        if (dao.createTables()){
            dao.closeConnection(true);
            return new ClearResponse();
        }
        dao.closeConnection(false);
        return new Error("Could not clear the data base");
    }
}
