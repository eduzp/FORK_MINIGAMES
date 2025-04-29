package mc.twilight.skywars.menus.cosmetics;

import mc.twilight.core.Core;
import mc.twilight.core.cash.CashManager;
import mc.twilight.core.libraries.menu.PagedPlayerMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.skywars.container.SelectedContainer;
import mc.twilight.skywars.cosmetics.Cosmetic;
import mc.twilight.skywars.menus.vendinha.MenuCosmeticos;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MenuCosmetics<T extends Cosmetic> extends PagedPlayerMenu {

  private Class<T> cosmeticClass;
  private Map<ItemStack, T> cosmetics = new HashMap<>();
  private Runnable backCommand;

  public MenuCosmetics(Profile profile, String name, Class<T> cosmeticClass) {
    this(profile, name, cosmeticClass, () -> new MenuCosmeticos(profile));
  }

  public MenuCosmetics(Profile profile, String name, Class<T> cosmeticClass, Runnable backCommand) {
    super(profile.getPlayer(), "Sky Wars - " + name, (Cosmetic.listByType(cosmeticClass).size() / 7) + 4);
    this.cosmeticClass = cosmeticClass;
    this.backCommand = backCommand;
    this.previousPage = (this.rows * 9) - 9;
    this.nextPage = (this.rows * 9) - 1;
    this.onlySlots(
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34
    );

    this.removeSlotsWith(
            BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : nome>&cVoltar : desc>&7Voltar para o menu anterior."),
            (this.rows * 9) - 5
    );

    this.loadCosmetics(profile);
    this.register(Core.getInstance());
    this.open();
  }

  private void loadCosmetics(Profile profile) {
    List<ItemStack> items = new ArrayList<>();
    List<T> cosmeticsList = Cosmetic.listByType(this.cosmeticClass);

    this.cosmetics.clear();

    for (T cosmetic : cosmeticsList) {
      ItemStack icon = cosmetic.getIcon(profile);

      ItemStack displayItem;
      if (!cosmetic.has(profile)) {
        displayItem = icon.clone();
        displayItem.setType(Material.INK_SACK);
        displayItem.setDurability((short) 8);
      } else {
        displayItem = icon;
      }

      items.add(displayItem);
      this.cosmetics.put(displayItem, cosmetic);
    }

    this.setItems(items);
  }

  private void updateMenu(Profile profile) {
    this.loadCosmetics(profile);
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (!evt.getInventory().equals(this.getCurrentInventory())) return;
    evt.setCancelled(true);

    if (!evt.getWhoClicked().equals(this.player)) return;

    Profile profile = Profile.getProfile(this.player.getName());
    if (profile == null) {
      this.player.closeInventory();
      return;
    }

    ItemStack item = evt.getCurrentItem();
    if (item == null || item.getType() == Material.AIR) return;

    int slot = evt.getSlot();
    if (slot == this.previousPage) {
      EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
      this.openPrevious();
      return;
    } else if (slot == this.nextPage) {
      EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
      this.openNext();
      return;
    } else if (slot == (this.rows * 9) - 5) {
      EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
      if (this.backCommand != null) this.backCommand.run();
      return;
    }

    T cosmetic = this.cosmetics.get(item);
    if (cosmetic == null) return;

    if (evt.isRightClick()) {
      // Aqui você pode implementar preview dos cosméticos
      return;
    }

    if (!cosmetic.has(profile)) {
      if (!cosmetic.canBuy(this.player) || (profile.getCoins("zCoreSkyWars") < cosmetic.getCoins()
              && (CashManager.CASH && profile.getStats("zCoreProfile", "cash") < cosmetic.getCash()))) {
        EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
        return;
      }

      EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
      if (!CashManager.CASH || cosmetic.getCash() == 0) {
        new MenuBuyCosmetic<>(profile, this.name.replace("Sky Wars - ", ""), cosmetic, this.cosmeticClass);
      } else {
        new MenuBuyCashCosmetic<>(profile, this.name.replace("Sky Wars - ", ""), cosmetic, this.cosmeticClass);
      }
      return;
    }

    if (!cosmetic.canBuy(this.player)) {
      EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
      this.player.sendMessage("§cVocê não possui permissão suficiente para continuar.");
      return;
    }

    EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);

    if (cosmetic.isSelected(profile)) {
      profile.getAbstractContainer("zCoreSkyWars", "selected", SelectedContainer.class)
              .setSelected(cosmetic.getType(), 0);
    } else {
      profile.getAbstractContainer("zCoreSkyWars", "selected", SelectedContainer.class)
              .setSelected(cosmetic);
    }

    this.updateMenu(profile); // Atualiza sem fechar
  }

  public void cancel() {
    HandlerList.unregisterAll(this);
    this.cosmeticClass = null;
    if (this.cosmetics != null) {
      this.cosmetics.clear();
      this.cosmetics = null;
    }
    this.backCommand = null;
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
