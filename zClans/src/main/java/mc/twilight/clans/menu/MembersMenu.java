package mc.twilight.clans.menu;

import mc.twilight.clans.clan.Clan;
import mc.twilight.core.Core;
import mc.twilight.core.Manager;
import mc.twilight.core.libraries.menu.PagedPlayerMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.plugin.KPlugin;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class MembersMenu extends PagedPlayerMenu {
  private Map<ItemStack, String> members = new HashMap<>();
  
  public MembersMenu(Player player, Clan clan) {
    super(player, "Membros do Clan", (int)ST(clan) / 7 + 4);
    this.previousPage = this.rows * 9 - 9;
    this.nextPage = this.rows * 9 - 1;
    onlySlots(new Integer[] { 
          Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(13), Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(19), Integer.valueOf(20), Integer.valueOf(21), 
          Integer.valueOf(22), Integer.valueOf(23), Integer.valueOf(24), Integer.valueOf(25), Integer.valueOf(28), Integer.valueOf(29), Integer.valueOf(30), Integer.valueOf(31), Integer.valueOf(32), Integer.valueOf(33), 
          Integer.valueOf(34) });
    List<ItemStack> items = new ArrayList<>();
    List<String> list = new ArrayList<>();
    list.add(clan.getLeader());
    list.addAll(clan.getOfficers());
    list.addAll(clan.getMembers());
    for (String member : list) {
      String item;
      ItemStack icon;
      if (player.getName().equals(clan.getLeader()) && !member.equals(player.getName())) {
        if (clan.getOfficers().contains(member)) {
          item = "SKULL_ITEM:3 : 1 : nome>&7" + Role.getPrefixed(member, true) + " : desc>&fCargo: &7" + clan.getRole(member) + "\n \n&eAções:\n &8▪ &fShift + Esquerdo para expulsar o membro\n &8▪ &fShift + Direito para rebaixar para membro\n \n" + ((Bukkit.getPlayerExact(member) != null) ? "&aOnline" : ("&7Offline : skin>" + Manager.getSkin(member, "value")));
        } else {
          item = "SKULL_ITEM:3 : 1 : nome>" + Role.getPrefixed(member, true) + " : desc>&fCargo: &7" + clan.getRole(member) + "\n \n&eAções:\n &8▪ &fShift + Esquerdo para expulsar o membro\n &8▪ &fShift + Direito para promover para oficial\n &8▪ &fDouble Click para transferir o clan\n \n" + ((Bukkit.getPlayerExact(member) != null) ? "&aOnline" : ("&7Offline : skin>" + Manager.getSkin(member, "value")));
        } 
      } else if (clan.getOfficers().contains(player.getName()) && clan.getMembers().contains(member)) {
        item = "SKULL_ITEM:3 : 1 : nome>" + Role.getPrefixed(member, true) + " : desc>&fCargo: &7" + clan.getRole(member) + "\n \n&eAções:\n &8▪ &fShift + Esquerdo para expulsar o membro\n \n" + ((Bukkit.getPlayerExact(member) != null) ? "&aOnline" : ("&7Offline : skin>" + Manager.getSkin(member, "value")));
      } else {
        item = "SKULL_ITEM:3 : 1 : nome>" + Role.getPrefixed(member, true) + " : desc>&fCargo: &7" + clan.getRole(member) + "\n \n" + ((Bukkit.getPlayerExact(member) != null) ? "&aOnline" : ("&7Offline : skin>" + Manager.getSkin(member, "value")));
      } 
      if (Bukkit.getPlayerExact(member) != null) {
        icon = BukkitUtils.putProfileOnSkull(Bukkit.getPlayerExact(member), BukkitUtils.deserializeItemStack(item));
      } else if (Manager.getSkin(member, "value") != null) {
        icon = BukkitUtils.deserializeItemStack(item + " : skin>" + Manager.getSkin(member, "value"));
      } else {
        icon = BukkitUtils.deserializeItemStack(item);
      } 
      items.add(icon);
      this.members.put(items.get(items.size() - 1), member);
    } 
    setItems(items);
    items.clear();
    list.clear();
    open();
    register((KPlugin)Core.getInstance());
  }
  
  public static double ST(Clan clan) {
    List<String> membersSize = new ArrayList<>();
    membersSize.add(clan.getLeader());
    membersSize.addAll(clan.getOfficers());
    membersSize.addAll(clan.getMembers());
    return membersSize.size();
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
        Clan clan = Clan.getClan(this.player);
        if (clan == null) {
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
            } else if (clan.getLeader().equals(this.player.getName())) {
              String member = this.members.get(item);
              if (member != null && 
                !member.equals(this.player.getName()))
                if (evt.getClick() == ClickType.SHIFT_LEFT) {
                  this.player.performCommand("clan expulsar " + member);
                  new MembersMenu(this.player, clan);
                } else if (evt.getClick() == ClickType.SHIFT_RIGHT) {
                  if (clan.getOfficers().contains(member)) {
                    this.player.performCommand("clan rebaixar " + member);
                  } else {
                    this.player.performCommand("clan promover " + member);
                  } 
                  new MembersMenu(this.player, clan);
                } else if (evt.getClick() == ClickType.DOUBLE_CLICK) {
                  if (clan.getOfficers().contains(member)) {
                    this.player.performCommand("clan transferir " + member);
                  } else {
                    this.player.sendMessage("§cEste jogador deve ser oficial primeiro.");
                  } 
                  new MembersMenu(this.player, clan);
                }  
            } else if (clan.getOfficers().contains(this.player.getName())) {
              String member = this.members.get(item);
              if (member != null && !member.equals(this.player.getName()) && !clan.getOfficers().contains(member) && 
                evt.getClick() == ClickType.SHIFT_LEFT) {
                this.player.performCommand("clan expulsar " + member);
                new MembersMenu(this.player, clan);
              } 
            }  
        } 
      } 
    } 
  }
  
  public void cancel() {
    HandlerList.unregisterAll((Listener)this);
    this.members.clear();
    this.members = null;
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
}
