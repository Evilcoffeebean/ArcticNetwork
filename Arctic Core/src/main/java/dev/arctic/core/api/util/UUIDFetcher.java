package dev.arctic.core.api.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * Created by Zvijer on 26.8.2017..
 */
public class UUIDFetcher implements Callable<List<UUID>> {

    private transient String name;
    private final String API = "https://api.mojang.com/profiles/minecraft";

    public UUIDFetcher(final String name) {
        this.name = name;
    }

    private UUID getUUID(final String id) {
        final StringBuilder string = new StringBuilder();
        string.append(id.substring(0, 8)).append('-').append(id.substring(8, 12)).append('-').append(id.substring(12, 16)).append('-').append(id.substring(16, 20)).append('-').append(id.substring(20, 32));

        return UUID.fromString(string.toString());
    }

    @Override
    public List<UUID> call() {
        final List<UUID> list = new ArrayList<>();

        try {
            final HttpURLConnection connection = (HttpURLConnection) new URL(API).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            final OutputStream outputStream = connection.getOutputStream();
            outputStream.write(new String("[\"" + this.name + "\"]").getBytes());

            outputStream.flush();
            outputStream.close();

            final JSONArray jsonArray = (JSONArray) new JSONParser().parse(new InputStreamReader(connection.getInputStream()));

            jsonArray.forEach(obj -> {
                list.add(getUUID(((JSONObject) obj).get("id").toString()));
            });
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return list;
    }
}
