package handlers;

import com.sun.net.httpserver.HttpExchange;
import FMSmodels.Person;
import responses.PersonsResponse;
import responses.Response;
import server.Serializer;
import services.PersonsService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class PersonsRequestHandler extends Handler {
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



                PersonsService es = new PersonsService(username);
                es.fillList();
                ArrayList<Person> personList = es.getPersonList();
                if (personList.size() == 0) {
                    error.setMessage("error, persons not found");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                    writeString(ser.generateResponse(error), respBody);
                    respBody.close();
                }

                // Start sending the HTTP response to the client, starting with
                // the status code and any defined headers.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                PersonsResponse resp = new PersonsResponse(personList);
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
            error.setMessage("error, failed to find persons");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

            writeString(ser.generateResponse(error), respBody);
            respBody.close();
        }
    }
}
