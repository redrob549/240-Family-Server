package services;

import DAOs.DataAccessException;
import DAOs.Database;

import java.sql.Connection;

public class ClearService {
    /**
     * clears out all the data in the database.
     */
    public boolean Clear() {
        try {
            Database db = new Database();
            Connection conn = db.openConnection();
            db.clearTables();
            db.closeConnection(true);
            return true;
        } catch (DataAccessException e) {
            //e.printStackTrace();
            return false;
        }
    }
}
