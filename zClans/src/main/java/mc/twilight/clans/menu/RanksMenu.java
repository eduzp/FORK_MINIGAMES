package mc.twilight.clans.menu;

import mc.twilight.clans.clan.Clan;
import mc.twilight.clans.database.Database;
import mc.twilight.core.Core;
import mc.twilight.core.libraries.menu.PagedPlayerMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.core.plugin.KPlugin;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class RanksMenu extends PagedPlayerMenu {
  public RanksMenu(Profile profile) {
    super(profile.getPlayer(), "Ranking - Clan Coins", 6);
    this.previousPage = 18;
    this.nextPage = 26;
    onlySlots(new Integer[] { 
          Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(13), Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(19), Integer.valueOf(20), Integer.valueOf(21), 
          Integer.valueOf(22), Integer.valueOf(23), Integer.valueOf(24), Integer.valueOf(25), Integer.valueOf(28), Integer.valueOf(29), Integer.valueOf(30), Integer.valueOf(31), Integer.valueOf(32), Integer.valueOf(33), 
          Integer.valueOf(34) });
    removeSlotsWith(BukkitUtils.deserializeItemStack("BOOK : 1 : nome>&aInformações : desc>&7O rank é atualizado de tempos em\n&7tempos. As informações vistas aqui não\n&7são em tempo real."), new int[] { 49 });
    List<ItemStack> items = new ArrayList<>();
    List<String[]> list = Database.getInstance().getLeaderBoard("zClans", new String[] { "coins" });
    int index = 1;
    for (String[] strings : list) {
      if (parse(strings[1]) < 1)
        continue; 
      Clan clan = Clan.getByTag(strings[0]);
      ItemStack itemStack = BukkitUtils.deserializeItemStack("PAPER : 1 : esconder>tudo : nome>&f&l" + index + "º, &" + (clan.tagPermissionPlus ? "6" : "7") + "[" + strings[0] + "] " + clan
          .getName() + " : desc>&fClan Coins: &7" + strings[1]);
      items.add(itemStack);
      index++;
    } 
    setItems(items);
    items.clear();
    register((KPlugin)Core.getInstance());
    open();
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(getCurrentInventory())) {
      evt.setCancelled(true);
      if (evt.getWhoClicked().equals(this.player)) {
        Profile profile = Profile.getProfile(this.player.getName());
        if (profile == null) {
          this.player.closeInventory();
          return;
        } 
        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(getCurrentInventory())) {
          ItemStack item = evt.getCurrentItem();
          if (item != null && item.getType() != Material.AIR)
            if (evt.getSlot() == this.previousPage) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              openPrevious();
            } else if (evt.getSlot() == this.nextPage) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              openNext();
            } else {
              EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
            }  
        } 
      } 
    } 
  }
  
  public void cancel() {
    HandlerList.unregisterAll((Listener)this);
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (evt.getPlayer().equals(this.player))
      cancel(); 
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent evt) {
    if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(getCurrentInventory()))
      cancel(); 
  }
  
  public int parse(String formattedString) {
    return Integer.parseInt(formattedString.replace(".", "").replace(",", ""));
  }
}
