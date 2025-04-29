package mc.twilight.lobby.listeners.player;

import mc.twilight.core.Core;
import mc.twilight.lobby.Language;
import mc.twilight.lobby.Main;
import mc.twilight.lobby.hook.LCoreHook;
import mc.twilight.lobby.utils.tagger.TagUtils;
import mc.twilight.core.nms.NMS;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.hotbar.Hotbar;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.titles.TitleManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent evt) {
    evt.setJoinMessage(null);
    
    Player player = evt.getPlayer();
    TagUtils.sendTeams(player);
    
    Profile profile = Profile.getProfile(player.getName());
    LCoreHook.reloadScoreboard(profile);
    profile.setHotbar(Hotbar.getHotbarById("lobby"));
    profile.refresh();
    
    Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), () -> TitleManager.joinLobby(profile), 10);
    
    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
      TagUtils.setTag(evt.getPlayer());
      if (Role.getPlayerRole(player).isBroadcast()) {
        String broadcast = Language.lobby$broadcast.replace("{player}", Role.getPrefixed(player.getName()));
        Profile.listProfiles().forEach(pf -> {
          if (!pf.playingGame()) {
            Player players = pf.getPlayer();
            if (players != null) {
              players.sendMessage(broadcast);
            }
          }
        });
      }
    }, 5);
    
    NMS.sendTitle(player, "", "", 0, 1, 0);
    if (Language.lobby$tab$enabled) {
      NMS.sendTabHeaderFooter(player, Language.lobby$tab$header, Language.lobby$tab$footer);
    }
  }
}
