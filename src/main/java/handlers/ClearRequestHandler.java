package handlers;


import com.sun.net.httpserver.HttpExchange;
import responses.Response;
import server.Serializer;
import services.ClearService;

import java.io.*;
import java.net.HttpURLConnection;

public class ClearRequestHandler extends Handler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Response resp = new Response();
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {

                ClearService cs = new ClearService();
                if (cs.Clear()) {
                    resp.setMessage("Clear succeeded");
                    resp.setSuccess(true);

                    //System.out.println("tables cleared");
                } else {
                    resp.setMessage("error, Clear failed");
                    resp.setSuccess(false);
                }

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                Serializer ser = new Serializer();
                OutputStream respBody = exchange.getResponseBody();
                writeString(ser.generateResponse(resp), respBody);
                respBody.close();
            } else {
                // We expected a GET but got something else, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
        } catch (IOException e) {
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            // Since the server is unable to complete the request, the client will
            // not receive the list of games, so we close the response body output stream,
            // indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }
    }
}
