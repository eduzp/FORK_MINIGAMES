package mc.twilight.cosmeticos.menu.cosmetics;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.Cosmetic;
import mc.twilight.cosmeticos.cosmetics.types.AnimatedHatCosmetic;
import mc.twilight.cosmeticos.hook.Users;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.cosmeticos.menu.CosmeticsMenu;
import mc.twilight.core.libraries.menu.PagedPlayerMenu;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;
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

public class AnimatedHatsMenu extends PagedPlayerMenu {
  
  private Map<ItemStack, AnimatedHatCosmetic> animatedHats;
  
  public AnimatedHatsMenu(CUser user) {
    super(user.getPlayer(), "Chapéus animados", 6);
    this.previousPage = 45;
    this.nextPage = 53;
    this.onlySlots(10, 11, 12, 13, 14, 15, 16,
        19, 20, 21, 22, 23, 24, 25,
        28, 29, 30, 31, 32, 33, 34);
    
    this.animatedHats = new HashMap<>();
    
    List<ItemStack> items = new ArrayList<>();
    for (AnimatedHatCosmetic cosmetic : Cosmetic.listCosmetics(AnimatedHatCosmetic.class)) {
      String color = user.hasCosmetic(cosmetic) ? "§a" : "§c";
      List<String> loreList = new ArrayList<>();
      
      if (user.isSelected(cosmetic)) {
        loreList.add("");
        loreList.add("§eClique para remover!");
      } else if (user.hasCosmetic(cosmetic)) {
        loreList.add("");
        loreList.add("§eClique para utilizar!");
      } else {
        loreList.add("");
        loreList.add("§cVocê não possui esse chapéu animado.");
      }
      
      ItemStack icon = cosmetic.getIcon(color, loreList);
      if (!user.hasCosmetic(cosmetic)) {
        icon.setType(Material.INK_SACK);
        icon.setDurability((short) 8);
      }
      items.add(icon);
      this.animatedHats.put(icon, cosmetic);
    }
    
    this.removeSlotsWith(BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : nome>&cVoltar"), 48);
    this.removeSlotsWith(BukkitUtils.deserializeItemStack("BARRIER : 1 : nome>&cRemover Chapéu Animado"), 49);
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
            if (!user.isSelected(Cosmetic.NONE_ANIMATED_HAT)) {
              user.selectCosmetic(Cosmetic.NONE_ANIMATED_HAT);
              user.handleAnimatedHat();
              new AnimatedHatsMenu(user);
            }
          } else {
            AnimatedHatCosmetic cosmetic = animatedHats.get(evt.getCurrentItem());
            if (cosmetic != null) {
              if (user.hasCosmetic(cosmetic)) {
                EnumSound.NOTE_PLING.play(player, 1.0F, 2.0F);
                if (user.isSelected(cosmetic)) {
                  user.selectCosmetic(Cosmetic.NONE_ANIMATED_HAT);
                  user.handleAnimatedHat();
                } else {
                  user.selectCosmetic(cosmetic);
                }
                new AnimatedHatsMenu(user);
              }
            }
          }
        }
      }
    }
  }
  
  public void cancel() {
    HandlerList.unregisterAll(this);
    this.animatedHats.clear();
    this.animatedHats = null;
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
