package mc.twilight.bedwars.listeners.player;

import mc.twilight.bedwars.cmd.bw.BuildCommand;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.core.game.GameState;
import mc.twilight.core.player.Profile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getWhoClicked() instanceof Player) {
      Player player = (Player) evt.getWhoClicked();
      ItemStack item = evt.getCurrentItem();
      Profile profile = Profile.getProfile(player.getName());
      
      if (profile != null) {
        BedWars game = profile.getGame(BedWars.class);
        if (game == null) {
          evt.setCancelled(!BuildCommand.hasBuilder(player));
        } else if (game.isSpectator(player) || game.getState() != GameState.EMJOGO) {
          evt.setCancelled(true);
        } else if (!game.isSpectator(player) && game.getState() == GameState.EMJOGO) {
          Material mat = Material.AIR;
          if (item != null) {
            mat = item.getType();
          }
          
          boolean cantDrop = mat.name().contains("_SWORD") || mat.name().contains("BOW")
              || mat.name().contains("_PICKAXE") || mat.name().contains("_AXE")
              || mat.name().contains("SHEARS") || mat.name().contains("COMPASS");
          if (mat.name().contains("_HELMET") || mat.name().contains("_BOOTS") || mat.name().contains("_CHESTPLATE") || mat.name().contains("_LEGGINGS")) {
            evt.setCancelled(true);
          } else {
            if (player.getOpenInventory().getTopInventory().getType().name().contains("CHEST")
                || (evt.getClickedInventory() != null
                && evt.getClickedInventory().getType().name().contains("CHEST"))) {
              evt.setCancelled(cantDrop);
              if (evt.getHotbarButton() != -1) {
                mat = Material.AIR;
                ItemStack itemMoved = player.getInventory().getItem(evt.getHotbarButton());
                if (itemMoved != null) {
                  mat = itemMoved.getType();
                }
                
                evt.setCancelled(mat.name().contains("_SWORD") || mat.name().contains("BOW")
                    || mat.name().contains("_PICKAXE") || mat.name().contains("_AXE")
                    || mat.name().contains("SHEARS") || mat.name().contains("COMPASS")
                    || mat.name().contains("_CHESTPLATE") || mat.name().contains("_BOOTS")
                    || mat.name().contains("_HELMET") || mat.name().contains("_LEGGINGS"));
              }
            }
          }
        }
      }
    }
  }
}