package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Model.AuthToken;

/**
 * AuthDAO
 */

public class AuthDAO extends ParentDAO{
    /**
     * getAuthToken
     * @param userId
     * @return authToken
     */
    public AuthToken getAuthToken(String userId){
        try{
            //set query
            String query = "select * from AuthToken where UserID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet results = stmt.executeQuery();
            AuthToken rtnToken = null;
            //create token from database result
            while (results.next()) {
                rtnToken = new AuthToken(results.getString(1), results.getString(2));
            }
            return rtnToken;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
//gets ID  of user based on the token
    public String getId(String token){
        try{
            //make query
            String query = "select UserID from AuthToken where AuthToken = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, token);
            ResultSet results = stmt.executeQuery();
            //return found ID
            while (results.next()) {
                return  results.getString(1);
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * sets authToken
     * @param userId
     * @param token
     */
    public boolean setAuthToken(String userId, String token){
        try{
            PreparedStatement stmt = null;
            try{
                //makes new authToken in table
                String sql = "insert into AuthToken values (?, ?)";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, userId);
                stmt.setString(2, token);
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
