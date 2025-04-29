package mc.twilight.caixas.menus.open;

import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.caixas.Main;
import mc.twilight.caixas.api.MysteryBoxAPI;
import mc.twilight.caixas.box.loot.LootMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.core.utils.BukkitUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class ConfirmOpenMenu extends PlayerMenu {
  
  private LootMenu menu;
  
  public ConfirmOpenMenu(Profile profile, LootMenu menu) {
    super(profile.getPlayer(), "Abrir Cápsulas Mágicas", 3);
    this.menu = menu;
    
    long amount = MysteryBoxAPI.getMysteryBoxes(profile);
    this.setItem(11, BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>&aAbrir 1 caixa : desc>&7Abrir apenas 1 caixa. : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWRlYmNmZTQxNDNhMTFlNjFkMzkzMTc5OWZiMzIyZTVhYTJhZTczMjc1YzUzYzJjNjc0MTYxNzhkMTQ5ZTE1MiJ9fX0="));
    this.setItem(15, BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : " + amount + " : nome>&aAbrir todas as caixas : desc>&7Abrir todas as caixas. : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWRlYmNmZTQxNDNhMTFlNjFkMzkzMTc5OWZiMzIyZTVhYTJhZTczMjc1YzUzYzJjNjc0MTYxNzhkMTQ5ZTE1MiJ9fX0="));
    
    this.register(Main.getInstance());
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
            if (evt.getSlot() == 11) {
              this.menu.open(menu.getChest().getAnimation());
              this.player.closeInventory();
            } else if (evt.getSlot() == 15) {
              long amount = MysteryBoxAPI.getMysteryBoxes(profile);
              if (amount < 2) {
                this.player.closeInventory();
                return;
              }
              
              this.menu.open(menu.getChest().getAnimation(), amount);
              this.player.closeInventory();
            }
          }
        }
      }
    }
  }
  
  public void cancel() {
    HandlerList.unregisterAll(this);
    this.menu = null;
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