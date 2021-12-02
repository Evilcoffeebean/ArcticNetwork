package dev.arctic.core.npc.packet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import java.lang.reflect.Method;

/**
 * Created by Zvijer on 27.8.2017..
 */
public class AI {

    private String version;
    private Method getHandle, getNBTTag;
    private Class<?> nmsEntityClass, nbtTagClass;
    private Method c, setInt, f;

    public AI(final Entity entity, boolean enable) {
        try {
            if (version == null) {
                String name = Bukkit.getServer().getClass().getName();
                String[] parts = name.split("\\.");
                version = parts[3];
            }
            if (getHandle == null) {
                Class<?> craftEntity = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftEntity");
                getHandle = craftEntity.getDeclaredMethod("getHandle");
                getHandle.setAccessible(true);
            }
            Object nmsEntity = getHandle.invoke(entity);
            if (nmsEntityClass == null) {
                nmsEntityClass = Class.forName("net.minecraft.server." + version + ".Entity");
            }
            if (getNBTTag == null) {
                getNBTTag = nmsEntityClass.getDeclaredMethod("getNBTTag");
                getNBTTag.setAccessible(true);
            }
            Object tag = getNBTTag.invoke(nmsEntity);
            if (nbtTagClass == null) {
                nbtTagClass = Class.forName("net.minecraft.server." + version + ".NBTTagCompound");
            }
            if (tag == null) {
                tag = nbtTagClass.newInstance();
            }
            if (c == null) {
                c = nmsEntityClass.getDeclaredMethod("c", nbtTagClass);
                c.setAccessible(true);
            }
            c.invoke(nmsEntity, tag);
            if (setInt == null) {
                setInt = nbtTagClass.getDeclaredMethod("setInt", String.class, Integer.TYPE);
                setInt.setAccessible(true);
            }
            int value = enable ? 0 : 1;
            setInt.invoke(tag, "NoAI", value);
            if (f == null) {
                f = nmsEntityClass.getDeclaredMethod("f", nbtTagClass);
                f.setAccessible(true);
            }
            f.invoke(nmsEntity, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
