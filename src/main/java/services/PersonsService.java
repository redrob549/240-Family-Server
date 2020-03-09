package services;

import DAOs.DataAccessException;
import DAOs.Database;
import DAOs.PersonDAO;
import FMSmodels.Person;

import java.sql.Connection;
import java.util.ArrayList;

public class PersonsService {
    private String username;
    ArrayList<Person> personList = new ArrayList<>();

    public PersonsService(String name) {
        username = name;
    }

    public ArrayList<Person> getPersonList() {
        return personList;
    }

    public void fillList() throws DataAccessException {
        Person[] persons;
        Database db = new Database();
        Connection conn = db.openConnection();
        PersonDAO eDao = new PersonDAO(conn);
        persons = eDao.findAll(username);
        db.closeConnection(true);
        for (int i = 0; i < persons.length; i++) {
            personList.add(persons[i]);
        }
    }
}
