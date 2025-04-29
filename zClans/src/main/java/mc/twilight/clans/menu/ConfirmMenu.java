package mc.twilight.clans.menu;

import mc.twilight.clans.Main;
import mc.twilight.clans.clan.Clan;
import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ConfirmMenu extends PlayerMenu implements Listener {
  private boolean delete;
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(getInventory())) {
      evt.setCancelled(true);
      if (evt.getWhoClicked() instanceof Player && evt.getWhoClicked().equals(this.player)) {
        Clan clan = Clan.getClan(this.player);
        if (clan == null) {
          this.player.closeInventory();
        } else {
          ItemStack item = evt.getCurrentItem();
          if (evt.getClickedInventory() != null && evt
            .getClickedInventory().equals(evt.getInventory()) && item != null && item
            .getType() != Material.AIR)
            if (evt.getSlot() == 20) {
              this.player.closeInventory();
              this.player.performCommand("clan " + (this.delete ? "excluir" : "sair"));
            } else if (evt.getSlot() == 24) {
              this.player.performCommand("clan");
            }  
        } 
      } 
    } 
  }
  
  public ConfirmMenu(Player player, boolean delete) {
    super(player, delete ? "Deletar seu Clan?" : "Sair do Clan?", 4);
    this.delete = delete;
    if (delete) {
      setItem(13, BukkitUtils.deserializeItemStack("WOOL:4 : 1 : nome>&aSim : desc>&7Deseja deletar seu clan?\n\n&c* Esta ação não pode ser revertida."));
    } else {
      setItem(13, BukkitUtils.deserializeItemStack("WOOL:4 : 1 : nome>&aSim : desc>&7Deseja sair do clan?\n\n&c* Esta ação não pode ser revertida."));
    } 
    setItem(20, BukkitUtils.deserializeItemStack("WOOL:13 : 1 : nome>&aSim"));
    setItem(24, BukkitUtils.deserializeItemStack("WOOL:14 : 1 : nome>&cNão"));
    player.openInventory(getInventory());
    Bukkit.getPluginManager().registerEvents(this, (Plugin)Main.getInstance());
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (evt.getPlayer().equals(this.player))
      HandlerList.unregisterAll(this); 
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent evt) {
    if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(getInventory()))
      HandlerList.unregisterAll(this); 
  }
}
