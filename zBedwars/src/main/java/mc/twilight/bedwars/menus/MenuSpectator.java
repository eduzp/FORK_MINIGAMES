package mc.twilight.bedwars.menus;

import mc.twilight.bedwars.Main;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.BedWarsTeam;
import mc.twilight.core.libraries.menu.UpdatablePlayerMenu;
import mc.twilight.core.utils.BukkitUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MenuSpectator extends UpdatablePlayerMenu {
  
  private BedWars game;
  private Map<Integer, Player> map = new HashMap<>();
  
  public MenuSpectator(Player player, BedWars game) {
    super(player, "Jogadores", 3);
    this.game = game;
    
    this.update();
    this.open();
    this.register(Main.getInstance(), 20L);
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(this.getInventory())) {
      evt.setCancelled(true);
      
      if (evt.getWhoClicked().equals(this.player)) {
        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getInventory())) {
          ItemStack item = evt.getCurrentItem();
          
          if (item != null && item.getType() != Material.AIR) {
            Player target = map.get(evt.getSlot());
            if (target != null && target.getWorld().equals(player.getWorld())) {
              player.teleport(target);
              player.closeInventory();
            }
          }
        }
      }
    }
  }
  
  @Override
  public void update() {
    int slot = 0;
    for (Player player : game.listPlayers(false)) {
      BedWarsTeam team = game.getTeam(player);
      int hp = (int) player.getHealth();
      int food = player.getFoodLevel();
      
      this.setItem(slot, BukkitUtils.putProfileOnSkull(player, BukkitUtils.deserializeItemStack(
          "SKULL_ITEM:3 : 1 : nome>" + team.getColored(player) + " : desc>&fAbates: &a" + game
              .getKills(player) + "\n&fCamas destruídas: &c" + game.getBeds(player) + "\n \n&fVida: &c" + hp + "\n&fFome: &a" + food + "\n \n&eClique para ir até " + team.getColored(player) + "&e.")));
      map.put(slot++, player);
    }
  }
  
  public void cancel() {
    super.cancel();
    HandlerList.unregisterAll(this);
    this.game = null;
    this.map.clear();
    this.map = null;
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