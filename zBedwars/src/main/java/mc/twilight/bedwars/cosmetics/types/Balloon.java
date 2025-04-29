package mc.twilight.bedwars.cosmetics.types;

import mc.twilight.bedwars.Language;
import mc.twilight.bedwars.Main;
import mc.twilight.bedwars.hook.container.SelectedContainer;
import mc.twilight.bedwars.cosmetics.Cosmetic;
import mc.twilight.bedwars.cosmetics.CosmeticType;
import mc.twilight.core.cash.CashManager;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.plugin.logger.KLogger;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumRarity;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Balloon extends Cosmetic {
  
  protected String name;
  protected String icon;
  protected List<String> textures;
  
  public Balloon(long id, EnumRarity rarity, double coins, long cash, String permission, String name, String icon, List<String> textures) {
    super(id, CosmeticType.BALLOON, coins, permission);
    this.name = name;
    this.icon = icon;
    this.textures = textures;
    this.rarity = rarity;
    this.cash = cash;
  }
  
  @Override
  public String getName() {
    return this.name;
  }
  
  public List<String> getTextures() {
    return this.textures;
  }
  
  @Override
  public ItemStack getIcon(Profile profile) {
    double coins = profile.getCoins("zCoreBedWars");
    long cash = profile.getStats("zCoreProfile", "cash");
    boolean has = this.has(profile);
    boolean canBuy = this.canBuy(profile.getPlayer());
    boolean isSelected = this.isSelected(profile);
    if (isSelected && !canBuy) {
      isSelected = false;
      profile.getAbstractContainer("zCoreBedWars", "selected", SelectedContainer.class).setSelected(getType(), 0);
    }
    
    Role role = Role.getRoleByPermission(this.getPermission());
    String color = has ?
        (isSelected ? Language.cosmetics$color$selected : Language.cosmetics$color$unlocked) :
        (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) && canBuy ? Language.cosmetics$color$canbuy : Language.cosmetics$color$locked;
    String desc = (has && canBuy ?
        Language.cosmetics$balloon$icon$has_desc$start.replace("{has_desc_status}", isSelected ? Language.cosmetics$icon$has_desc$selected : Language.cosmetics$icon$has_desc$select) :
        canBuy ?
            Language.cosmetics$balloon$icon$buy_desc$start
                .replace("{buy_desc_status}", (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) ? Language.cosmetics$icon$buy_desc$click_to_buy : Language.cosmetics$icon$buy_desc$enough) :
            Language.cosmetics$balloon$icon$perm_desc$start
                .replace("{perm_desc_status}", (role == null ? Language.cosmetics$icon$perm_desc$common : Language.cosmetics$icon$perm_desc$role.replace("{role}", role.getName()))))
        .replace("{name}", this.name).replace("{rarity}", this.getRarity().getName()).replace("{coins}", StringUtils.formatNumber(this.getCoins())).replace("{cash}", StringUtils.formatNumber(this.getCash()));
    ItemStack item = BukkitUtils.deserializeItemStack(this.icon + " : nome>" + color + this.name + " : desc>" + desc);
    if (isSelected) {
      BukkitUtils.putGlowEnchantment(item);
    }
    
    return item;
  }
  
  public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger()).getModule("BALLOONS");
  
  public static void setupBalloons() {
    KConfig config = Main.getInstance().getConfig("cosmetics", "balloons");
    
    for (String key : config.getKeys(false)) {
      long id = config.getInt(key + ".id");
      double coins = config.getDouble(key + ".coins");
      if (!config.contains(key + ".cash")) {
        config.set(key + ".cash", getAbsentProperty("balloons", key + ".cash"));
      }
      long cash = config.getInt(key + ".cash");
      String permission = config.getString(key + ".permission");
      String name = config.getString(key + ".name");
      String icon = config.getString(key + ".icon");
      if (!config.contains(key + ".rarity")) {
        config.set(key + ".rarity", getAbsentProperty("balloons", key + ".rarity"));
      }
      List<String> textures = config.getStringList(key + ".textures");
      if (textures.isEmpty()) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () ->
            LOGGER.warning("O balao \"" + key + "\" nao possui texturas."));
        continue;
      }
      
      new Balloon(id, EnumRarity.fromName(config.getString(key + ".rarity")), coins, cash, permission, name, icon, textures);
    }
  }
}
