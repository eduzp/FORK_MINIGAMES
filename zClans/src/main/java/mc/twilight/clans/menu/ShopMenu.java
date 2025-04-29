package mc.twilight.clans.menu;

import mc.twilight.clans.clan.Clan;
import mc.twilight.core.Core;
import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.plugin.KPlugin;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class ShopMenu extends PlayerMenu implements Listener {
  public ShopMenu(Player player, Clan clan) {
    super(player, "Loja do Clan", 5);
    int index = 0;
    int[] prices = { 2000, 4000, 6000, 8000, 10000 };
    for (int slots : new int[] { 10, 15, 20, 25, 30 }) {
      int price = prices[index];
      String name = (index < 3) ? StringUtils.repeat("I", index + 1) : ((index == 4) ? "V" : "IV");
      if (clan.getSlots() / 5 != index + 1) {
        if (clan.getSlots() / 5 > index + 1) {
          setItem(index + 11, 
              BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>&eLimite de Membros " + name + " : desc>&7Amplia a capacidade do clan para &a" + slots + " &7membros.\n \n&eVocê já possui esta melhoria!"));
        } else {
          setItem(index + 11, 
              BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>&cLimite de Membros " + name + " : desc>&7Amplia a capacidade do clan para &a" + slots + " &7membros.\n \n&cNecessita do nível anterior!"));
        } 
      } else if (clan.getCoins() < price) {
        setItem(index + 11, 
            BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>&cLimite de Membros " + name + " : desc>&7Amplia a capacidade do clan para &a" + slots + " &7membros.\n \n&fPreço: &b" + 
              
              StringUtils.formatNumber(price) + " Clan Coins\n \n&cVocê não tem possui suficiente!"));
      } else {
        setItem(index + 11, 
            BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>&eLimite de Membros " + name + " : desc>&7Amplia a capacidade do clan para &a" + slots + " &7membros.\n \n&fPreço: &b" + 
              
              StringUtils.formatNumber(price) + " Clan Coins\n \n&eClique para comprar!"));
      } 
      index++;
    } 
    setItem(30, BukkitUtils.deserializeItemStack("SIGN : 1 : nome>" + ((clan.getCoins() < 15000) ? "&cSigla do clan" : "&aSigla do clan") + " : desc>&7Seu clan terá no tab e no chat a\n&7tag do seu clan na cor cinza.\n \n&fPreço: &615000 Clan Coins\n \n" + (clan.tagPermission ? "§eVocê já possui esta melhoria" : ((clan.getCoins() < 15000) ? "§cVocê não possui saldo suficiente!" : "§eClique para comprar!"))));
    setItem(32, BukkitUtils.deserializeItemStack("SIGN : 1 : nome>" + (!clan.tagPermission ? "&cSigla do clan dourada" : "&aSigla do clan dourada") + " : desc>&7Seu clan terá no tab e no chat a\n&7tag do seu clan na cor dourada.\n \n&fPreço: &625000 Clan Coins\n \n" + (clan.tagPermissionPlus ? "§eVocê já possui esta melhoria" : ((clan.getCoins() < 25000) ? "§cVocê não possui saldo suficiente!" : "§eClique para comprar!")) + (!clan.tagPermission ? "\n&cNecessita do nível anterior!" : ("" + (!player.hasPermission("utils.clan.tag") ? "\n&cSomente MVP+ pode fazer isso." : "")))));
    open();
    register((KPlugin)Core.getInstance());
  }
  
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
          if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(evt.getInventory()) && item != null && item.getType() != Material.AIR)
            if (evt.getSlot() == 30) {
              if (!clan.tagPermission)
                if (clan.getCoins() >= 15000) {
                  if (!clan.getRole(this.player.getName()).equals("Líder") || !clan.getRole(this.player.getName()).equals("Oficial")) {
                    EnumSound.VILLAGER_NO.play(this.player, 1.0F, 1.0F);
                    this.player.closeInventory();
                    this.player.sendMessage("§cSomente o Líder e Oficial pode adquirir upgrades.");
                    return;
                  } 
                  clan.addTagColor(false);
                  clan.removeCoins(15000);
                  this.player.sendMessage("§aVocê adquiriu " + StringUtils.stripColors(item.getItemMeta().getDisplayName()));
                  EnumSound.LEVEL_UP.play(this.player, 0.5F, 2.0F);
                  new ShopMenu(this.player, clan);
                } else {
                  EnumSound.ENDERMAN_TELEPORT.play(this.player, 1.0F, 1.0F);
                }  
            } else if (evt.getSlot() == 32) {
              if (clan.tagPermission && !clan.tagPermissionPlus)
                if (clan.getCoins() >= 25000 && this.player.hasPermission("utils.clan.tag")) {
                  if (!clan.getRole(this.player.getName()).equals("Líder") || !clan.getRole(this.player.getName()).equals("Oficial")) {
                    EnumSound.VILLAGER_NO.play(this.player, 1.0F, 1.0F);
                    this.player.closeInventory();
                    this.player.sendMessage("§cSomente o Líder pode adquirir upgrades.");
                    return;
                  } 
                  if (!this.player.hasPermission("utils.clan.tag")) {
                    EnumSound.VILLAGER_NO.play(this.player, 1.0F, 1.0F);
                    this.player.closeInventory();
                    this.player.sendMessage("§cSomente MVP+ pode adquirir este upgrade.");
                    return;
                  } 
                  clan.addTagColor(true);
                  clan.removeCoins(25000);
                  this.player.sendMessage("§aVocê adquiriu " + StringUtils.stripColors(item.getItemMeta().getDisplayName()));
                  EnumSound.LEVEL_UP.play(this.player, 0.5F, 2.0F);
                  new ShopMenu(this.player, clan);
                } else {
                  EnumSound.ENDERMAN_TELEPORT.play(this.player, 1.0F, 1.0F);
                }  
            } else if (item.getType() == Material.SKULL_ITEM && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item
              .getItemMeta().getDisplayName().contains("Limite de Membros")) {
              if (evt.getSlot() - 11 > getInventory().getSize())
                return; 
              int index = evt.getSlot() - 11;
              int[] prices = { 2000, 4000, 6000, 8000, 10000 };
              if (clan.getSlots() / 5 != index + 1) {
                EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
                return;
              } 
              if (prices[index] <= clan.getCoins()) {
                if ((clan.getRole(this.player.getName()) != null && !clan.getRole(this.player.getName()).equals("Líder")) || !clan.getRole(this.player.getName()).equals("Oficial")) {
                  EnumSound.VILLAGER_NO.play(this.player, 1.0F, 1.0F);
                  this.player.closeInventory();
                  this.player.sendMessage("§cApenas o líder e o oficial pode comprar upgrades.");
                  return;
                } 
                clan.addSlots();
                clan.removeCoins(prices[index]);
                this.player.sendMessage("§aVocê comprou §6{upgrade}".replace("{upgrade}", 
                      StringUtils.stripColors(item.getItemMeta().getDisplayName())));
                EnumSound.LEVEL_UP.play(this.player, 0.5F, 2.0F);
                new ShopMenu(this.player, clan);
              } else {
                EnumSound.ENDERMAN_TELEPORT.play(this.player, 1.0F, 1.0F);
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
    if (evt.getPlayer().equals(this.player))
      cancel(); 
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent evt) {
    if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(getInventory()))
      cancel(); 
  }
}
