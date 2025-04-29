package mc.twilight.skywars.cosmetics.types;

import mc.twilight.core.cash.CashManager;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.skywars.Language;
import mc.twilight.skywars.Main;
import mc.twilight.skywars.container.SelectedContainer;
import mc.twilight.skywars.cosmetics.Cosmetic;
import mc.twilight.skywars.cosmetics.CosmeticType;
import mc.twilight.skywars.cosmetics.object.AbstractExecutor;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumRarity;
import mc.twilight.skywars.cosmetics.types.winanimations.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public abstract class WinAnimation extends Cosmetic {

  private static final KConfig CONFIG = Main.getInstance().getConfig("loja/cosméticos", "winanimations");
  private final String name;
  private final String icon;

  public WinAnimation(long id, String key, double coins, String permission, String name, String icon) {
    super(id, CosmeticType.WIN_ANIMATION, coins, permission);
    this.name = name;
    if (id == 11 && icon.contains("dono>{player}")) {
      this.icon = icon.replace("dono>{player}", "skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGEyODRkOTc4MDViZTUzMWNhZGYwNjI3YzVjMjllOTAzNWUxNzEyMTU4MWRjYWJjZjk1MTBmZmQ5ZDQ2MDdmZiJ9fX0=");
    } else {
      this.icon = icon;
    }
    if (id != 0) {
      this.rarity = this.getRarity(key);
      this.cash = this.getCash(key);
    } else {
      this.rarity = EnumRarity.COMUM;
    }
  }

  public static void setupAnimations() {
    checkIfAbsent("fireworks");
    checkIfAbsent("ender_dragon");
    checkIfAbsent("cowboy");
    checkIfAbsent("thor");
    checkIfAbsent("cart");
    checkIfAbsent("rainbow");
    checkIfAbsent("victoryheat");
    checkIfAbsent("you");
    checkIfAbsent("anvil");
    checkIfAbsent("cake");
    checkIfAbsent("wither");
    checkIfAbsent("zombie");
    checkIfAbsent("tnt");

    new Fireworks(CONFIG.getSection("fireworks"));
    new EnderDragon(CONFIG.getSection("ender_dragon"));
    new Cowboy(CONFIG.getSection("cowboy"));
    new Thor(CONFIG.getSection("thor"));
    new Cart(CONFIG.getSection("cart"));
    new ColoredSheep(CONFIG.getSection("rainbow"));
    new You(CONFIG.getSection("you"));
    new Anvil(CONFIG.getSection("anvil"));
    new VictoryHeat(CONFIG.getSection("victoryheat"));
    new Cake(CONFIG.getSection("cake"));
    new Wither(CONFIG.getSection("wither"));
    new Zombie(CONFIG.getSection("zombie"));
    new Tnt(CONFIG.getSection("tnt"));
  }

  private static void checkIfAbsent(String key) {
    if (CONFIG.contains(key)) {
      return;
    }

    FileConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(Main.getInstance().getResource("winanimations.yml"), StandardCharsets.UTF_8));
    for (String dataKey : config.getConfigurationSection(key).getKeys(false)) {
      CONFIG.set(key + "." + dataKey, config.get(key + "." + dataKey));
    }
  }

  protected long getCash(String key) {
    if (!CONFIG.contains(key + ".cash")) {
      CONFIG.set(key + ".cash", getAbsentProperty("winanimations", key + ".cash"));
    }

    return CONFIG.getInt(key + ".cash");
  }

  protected EnumRarity getRarity(String key) {
    if (!CONFIG.contains(key + ".rarity")) {
      CONFIG.set(key + ".rarity", getAbsentProperty("winanimations", key + ".rarity"));
    }

    return EnumRarity.fromName(CONFIG.getString(key + ".rarity"));
  }

  public abstract AbstractExecutor execute(Player player);

  @Override
  public String getName() {
    return this.name;
  }

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
    String color = has ? (isSelected ? Language.cosmetics$color$selected : Language.cosmetics$color$unlocked)
        : (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) && canBuy ? Language.cosmetics$color$canbuy : Language.cosmetics$color$locked;
    String desc = (has && canBuy ?
        Language.cosmetics$win_animation$icon$has_desc$start.replace("{has_desc_status}", isSelected ? Language.cosmetics$icon$has_desc$selected : Language.cosmetics$icon$has_desc$select) :
        canBuy ?
            Language.cosmetics$win_animation$icon$buy_desc$start
                .replace("{buy_desc_status}", (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) ? Language.cosmetics$icon$buy_desc$click_to_buy : Language.cosmetics$icon$buy_desc$enough) :
            Language.cosmetics$win_animation$icon$perm_desc$start
                .replace("{perm_desc_status}", (role == null ? Language.cosmetics$icon$perm_desc$common : Language.cosmetics$icon$perm_desc$role.replace("{role}", role.getName()))))
        .replace("{name}", this.name).replace("{rarity}", this.getRarity().getName()).replace("{coins}", StringUtils.formatNumber(this.getCoins())).replace("{cash}", StringUtils.formatNumber(this.getCash()));
    ItemStack item = BukkitUtils.deserializeItemStack(this.icon.replace("{player}", profile.getName()) + desc + " : nome>" + (color + this.name));
    if (isSelected) {
      BukkitUtils.putGlowEnchantment(item);
    }

    return item;
  }
}
