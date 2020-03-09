package handlers;


import FMSmodels.AuthToken;
import com.sun.net.httpserver.HttpExchange;

import FMSmodels.User;
import responses.AuthTokenResponse;
import responses.Response;
import server.Deserializer;
import server.Serializer;
import services.FillService;
import services.LoginService;
import services.RegisterService;

import java.util.UUID;

import java.io.*;
import java.net.HttpURLConnection;


public class RegisterRequestHandler extends Handler{

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
                User newUser = ds.parseUser(reqBody);
                newUser.setPersonID(UUID.randomUUID().toString());

                RegisterService rs = new RegisterService(newUser);
                if (!rs.Register()) {
                    error.setMessage("error, user registration failed.");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    writeString(ser.generateResponse(error), respBody);
                    respBody.close();
                }

                //generate ancestor data
                FillService fs = new FillService(newUser.getUserName(), 4);
                if (!fs.Fill()) {
                    error.setMessage("error, filling new user info failed.");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    writeString(ser.generateResponse(error), respBody);
                    respBody.close();
                }

                //perform login function of registration
                LoginService ls = new LoginService(newUser);
                ls.login();

                // Start sending the HTTP response to the client, starting with
                // the status code and any defined headers.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                AuthToken newToken = ls.createAuthToken();
                AuthTokenResponse resp = new AuthTokenResponse(newToken, ls.getUserID());
                writeString(ser.generateAuthTokenResponse(resp), respBody);

                exchange.getResponseBody().close();

                //System.out.println("user added to database");

            } else {
                // We expected a POST but got something else, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
        } catch (Exception e) {
            error.setMessage("error, user registration failed.");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            writeString(ser.generateResponse(error), respBody);
            respBody.close();
        }
    }
}
