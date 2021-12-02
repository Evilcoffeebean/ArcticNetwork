package dev.arctic.core.punish;

import dev.arctic.core.Core;

import java.util.Random;
import java.util.UUID;

public class PunishPlayer {

    private UUID victimUUID;
    private String ip;
    private String victimName, punisherName;
    private PunishType type;
    private long duration;
    private String reason;
    private String dateTime;

    public PunishPlayer(UUID uuid, String ip, String name, String punisher, PunishType type, String date, long duration, String reason) {
        load(uuid, ip, name, punisher, type, date, duration, reason);
    }

    private void load(UUID uuid, String ip, String name, String punisher, PunishType type, String date, long duration, String reason) {
        if (!Core.getCore().getDatabaseManager().getQueryBuilder().exists("punishments", new String[] {"uuid", victimUUID.toString()})) {
            this.victimUUID = uuid;
            this.ip = ip;
            this.victimName = name;
            this.punisherName = punisher;
            this.dateTime = date;
            this.duration = duration;
            this.reason = reason;
            Core.getCore().getDatabaseManager().getQueryBuilder().insert("punishments (uuid, ip, name, punisher, type, date, duration, reason)", uuid.toString(), ip, name, punisher, type, date, duration, reason);
            Core.getCore().getDatabaseManager().getQueryBuilder().insert("previous_punishments (uuid, punisher, date, type, duration, reason)", uuid.toString(), punisher, date, type, duration, reason);
            executeEvent(duration, uuid, reason);
            System.out.println("Punish> Creating a new punishment...");
            System.out.printf("Punish> Target UUID: %s", victimUUID.toString());
            System.out.printf("Punish> Target Name: %s", victimName);
            System.out.printf("Punish> Punisher Name: %s", punisherName);
            System.out.printf("Punish> Punish Date: %s", date);
            System.out.printf("Punish> Punish Type: %s", type);
            System.out.printf("Punish> Duration: %s", duration);
            System.out.printf("Punish> Reason: %s", reason);
        } else {
            this.victimUUID  = Core.getCore().getDatabaseManager().getQueryBuilder().fetch("uuid", "punishments", new String[] {"uuid", uuid.toString()});
            this.ip  = Core.getCore().getDatabaseManager().getQueryBuilder().fetch("ip", "punishments", new String[] {"uuid", uuid.toString()});
            this.victimName  = Core.getCore().getDatabaseManager().getQueryBuilder().fetch("name", "punishments", new String[] {"uuid", uuid.toString()});
            this.punisherName  = Core.getCore().getDatabaseManager().getQueryBuilder().fetch("punisher", "punishments", new String[] {"uuid", uuid.toString()});
            this.type  = Core.getCore().getDatabaseManager().getQueryBuilder().fetch("type", "punishments", new String[] {"uuid", uuid.toString()});
            this.dateTime  = Core.getCore().getDatabaseManager().getQueryBuilder().fetch("date", "punishments", new String[] {"uuid", uuid.toString()});
            this.duration  = Core.getCore().getDatabaseManager().getQueryBuilder().fetch("duration", "punishments", new String[] {"uuid", uuid.toString()});
            this.reason  = Core.getCore().getDatabaseManager().getQueryBuilder().fetch("reason", "punishments", new String[] {"uuid", uuid.toString()});
        }
    }

    private void executeEvent(long duration, UUID uuid, String reason) {
        final String query = "CREATE EVENT punishevent" + new Random().nextInt(999999)
                + " ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL " + duration
                + " HOUR DO DELETE FROM punishments WHERE uuid='" + uuid + "' AND"
                + " reason='" + reason + "';";
        Core.getCore().getDatabaseManager().getQueryBuilder().getDatabase().update(query);
    }

    public UUID getVictimUUID() {
        return victimUUID;
    }

    public String getIp() {
        return ip;
    }

    public long getDuration() {
        return duration;
    }

    public String getVictimName() {
        return victimName;
    }

    public String getReason() {
        return reason;
    }

    public String getPunisherName() {
        return punisherName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public PunishType getType() {
        return type;
    }
}
