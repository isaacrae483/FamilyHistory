package DAO;

import org.junit.* ;

import java.util.Set;

import Model.Person;

import static org.junit.Assert.* ;

public class PersonDAOTest {
    Person person = new Person("987654", "username", "Bobby", "Fletcher", "m");
    Person person1 = new Person("951623", "username", "Blob", "woman", "f");
    private PersonDAO db;

    @Before
    public void setUp() {
        db = new PersonDAO();
        db.openConnection();
    }

    @After
    public void tearDown(){
        db.closeConnection(true);
    }

    @Test
    public void testCreatePerson(){
        boolean test = db.createPerson(person);
        assertEquals(true, test);
        test = db.createPerson(person1);
        assertEquals(true, test);

    }
    @Test
    public void testGetPerson(){
        testCreatePerson();
        Person testPerson = db.getPerson("987654", "username");
        assertEquals("987654", testPerson.getId());
        assertEquals("Bobby", testPerson.getFirstName());
        assertEquals("Fletcher", testPerson.getLastName());
        assertEquals("m", testPerson.getGender());
        testPerson = db.getPerson("951623", "username");
        assertEquals("951623", testPerson.getId());
        assertEquals("Blob", testPerson.getFirstName());
        assertEquals("woman", testPerson.getLastName());
        assertEquals("f", testPerson.getGender());

    }
    @Test
    public void testGetTree(){
        testCreatePerson();
        Set<Person> test = db.getTree("username");
        assertEquals(2, test.size());
    }

    @Test
    public void testDeleteTree() {
        testCreatePerson();
        boolean test = db.deleteTree("username");
        assertEquals(true, test);
    }
    @Test
    public void testGetDTree(){
        testCreatePerson();
        testDeleteTree();
        Set<Person> test = db.getTree("username");
        assertEquals(0, test.size());
    }

}
