package fr.torahime.freecube.services;

import fr.torahime.freecube.utils.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Service {

    protected Logger logger;

    public Service(String loggerName) {
        this.logger = Logger.getLogger(loggerName);
    }

    public boolean checkApiStatus(){
        return sendRequest(URI.create(String.format("%s/api/v1/status", Dotenv.get("BASE_API_URL"))),"GET", 200);
    }

    protected boolean sendRequest(URI uri, String method, int expectedStatusCode) {
        return sendRequest(String.valueOf(uri), HttpRequest.BodyPublishers.noBody(), method, expectedStatusCode);
    }

    protected boolean sendRequest(String url, HttpRequest.BodyPublisher bodyPublisher, String method, int expectedStatusCode) {

        logger.info(method + " - " + url + " - (expected ->" + expectedStatusCode + ")");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json");

        switch (method) {
            case "GET":
                builder.GET();
                break;
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
            logger.info(method + " - " + url + " - (actual ->" + response.statusCode() + ")");
            return response.statusCode() == expectedStatusCode;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "HTTP request failed ->", e);
            return false;
        }
    }

}
