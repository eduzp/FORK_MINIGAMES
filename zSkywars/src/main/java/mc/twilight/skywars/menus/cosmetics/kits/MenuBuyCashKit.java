package mc.twilight.skywars.menus.cosmetics.kits;

import mc.twilight.core.cash.CashException;
import mc.twilight.core.cash.CashManager;
import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.skywars.Main;
import mc.twilight.skywars.cosmetics.types.Kit;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class MenuBuyCashKit<T extends Kit> extends PlayerMenu {

  private String name;
  private T cosmetic;

  public MenuBuyCashKit(Profile profile, String name, T cosmetic) {
    super(profile.getPlayer(), "Confirmar compra", 4);
    this.name = name;
    this.cosmetic = cosmetic;

    // Head skin Coins
    ItemStack coinsItem = BukkitUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDFiNWZkZWZmZmZmYTcwODM4MWU3MGYzNTAzYTI3NTc3MmI0NTI5NmNmOWYxNjI1YTg3ZWRjNmI2MjU0OWVmNiJ9fX0=");
    BukkitUtils.setItemNameAndLore(coinsItem, "§eComprar com Coins", "§7Comprar \"" + cosmetic.getName() + "\"", "§7por §6" + StringUtils.formatNumber(cosmetic.getCoins()) + " Coins§7.");

    // Head skin Tokens
    ItemStack tokensItem = BukkitUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2I1ZmFmNGNkODcxMzhjODcxY2M2YTg2NzU4MTdhODk5ODVhM2NiODk3MjFhNGM3NjJmZTY2NmZmNjE4MWMyNCJ9fX0=");
    BukkitUtils.setItemNameAndLore(tokensItem, "§bComprar com Tokens", "§7Comprar \"" + cosmetic.getName() + "\"", "§7por §b" + StringUtils.formatNumber(cosmetic.getCash()) + " Tokens§7.");

    // Barrier cancelar
    ItemStack cancelItem = BukkitUtils.deserializeItemStack("BARRIER : 1 : nome>&cCancelar : desc>&7Voltar para Kits " + this.name + ".");

    // Setando nos slots
    this.setItem(11, coinsItem);
    this.setItem(15, tokensItem);
    this.setItem(31, cancelItem);

    this.register(Main.getInstance());
    this.open();
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(this.getInventory())) {
      evt.setCancelled(true);

      if (evt.getWhoClicked().equals(this.player)) {
        Profile profile = Profile.getProfile(this.player.getName());
        if (profile == null) {
          this.player.closeInventory();
          return;
        }

        ItemStack item = evt.getCurrentItem();
        if (item != null && item.getType() != Material.AIR) {
          int slot = evt.getSlot();
          if (slot == 11) { // Comprar com Coins
            if (profile.getCoins("zCoreSkyWars") < this.cosmetic.getCoins()) {
              EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
              this.player.sendMessage("§cVocê não possui Coins suficientes para completar esta transação.");
              return;
            }

            EnumSound.LEVEL_UP.play(this.player, 0.5F, 2.0F);
            profile.removeCoins("zCoreSkyWars", this.cosmetic.getCoins());
            this.cosmetic.give(profile);
            this.player.sendMessage("§aVocê comprou '" + this.cosmetic.getName() + "'");
            new MenuKits<>(profile, this.name, this.cosmetic.getClass());

          } else if (slot == 15) { // Comprar com Tokens
            if (profile.getStats("zCoreProfile", "cash") < this.cosmetic.getCash()) {
              EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
              this.player.sendMessage("§cVocê não possui Tokens suficientes para completar esta transação.");
              return;
            }

            try {
              CashManager.removeCash(profile, this.cosmetic.getCash());
              this.cosmetic.give(profile);
              this.player.sendMessage("§aVocê comprou '" + this.cosmetic.getName() + "'");
              EnumSound.LEVEL_UP.play(this.player, 0.5F, 2.0F);
            } catch (CashException ignore) {
            }
            new MenuKits<>(profile, this.name, this.cosmetic.getClass());

          } else if (slot == 31) { // Cancelar
            EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
            new MenuKits<>(profile, this.name, this.cosmetic.getClass());
          }
        }
      }
    }
  }

  public void cancel() {
    HandlerList.unregisterAll(this);
    this.name = null;
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
