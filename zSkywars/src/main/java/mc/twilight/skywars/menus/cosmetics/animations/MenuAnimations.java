package mc.twilight.skywars.menus.cosmetics.animations;

import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.skywars.Main;
import mc.twilight.skywars.cosmetics.Cosmetic;
import mc.twilight.skywars.cosmetics.types.*;
import mc.twilight.skywars.menus.vendinha.MenuShop;
import mc.twilight.skywars.menus.cosmetics.MenuCosmetics;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MenuAnimations extends PlayerMenu {

  public MenuAnimations(Profile profile) {
    super(profile.getPlayer(), "Animações", 5);

    setupMenu(profile);
    this.register(Main.getInstance());
    this.open();
  }

  private void setupMenu(Profile profile) {
    setupCategory(profile, 20, KillEffect.class, "BONE", "Animações de Abate",
            "&7Deixa a sua marca quando abater\n&7os seus oponentes.");
    setupCategory(profile, 21, ProjectileEffect.class, "EGG", "Animações de Projétil",
            "&7Esbanje estilo nos seus projéteis\n&7com partículas maravilhosas.");
    setupCategory(profile, 22, FallEffect.class, "DIAMOND_BOOTS", "Animações de Queda",
            "&7Quando você levar dano de queda\n&7irá aparecer partículas.");
    setupCategory(profile, 23, TeleportEffect.class, "ENDER_PEARL", "Animações de Teleporte",
            "&7Quando você se teletransportar\n&7irá aparecer partículas.");
    setupCategory(profile, 24, DeathHologram.class, "ARMOR_STAND", "Hologramas de Morte",
            "&7Quando você morrer\n&7um holograma especial aparecerá no local.");

    this.setItem(40, BukkitUtils.deserializeItemStack(
            "INK_SACK:1 : 1 : nome>&cVoltar : desc>&7Para a loja."));
  }

  private <T extends Cosmetic> void setupCategory(Profile profile, int slot, Class<T> cosmeticClass,
                                                  String material, String name, String description) {
    this.setItem(slot, BukkitUtils.deserializeItemStack(
            material + " : 1 : nome>&a" + name +
                    " : desc>" + description +
                    "\n \n&eClique para comprar ou selecionar!"));
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (!evt.getInventory().equals(this.getInventory())) return;
    evt.setCancelled(true);

    if (!evt.getWhoClicked().equals(this.player)) return;

    Profile profile = Profile.getProfile(this.player.getName());
    if (profile == null) {
      this.player.closeInventory();
      return;
    }

    ItemStack item = evt.getCurrentItem();
    if (item == null || item.getType() == Material.AIR) return;

    EnumSound.CLICK.play(this.player, 0.5F, 2.0F);

    switch (evt.getSlot()) {
      case 40:
        new MenuShop(profile);
        break;
      case 20:
        new MenuCosmetics<>(profile, "Abate", KillEffect.class, () -> new MenuAnimations(profile));
        break;
      case 21:
        new MenuCosmetics<>(profile, "Projétil", ProjectileEffect.class, () -> new MenuAnimations(profile));
        break;
      case 22:
        new MenuCosmetics<>(profile, "Queda", FallEffect.class, () -> new MenuAnimations(profile));
        break;
      case 23:
        new MenuCosmetics<>(profile, "Teleporte", TeleportEffect.class, () -> new MenuAnimations(profile));
        break;
      case 24:
        new MenuCosmetics<>(profile, "Morte", DeathHologram.class, () -> new MenuAnimations(profile));
        break;
      default:
        break;
    }
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

  public void cancel() {
    HandlerList.unregisterAll(this);
  }
}
