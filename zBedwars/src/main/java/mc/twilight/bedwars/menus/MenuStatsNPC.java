package mc.twilight.bedwars.menus;

import mc.twilight.core.Core;
import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class MenuStatsNPC extends PlayerMenu {
  
  public MenuStatsNPC(Profile profile) {
    super(profile.getPlayer(), "Estatísticas - Bed Wars", 5);
    
    this.setItem(4, BukkitUtils.deserializeItemStack(PlaceholderAPI.setPlaceholders(this.player,
        "PAPER : 1 : nome>&aTodos os Modos : desc> &8▪ &fPartidas: &7%zCore_BedWars_games%\n \n &8▪ &fCamas destruídas: &7%zCore_BedWars_bedsdestroyeds%\n &8▪ &fCamas perdidas: &7%zCore_BedWars_bedslosteds%\n \n &8▪ &fAbates: &7%zCore_BedWars_kills%\n &8▪ &fMortes: &7%zCore_BedWars_deaths%\n &8▪ &fAbates Finais: &7%zCore_BedWars_finalkills%\n &8▪ &fMortes Finais: &7%zCore_BedWars_finaldeaths%\n \n &8▪ &fVitórias: &7%zCore_BedWars_wins%")));
    
    this.setItem(20, BukkitUtils.deserializeItemStack(PlaceholderAPI.setPlaceholders(this.player,
        "PAPER : 1 : nome>&aSolo : desc> &8▪ &fPartidas: &7%zCore_BedWars_1v1games%\n \n &8▪ &fCamas destruídas: &7%zCore_BedWars_1v1bedsdestroyeds%\n &8▪ &fCamas perdidas: &7%zCore_BedWars_1v1bedslosteds%\n \n &8▪ &fAbates: &7%zCore_BedWars_1v1kills%\n &8▪ &fMortes: &7%zCore_BedWars_1v1deaths%\n &8▪ &fAbates Finais: &7%zCore_BedWars_1v1finalkills%\n &8▪ &fMortes Finais: &7%zCore_BedWars_1v1finaldeaths%\n \n &8▪ &fVitórias: &7%zCore_BedWars_1v1wins%")));
    
    this.setItem(22, BukkitUtils.deserializeItemStack(PlaceholderAPI.setPlaceholders(this.player,
        "PAPER : 1 : nome>&aDupla : desc> &8▪ &fPartidas: &7%zCore_BedWars_2v2games%\n \n &8▪ &fCamas destruídas: &7%zCore_BedWars_2v2bedsdestroyeds%\n &8▪ &fCamas perdidas: &7%zCore_BedWars_2v2bedslosteds%\n \n &8▪ &fAbates: &7%zCore_BedWars_2v2kills%\n &8▪ &fMortes: &7%zCore_BedWars_2v2deaths%\n &8▪ &fAbates Finais: &7%zCore_BedWars_2v2finalkills%\n &8▪ &fMortes Finais: &7%zCore_BedWars_2v2finaldeaths%\n \n &8▪ &fVitórias: &7%zCore_BedWars_2v2wins%")));
    
    this.setItem(24, BukkitUtils.deserializeItemStack(PlaceholderAPI.setPlaceholders(this.player,
        "PAPER : 1 : nome>&aQuarteto : desc> &8▪ &fPartidas: &7%zCore_BedWars_4v4games%\n \n &8▪ &fCamas destruídas: &7%zCore_BedWars_4v4bedsdestroyeds%\n &8▪ &fCamas perdidas: &7%zCore_BedWars_4v4bedslosteds%\n \n &8▪ &fAbates: &7%zCore_BedWars_4v4kills%\n &8▪ &fMortes: &7%zCore_BedWars_4v4deaths%\n &8▪ &fAbates Finais: &7%zCore_BedWars_4v4finalkills%\n &8▪ &fMortes Finais: &7%zCore_BedWars_4v4finaldeaths%\n \n &8▪ &fVitórias: &7%zCore_BedWars_4v4wins%")));
    
    this.setItem(40, BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : nome>&cFechar"));
    
    this.register(Core.getInstance());
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
            if (evt.getSlot() == 40) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              this.player.closeInventory();
            }
          }
        }
      }
    }
  }
  
  public void cancel() {
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