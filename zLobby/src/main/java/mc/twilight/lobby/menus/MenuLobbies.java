package mc.twilight.lobby.menus;

import mc.twilight.core.Core;
import mc.twilight.core.libraries.menu.UpdatablePlayerMenu;
import mc.twilight.lobby.Main;
import mc.twilight.lobby.lobby.Lobby;
import mc.twilight.core.player.Profile;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import static mc.twilight.lobby.lobby.Lobby.CONFIG;
import static mc.twilight.lobby.lobby.Lobby.listLobbies;

public class MenuLobbies extends UpdatablePlayerMenu {
  
  public MenuLobbies(Profile profile) {
    super(profile.getPlayer(), CONFIG.getString("title"), CONFIG.getInt("rows"));
    
    this.update();
    this.register(Core.getInstance(), 20);
    this.open();
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(this.getInventory())) {
      evt.setCancelled(true);
      
      if (evt.getWhoClicked().equals(this.player)) {
        Profile profile = Profile.getProfile(this.player.getName());
        if (profile == null) {
          this.player.closeInventory();
          return;
        }
        
        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getInventory())) {
          ItemStack item = evt.getCurrentItem();
          
          if (item != null && item.getType() != Material.AIR) {
            Lobby lobby = listLobbies().stream().filter(s -> s.getSlot() == evt.getSlot()).findFirst().orElse(null);
            if (lobby != null && !Main.currentServerName.contentEquals(lobby.getServerName()) && lobby.getPlayers() < lobby.getMaxPlayers()) {
              Core.sendServer(profile, lobby.getServerName());
              this.player.closeInventory();
            }
          }
        }
      }
    }
  }
  
  @Override
  public void update() {
    for (Lobby lobby : listLobbies()) {
      ItemStack icon = BukkitUtils.deserializeItemStack(
          lobby.getIcon().replace("{players}", StringUtils.formatNumber(lobby.getPlayers())).replace("{maxplayers}", StringUtils.formatNumber(lobby.getMaxPlayers()))
              .replace("{description}",
                  StringUtils.formatColors(CONFIG.getString("messages.description." + (Main.currentServerName.equals(lobby.getServerName()) ? "current" : "connect")))));
      if (Main.currentServerName.equals(lobby.getServerName())) {
        BukkitUtils.putGlowEnchantment(icon);
      }
      
      this.setItem(lobby.getSlot(), icon);
    }
  }
  
  public void cancel() {
    super.cancel();
    HandlerList.unregisterAll(this);
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (evt.getPlayer().equals(this.player)) {
      this.cancel();
    }
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent evt) {
    if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getInventory())) {
      this.cancel();
    }
  }
}
