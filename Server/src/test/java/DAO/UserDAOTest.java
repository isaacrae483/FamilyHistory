package DAO;

import org.junit.* ;

import Model.User;

import static org.junit.Assert.* ;

public class UserDAOTest {

    private UserDAO db;
    private User user;
    private User user2;

    @Before
    public void setUp() {
        user = new User("turtlePower", "Turtle123", "tpower123@gmail", "john", "smith", "f", "123456");
        user2 = new User("hawkPower", "Hawk123", "htothemax123@gmail", "jane", "doe", "f", "654321");
        db = new UserDAO();
        db.openConnection();
    }

    @After
    public void tearDown(){
        db.closeConnection(true);
    }

    @Test
    public void testCreateUser() {
        db.createTables();
        db.createUser(user);
        db.createUser(user2);
    }
    @Test
    public void testGetUser(){
        testCreateUser();
        User testUser = db.getUser("654321");
        assertEquals("hawkPower", testUser.getUsername());
        assertEquals("Hawk123", testUser.getPassword());
        assertEquals("htothemax123@gmail", testUser.getEmail());
        assertEquals("jane", testUser.getFirstName());
        assertEquals("doe", testUser.getLastName());
        assertEquals("f", testUser.getGender());
        assertEquals("654321", testUser.getId());
    }
    @Test
    public void testVerify(){
        testCreateUser();
        String test = db.verify("hawkPower", "Hawk123");
        assertEquals("654321", test);
    }
   @Test
    public void testFindUser(){
        testCreateUser();
        User testUser = db.findUser("hawkPower");
       assertEquals("hawkPower", testUser.getUsername());
       assertEquals("Hawk123", testUser.getPassword());
       assertEquals("htothemax123@gmail", testUser.getEmail());
       assertEquals("jane", testUser.getFirstName());
       assertEquals("doe", testUser.getLastName());
       assertEquals("f", testUser.getGender());
       assertEquals("654321", testUser.getId());
    }
    @Test
    public void testGetUserName(){
        testCreateUser();
        String testUsername = db.getUserName("123456");
        assertEquals("turtlePower", testUsername);

    }
}
