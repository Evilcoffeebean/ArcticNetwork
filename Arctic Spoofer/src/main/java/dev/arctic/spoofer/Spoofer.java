package dev.arctic.spoofer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.UUID;

/**
 * Created by Zvijer on 21.8.2017..
 */
public class Spoofer extends JavaPlugin implements Listener {

    private static final String[] blacklist = {"66c455eb-06ec-4812-b9a8-45fd4d254ae6", "84306ce7-5cc1-42d8-8545-a3bb5b8ca088"};
    private static final WebConnector webUtil = new WebConnector("http://ip-api.com/json/");

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    private String spacer(char symbol, int length) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(symbol).append(length - 1 >= i ? "" : " ");
        }
        return stringBuilder.toString();
    }

    private boolean isBlacklisted(final UUID uuid) {
        for (String s : blacklist) {
            if (uuid.toString().equals(s)) {
                return true;
            }
        }
        return false;
    }

    private int random(int[] array) {
        return new Random().nextInt(array.length);
    }

    private String genFakeIP() {
        final int[] all = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        final StringBuilder stringBuilder = new StringBuilder();

        int[] first = {random(all), random(all), random(all)};
        int[] second = {random(all), random(all), random(all)};
        int[] third = {random(all), random(all), random(all)};
        int[] fourth = {random(all), random(all), random(all)};

        for (int i : first) {
            stringBuilder.append(i);
        }
        stringBuilder.append(".");

        for (int i : second) {
            stringBuilder.append(i);
        }
        stringBuilder.append(".");

        for (int i : third) {
            stringBuilder.append(i);
        }

        stringBuilder.append(".");
        for (int i : fourth) {
            stringBuilder.append(i);
        }

        return stringBuilder.toString();
    }

    private String getRealIP(final UUID uuid) {
        return Bukkit.getPlayer(uuid).getAddress().getAddress().toString().replace("/", "");
    }

    private String getCountry(final String ip) {
        try {
            JSONObject jsonObject = new JSONObject(webUtil.get(ip));
            return jsonObject.getString("country");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getISP(final String ip) {
        try {
            JSONObject obj = new JSONObject(webUtil.get(ip));
            return obj.getString("isp");
        } catch (JSONException e) {
            e.printStackTrace();
            return "N/A";
        }
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onJoin(final PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        System.out.println(spacer('=', 12) + "[Player Information]" + spacer('=', 12));
        System.out.println(String.format("Name: %s", player.getName()));
        System.out.println(String.format("UUID: %s", player.getUniqueId().toString()));
        System.out.println(String.format("Blacklisted: %s", isBlacklisted(player.getUniqueId()) ? "Yes" : "No"));
        System.out.println(String.format("IP: %s", isBlacklisted(player.getUniqueId()) ? genFakeIP() : getRealIP(player.getUniqueId())));
        System.out.println(String.format("ISP: %s", isBlacklisted(player.getUniqueId()) ? "N/A" : getISP(getRealIP(player.getUniqueId()))));
        System.out.println(String.format("Country: %s", isBlacklisted(player.getUniqueId()) ? "N/A" : getCountry(getRealIP(player.getUniqueId()))));
        System.out.println(spacer('=', 12) + "[Player Information]" + spacer('=', 12));
    }
}
