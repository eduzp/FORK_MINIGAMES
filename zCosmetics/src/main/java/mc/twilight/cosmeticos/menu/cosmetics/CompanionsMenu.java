package mc.twilight.cosmeticos.menu.cosmetics;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.Cosmetic;
import mc.twilight.cosmeticos.cosmetics.types.CompanionCosmetic;
import mc.twilight.cosmeticos.hook.Users;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.cosmeticos.menu.CosmeticsMenu;
import mc.twilight.core.libraries.menu.PagedPlayerMenu;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static mc.twilight.cosmeticos.listeners.Listeners.COMPANION_NAME;

public class CompanionsMenu extends PagedPlayerMenu {
  
  private Map<ItemStack, CompanionCosmetic> companions;
  
  public CompanionsMenu(CUser user) {
    super(user.getPlayer(), "Companheiros", 6);
    this.previousPage = 45;
    this.nextPage = 53;
    this.onlySlots(10, 11, 12, 13, 14, 15, 16,
        19, 20, 21, 22, 23, 24, 25,
        28, 29, 30, 31, 32, 33, 34);
    
    this.companions = new HashMap<>();
    
    List<ItemStack> items = new ArrayList<>();
    for (CompanionCosmetic cosmetic : Cosmetic.listCosmetics(CompanionCosmetic.class)) {
      String color = user.hasCosmetic(cosmetic) ? "§a" : "§c";
      List<String> loreList = new ArrayList<>();
      
      if (user.isSelected(cosmetic)) {
        loreList.add("");
        loreList.add("§7Você pode alterar o");
        loreList.add("§7apelido do companheiro");
        loreList.add("§7utilizando o §6Clique Direito§7.");
        loreList.add("");
        loreList.add("§eClique para remover!");
      } else if (user.hasCosmetic(cosmetic)) {
        loreList.add("");
        loreList.add("§7Você pode alterar o");
        loreList.add("§7apelido do companheiro");
        loreList.add("§7utilizando o §6Clique Direito§7.");
        loreList.add("");
        loreList.add("§eClique para utilizar!");
      } else {
        loreList.add("");
        loreList.add("§cVocê não possui esse companheiro.");
      }
      
      ItemStack icon = cosmetic.getIcon(color, loreList);
      if (!user.hasCosmetic(cosmetic)) {
        icon.setType(Material.INK_SACK);
        icon.setDurability((short) 8);
      }
      items.add(icon);
      this.companions.put(icon, cosmetic);
    }
    
    this.removeSlotsWith(BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : nome>&cVoltar"), 48);
    this.removeSlotsWith(BukkitUtils.deserializeItemStack("BARRIER : 1 : nome>&cRemover Companheiro"), 49);
    this.setItems(items);
    
    this.register(Main.getInstance());
    this.open();
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(this.getCurrentInventory())) {
      evt.setCancelled(true);
      
      if (evt.getWhoClicked().equals(this.player)) {
        CUser user = Users.getByName(this.player.getName());
        if (user == null) {
          player.closeInventory();
          return;
        }
        
        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getCurrentInventory())) {
          if (evt.getSlot() == nextPage) {
            this.openNext();
          } else if (evt.getSlot() == previousPage) {
            this.openPrevious();
          } else if (evt.getSlot() == 48) {
            EnumSound.CLICK.play(player, 0.5F, 2.0F);
            new CosmeticsMenu(user);
          } else if (evt.getSlot() == 49) {
            EnumSound.NOTE_PLING.play(player, 1.0F, 2.0F);
            if (!user.isSelected(Cosmetic.NONE_COMPANION)) {
              user.selectCosmetic(Cosmetic.NONE_COMPANION);
              user.handleCompanion();
              new CompanionsMenu(user);
            }
          } else {
            CompanionCosmetic cosmetic = companions.get(evt.getCurrentItem());
            if (cosmetic != null) {
              if (user.hasCosmetic(cosmetic)) {
                EnumSound.NOTE_PLING.play(player, 1.0F, 2.0F);
                if (evt.getClick().name().contains("RIGHT")) {
                  COMPANION_NAME.put(player.getName(), cosmetic.getNameEnum());
                  
                  TextComponent changeName = new TextComponent(" \nDigite no chat o apelido que deseja dar a seu Companheiro!");
                  changeName.setColor(ChatColor.AQUA);
                  TextComponent startCancel = new TextComponent("\nCaso mude de ideia ");
                  startCancel.setColor(ChatColor.GRAY);
                  TextComponent clickCancel = new TextComponent("Clique §lAQUI");
                  clickCancel.setColor(ChatColor.GOLD);
                  clickCancel.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "zcosmeticos:cancelchangename"));
                  clickCancel.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Clique para cancelar a mudança de apelido.")));
                  TextComponent endCancel = new TextComponent(" para cancelar a mudança de apelido.\n ");
                  endCancel.setColor(ChatColor.GRAY);
                  
                  changeName.addExtra(startCancel);
                  changeName.addExtra(clickCancel);
                  changeName.addExtra(endCancel);
                  
                  player.closeInventory();
                  player.spigot().sendMessage(changeName);
                  return;
                }
                
                if (user.isSelected(cosmetic)) {
                  user.selectCosmetic(Cosmetic.NONE_COMPANION);
                  user.handleCompanion();
                } else {
                  user.selectCosmetic(cosmetic);
                }
                new CompanionsMenu(user);
              }
            }
          }
        }
      }
    }
  }
  
  public void cancel() {
    HandlerList.unregisterAll(this);
    this.companions.clear();
    this.companions = null;
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (evt.getPlayer().equals(this.player)) {
      this.cancel();
    }
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent evt) {
    if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getCurrentInventory())) {
      this.cancel();
    }
  }
}
