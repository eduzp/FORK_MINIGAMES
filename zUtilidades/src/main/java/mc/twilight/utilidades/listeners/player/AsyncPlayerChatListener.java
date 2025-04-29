package mc.twilight.utilidades.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import mc.twilight.utilidades.Language;
import mc.twilight.utilidades.cmd.utils.StaffChatCommand;
import mc.twilight.core.nms.NMS;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumSound;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent evt) {
        if (StaffChatCommand.BAIANOS.contains(evt.getPlayer())) {
            evt.setCancelled(true);
            Player player = evt.getPlayer();
            Bukkit.getOnlinePlayers().stream().filter(playerx -> playerx.hasPermission("zutilidades.cmd.staffchat")).forEach(playerx -> {
                if (Language.staffchat$warning_actionbar) {
                    NMS.sendActionBar(playerx, Language.staffchat$actionbar);
                }
                if (Language.staffchat$play_sound) {
                    EnumSound.valueOf(Language.staffchat$sound).play(playerx, 1.0F, 1.0F);
                }
                playerx.sendMessage(Language.staffchat$format.replace("{player}", Role.getPrefixed(player.getName(), true)).replace("{message}", evt.getMessage().replace("&", "§")));
            });
        }
    }

}
