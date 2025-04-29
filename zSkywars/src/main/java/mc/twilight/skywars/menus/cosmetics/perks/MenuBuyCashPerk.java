package mc.twilight.skywars.menus.cosmetics.perks;

import mc.twilight.core.Core;
import mc.twilight.core.cash.CashException;
import mc.twilight.core.cash.CashManager;
import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.skywars.cosmetics.types.Perk;
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

public class MenuBuyCashPerk<T extends Perk> extends PlayerMenu {

  private String name;
  private T cosmetic;
  private Class<T> cosmeticClass;

  public MenuBuyCashPerk(Profile profile, String name, T cosmetic, Class<T> cosmeticClass) {
    super(profile.getPlayer(), "Confirmar compra", 4);
    this.name = name;
    this.cosmetic = cosmetic;
    this.cosmeticClass = cosmeticClass;

    // Head Coins
    ItemStack coinsItem = BukkitUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDFiNWZkZWZmZmZmYTcwODM4MWU3MGYzNTAzYTI3NTc3MmI0NTI5NmNmOWYxNjI1YTg3ZWRjNmI2MjU0OWVmNiJ9fX0=");
    BukkitUtils.setItemNameAndLore(
            coinsItem,
            "§eComprar com Coins",
            "§7Comprar \"" + cosmetic.getName() + "\"",
            "§7por §6" + StringUtils.formatNumber(cosmetic.getCoins()) + " Coins§7."
    );

    // Head Tokens (Cash)
    ItemStack tokensItem = BukkitUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2I1ZmFmNGNkODcxMzhjODcxY2M2YTg2NzU4MTdhODk5ODVhM2NiODk3MjFhNGM3NjJmZTY2NmZmNjE4MWMyNCJ9fX0=");
    BukkitUtils.setItemNameAndLore(
            tokensItem,
            "§bComprar com Tokens",
            "§7Comprar \"" + cosmetic.getName() + "\"",
            "§7por §b" + StringUtils.formatNumber(cosmetic.getCash()) + " Tokens§7."
    );

    // Cancelar - Barrier
    ItemStack cancelItem = BukkitUtils.deserializeItemStack(
            "BARRIER : 1 : nome>&cCancelar : desc>&7Voltar para Habilidades " + this.name + "."
    );

    // Setar os itens nos slots corretos
    this.setItem(11, coinsItem);
    this.setItem(15, tokensItem);
    this.setItem(31, cancelItem);

    this.register(Core.getInstance());
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

    ItemStack item = evt.getCurrentItem();
    if (item == null || item.getType() == Material.AIR) return;

    int slot = evt.getSlot();
    if (slot == 11) { // Comprar com Coins
      if (profile.getCoins("zCoreSkyWars") < this.cosmetic.getCoins()) {
        EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
        this.player.sendMessage("§cVocê não possui Coins suficientes para completar esta transação.");
        return;
      }

      profile.removeCoins("zCoreSkyWars", this.cosmetic.getCoins());
      this.cosmetic.give(profile);
      this.player.sendMessage("§aVocê comprou '" + this.cosmetic.getName() + "'");
      EnumSound.LEVEL_UP.play(this.player, 0.5F, 2.0F);
      new MenuPerks<>(profile, this.name, this.cosmeticClass);

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
      new MenuPerks<>(profile, this.name, this.cosmeticClass);

    } else if (slot == 31) { // Cancelar
      EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
      new MenuPerks<>(profile, this.name, this.cosmeticClass);
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
    this.name = null;
    this.cosmetic = null;
    this.cosmeticClass = null;
  }
}
