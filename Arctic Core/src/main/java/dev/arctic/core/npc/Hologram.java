package dev.arctic.core.npc;

import com.google.common.collect.Maps;
import dev.arctic.core.api.util.StringUtil;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.Map;

/**
 * Created by Zvijer on 17.8.2017..
 */
public class Hologram {

    private @Getter String name, text;
    private @Getter Location location;
    private @Getter Entity entity;

    public Hologram(final String name, final String text, final Location location) {
        this.name = name;
        this.text = text;
        this.location = location;
    }

    public void remove() {
        if (entity != null)
            entity.remove();
    }

    public void spawn() {
        entity = location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        ArmorStand armorStand = (ArmorStand) entity;
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setCustomName(StringUtil.color(text));
        armorStand.setCustomNameVisible(true);
    }

    public static class HologramManager {
        private @Getter Map<String, Hologram> holograms;

        public HologramManager() {
            holograms = Maps.newConcurrentMap();
        }

        public boolean isTaken(String name) {
            return holograms.containsKey(name);
        }

        public void createHologram(String name, String text, Location location) {
            Hologram hologram = new Hologram(name, text, location);
            hologram.spawn();
            holograms.put(name, hologram);
        }

        public void clearFromCache(String name) {
            if (holograms.containsKey(name))
                holograms.remove(name);
        }

        public void clearAllCache() {
            if (!holograms.isEmpty())
                holograms.clear();
        }
    }
}
