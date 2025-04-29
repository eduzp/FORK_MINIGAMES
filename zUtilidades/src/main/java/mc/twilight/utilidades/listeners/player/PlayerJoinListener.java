package mc.twilight.utilidades.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import mc.twilight.utilidades.Language;
import mc.twilight.core.nms.NMS;

public class PlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent evt) {
        Player player = evt.getPlayer();
        if (Language.lobby$titles$welcome$enabled) {
            NMS.sendTitle(player, Language.lobby$titles$welcome$header, Language.lobby$titles$welcome$footer);
        }
    }

}
