package mc.twilight.skywars.menus.vendinha;

import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.skywars.Main;
import mc.twilight.skywars.cosmetics.types.Perk;
import mc.twilight.skywars.cosmetics.types.kits.NormalKit;
import mc.twilight.skywars.menus.cosmetics.animations.MenuAnimations;
import mc.twilight.skywars.menus.cosmetics.kits.MenuKits;
import mc.twilight.skywars.menus.cosmetics.perks.MenuPerks;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class MenuShop extends PlayerMenu {

  public MenuShop(Profile profile) {
    super(profile.getPlayer(), "Vendinha - Sky Wars", 5);

    this.setItem(19, BukkitUtils.deserializeItemStack(
            "EYE_OF_ENDER : 1 : esconder>tudo : nome>&aKits : desc>&7Um verdadeiro guerreiro sempre estará\n&7preparado para o combate.\n\n&eClique para comprar ou evoluir!"));

    this.setItem(21, BukkitUtils.deserializeItemStack(
            "BREWING_STAND_ITEM : 1 : nome>&aHabilidades : desc>&7Tenha vantagens únicas para\n&7auxiliar você nas partidas.\n\n&eClique para comprar ou evoluir!"));

    this.setItem(23, BukkitUtils.deserializeItemStack(
            "CHEST : 1 : nome>&aCosméticos : desc>&7Personalize sua experiência no Sky Wars\n&7com efeitos, visuais e muito mais.\n\n&eClique para comprar ou selecionar!"));

    this.setItem(25, BukkitUtils.deserializeItemStack(
            "WEB : 1 : nome>&aAnimações : desc>&7Adicione efeitos especiais às suas ações\n&7para se destacar nas partidas.\n\n&eClique para comprar ou selecionar!"));

    this.setItem(40, BukkitUtils.deserializeItemStack(
            "INK_SACK:1 : 1 : nome>&cSair : desc>&7Fechar o menu."));

    this.register(Main.getInstance());
    this.open();
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

    if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getInventory())) {
      ItemStack item = evt.getCurrentItem();
      if (item == null || item.getType() == Material.AIR) return;

      switch (evt.getSlot()) {
        case 19:
          EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
          new MenuKits<>(profile, "Solo e Dupla", NormalKit.class);
          break;
        case 21:
          EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
          new MenuPerks<>(profile, "Solo e Dupla", Perk.class);
          break;
        case 23:
          EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
          new MenuCosmeticos(profile);
          break;
        case 25:
          EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
          new MenuAnimations(profile);
          break;
        case 40:
          EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
          this.player.closeInventory();
          break;
      }
    }
  }

  public void cancel() {
    HandlerList.unregisterAll(this);
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
