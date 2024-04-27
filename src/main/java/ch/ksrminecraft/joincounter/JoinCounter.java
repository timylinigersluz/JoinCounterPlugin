package ch.ksrminecraft.joincounter;

import ch.ksrminecraft.joincounter.listeners.PlayerJoinEventListener;
import ch.ksrminecraft.joincounter.utils.MongoDBUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class JoinCounter extends JavaPlugin {

    // MongoDBUtils-Objekt erzeugen
    private MongoDBUtils mongoDBUtils;

    @Override
    public void onEnable() {
        // Plugin startup logic
        // Verbindung zur MongoDB aufbauen
        mongoDBUtils = new MongoDBUtils("mongodb+srv://user:Xfpjfee1YKPIXDRl@cIuster0.dnfzsu9.mongodb.net/?retryWrites=true&w=majority",
                        "Minecraft",
                        "players");


        // EventListener registrieren
        getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(mongoDBUtils), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        mongoDBUtils.close();
    }
}
