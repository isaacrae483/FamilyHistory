package Services;

import DAO.EventDAO;
import DAO.PersonDAO;
import DAO.UserDAO;
import Model.Event;
import Model.Person;
import Model.User;
import Requests.LoadRequest;
import Result.LoadResponse;
import Result.Response;
import Result.Error;

/**
 * load Service
 */

public class LoadService {
    /**
     * load
     * @return response fail/succeed
     */
    public Response load(LoadRequest request){
        UserDAO userDao = new UserDAO();
        PersonDAO personDao = new PersonDAO();
        EventDAO eventDao = new EventDAO();
        User[] userList = request.getUsers();
        Person[] personList = request.getPerson();
        Event[] eventList = request.getEvents();
        int userSize = userList.length;
        int personSize = personList.length;
        int eventSize = eventList.length;

        boolean success = false;


        ////this is sketchy
        for(Person x : personList){
            String fId = x.getmId();
            String mId = x.getfId();
            x.setfId(fId);
            x.setmId(mId);
        }

        userDao.openConnection();
        for(int i = 0; i < userSize; i++){
            //loads users into database
            success = userDao.createUser(userList[i]);
            if(!success){
                userDao.closeConnection(false);
                return new Error("Could not add users");
            }
        }
        userDao.closeConnection(true);

        personDao.openConnection();
        for(int i = 0; i < personSize; i++){
            //loads persons into database
            success = personDao.createPerson(personList[i]);
            if(!success){
                personDao.closeConnection(false);
                return new Error("Could not add people");
            }
        }
        personDao.closeConnection(true);

        eventDao.openConnection();
        for(int i = 0; i < eventSize; i++){
            //load events into database
            success = eventDao.createEvent(eventList[i]);
            if(!success){
                eventDao.closeConnection(false);
                return new Error("Could not add events");
            }
        }
        eventDao.closeConnection(true);

        return new LoadResponse(userSize, personSize, eventSize);
    }
}
