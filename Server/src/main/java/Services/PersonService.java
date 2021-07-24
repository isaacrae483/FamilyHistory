package Services;

import java.util.Set;

import DAO.AuthDAO;
import DAO.PersonDAO;
import DAO.UserDAO;
import Model.Person;
import Result.PersonResponse;
import Result.Response;
import Result.Error;

/**
 * PersonService
 */

public class PersonService {
    /**
     * personService
     * @return response fail/succeed
     */
    public Response personService(String token){
        PersonDAO PersonDao = new PersonDAO();
        AuthDAO AuthDao = new AuthDAO();
        UserDAO UserDao = new UserDAO();
        AuthDao.openConnection();
        //finds ID based on authToken
        String Id = AuthDao.getId(token);
        if(Id == null)
        {
            AuthDao.closeConnection(true);
            return new Error("Could not find AuthToken");
        }
        AuthDao.closeConnection(true);
        UserDao.openConnection();
        String userName = UserDao.getUserName(Id);
        //gets username for ID
        if(userName == null){
            UserDao.closeConnection(true);
            return new Error("Could not find user");
        }
        UserDao.closeConnection(true);
        PersonDao.openConnection();
        //gathers all people connected to the user
        Set<Person> tree = PersonDao.getTree(userName);
        if(tree == null)
        {
            PersonDao.closeConnection(true);
            return new Error("Could not find any people for user");
        }

        PersonDao.closeConnection(true);
        PersonResponse response = new PersonResponse(tree);
        return response;

    }
}
