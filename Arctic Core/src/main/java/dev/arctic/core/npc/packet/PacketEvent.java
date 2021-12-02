package dev.arctic.core.npc.packet;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.lang.reflect.Field;

/**
 * Created by Zvijer on 26.8.2017..
 */
public class PacketEvent extends Event{
    private static final HandlerList handlers = new HandlerList();

    private PacketType packetType;

    private boolean cancelled = false;

    private Packet packet;

    private Player player;

    public PacketEvent(Player player, PacketType packetType, Packet packet) {
        this.packetType = packetType;
        this.packet = packet;
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public PacketType getPacketType() {
        return this.packetType;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void printData() {
        System.out.println(" player - > " + packet.getClass().getName());
        System.out.println();

        for (Field f : packet.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                System.out.print("  " + f.getName() + "  " + f.get(packet) + "  ");
            } catch (IllegalAccessException e) {
            }
        }
        System.out.println();
        System.out.println(" player - > ");
        System.out.println();
    }


    public static enum PacketType {
        INWARDS, OUTWARDS
    }
}
