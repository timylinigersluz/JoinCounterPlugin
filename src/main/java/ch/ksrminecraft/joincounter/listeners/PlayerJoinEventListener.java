package ch.ksrminecraft.joincounter.listeners;

import ch.ksrminecraft.joincounter.utils.MongoDBUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEventListener implements Listener {

    // MongoDBUtils-Objekt, um auf die MongoDB zuzugreifen
    private final MongoDBUtils mongoDBUtils;

    // Konstruktor, um das MongoDBUtils-Objekt zu übergeben
    public PlayerJoinEventListener( MongoDBUtils mongoDBUtils) {
        this.mongoDBUtils = mongoDBUtils;
    }

    // EventListener, der aufgerufen wird, wenn ein Spieler dem Server beitritt

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        // Anzahl der Beitritte des Spielers in der MongoDB um 1 erhöhen
        mongoDBUtils.incrementPlayerJoinCount(playerName);

        // Anzahl der Beitritte des Spielers aus der MongoDB auslesen
        int joinCount= mongoDBUtils.getPlayerJoinCount(playerName);

        // Dem Spieler eine Nachricht senden
        player.sendMessage(Component.text(ChatColor.BLUE + "Du bist dem Server " + ChatColor.YELLOW + joinCount + ChatColor.BLUE + " mal beigetreten!"));

    }


}
