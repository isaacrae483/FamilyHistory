package DAO;

import org.junit.* ;
import static org.junit.Assert.* ;

public class ParentDAOTest {

    private ParentDAO db;

    @Before
    public void setUp() {
        db = new ParentDAO();
        db.openConnection();
    }

    @After
    public void tearDown(){
        db.closeConnection(true);
    }
@Test
    public void testCreateTables() {
        //not really sure how to test this
        db.createTables();
    }


}
