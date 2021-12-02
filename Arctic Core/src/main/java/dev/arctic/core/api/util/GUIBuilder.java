package dev.arctic.core.api.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class GUIBuilder {

    private final String name;
    private final int slots;
    private final Player owner;
    private Inventory gui;

    public GUIBuilder(Player p, int slots, String name) {
        this.name = name;
        this.slots = slots % 9 == 0 ? slots : 54;
        this.owner = p;
        this.gui = Bukkit.createInventory(p, slots, StringUtil.color(name));
    }

    public abstract void build();

    public void open() {
        owner.openInventory(gui);
    }

    public void close() {
        owner.closeInventory();
    }

    public void set(int i, ItemStack item) {
        gui.setItem(i, item);
    }
}
