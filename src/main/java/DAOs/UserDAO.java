package DAOs;

import FMSmodels.User;

import java.sql.*;

public class UserDAO {
    private final Connection conn;

    public UserDAO(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * inserts a new user object into the database.
     * @param user a User object to insert into the database
     * @throws DataAccessException thrown when insertion was unsuccessful
     */
    public void insert(User user) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO User (UserName, Password, Email, FirstName, LastName, Gender, PersonID) " +
                "VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * finds a User from the database with a matching personID.
     * @param userID a string ID of the user object to find
     * @return a user object with matching ID.
     * @throws DataAccessException thrown when unable to find, or not in the database.
     */
    public User find(String userID) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM User WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("userName"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("personID"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
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

    /**
     * finds a User from the database with a matching username.
     * @param username a string ID of the user object to find
     * @return a user object with matching username.
     * @throws DataAccessException thrown when unable to find, or not in the database.
     */
    public User findUser(String username) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM User WHERE UserName = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("userName"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("personID"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
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

    /**
     * deletes everything from the User table.
     * @throws DataAccessException thrown if somehow deleting everything didn't work
     */
    public void clear() throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM User";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
