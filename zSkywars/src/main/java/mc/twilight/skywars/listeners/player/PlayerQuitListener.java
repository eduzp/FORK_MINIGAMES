package mc.twilight.skywars.listeners.player;

import mc.twilight.core.player.Profile;
import mc.twilight.skywars.cmd.sw.BuildCommand;
import mc.twilight.skywars.cmd.sw.ChestCommand;
import mc.twilight.skywars.cmd.sw.CreateCommand;
import mc.twilight.skywars.utils.tagger.TagUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    evt.setQuitMessage(null);
    Profile profile = Profile.getProfile(evt.getPlayer().getName());;
    BuildCommand.remove(evt.getPlayer());
    TagUtils.reset(evt.getPlayer().getName());
    CreateCommand.CREATING.remove(evt.getPlayer());
    ChestCommand.CHEST.remove(evt.getPlayer());
  }
}
