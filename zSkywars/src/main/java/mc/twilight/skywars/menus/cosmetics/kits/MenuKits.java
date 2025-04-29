package mc.twilight.skywars.menus.cosmetics.kits;

import mc.twilight.core.Core;
import mc.twilight.core.cash.CashManager;
import mc.twilight.core.libraries.menu.PagedPlayerMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.skywars.cosmetics.Cosmetic;
import mc.twilight.skywars.cosmetics.types.Kit;
import mc.twilight.skywars.menus.vendinha.MenuShop;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuKits<T extends Kit> extends PagedPlayerMenu {

  private String mode;
  private Class<T> cosmeticClass;
  private Map<ItemStack, T> cosmetics = new HashMap<>();

  public MenuKits(Profile profile, String name, Class<T> cosmeticClass) {
    super(profile.getPlayer(), "Kits (" + name + ")", (Cosmetic.listByType(cosmeticClass).size() / 7) + 4);
    this.mode = name;
    this.cosmeticClass = cosmeticClass;
    this.previousPage = (this.rows * 9) - 9;
    this.nextPage = (this.rows * 9) - 1;
    this.onlySlots(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34);

    this.removeSlotsWith(BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : nome>&cVoltar : desc>&7Para a Loja."), (this.rows * 9) - 5);

    List<ItemStack> items = new ArrayList<>();
    List<T> cosmetics = Cosmetic.listByType(cosmeticClass);
    for (T cosmetic : cosmetics) {
      ItemStack icon = cosmetic.getIcon(profile);

      if (!cosmetic.has(profile)) {
        // Se não tiver o kit, altera só o tipo do item para Gray Dye, mas mantém nome/descrição
        ItemMeta meta = icon.getItemMeta();
        icon = new ItemStack(Material.INK_SACK, 1, (short) 8); // Gray Dye (data value 8)
        if (meta != null) {
          icon.setItemMeta(meta);
        }
      }

      items.add(icon);
      this.cosmetics.put(icon, cosmetic);
    }

    this.setItems(items);
    cosmetics.clear();
    items.clear();

    this.register(Core.getInstance());
    this.open();
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(this.getCurrentInventory())) {
      evt.setCancelled(true);

      if (evt.getWhoClicked().equals(this.player)) {
        Profile profile = Profile.getProfile(this.player.getName());
        if (profile == null) {
          this.player.closeInventory();
          return;
        }

        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getCurrentInventory())) {
          ItemStack item = evt.getCurrentItem();

          if (item != null && item.getType() != Material.AIR) {
            if (evt.getSlot() == this.previousPage) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              this.openPrevious();
            } else if (evt.getSlot() == this.nextPage) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              this.openNext();
            } else if (evt.getSlot() == (this.rows * 9) - 5) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuShop(profile);
            } else {
              T cosmetic = this.cosmetics.get(item);
              if (cosmetic != null) {
                if (!cosmetic.has(profile)) {
                  if (!cosmetic.canBuy(this.player) || (profile.getCoins("zCoreSkyWars") < cosmetic.getCoins() && (CashManager.CASH && profile
                          .getStats("zCoreProfile", "cash") < cosmetic.getCash()))) {
                    EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                    return;
                  }

                  EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                  if (!CashManager.CASH && cosmetic.getCash() == 0) {
                    new MenuBuyKit<>(profile, this.mode, cosmetic);
                  } else {
                    new MenuBuyCashKit<>(profile, this.mode, cosmetic);
                  }
                  return;
                }

                if (!cosmetic.canBuy(this.player)) {
                  EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                  this.player.sendMessage("§cVocê não possui permissão suficiente para continuar.");
                  return;
                }

                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                new MenuKitUpgrade<>(profile, this.mode, cosmetic);
              }
            }
          }
        }
      }
    }
  }

  public void cancel() {
    HandlerList.unregisterAll(this);
    this.mode = null;
    this.cosmeticClass = null;
    this.cosmetics.clear();
    this.cosmetics = null;
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
