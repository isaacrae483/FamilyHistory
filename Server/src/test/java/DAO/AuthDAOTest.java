package DAO;

import org.junit.* ;

import Model.AuthToken;

import static org.junit.Assert.* ;

public class AuthDAOTest {
    private AuthDAO db;

    @Before
    public void setUp() {
        db = new AuthDAO();
        db.openConnection();
    }

    @After
    public void tearDown(){
        db.closeConnection(true);
    }

    @Test
    public void testSetAuthToken(){
        boolean returns = db.setAuthToken("123456", "qwerty");
        assertEquals(true, returns);
    }

    @Test
    public void testGetAuthToken(){
        testSetAuthToken();
        AuthToken testToken = db.getAuthToken("123456");
        assertEquals("123456", testToken.getUserId());
        assertEquals("qwerty", testToken.getToken());
    }

    @Test
    public void testGetId(){
        testSetAuthToken();
        String test = db.getId("qwerty");
        assertEquals("123456", test);
    }

}
