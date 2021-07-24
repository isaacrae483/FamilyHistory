package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import Model.Event;

/**
 * EventDAO
 */

public class EventDAO extends ParentDAO {
    /**
     * createEvent
     * @param event
     * @return eventID
     */
    public boolean createEvent(Event event){
        try{
            PreparedStatement stmt = null;
            try{
                //create new event in database
                String sql = "insert into Event values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, event.geteId());
                stmt.setString(2, event.getDescendant());
                stmt.setString(3, event.getpId());
                stmt.setString(4, event.getLatitude());
                stmt.setString(5, event.getLongitude());
                stmt.setString(6, event.getCountry());
                stmt.setString(7, event.getCity());
                stmt.setString(8, event.getType());
                stmt.setInt(9, event.getYear());
                stmt.executeUpdate();

            }finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                    return true;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * getDescendant
     * @param person
     * @return descendant Id
     */
    public String getDescendant(String person){
        try{
            //make query statement
            String query = "select Descendant from Event where Person = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, person);
            ResultSet results = stmt.executeQuery();
            //return results
            while(results.next()){
                return results.getString(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //returns set of events for a given user ID
    public Set<Event> getEvents(String Id){
        Set<Event> events = new HashSet<>();
        Event event;
        try{
            //make query statement
            String query = "select * from Event where Descendant = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, Id);
            ResultSet results = stmt.executeQuery();
            while(results.next()){
                event = new Event(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getInt(9));
                events.add(event);
            }
            //return set of events
            return events;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    // get a specific event based on the events ID
    public Event getEvent(String userName, String eventId){
        Set<Event> events = new HashSet<>();
        Event event;
        try{
            String query = "select * from Event where Descendant = ? and ID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userName);
            stmt.setString(2, eventId);
            ResultSet results = stmt.executeQuery();
            while(results.next()){
                event = new Event(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getInt(9));
                return event;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteEvents(String descendant){
        try{
            PreparedStatement stmt = null;
            try{
                String sql = "delete from Event where Descendant = ?";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, descendant);
                stmt.executeUpdate();
                }finally {
                    if (stmt != null) {
                        stmt.close();
                        stmt = null;
                        return true;
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
            return false;
        }
    }
