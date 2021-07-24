package Services;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import DAO.EventDAO;
import DAO.PersonDAO;
import DAO.UserDAO;
import Data.Fnames;
import Data.Locations;
import Data.Mnames;
import Data.Snames;
import Model.Event;
import Model.Location;
import Model.Person;
import Model.User;
import Result.FillResponse;
import Result.Response;
import Result.Error;

/**
 * FillService
 */
public class FillService {
    Fnames femaleNames;
    Mnames maleNames;
    Snames surNames;
    Locations locations;
    String username;
    Set<Person> persons = new HashSet<>();
    Set<Event> events = new HashSet<>();

    /**
     * fill
     * @return response fail/succeed
     */
    public Response fill(String userName, int generations, Fnames fNames, Mnames mNames, Snames sNames, Locations locs){
        boolean success;
        //sets data globally
        femaleNames = fNames;
        maleNames = mNames;
        surNames = sNames;
        locations = locs;
        username = userName;
        UserDAO userDao = new UserDAO();
        userDao.openConnection();
        //finds the user that matches the username
        User user = userDao.findUser(userName);
        if(user == null){
            userDao.closeConnection(true);
            return new Error("no User with UserName");
        }
        userDao.closeConnection(true);
        //creates person for the user
        Person person = new Person(user.getId(), userName, user.getFirstName(), user.getLastName(), user.getGender());
        //generates tree for user and events
        createTree(generations, person, 2000, null);

        PersonDAO personDao = new PersonDAO();
        personDao.openConnection();
        //clears data for current user
        success = personDao.deleteTree(username);
        if(!success){
            personDao.closeConnection(false);
            return new Error("could not clear database");
        }
        int pAdd = 0;
        for(Person x : persons){
            //adds all people to the data base
            success = personDao.createPerson(x);
            if(!success){
                personDao.closeConnection(false);
                return new Error("could not add people to dataBase");
            }
            pAdd++;
        }
        personDao.closeConnection(true);

        EventDAO eventDao = new EventDAO();
        eventDao.openConnection();
        success = eventDao.deleteEvents(username);
        if(!success){
            personDao.closeConnection(false);
            return new Error("could not clear database");
        }
        int eAdd = 0;
        for(Event x : events){
            //adds all events to database
            success = eventDao.createEvent(x);
            if(!success){
                eventDao.closeConnection(false);
                return new Error("could not add events to dataBase");
            }
            eAdd++;
        }
        eventDao.closeConnection(true);

        return new FillResponse(pAdd, eAdd);
    }

    private void createTree(int generations, Person person, int birthYear, Location marriageLoc){
        String motherID = null;
        String fatherID = null;
        if(generations != 0){
            motherID = createId();
            fatherID = createId();
        }
        //sets mother and father IDs for person
        person.setmId(fatherID);
        person.setfId(motherID);
        persons.add(person);
        //creates birth marriage and death events for person
        createEvents(birthYear, person.getId(), marriageLoc);
        int parentBirthYear = birthYear - 23;
        if(generations != 0){
            //recursive call on both the mother and father till generations hits 0
            int size = locations.getSize();
            Random random = new Random();
            Location marriageLocation = locations.getData(random.nextInt(size));
            Person Father = makeFather(fatherID, motherID);
            Person Mother = makeMother(motherID, fatherID);
            createTree((generations - 1), Father, parentBirthYear, marriageLocation);
            createTree((generations - 1), Mother, parentBirthYear, marriageLocation);
        }
    }

    private Person makeFather(String personID, String spouseID){
        //makes father for person
        Random random = new Random();
        int size = maleNames.getSize();
        String firstName = maleNames.getData(random.nextInt(size));
        size = surNames.getSize();
        String lastName = surNames.getData(random.nextInt(size));
        return new Person(personID, username, firstName, lastName, "m", spouseID);
    }
    private Person makeMother(String personID, String spouseID){
        //makes mother for person
        Random random = new Random();
        int size = femaleNames.getSize();
        String firstName = femaleNames.getData(random.nextInt(size));
        size = surNames.getSize();
        String lastName = surNames.getData(random.nextInt(size));
        return new Person(personID, username, firstName, lastName, "f", spouseID);
    }


    private void createEvents(int birthYear, String personID, Location marriageLoc){
        events.add(birth(birthYear, personID));
        if(marriageLoc != null){
            events.add(marriage(birthYear, personID, marriageLoc));
        }
        events.add(death(birthYear,personID));
    }
    private Event birth(int birthYear, String personID){
        //makes birth event
        String eventID = createId();
        int size = locations.getSize();
        Random random = new Random();
        Location location = locations.getData(random.nextInt(size));
        return new Event(eventID, username, personID, location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity() ,"Birth", birthYear);
    }
    private Event marriage(int birthYear, String personID, Location marriageLoc){
        //makes marriage event
        String eventID = createId();
        int marriageYear = birthYear + 21;
        Location location = marriageLoc;
        return new Event(eventID, username, personID, location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity() ,"Marriage", marriageYear);
    }
    private Event death(int birthYear, String personID){
        //makes death event
        String eventID = createId();
        int deathYear = birthYear + 87;
        int size = locations.getSize();
        Random random = new Random();
        Location location = locations.getData(random.nextInt(size));
        return new Event(eventID, username, personID, location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity() ,"Death", deathYear);
    }

    //generates random 6 digit id
    private String createId(){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++){
            sb.append(random.nextInt(9));
        }

        return sb.toString();
    }
}
