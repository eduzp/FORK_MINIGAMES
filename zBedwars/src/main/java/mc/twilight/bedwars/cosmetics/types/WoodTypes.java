package mc.twilight.bedwars.cosmetics.types;

import mc.twilight.bedwars.Language;
import mc.twilight.bedwars.Main;
import mc.twilight.bedwars.cosmetics.Cosmetic;
import mc.twilight.bedwars.cosmetics.CosmeticType;
import mc.twilight.bedwars.hook.container.SelectedContainer;
import mc.twilight.core.cash.CashManager;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumRarity;
import org.bukkit.inventory.ItemStack;

public class WoodTypes extends Cosmetic {
  
  private static final KConfig CONFIG = Main.getInstance().getConfig("cosmetics", "woodtypes");
  private String name;
  private String icon;
  
  public WoodTypes(long id, String key, double coins, String permission, String name, String icon) {
    super(id, CosmeticType.WOOD_TYPE, coins, permission);
    this.name = name;
    this.icon = icon;
    if (id != 0) {
      this.rarity = this.getRarity(key);
      this.cash = this.getCash(key);
    } else {
      this.rarity = EnumRarity.COMUM;
    }
  }
  
  public ItemStack getItem() {
    return BukkitUtils.deserializeItemStack(this.icon);
  }
  
  public static void setupTypes() {
    KConfig config = Main.getInstance().getConfig("cosmetics", "woodtypes");
  
    for (String key : config.getKeys(false)) {
      long id = config.getInt(key + ".id", 0);
      double coins = config.getDouble(key + ".coins", 0.0D);
      String permission = config.getString(key + ".permission");
      if (permission == null) {
        permission = "";
      }
      String name = config.getString(key + ".name");
      String item = config.getString(key + ".item");
    
      new WoodTypes(id, key, coins, permission, name, item);
    }
  }
  
  protected long getCash(String key) {
    if (!CONFIG.contains(key + ".cash")) {
      CONFIG.set(key + ".cash", getAbsentProperty("woodtypes", key + ".cash"));
    }
    
    return (long) CONFIG.getInt(key + ".cash");
  }
  
  protected EnumRarity getRarity(String key) {
    if (!CONFIG.contains(key + ".rarity")) {
      CONFIG.set(key + ".rarity", getAbsentProperty("woodtypes", key + ".rarity"));
    }
    
    return EnumRarity.fromName(CONFIG.getString(key + ".rarity"));
  }
  
  @Override
  public String getName() {
    return this.name;
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
    String color = has ? (isSelected ? Language.cosmetics$color$selected : Language.cosmetics$color$unlocked)
        : (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) && canBuy ? Language.cosmetics$color$canbuy : Language.cosmetics$color$locked;
    String desc = (has && canBuy ?
        Language.cosmetics$wood_types$icon$has_desc$start.replace("{has_desc_status}", isSelected ? Language.cosmetics$icon$has_desc$selected : Language.cosmetics$icon$has_desc$select) :
        canBuy ?
            Language.cosmetics$wood_types$icon$buy_desc$start
                .replace("{buy_desc_status}", (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) ? Language.cosmetics$icon$buy_desc$click_to_buy : Language.cosmetics$icon$buy_desc$enough) :
            Language.cosmetics$wood_types$icon$perm_desc$start
                .replace("{perm_desc_status}", (role == null ? Language.cosmetics$icon$perm_desc$common : Language.cosmetics$icon$perm_desc$role.replace("{role}", role.getName()))))
        .replace("{name}", this.name).replace("{rarity}", this.getRarity().getName()).replace("{coins}", StringUtils.formatNumber(this.getCoins())).replace("{cash}", StringUtils.formatNumber(this.getCash()));
    ItemStack item = BukkitUtils.deserializeItemStack(this.icon + " : desc>" + desc + " : nome>" + (color + this.name));
    if (isSelected) {
      BukkitUtils.putGlowEnchantment(item);
    }
    
    return item;
  }
}