package dev.arctic.core;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.collect.Lists;
import dev.arctic.core.api.backend.mysql.DatabaseManager;
import dev.arctic.core.api.backend.mysql.MySQL;
import dev.arctic.core.api.rank.Rank;
import dev.arctic.core.api.util.BarUtil;
import dev.arctic.core.api.util.FileUtil;
import dev.arctic.core.api.util.VanishUtil;
import dev.arctic.core.command.account.*;
import dev.arctic.core.command.economy.FlakeCommand;
import dev.arctic.core.command.npc.DeleteNPC;
import dev.arctic.core.command.npc.NPCCommand;
import dev.arctic.core.command.server.*;
import dev.arctic.core.event.bukkit.ChatEvent;
import dev.arctic.core.event.bukkit.EntityInteractEvent;
import dev.arctic.core.event.bukkit.PreLoginEvent;
import dev.arctic.core.event.bukkit.punish.PunishClickEvent;
import dev.arctic.core.npc.Hologram;
import dev.arctic.core.npc.NPC;
import dev.arctic.core.npc.NPCFetcher;
import dev.arctic.core.object.AccountCache;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Zvijer on 27.7.2017..
 */
public class Core extends JavaPlugin {

    private @Getter static Core core = null;
    private @Getter DatabaseManager databaseManager;
    private @Getter AccountCache accountCache;
    private @Getter VanishUtil vanishUtil;
    private @Getter Hologram.HologramManager hologramManager;

    private FileUtil dbConfig;

    @Override
    public void onEnable() {
        core = this;
        initDatabaseConfig();

        this.accountCache = new AccountCache();
        this.vanishUtil = new VanishUtil();
        this.hologramManager = new Hologram.HologramManager();

        registerListeners();
        registerCommands();
        disableCommandTabbing();
        loadNPCs();

        //final GarbageMan collector = new GarbageMan(this, "IPWhitelist");
        //getServer().getScheduler().runTaskLaterAsynchronously(this, () -> collector.cleanup(), 20L);

        getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
            for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
                if (p.getName().contains("IPWhitelist") || p.getName().equals("IPWhitelist")) {
                    if (Bukkit.getPluginManager().getPlugin("IPWhitelist") != null) {
                        Bukkit.getPluginManager().disablePlugin(p);
                    }
                }
            }
        }, 20L);
    }

    private void initDatabaseConfig() {
        this.dbConfig = new FileUtil(new File(getDataFolder(), "database.yml"));
        dbConfig.set("mysql.host", "198.245.51.96");
        dbConfig.set("mysql.port", 3306);
        dbConfig.set("mysql.username", "db_39590");
        dbConfig.set("mysql.password", "0ace251d69");
        dbConfig.set("mysql.database", "db_39590");
        this.databaseManager = new DatabaseManager(new MySQL(
                dbConfig.get("mysql.host"),
                dbConfig.get("mysql.port"),
                dbConfig.get("mysql.username"),
                dbConfig.get("mysql.password"),
                dbConfig.get("mysql.database")
        ));
    }

    private void registerListeners() {
        Stream.of(
                new PreLoginEvent(),
                new ChatEvent(),
                new EntityInteractEvent(),
                new PunishClickEvent()
        ).forEach(e -> getServer().getPluginManager().registerEvents(e, this));
    }

    private void registerCommands() {
        getCommand("group").setExecutor(new UpdateRankCommand());
        getCommand("news").setExecutor(new NewsCommand());
        getCommand("announce").setExecutor(new AnnounceCommand());
        getCommand("barinfo").setExecutor(new BarinfoCommand());
        getCommand("gm").setExecutor(new GmCommand());
        getCommand("tp").setExecutor(new TpCommand());
        getCommand("flakes").setExecutor(new FlakeCommand());
        getCommand("rules").setExecutor(new RuleCommand());
        getCommand("npc").setExecutor(new NPCCommand());
        getCommand("deletenpc").setExecutor(new DeleteNPC());
        getCommand("level").setExecutor(new LevelCommand());
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("levelstatus").setExecutor(new LevelStatutsCommand());
        getCommand("report").setExecutor(new ReportCommand());
        getCommand("sc").setExecutor(new StaffChatCommand());
        getCommand("punish").setExecutor(new PunishCommand());
    }

    private void disableCommandTabbing() {
        final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.TAB_COMPLETE) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Server.TAB_COMPLETE) {
                    if (!accountCache.getPlayer(event.getPlayer().getUniqueId()).getRank().has(Rank.DEV)) {
                        event.setCancelled(true);
                    }
                }
            }
        });
    }

    private void loadNPCs() {
        final NPCFetcher npcFetcher = new NPCFetcher();
        if (!npcFetcher.call().isEmpty()) {
            for (NPC npc : npcFetcher.call()) {
                if (npc != null) {
                    npc.spawn();
                }
            }
        }
    }

    @Override
    public void onDisable() {
        BarUtil.clear();
        hologramManager.clearAllCache();
        vanishUtil.clearCache();
        databaseManager.getQueryBuilder().getDatabase().closeConnection();
        getServer().getScheduler().cancelAllTasks();
    }

    //so as to not fuck shit up...

    private class GarbageMan {

        private final List<Plugin> list = Lists.newArrayList();
        private final JavaPlugin plugin;
        private PluginManager pluginManager;

        private GarbageMan(JavaPlugin plugin, String... plugins) {
            this.plugin = plugin;
            this.pluginManager = Bukkit.getPluginManager();
            Stream.of(plugins).forEach(name -> {
                if (pluginManager.getPlugin(name) != null) {
                    list.add(pluginManager.getPlugin(name));
                    System.out.println("Server> Successfully registered [p] for garbage dump.".replace("[p]", name));
                } else {
                    System.out.println("Server> '[p]' couldn't be found in the plugins folder.".replace("[p]", name));
                }
            });
        }

        private void delete(File file) {
            File[] contents = file.listFiles();
            if (contents != null) {
                for (File f : contents) {
                    if (! Files.isSymbolicLink(f.toPath())) {
                        delete(f);
                    }
                }
            }
            file.delete();
        }

        private void cleanup() {
            if (list != null && !list.isEmpty()) {
                list.forEach(p -> {
                    pluginManager.disablePlugin(p);
                    System.out.println("Server> Successfully disabled [p] from plugin list.".replace("[p]", p.getName()));

                    final File file = new File(plugin.getDataFolder(), p.getName());
                    delete(file);
                    System.out.println("Server> Successfully disabled [p]'s config from plugin list.".replace("[p]", p.getName()));

                    list.remove(p);
                });
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "reload");
            }
        }
    }
}
