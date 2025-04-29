package mc.twilight.skywars.cosmetics.types;

import mc.twilight.core.cash.CashManager;
import mc.twilight.core.libraries.holograms.HologramLibrary;
import mc.twilight.core.libraries.holograms.api.Hologram;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.plugin.logger.KLogger;
import mc.twilight.skywars.Language;
import mc.twilight.skywars.Main;
import mc.twilight.skywars.container.SelectedContainer;
import mc.twilight.skywars.cosmetics.Cosmetic;
import mc.twilight.skywars.cosmetics.CosmeticType;
import mc.twilight.skywars.game.AbstractSkyWars;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumRarity;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class DeathHologram extends Cosmetic {

  public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger()).getModule("DEATH_HOLOGRAM");

  private final String name;
  private final String icon;
  private final List<String> messages;

  public DeathHologram(long id, EnumRarity rarity, double coins, long cash, String permission, String name, String icon, List<String> messages) {
    super(id, CosmeticType.DEATH_HOLOGRAM, coins, permission);
    this.name = name;
    this.icon = icon;
    this.messages = messages;
    this.rarity = rarity;
    this.cash = cash;
  }

  public static void setupDeathHolograms() {
    KConfig config = Main.getInstance().getConfig("loja/animações", "deathholograms");

    for (String key : config.getKeys(false)) {
      long id = config.getInt(key + ".id");
      double coins = config.getDouble(key + ".coins");
      if (!config.contains(key + ".cash")) {
        config.set(key + ".cash", getAbsentProperty("deathholograms", key + ".cash"));
      }
      long cash = config.getInt(key + ".cash", 0);
      String permission = config.getString(key + ".permission");
      String name = config.getString(key + ".name");
      String icon = config.getString(key + ".icon");
      if (!config.contains(key + ".rarity")) {
        config.set(key + ".rarity", getAbsentProperty("deathholograms", key + ".rarity"));
      }
      List<String> sound = config.getStringList(key + ".holograms");

      new DeathHologram(id, EnumRarity.fromName(config.getString(key + ".rarity")), coins, cash, permission, name, icon, sound);
    }
  }

  @Override
  public String getName() {
    return this.name;
  }

  public void createHologram(Player player, Location location, AbstractSkyWars game) {
    Hologram hologram = HologramLibrary.createHologram(location, false);
    hologram.spawn();
    for (int index = messages.size(); index > 0; index--) {
      hologram.withLine(messages.get(index - 1)
          .replace("{colored}", Role.getColored(player.getName())));
    }
    new BukkitRunnable() {

      @Override
      public void run() {
        if (game.getTimer() <= 5) {
          HologramLibrary.removeHologram(hologram);
          cancel();
        }
      }
    }.runTaskTimerAsynchronously(Main.getInstance(), 0, 1);
  }

  // Sword art Online
  @Override
  public ItemStack getIcon(Profile profile) {
    double coins = profile.getCoins("zCoreSkyWars");
    long cash = profile.getStats("zCoreProfile", "cash");
    boolean has = this.has(profile);
    boolean canBuy = this.canBuy(profile.getPlayer());
    boolean isSelected = this.isSelected(profile);
    if (isSelected && !canBuy) {
      isSelected = false;
      profile.getAbstractContainer("zCoreSkyWars", "selected", SelectedContainer.class).setSelected(getType(), 0);
    }

    Role role = Role.getRoleByPermission(this.getPermission());
    String color = has ?
        (isSelected ? Language.cosmetics$color$selected : Language.cosmetics$color$unlocked) :
        (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) && canBuy ? Language.cosmetics$color$canbuy : Language.cosmetics$color$locked;
    String desc = (has && canBuy ?
        Language.cosmetics$deathhologram$icon$has_desc$start.replace("{has_desc_status}", isSelected ? Language.cosmetics$icon$has_desc$selected : Language.cosmetics$icon$has_desc$select) :
        canBuy ?
            Language.cosmetics$deathhologram$icon$buy_desc$start
                .replace("{buy_desc_status}", (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) ? Language.cosmetics$icon$buy_desc$click_to_buy : Language.cosmetics$icon$buy_desc$enough) :
            Language.cosmetics$deathhologram$icon$perm_desc$start
                .replace("{perm_desc_status}", (role == null ? Language.cosmetics$icon$perm_desc$common : Language.cosmetics$icon$perm_desc$role.replace("{role}", role.getName()))))
        .replace("{name}", this.name).replace("{rarity}", this.getRarity().getName()).replace("{coins}", StringUtils.formatNumber(this.getCoins())).replace("{cash}", StringUtils.formatNumber(this.getCash()));
    ItemStack item = BukkitUtils.deserializeItemStack(this.icon + " : nome>" + color + this.name + " : desc>" + desc);
    if (isSelected) {
      BukkitUtils.putGlowEnchantment(item);
    }

    return item;
  }
}
