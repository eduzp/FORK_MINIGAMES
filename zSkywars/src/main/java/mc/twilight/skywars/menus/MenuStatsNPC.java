package mc.twilight.skywars.menus;

import mc.twilight.core.Core;
import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.skywars.game.object.SkyWarsLeague;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
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
    super(profile.getPlayer(), "Estatísticas - Sky Wars", 5);
    
    long kills = profile.getStats("zCoreSkyWars", "1v1kills", "2v2kills", "rankedkills") == 0
        ? 1 : profile.getStats("zCoreSkyWars", "1v1kills", "2v2kills", "rankedkills"), deaths =
        profile.getStats("zCoreSkyWars", "1v1deaths", "2v2deaths", "rankeddeaths") == 0 ? 1 :
            profile.getStats("zCoreSkyWars", "1v1deaths", "2v2deaths", "rankeddeaths"), skills =
        profile.getStats("zCoreSkyWars", "1v1kills"), sdeaths = profile.getStats("zCoreSkyWars", "1v1deaths"),
        tkills = profile.getStats("zCoreSkyWars", "2v2kills"), rkills = profile.getStats("zCoreSkyWars", "rankedkills"),
        rdeaths = profile.getStats("zCoreSkyWars", "rankeddeaths"), tdeaths = profile.getStats("zCoreSkyWars", "2v2deaths");
    
    this.setItem(4, BukkitUtils.deserializeItemStack(PlaceholderAPI.setPlaceholders(this.player,
        "PAPER : 1 : nome>&aTodos os Modos : desc>&fAbates: &7%zCore_SkyWars_kills%\n&fMortes: &7%zCore_SkyWars_deaths%\n&fVitórias: &7%zCore_SkyWars_wins%\n&fPartidas: &7%zCore_SkyWars_games%\n&fAssistências: &7%zCore_SkyWars_assists%\n \n&fKDR: &7" + StringUtils.formatNumber(kills / deaths) + "\n \n&fCoins: &6%zCore_SkyWars_coins%")));
    
    this.setItem(20, BukkitUtils.deserializeItemStack(PlaceholderAPI.setPlaceholders(this.player,
        "PAPER : 1 : nome>&aSolo : desc>&fAbates: &7%zCore_SkyWars_1v1kills%\n&fMortes: &7%zCore_SkyWars_1v1deaths%\n&fVitórias: &7%zCore_SkyWars_1v1wins%\n&fPartidas: &7%zCore_SkyWars_1v1games%\n&fAssistências: &7%zCore_SkyWars_1v1assists%\n \n&fKDR: &7" + StringUtils.formatNumber((skills == 0 ? 1 : skills) / (sdeaths == 0 ? 1 : sdeaths)))));
    
    this.setItem(22, BukkitUtils.deserializeItemStack(PlaceholderAPI.setPlaceholders(this.player,
        "PAPER : 1 : nome>&aRanked : desc>&fAbates: &7%zCore_SkyWars_rankedkills%\n&fMortes: &7%zCore_SkyWars_rankeddeaths%\n&fVitórias: &7%zCore_SkyWars_rankedwins%\n&fPartidas: &7%zCore_SkyWars_rankedgames%\n&fPontos: &7%zCore_SkyWars_rankedpoints%\n&fLiga: &7" + SkyWarsLeague.getLeague(profile).getTag() + "\n \n&fKDR: &7" + StringUtils.formatNumber((rkills == 0 ? 1 : rkills) / (rdeaths == 0 ? 1 : rdeaths)))));
    
    this.setItem(24, BukkitUtils.deserializeItemStack(PlaceholderAPI.setPlaceholders(this.player,
        "PAPER : 1 : nome>&aDupla : desc>&fAbates: &7%zCore_SkyWars_2v2kills%\n&fMortes: &7%zCore_SkyWars_2v2deaths%\n&fVitórias: &7%zCore_SkyWars_2v2wins%\n&fPartidas: &7%zCore_SkyWars_2v2games%\n&fAssistências: &7%zCore_SkyWars_2v2assists%\n \n&fKDR: &7" + StringUtils.formatNumber((tkills == 0 ? 1 : tkills) / (tdeaths == 0 ? 1 : tdeaths)))));
    
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
