package mc.twilight.caixas.listeners;

import mc.twilight.caixas.Main;
import mc.twilight.caixas.box.action.BoxContent;
import mc.twilight.caixas.hook.container.MysteryBoxQueueContainer;
import mc.twilight.caixas.lobby.BoxNPC;
import mc.twilight.caixas.menus.MenuBoxes;
import mc.twilight.caixas.nms.NMS;
import mc.twilight.core.player.Profile;
import mc.twilight.core.plugin.logger.KLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.logging.Level;

public class Listeners implements Listener {
  
  public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger()).getModule("LISTENERS");
  
  public static void setupListeners() {
    Bukkit.getPluginManager().registerEvents(new Listeners(), Main.getInstance());
  }
  
  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerJoin(PlayerJoinEvent evt) {
    LOGGER.run(Level.SEVERE, "Could not pass PlayerJoinEvent for ${n} v${v}", () -> {
      Player player = evt.getPlayer();
      Profile profile = Profile.getProfile(player.getName());
      if (profile != null) {
        MysteryBoxQueueContainer container = profile.getAbstractContainer("zCaixas", "queueRewards", MysteryBoxQueueContainer.class);
        container.listQueuedContents().stream().filter(BoxContent::canBeDispatched).forEach(reward -> {
          container.removeFromQueue(reward);
          reward.dispatch(profile);
        });
      }
    });
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    NMS.BLOCK_CAMERA.remove(evt.getPlayer().getName());
  }
  
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent evt) {
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    if (profile != null) {
      if (evt.getClickedBlock() != null) {
        BoxNPC bn = BoxNPC.getByLocation(evt.getClickedBlock().getLocation());
        if (bn != null) {
          new MenuBoxes(profile, bn);
        }
      }
    }
  }
}
