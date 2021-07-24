package DAO;

import java.sql.Statement;
import java.sql.*;
import java.util.*;
import Model.User;

/**
 * UserDAO
 */

public class UserDAO extends ParentDAO {
    /**
     * createUser
     * @param user
     * @return
     */
    public boolean createUser (User user){
        try{
            PreparedStatement stmt = null;
            try{
                String sql = "insert into User values (?, ?, ?, ?, ?, ?, ?)";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, user.getUsername());
                stmt.setString(2,user.getPassword());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getFirstName());
                stmt.setString(5, user.getLastName());
                stmt.setString(6, user.getGender());
                stmt.setString(7, user.getId());
                if (stmt.executeUpdate() != 1) {
                    throw new Exception("fillDictionary failed: Could not insert word");
                }


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
     * getUser
     * @param id
     * @return
     */
    public User getUser(String id){
       try{
           String query = "select * from User where ID = ?";
           PreparedStatement stmt = connection.prepareStatement(query);
           stmt.setString(1, id);
           ResultSet results = stmt.executeQuery();
           User rtnUser = null;
           while (results.next()) {
              rtnUser = new User(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7));
           }
           return rtnUser;
       }catch(Exception e){
           e.printStackTrace();
           return null;
       }
    }

    public User findUser(String userName){
        try{
            String query = "select * from User where Username = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userName);
            ResultSet results = stmt.executeQuery();
            User rtnUser = null;
            while (results.next()) {
                rtnUser = new User(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7));
            }
            return rtnUser;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * verify
     * @param username
     * @param Password
     * @return
     */
    public String verify(String username, String Password){
        try{
            String query = "select * from User where Username = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet results = stmt.executeQuery();
            while(results.next()){
                if(Password.equals(results.getString(2))) {
                    return results.getString(7);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }
//gets the userName for a user based on the given ID
    public String getUserName(String Id){
        try{
            String query = "select Username from User where Id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, Id);
            ResultSet results = stmt.executeQuery();
            User rtnUser = null;
            while (results.next()) {
                return results.getString(1);
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

}
