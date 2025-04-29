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
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumRarity;
import mc.twilight.skywars.cosmetics.types.killeffects.*;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public abstract class KillEffect extends Cosmetic {

  private static final KConfig CONFIG = Main.getInstance().getConfig("loja/animações", "killeffects");
  private final String name;
  private final String icon;

  public KillEffect(long id, EnumRarity rarity, double coins, long cash, String permission, String name, String icon) {
    super(id, CosmeticType.KILL_EFFECT, coins, permission);
    this.name = name;
    this.icon = icon;
    this.rarity = rarity;
    this.cash = cash;
  }

  public static void setupEffects() {
    checkIfAbsent("blood_explosion");
    checkIfAbsent("firework");
    checkIfAbsent("humantorch");
    checkIfAbsent("headrocket");
    checkIfAbsent("finalsmash");

    new BloodExplosion(CONFIG.getSection("blood_explosion"));
    new HumanTorch(CONFIG.getSection("humantorch"));
    new Firework(CONFIG.getSection("firework"));
    new HeadRocket(CONFIG.getSection("headrocket"));
    new FinalSmash(CONFIG.getSection("finalsmash"));
  }

  private static void checkIfAbsent(String key) {
    if (CONFIG.contains(key)) {
      return;
    }

    FileConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(Main.getInstance().getResource("killeffects.yml"), StandardCharsets.UTF_8));
    for (String dataKey : config.getConfigurationSection(key).getKeys(false)) {
      CONFIG.set(key + "." + dataKey, config.get(key + "." + dataKey));
    }
  }

  public void execute(Location location) {
    this.execute(null, location);
  }

  public abstract void execute(Player viewer, Location location);

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
    String color = has ?
        (isSelected ? Language.cosmetics$color$selected : Language.cosmetics$color$unlocked) :
        (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) && canBuy ? Language.cosmetics$color$canbuy : Language.cosmetics$color$locked;
    String desc = (has && canBuy ?
        Language.cosmetics$kill_effect$icon$has_desc$start.replace("{has_desc_status}", isSelected ? Language.cosmetics$icon$has_desc$selected : Language.cosmetics$icon$has_desc$select) :
        canBuy ?
            Language.cosmetics$kill_effect$icon$buy_desc$start
                .replace("{buy_desc_status}", (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) ? Language.cosmetics$icon$buy_desc$click_to_buy : Language.cosmetics$icon$buy_desc$enough) :
            Language.cosmetics$kill_effect$icon$perm_desc$start
                .replace("{perm_desc_status}", (role == null ? Language.cosmetics$icon$perm_desc$common : Language.cosmetics$icon$perm_desc$role.replace("{role}", role.getName()))))
        .replace("{name}", this.name).replace("{rarity}", this.getRarity().getName()).replace("{coins}", StringUtils.formatNumber(this.getCoins())).replace("{cash}", StringUtils.formatNumber(this.getCash()));
    ItemStack item = BukkitUtils.deserializeItemStack(this.icon + desc + " : nome>" + (color + this.name));
    if (isSelected) {
      BukkitUtils.putGlowEnchantment(item);
    }

    return item;
  }
}
