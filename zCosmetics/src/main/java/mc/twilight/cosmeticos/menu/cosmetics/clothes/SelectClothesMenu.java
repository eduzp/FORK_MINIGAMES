package mc.twilight.cosmeticos.menu.cosmetics.clothes;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.Cosmetic;
import mc.twilight.cosmeticos.cosmetics.types.ClothesCosmetic;
import mc.twilight.cosmeticos.hook.Users;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.cosmeticos.menu.cosmetics.ClothesMenu;
import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SelectClothesMenu extends PlayerMenu {
  
  private ClothesCosmetic cosmetic;
  
  public SelectClothesMenu(CUser user, ClothesCosmetic cosmetic) {
    super(user.getPlayer(), cosmetic.getName(), 6);
    this.cosmetic = cosmetic;
    
    for (int index = 0; index < 4; index++) {
      ItemStack clone = cosmetic.getPiece(index).clone();
      ItemMeta meta = clone.getItemMeta();
      List<String> loreList = new ArrayList<>();
      loreList.add("");
      if (user.isSelected(cosmetic, index + 1)) {
        loreList.add("§eClique para remover!");
      } else {
        loreList.add("§eClique para utilizar!");
      }
      meta.setLore(loreList);
      clone.setItemMeta(meta);
      this.setItem(4 + (9 * index), clone);
    }
    
    this.setItem(48, BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : nome>&cVoltar"));
    this.setItem(49,
        BukkitUtils.deserializeItemStack("CHEST : 1 : nome>&aEquipar : desc>&7Caso deseje utilizar toda a roupa\n&7de forma rápida você pode.\n \n&eClique para equipar tudo!"));
    
    this.register(Main.getInstance());
    this.open();
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(this.getInventory())) {
      evt.setCancelled(true);
      
      if (evt.getWhoClicked().equals(this.player)) {
        CUser user = Users.getByName(this.player.getName());
        if (user == null) {
          player.closeInventory();
          return;
        }
        
        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getInventory())) {
          if (evt.getSlot() == 4) {
            EnumSound.NOTE_PLING.play(player, 1.0F, 2.0F);
            if (user.isSelected(cosmetic, 1)) {
              user.selectCosmetic(Cosmetic.NONE_CLOTHES, 1);
              user.handleClothes();
            } else {
              user.selectCosmetic(cosmetic, 1);
            }
            new SelectClothesMenu(user, cosmetic);
          } else if (evt.getSlot() == 13) {
            EnumSound.NOTE_PLING.play(player, 1.0F, 2.0F);
            if (user.isSelected(cosmetic, 2)) {
              user.selectCosmetic(Cosmetic.NONE_CLOTHES, 2);
              user.handleClothes();
            } else {
              user.selectCosmetic(cosmetic, 2);
            }
            new SelectClothesMenu(user, cosmetic);
          } else if (evt.getSlot() == 22) {
            EnumSound.NOTE_PLING.play(player, 1.0F, 2.0F);
            if (user.isSelected(cosmetic, 3)) {
              user.selectCosmetic(Cosmetic.NONE_CLOTHES, 3);
              user.handleClothes();
            } else {
              user.selectCosmetic(cosmetic, 3);
            }
            new SelectClothesMenu(user, cosmetic);
          } else if (evt.getSlot() == 31) {
            EnumSound.NOTE_PLING.play(player, 1.0F, 2.0F);
            if (user.isSelected(cosmetic, 4)) {
              user.selectCosmetic(Cosmetic.NONE_CLOTHES, 4);
              user.handleClothes();
            } else {
              user.selectCosmetic(cosmetic, 4);
            }
            new SelectClothesMenu(user, cosmetic);
          } else if (evt.getSlot() == 48) {
            EnumSound.CLICK.play(player, 0.5F, 2.0F);
            new ClothesMenu(user);
          } else if (evt.getSlot() == 49) {
            EnumSound.NOTE_PLING.play(player, 1.0F, 2.0F);
            boolean update = false;
            if (!user.isSelected(cosmetic, 1)) {
              update = true;
              user.selectCosmetic(cosmetic, 1);
            }
            if (!user.isSelected(cosmetic, 2)) {
              update = true;
              user.selectCosmetic(cosmetic, 2);
            }
            if (!user.isSelected(cosmetic, 3)) {
              update = true;
              user.selectCosmetic(cosmetic, 3);
            }
            if (!user.isSelected(cosmetic, 4)) {
              update = true;
              user.selectCosmetic(cosmetic, 4);
            }
            if (update) {
              new SelectClothesMenu(user, cosmetic);
            }
          }
        }
      }
    }
  }
  
  public void cancel() {
    HandlerList.unregisterAll(this);
    this.cosmetic = null;
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
