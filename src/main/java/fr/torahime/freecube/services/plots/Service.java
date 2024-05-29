package fr.torahime.freecube.services.plots;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Service {

    Logger logger;

    public Service(String loggerName) {
        this.logger = Logger.getLogger(loggerName);
    }

    protected boolean sendRequest(String url, HttpRequest.BodyPublisher bodyPublisher, String method, int expectedStatusCode) {
        logger.info(method + " - " + url + " - (expected ->" + expectedStatusCode + ")");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json");

        switch (method) {
            case "POST":
                builder.POST(bodyPublisher);
                break;
            case "PUT":
                builder.PUT(bodyPublisher);
                break;
            case "DELETE":
                builder.DELETE();
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }

        HttpRequest request = builder.build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == expectedStatusCode;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "HTTP request failed", e);
            return false;
        }
    }

}
