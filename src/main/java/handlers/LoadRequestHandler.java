package handlers;

import DAOs.Database;
import DAOs.EventDAO;
import DAOs.PersonDAO;
import DAOs.UserDAO;
import com.sun.net.httpserver.HttpExchange;
import requests.LoadRequest;
import responses.Response;
import server.Deserializer;
import server.Serializer;
import services.ClearService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.sql.Connection;

public class LoadRequestHandler extends Handler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Response error = new Response();
        error.setSuccess(false);
        Serializer ser = new Serializer();
        OutputStream respBody = exchange.getResponseBody();
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();
                Deserializer ds = new Deserializer();
                LoadRequest lr = ds.parseLoadRequest(reqBody);

                ClearService cs = new ClearService();
                if (cs.Clear()) {
                    //System.out.println("tables cleared");
                } else {
                    error.setMessage("error, Clear failed");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                    writeString(ser.generateResponse(error), respBody);
                    respBody.close();
                }
                if (!loadData(lr)) {
                    error.setMessage("error, Loading Data failed");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                    writeString(ser.generateResponse(error), respBody);
                    respBody.close();
                }

                // Start sending the HTTP response to the client, starting with
                // the status code and any defined headers.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                String goodMessage = "Successfully added " + lr.getUsers().length + " users, "
                                    + lr.getPersons().length + " persons, and "
                                    + lr.getEvents().length + " events to the database.";
                Response success = new Response();
                success.setMessage(goodMessage);
                success.setSuccess(true);
                writeString(ser.generateResponse(success), respBody);
                exchange.getResponseBody().close();

            } else {
                // We expected a POST but got something else, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
        } catch (Exception e) {
            error.setMessage("error, loading data failed.");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            writeString(ser.generateResponse(error), respBody);
            respBody.close();
        }
    }

    private boolean loadData(LoadRequest lr) {
        try {

            for (int i = 0; i < lr.getUsers().length; i++) {
                Database db = new Database();
                Connection conn = db.openConnection();
                UserDAO uDao = new UserDAO(conn);
                uDao.insert(lr.getUsers()[i]);
                db.closeConnection(true);
            }
            for (int i = 0; i < lr.getEvents().length; i++) {
                Database db = new Database();
                Connection conn = db.openConnection();
                EventDAO eDao = new EventDAO(conn);
                eDao.insert(lr.getEvents()[i]);
                db.closeConnection(true);
            }
            for (int i = 0; i < lr.getPersons().length; i++) {
                Database db = new Database();
                Connection conn = db.openConnection();
                PersonDAO pDao = new PersonDAO(conn);
                pDao.insert(lr.getPersons()[i]);
                db.closeConnection(true);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}