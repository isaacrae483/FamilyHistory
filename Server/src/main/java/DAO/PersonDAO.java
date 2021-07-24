package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import Model.Person;

/**
 * PersonDAO
 */

public class PersonDAO extends ParentDAO {
    /**
     * getPerson
     * @param id
     * @return Person
     */
    public Person getPerson(String id, String userName){
        try{
            String query = "select * from Person where ID = ? and Descendant = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, id);
            stmt.setString(2, userName);
            ResultSet results = stmt.executeQuery();
            while(results.next()){
                Person person = new Person(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8));
                return person;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getTree
     * @param userName
     * @return family tree
     */
    public Set<Person> getTree(String userName){
        Set<Person> people = new HashSet<>();
        try{
            String query = "select * from Person where Descendant = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userName);
            ResultSet results = stmt.executeQuery();
            while(results.next()){
                Person person = new Person(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8));
                people.add(person);
            }
            return people;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * createPerson
     * @param person
     * @return personID
     */
    public boolean createPerson(Person person){
        try{
            PreparedStatement stmt = null;
            try{
                String sql = "insert into Person values (?, ?, ?, ?, ?, ?, ?, ?)";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, person.getId());
                stmt.setString(2, person.getDescendant());
                stmt.setString(3, person.getFirstName());
                stmt.setString(4, person.getLastName());
                stmt.setString(5, person.getGender());
                stmt.setString(6, person.getfId());
                stmt.setString(7, person.getmId());
                stmt.setString(8, person.getsId());
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
//clears the tree for a given user
    public boolean deleteTree(String username){
        try{
            PreparedStatement stmt = null;
            try{
                String sql = "delete from Person where Descendant = ?";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, username);
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
