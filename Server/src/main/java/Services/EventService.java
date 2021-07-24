package Services;

import java.util.Set;

import javax.jws.soap.SOAPBinding;

import DAO.AuthDAO;
import DAO.EventDAO;
import DAO.PersonDAO;
import DAO.UserDAO;
import Model.Event;
import Result.EventResponse;
import Result.Response;
import Result.Error;

/**
 * EventService
 */

public class EventService {
    /**
     * eventService
     * @return response fail/succeed
     */
    public Response eventService(String token){
        EventDAO EventDao = new EventDAO();
        AuthDAO AuthDao = new AuthDAO();
        UserDAO UserDao = new UserDAO();
        AuthDao.openConnection();
        //gets ID using authToken
        String Id = AuthDao.getId(token);
        if(Id == null)
        {
            AuthDao.closeConnection(true);
            return new Error("Could not find AuthToken");
        }
        AuthDao.closeConnection(true);
        UserDao.openConnection();
        //gets username for the ID
        String userName = (UserDao.getUserName(Id));
        if(userName == null)
        {
            AuthDao.closeConnection(true);
            return new Error("Could not find User");
        }
        UserDao.closeConnection(true);
        EventDao.openConnection();
        //finds all events for user
        Set<Event> events = EventDao.getEvents(userName);
        if(events == null)
        {
            EventDao.closeConnection(true);
            return new Error("Could not find any events for user");
        }

        EventDao.closeConnection(true);
        return new EventResponse(events);

    }
}
