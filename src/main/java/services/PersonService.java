package services;

import DAOs.DataAccessException;
import DAOs.Database;
import DAOs.PersonDAO;
import FMSmodels.Person;

import java.sql.Connection;

public class PersonService {
    public PersonService() {
    }

    /**
     * constructor to find a single person.
     * @param personID the person ID to look up the person.
     */
    public  PersonService(String personID) {
        this.personID = personID;
    }

    public Person getPerson() throws DataAccessException {
        Person ret;
        Database db = new Database();
        Connection conn = db.openConnection();
        PersonDAO eDao = new PersonDAO(conn);
        ret = eDao.find(personID);
        db.closeConnection(true);
        return ret;
    }

    private String personID = "";

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
