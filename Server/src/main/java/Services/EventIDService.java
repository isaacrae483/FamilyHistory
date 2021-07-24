package Services;

import DAO.AuthDAO;
import DAO.EventDAO;
import DAO.UserDAO;
import Model.Event;
import Result.Error;
import Result.EventIDResponse;
import Result.Response;

public class EventIDService {
    public Response getEvent(String eventId, String token){
        EventDAO EventDao = new EventDAO();
        AuthDAO AuthDao = new AuthDAO();
        UserDAO UserDao = new UserDAO();
        AuthDao.openConnection();
        //gets the user ID using authtoken
        String userId = AuthDao.getId(token);

        if(userId == null)
        {
            AuthDao.closeConnection(true);
            return new Error("Could not find AuthToken");
        }
        AuthDao.closeConnection(true);
        UserDao.openConnection();
        //gets username based on the ID
        String userName = (UserDao.getUserName(userId));
        if(userName == null){
            UserDao.closeConnection(true);
            return new Error("Could not find User");
        }
        UserDao.closeConnection(true);
        EventDao.openConnection();
        //finds the specified event
        Event event = EventDao.getEvent(userName, eventId);
        if(event == null)
        {
            EventDao.closeConnection(true);
            return new Error("Could not find any event for ID");
        }

        EventDao.closeConnection(true);

        return new EventIDResponse(event);

    }
}
