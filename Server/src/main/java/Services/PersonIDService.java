package Services;

import DAO.AuthDAO;
import DAO.PersonDAO;
import DAO.UserDAO;
import Model.Person;
import Result.PersonIDResponse;
import Result.Response;
import Result.Error;

public class PersonIDService {
    public Response getPerson(String personId, String token){
        PersonDAO PersonDao = new PersonDAO();
        UserDAO UserDao = new UserDAO();
        AuthDAO AuthDao = new AuthDAO();
        AuthDao.openConnection();
        //gets ID using authToken
        String userId = AuthDao.getId(token);
        if(userId == null)
        {
            AuthDao.closeConnection(true);
            return new Error("Could not find AuthToken");
        }
        AuthDao.closeConnection(true);
        UserDao.openConnection();
        String userName = UserDao.getUserName(userId);
        if(userName == null)
        {
            AuthDao.closeConnection(true);
            return new Error("Could not find User");
        }
        UserDao.closeConnection(true);
        PersonDao.openConnection();
        //finds person with given ID
        Person person = PersonDao.getPerson(personId, userName);
        if(person == null)
        {
            PersonDao.closeConnection(true);
            return new Error("Could not find person for ID");
        }

        PersonDao.closeConnection(true);

        return new PersonIDResponse(person);

    }
}
