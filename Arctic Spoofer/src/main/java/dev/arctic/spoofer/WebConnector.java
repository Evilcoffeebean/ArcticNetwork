package dev.arctic.spoofer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Zvijer on 21.8.2017..
 */
public class WebConnector {

    private Queue<String> postQueue = new LinkedBlockingDeque<>();
    private String host;

    public WebConnector(final String host) {
        this.host = host;
        new Thread(() -> {
            long last = System.currentTimeMillis();
            while (true) {
                if (System.currentTimeMillis() - last < 10) continue;
                if (postQueue.isEmpty()) continue;
                String post = postQueue.remove();
                post(post);
                System.out.println("Executing POST req: Queue size: " + postQueue.size());
                last = System.currentTimeMillis();
            }
        }).start();
    }

    public String get(final String url) {
        try {
            URLConnection urlConnection = new URL(host + url).openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inLine;

            while ((inLine = in.readLine()) != null)
                response.append(inLine);

            in.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Couldn't fetch data using primary node.";
        }
    }

    public void post(final String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(host + url).openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inLine;

            while ((inLine = in.readLine()) != null)
                response.append(inLine);

            in.close();
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
