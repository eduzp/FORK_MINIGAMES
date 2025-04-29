package mc.twilight.utilidades.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import mc.twilight.utilidades.cmd.utils.FlyCommand;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt) {
        Player player = evt.getPlayer();
        FlyCommand.remove(player);
    }

}
