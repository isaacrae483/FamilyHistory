package DAO;

import java.sql.*;
import java.util.*;

public class ParentDAO {
//starts the database and makes the file
    static{
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
            // ERROR! Could not load database driver
        }
    }
    protected Connection connection = null;
    protected Statement stmt = null;

    public void openConnection() {
        String connectionURL = "jdbc:sqlite:FamSearch.splite";

        try {
            // Open a database connection
            connection = DriverManager.getConnection(connectionURL);

            // Start a transaction
            connection.setAutoCommit(false);

        } catch (SQLException e) {
            e.printStackTrace();
            // ERROR
        }
    }

    public void closeConnection(boolean commit){
        try{
            //closes the connection and roles back if needed
            if(commit){
                connection.commit();
                connection.close();
                connection = null;
            }
            else{
                connection.rollback();
                connection.close();
                connection = null;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean createTables(){
        try{
            try{
                stmt = connection.createStatement();

                //drops all tables
                dropTables();
                //create tables in database
                createEventTable(stmt);
                createPersonTable(stmt);
                createUserTable(stmt);
                createAuthTokenTable(stmt);
            }finally{
                if(stmt != null){
                    stmt.close();
                    stmt = null;
                }
                return true;
            }

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void dropTables(){
        try{
            //drops all database tables
            stmt = connection.createStatement();
            stmt.executeUpdate("DROP TABLE IF EXISTS Event");
            stmt.executeUpdate("DROP TABLE IF EXISTS User");
            stmt.executeUpdate("DROP TABLE IF EXISTS Person");
            stmt.executeUpdate("DROP TABLE IF EXISTS AuthToken");

        }catch(Exception e){
            e.printStackTrace();
        }
    }



    void createEventTable(Statement stmt){
        try{
            stmt.executeUpdate("CREATE TABLE `Event` (\n" +
                    "\t`ID`\tTEXT NOT NULL UNIQUE,\n" +
                    "\t`Descendant`\tTEXT NOT NULL,\n" +
                    "\t`Person`\tTEXT NOT NULL,\n" +
                    "\t`Latitude`\tTEXT NOT NULL,\n" +
                    "\t`Longitude`\tTEXT NOT NULL,\n" +
                    "\t`Country`\tTEXT NOT NULL,\n" +
                    "\t`City`\tTEXT NOT NULL,\n" +
                    "\t`EventType`\tTEXT NOT NULL,\n" +
                    "\t`Year`\tINTEGER NOT NULL,\n" +
                    "\tPRIMARY KEY(`ID`)\n" +
                    ")");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    void createPersonTable(Statement stmt){
        try{
            stmt.executeUpdate("CREATE TABLE `Person` (\n" +
                    "\t`ID`\tTEXT NOT NULL UNIQUE,\n" +
                    "\t`Descendant`\tTEXT NOT NULL,\n" +
                    "\t`First`\tTEXT NOT NULL,\n" +
                    "\t`Last`\tTEXT NOT NULL,\n" +
                    "\t`Gender`\tTEXT NOT NULL,\n" +
                    "\t`Father`\tTEXT,\n" +
                    "\t`Mother`\tTEXT,\n" +
                    "\t`Spouse`\tTEXT,\n" +
                    "\tPRIMARY KEY(`ID`)\n"+
                    ")");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    void createUserTable(Statement stmt){
        try{
            stmt.executeUpdate("CREATE TABLE `User` (\n" +
                    "\t`Username`\tTEXT NOT NULL UNIQUE,\n" +
                    "\t`Password`\tTEXT NOT NULL,\n" +
                    "\t`Email`\tTEXT NOT NULL,\n" +
                    "\t`First`\tTEXT NOT NULL,\n" +
                    "\t`Last`\tTEXT NOT NULL,\n" +
                    "\t`Gender`\tTEXT NOT NULL,\n" +
                    "\t`ID`\tTEXT NOT NULL UNIQUE,\n" +
                    "\tPRIMARY KEY(`ID`)\n" +
                    ")");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    void createAuthTokenTable(Statement stmt){
        try{
            stmt.executeUpdate("CREATE TABLE `AuthToken` (\n" +
                    "\t`UserID`\tTEXT NOT NULL,\n" +
                    "\t`AuthToken`\tTEXT NOT NULL\n" +
                    ")");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}