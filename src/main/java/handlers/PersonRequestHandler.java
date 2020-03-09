package handlers;

import com.sun.net.httpserver.HttpExchange;
import FMSmodels.Person;
import responses.PersonResponse;
import responses.Response;
import server.Serializer;
import services.PersonService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class PersonRequestHandler extends Handler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Response error = new Response();
        error.setSuccess(false);
        Serializer ser = new Serializer();
        OutputStream respBody = exchange.getResponseBody();
        try {
            String username = checkAuthorization(exchange);
            if (exchange.getRequestMethod().toUpperCase().equals("GET") &&
                    username != null) {
                String personID = exchange.getRequestURI().toString();
                personID = personID.substring(8);

                PersonService ps = new PersonService(personID);

                Person retPerson = ps.getPerson();
                if (retPerson == null) {
                    error.setMessage("error, person not found");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                    writeString(ser.generateResponse(error), respBody);
                    respBody.close();
                }
                if (!retPerson.getAssociatedUser().equals(username))
                {
                    error.setMessage("error, person not associated with this user");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                    writeString(ser.generateResponse(error), respBody);
                    respBody.close();
                }

                // Start sending the HTTP response to the client, starting with
                // the status code and any defined headers.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                PersonResponse resp = new PersonResponse(retPerson);
                writeString(ser.generateResponse(resp), respBody);

                exchange.getResponseBody().close();

                //System.out.println("user added to database");

            } else {
                // We expected a POST but got something else, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                error.setMessage("error, bad authorization");
                writeString(ser.generateResponse(error), respBody);
                respBody.close();
            }
        } catch (Exception e) {
            error.setMessage("error, failed to find person");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

            writeString(ser.generateResponse(error), respBody);
            respBody.close();
        }
    }
}
