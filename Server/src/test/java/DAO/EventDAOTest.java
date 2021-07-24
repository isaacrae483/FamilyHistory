package DAO;

import org.junit.* ;

import java.util.Set;

import Model.Event;

import static org.junit.Assert.* ;

public class EventDAOTest {

    private EventDAO db;
    private Event event;
    private Event event1;

    @Before
    public void setUp() {
        event = new Event("454879", "username", "654321", "95162", "95162", "USA", "Provo", "birth", 1997);
        event1 = new Event("454878", "username", "654321", "95162", "95162", "USA", "Provo", "death", 2007);
        db = new EventDAO();
        db.openConnection();
    }

    @After
    public void tearDown(){
        db.closeConnection(true);
    }

    @Test
    public void testCreateEvent(){
       boolean test = db.createEvent(event);
       assertEquals(true, test);
       test = db.createEvent(event1);
       assertEquals(true, test);
    }
    @Test
    public void testGetDescendant(){
        testCreateEvent();
        String personID = db.getDescendant("654321");
        assertEquals("username", personID);
    }
    @Test
    public void testGetEvent(){
        testCreateEvent();
        Event test = db.getEvent("username", "454879");
        assertEquals("454879", test.geteId());
        assertEquals("username", test.getDescendant());
        assertEquals("654321", test.getpId());
        assertEquals("95162", test.getLatitude());
        assertEquals("95162", test.getLongitude());
        assertEquals("Provo", test.getCity());
        assertEquals("USA", test.getCountry());
        assertEquals("birth", test.getType());
        assertEquals(1997, test.getYear());
        test = db.getEvent("username", "454878");
        assertEquals("454878", test.geteId());
        assertEquals("username", test.getDescendant());
        assertEquals("654321", test.getpId());
        assertEquals("95162", test.getLatitude());
        assertEquals("95162", test.getLongitude());
        assertEquals("Provo", test.getCity());
        assertEquals("USA", test.getCountry());
        assertEquals("death", test.getType());
        assertEquals(2007, test.getYear());

    }
    @Test
    public void testGetEvents(){
        testCreateEvent();
        Set<Event> test = db.getEvents("username");
        assertEquals(2, test.size());
    }
    @Test
    public void testDeletEvents(){
        testCreateEvent();
        boolean test = db.deleteEvents("username");
        assertEquals(true, test);
    }
}
