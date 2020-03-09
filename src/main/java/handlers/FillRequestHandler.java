package handlers;

import com.sun.net.httpserver.HttpExchange;
import responses.Response;
import server.Serializer;
import services.FillService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class FillRequestHandler extends Handler {
    private String username;
    private int generations;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Response error = new Response();
        error.setSuccess(false);
        Serializer ser = new Serializer();
        OutputStream respBody = exchange.getResponseBody();
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                //looking for /fill/susan/3
                String uri = exchange.getRequestURI().toString();

                //cut off first '/'
                uri = uri.substring(1);
                //if there is a following slash.
                if (uri.indexOf('/',6) != -1) {
                    username = uri.substring(5, uri.indexOf('/', 6));
                    int trimlength = 6 + username.length();
                    //if there is no number after the slash
                    if (trimlength < uri.length()) {
                        generations = Integer.parseInt(uri.substring(trimlength));
                    }
                    else {
                        generations = 4;
                    }
                } else {
                    username = uri.substring(5);
                    generations = 4;
                }


                FillService fs = new FillService(username, generations);
                if (!fs.Fill()) {
                    error.setMessage("error, filling user data failed.");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    writeString(ser.generateResponse(error), respBody);
                    respBody.close();
                }
                else {
                    Response good = new Response();
                    good.setSuccess(true);
                    int pCount = fs.getPersonCount();
                    int eCount = fs.getEventCount();
                    String mess = "Successfully added " + pCount + " persons and " + eCount + " events to the database.";
                    good.setMessage(mess);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    writeString(ser.generateResponse(good), respBody);
                    respBody.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
