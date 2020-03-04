package DAOs;

import models.Person;

import java.sql.*;
import java.util.ArrayList;

public class PersonDAO {
    private final Connection conn;

    public PersonDAO(Connection conn)
    {
        this.conn = conn;
    }
    /**
     * inserts a new person object into the database.
     * @param person a person object to insert into the database
     * @throws DataAccessException thrown when insertion was unsuccessful
     */
    public void insert(Person person) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Person (personID, associatedUser, firstName, lastName, gender, motherID, fatherID, spouseID) " +
                "VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUser());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getMotherID());
            stmt.setString(7, person.getFatherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }
    /**
     * finds a person from the database with a matching personID.
     * @param personID a string ID of the person object to find
     * @return a person object with matching ID.
     * @throws DataAccessException thrown when unable to find, or not in the database.
     */
    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUser"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("motherID"), rs.getString("fatherID"), rs.getString("spouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public Person[] findAll(String username) throws DataAccessException {
        ArrayList<Person> personList = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE associatedUser = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Person tempPerson = new Person(rs.getString("personID"), rs.getString("associatedUser"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("motherID"), rs.getString("fatherID"), rs.getString("spouseID"));
                personList.add(tempPerson);
            }
            Person[] retArray = new Person[personList.size()];
            return personList.toArray(retArray);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * deletes everything from the Person table.
     * @throws DataAccessException thrown if somehow deleting everything didn't work
     */
    public void clear() throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Person";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
