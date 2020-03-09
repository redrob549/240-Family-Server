package handlers;

import DAOs.AuthTokenDAO;
import DAOs.DataAccessException;
import DAOs.Database;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import FMSmodels.AuthToken;

import java.io.*;
import java.sql.Connection;

public abstract class Handler implements HttpHandler {
    void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);
        bw.flush();
    }

    /**
     *
     * @param exchange
     * @return the username associated with the authorization code in teh exchange.
     * @throws DataAccessException
     */
    String checkAuthorization(HttpExchange exchange) throws DataAccessException {
        // Get the HTTP request headers
        Headers reqHeaders = exchange.getRequestHeaders();

        // Check to see if an "Authorization" header is present
        if (reqHeaders.containsKey("Authorization")) {

            // Extract the auth token from the "Authorization" header
            String authToken = reqHeaders.getFirst("Authorization");

            // Verify that the auth token is the one we're looking for
            Database db = new Database();
            Connection conn = db.openConnection();
            AuthTokenDAO aDao = new AuthTokenDAO(conn);
            AuthToken token = aDao.find(authToken);
            db.closeConnection(true);
            if (token != null) {
                return token.getUserName();
            }

        }
        return null;
    }
}
