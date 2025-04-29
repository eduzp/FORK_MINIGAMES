package mc.twilight.bedwars.menus.game;


import mc.twilight.core.Core;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.BedWarsTeam;
import mc.twilight.bedwars.game.object.BedWarsEquipment;
import mc.twilight.bedwars.utils.PlayerUtils;
import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuTrackerShop extends PlayerMenu {
  
  private Map<Integer, BedWarsTeam> teams = new HashMap<>();
  
  public MenuTrackerShop(Profile profile) {
    super(profile.getPlayer(), "Rastreador", 3);
    
    BedWars server = profile.getGame(BedWars.class);
    BedWarsTeam selfTeam = server.getTeam(player);
    BedWarsEquipment equipment = selfTeam.getEquipment(player);
    
    int index = 0;
    List<Integer> list = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17);
    for (String id : BedWarsTeam.ids) {
      BedWarsTeam team = server.listTeams().stream().filter(bt -> bt.getId().equals(id)).findFirst().orElse(null);
      if (team != null && team.hasMember(player)) {
        continue;
      }
      
      String color = server.listTeams().stream().anyMatch(bt -> !bt.hasMember(player) && !bt.bed()) ? "&e"
          : PlayerUtils.getCountFromMaterial(player.getInventory(), Material.EMERALD) < 2 ? "&c" : "&a";
      String name = null;
      if (team != null) {
        name = team.getName();
      }
      ItemStack icon = BukkitUtils.deserializeItemStack(
          ("WOOL:{id} : 1 : nome>{color}Rastreador do Time " + StringUtils.stripColors(name == null ? BedWarsTeam.names[index] : name) + " : desc>&7Compre a melhoria para a sua\n&7bússola de rastreio de jogadores\n&7próximos do time {name} &7até você\n&7morrer.\n \n&7Custo: &22 Esmeraldas").replace("{id}", id).replace("{color}", color).replace("{name}", name == null ? BedWarsTeam.names[index] : name));
      ItemMeta meta = icon.getItemMeta();
      List<String> lore = meta.getLore();
      String desc = color.equals("&e") ? "&cAs camas inimigas ainda não" : color.equals("&c") ? "&cVocê não tem Esmeraldas suficientes!" : equipment.getTracking().equals(id) ? "§eVocê já está rastreando este time" :
          team == null || !team.isAlive() ? "§cEste time já foi eliminado." : "§eClique para comprar";
      
      lore.add("");
      lore.add(StringUtils.formatColors(desc));
      if (color.equals("&e")) {
        lore.add("§cforam destruídas por completo.");
      }
      meta.setLore(lore);
      icon.setItemMeta(meta);
      
      this.setItem(list.size() <= index ? index : list.get(index), icon);
      this.teams.put(list.size() <= index ? index : list.get(index), team);
      index++;
    }
    
    this.open();
    this.register(Core.getInstance());
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(getInventory())) {
      evt.setCancelled(true);
      
      if (evt.getWhoClicked() instanceof Player && evt.getWhoClicked().equals(player)) {
        ItemStack item = evt.getCurrentItem();
        BedWars game;
        BedWarsTeam team;
        Profile profile = Profile.getProfile(player.getName());
        if (profile == null || (game = profile.getGame(BedWars.class)) == null || (team = game.getTeam(player)) == null) {
          player.closeInventory();
          return;
        }
        
        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(evt.getInventory()) && item != null && item.getType() != Material.AIR) {
          boolean beds = game.listTeams().stream().anyMatch(bt -> !bt.hasMember(player) && !bt.bed());
          
          if (this.teams.containsKey(evt.getSlot())) {
            if (beds) {
              player.sendMessage(StringUtils.formatColors("§cAs camas inimigas ainda não foram destruídas por completo."));
              return;
            }
            
            if (PlayerUtils.getCountFromMaterial(player.getInventory(), Material.EMERALD) < 2) {
              player.sendMessage("§cVocê não tem recursos suficientes para isto!");
              return;
            }
            
            BedWarsTeam target = this.teams.get(evt.getSlot());
            if (target != null && team.getEquipment(player).getTracking().equals(target.getId())) {
              player.sendMessage("§cVocê já está rastreando este time.");
              return;
            }
            
            if (target == null || !target.isAlive()) {
              player.sendMessage("§cEste time já foi eliminado.");
              return;
            }
            
            PlayerUtils.removeItem(player.getInventory(), Material.EMERALD, 2);
            team.getEquipment(player).setTracking(target.getId());
            player.sendMessage("§aVocê comprou §6§l".replace("{tracker}", StringUtils.stripColors(item.getItemMeta().getDisplayName())));
            player.closeInventory();
          }
        }
      }
    }
  }
  
  public void cancel() {
    teams.clear();
    teams = null;
    HandlerList.unregisterAll(this);
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (evt.getPlayer().equals(player)) {
      this.cancel();
    }
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent evt) {
    if (evt.getPlayer().equals(player) && evt.getInventory().equals(this.getInventory())) {
      this.cancel();
    }
  }
}
