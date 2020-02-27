package dao;
import DAOs.*;
import models.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
public class PersonDAOTest {
    private Database db;
    private Person testPerson;
    private Person testPerson2;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        testPerson = new Person("qwer1234", "mememaster420", "samantha",
                "parkins", "F", "mom123", "dad123", "babe123");
        testPerson2 = new Person("qwer1235", "mememaster421", "samanthar",
                "parkins", "F", "mom123", "dad123", "babe123");
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void insertPass() throws Exception {
        //We want to make sure insert works
        //First lets create an Event that we'll set to null. We'll use this to make sure what we put
        //in the database is actually there.
        Person compareTest = null;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDAO pDao = new PersonDAO(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            pDao.insert(testPerson);
            //So lets use a find method to get the event that we just put in back out
            compareTest = pDao.find(testPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(testPerson, compareTest);

    }

    @Test
    public void insertFail() throws Exception {
        //lets do this test again but this time lets try to make it fail

        // NOTE: The correct way to test for an exception in Junit 5 is to use an assertThrows
        // with a lambda function. However, lambda functions are beyond the scope of this class
        // so we are doing it another way.
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            PersonDAO pDao = new PersonDAO(conn);
            //if we call the method the first time it will insert it successfully
            pDao.insert(testPerson);
            //but our sql table is set up so that "eventID" must be unique. So trying to insert it
            //again will cause the method to throw an exception
            pDao.insert(testPerson);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            //If we catch an exception we will end up in here, where we can change our boolean to
            //false to show that our function failed to perform correctly
            db.closeConnection(false);
            didItWork = false;
        }
        //Check to make sure that we did in fact enter our catch statement
        assertFalse(didItWork);

        //Since we know our database encountered an error, both instances of insert should have been
        //rolled back. So for added security lets make one more quick check using our find function
        //to make sure that our event is not in the database
        //Set our compareTest to an actual event
        Person compareTest = testPerson;
        try {
            Connection conn = db.openConnection();
            PersonDAO pDao = new PersonDAO(conn);
            //and then get something back from our find. If the event is not in the database we
            //should have just changed our compareTest to a null object
            compareTest = pDao.find(testPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        //Now make sure that compareTest is indeed null
        assertNull(compareTest);
    }

    @Test
    public void findPass() throws Exception {
        //We want to make sure insert works
        //First lets create an Event that we'll set to null. We'll use this to make sure what we put
        //in the database is actually there.
        Person compareTest1 = null;
        Person compareTest2 = null;


        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDAO uDao = new PersonDAO(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            uDao.insert(testPerson);
            uDao.insert(testPerson2);
            //So lets use a find method to get the event that we just put in back out
            compareTest1 = uDao.find(testPerson.getPersonID());
            compareTest2 = uDao.find(testPerson2.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest1);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(testPerson, compareTest1);
        assertEquals(testPerson2, compareTest2);

    }

    @Test
    public void findFail() throws Exception {
        //find should fail when you look for something that isn't there, when the database is empty,
        //then looking for anything will fail it.  Find will return a null object.
        Person compareTest = testPerson;
        try {
            Connection conn = db.openConnection();
            PersonDAO uDao = new PersonDAO(conn);
            //and then get something back from our find. If the event is not in the database we
            //should have just changed our compareTest to a null object
            compareTest = uDao.find(testPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        //Now make sure that compareTest is indeed null
        assertNull(compareTest);
    }

    @Test
    public void clearPass() throws Exception {
        // for this test we will want to make sure the database has stuff in it, and that after the clear,
        // the stuff is removed.

        //copying from find test to propogate the database with 2 rows.

        Person compareTest1 = null;
        Person compareTest2 = null;


        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDAO uDao = new PersonDAO(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            uDao.insert(testPerson);
            uDao.insert(testPerson2);
            //So lets use a find method to get the event that we just put in back out
            compareTest1 = uDao.find(testPerson.getPersonID());
            compareTest2 = uDao.find(testPerson2.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest1);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(testPerson, compareTest1);
        assertEquals(testPerson2, compareTest2);

        //trying to clear
        try {
            Connection conn = db.openConnection();
            PersonDAO uDao = new PersonDAO(conn);
            uDao.clear();
            //this should set compareTest1 to null, if it actually cleared.
            compareTest1 = uDao.find(testPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //if clear worked, then this will pass.
        assertNull(compareTest1);
    }
}
