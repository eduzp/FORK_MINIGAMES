package mc.twilight.cosmeticos.menu.cosmetics;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.Cosmetic;
import mc.twilight.cosmeticos.cosmetics.types.ClothesCosmetic;
import mc.twilight.cosmeticos.hook.Users;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.cosmeticos.menu.CosmeticsMenu;
import mc.twilight.cosmeticos.menu.cosmetics.clothes.SelectClothesMenu;
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

public class ClothesMenu extends PagedPlayerMenu {
  
  private Map<ItemStack, ClothesCosmetic> clothes;
  
  public ClothesMenu(CUser user) {
    super(user.getPlayer(), "Guarda-Roupa", 6);
    this.previousPage = 45;
    this.nextPage = 53;
    this.onlySlots(10, 11, 12, 13, 14, 15, 16,
        19, 20, 21, 22, 23, 24, 25,
        28, 29, 30, 31, 32, 33, 34);
    
    this.clothes = new HashMap<>();
    
    List<ItemStack> items = new ArrayList<>();
    for (ClothesCosmetic cosmetic : Cosmetic.listCosmetics(ClothesCosmetic.class)) {
      String color = user.hasCosmetic(cosmetic) ? "§a" : "§c";
      List<String> loreList = new ArrayList<>();
      
      if (user.hasCosmetic(cosmetic)) {
        boolean helmet = user.isSelected(cosmetic, 1);
        boolean chestplate = user.isSelected(cosmetic, 2);
        boolean leggings = user.isSelected(cosmetic, 3);
        boolean boots = user.isSelected(cosmetic, 4);
        if (helmet || chestplate || leggings || boots) {
          loreList.add("");
          loreList.add("§7Peças utilizadas:");
          if (helmet) {
            loreList.add(" §8• §fCapacete");
          }
          if (chestplate) {
            loreList.add(" §8• §fPeitoral");
          }
          if (leggings) {
            loreList.add(" §8• §fCalças");
          }
          if (boots) {
            loreList.add(" §8• §fBotas");
          }
        }
        loreList.add("");
        loreList.add("§eClique para visualizar!");
      } else {
        loreList.add("");
        loreList.add("§cVocê não possui essa roupa.");
      }
      
      ItemStack icon = cosmetic.getIcon(color, loreList);
      if (!user.hasCosmetic(cosmetic)) {
        icon.setType(Material.INK_SACK);
        icon.setDurability((short) 8);
      }
      items.add(icon);
      this.clothes.put(icon, cosmetic);
    }
    
    this.removeSlotsWith(BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : nome>&cVoltar"), 48);
    this.removeSlotsWith(BukkitUtils.deserializeItemStack("BARRIER : 1 : nome>&cRemover Roupa"), 49);
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
            boolean update = false;
            if (!user.isSelected(Cosmetic.NONE_CLOTHES, 1)) {
              update = true;
              user.selectCosmetic(Cosmetic.NONE_CLOTHES, 1);
            }
            if (!user.isSelected(Cosmetic.NONE_CLOTHES, 2)) {
              update = true;
              user.selectCosmetic(Cosmetic.NONE_CLOTHES, 2);
            }
            if (!user.isSelected(Cosmetic.NONE_CLOTHES, 3)) {
              update = true;
              user.selectCosmetic(Cosmetic.NONE_CLOTHES, 3);
            }
            if (!user.isSelected(Cosmetic.NONE_CLOTHES, 4)) {
              update = true;
              user.selectCosmetic(Cosmetic.NONE_CLOTHES, 4);
            }
            
            if (update) {
              user.handleClothes();
              new ClothesMenu(user);
            }
          } else {
            ClothesCosmetic cosmetic = clothes.get(evt.getCurrentItem());
            if (cosmetic != null) {
              if (user.hasCosmetic(cosmetic)) {
                EnumSound.NOTE_PLING.play(player, 1.0F, 2.0F);
                new SelectClothesMenu(user, cosmetic);
              }
            }
          }
        }
      }
    }
  }
  
  public void cancel() {
    HandlerList.unregisterAll(this);
    this.clothes.clear();
    this.clothes = null;
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
