package mc.twilight.skywars.cosmetics.types;

import mc.twilight.core.cash.CashManager;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.skywars.Language;
import mc.twilight.skywars.Main;
import mc.twilight.skywars.api.SWEvent;
import mc.twilight.skywars.api.SWEventHandler;
import mc.twilight.skywars.container.CosmeticsContainer;
import mc.twilight.skywars.container.SelectedContainer;
import mc.twilight.skywars.cosmetics.Cosmetic;
import mc.twilight.skywars.cosmetics.CosmeticType;
import mc.twilight.skywars.cosmetics.object.perk.PerkLevel;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumRarity;
import mc.twilight.skywars.cosmetics.types.perk.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public abstract class Perk extends Cosmetic implements SWEventHandler {

  protected static final KConfig CONFIG = Main.getInstance().getConfig("loja/habilidades", "perks");
  protected List<PerkLevel> levels;
  private final String name;
  private final String icon;

  public Perk(long id, String key, String permission, String name, String icon, List<PerkLevel> levels) {
    super(id, CosmeticType.PERK, 0.0, permission);
    this.name = name;
    this.icon = icon;
    this.levels = levels;
    this.rarity = this.getRarity(key);
  }

  public static void setupPerks() {
    checkIfAbsent("preparativos");
    checkIfAbsent("insanidade");
    checkIfAbsent("maestria_com_arcos");
    checkIfAbsent("feiticeiro");
    checkIfAbsent("piromaniaco");
    checkIfAbsent("resistente_a_quedas");
    checkIfAbsent("mestre_do_fim");
    checkIfAbsent("frio_no_combate");
    checkIfAbsent("headshot");
    checkIfAbsent("vingança");
    checkIfAbsent("ocultista");
    checkIfAbsent("foguete");
    checkIfAbsent("necromante");
    checkIfAbsent("trevo_da_sorte");
    checkIfAbsent("blindado");
    checkIfAbsent("vidaextra");

    new Preparativos(1, "preparativos");
    new Insanidade(1, "insanidade");
    new MaestriaComArcos(1, "maestria_com_arcos");
    new Headshot(1, "headshot");
    new Necromante(1, "necromante");
    new Feiticeiro(1, "feiticeiro");
    new Piromaniaco(1, "piromaniaco");
    new ResistenteAQuedas(1, "resistente_a_quedas");
    new MestreDoFim(1, "mestre_do_fim");
    new FrioNoCombate(1, "frio_no_combate");
    new Vinganca(1, "vingança");
    new Ocultista(1, "ocultista");
    new TrevoDaSorte(1, "trevo_da_sorte");
    new Blindado(1, "blindado");
    new VidaExtra(1, "vidaextra");

    if (CONFIG.getBoolean("foguete.enabled", true)) {
      new Foguete(1, "foguete");
    }
  }

  private static void checkIfAbsent(String key) {
    if (CONFIG.contains(key)) {
      return;
    }

    FileConfiguration config = YamlConfiguration.loadConfiguration(
            new InputStreamReader(Main.getInstance().getResource("perks.yml"), StandardCharsets.UTF_8));
    for (String dataKey : config.getConfigurationSection(key).getKeys(false)) {
      CONFIG.set(key + "." + dataKey, config.get(key + "." + dataKey));
    }
  }

  protected EnumRarity getRarity(String key) {
    if (!CONFIG.contains(key + ".rarity")) {
      CONFIG.set(key + ".rarity", getAbsentProperty("perks", key + ".rarity"));
    }

    return EnumRarity.fromName(CONFIG.getString(key + ".rarity"));
  }

  protected void register() {
    SWEvent.registerHandler(this);
  }

  protected void setupLevels(String key) {
    ConfigurationSection section = CONFIG.getSection(key);
    for (String level : section.getConfigurationSection("levels").getKeys(false)) {
      if (!section.contains("levels." + level + ".cash")) {
        CONFIG.set(key + ".levels." + level + ".cash",
                getAbsentProperty("perks", key + ".levels." + level + ".cash"));
      }

      PerkLevel perkLevel = new PerkLevel(
              section.getDouble("levels." + level + ".coins"),
              section.getInt("levels." + level + ".cash"),
              section.getString("levels." + level + ".description"),
              new HashMap<>());

      for (String property : section.getConfigurationSection("levels." + level).getKeys(false)) {
        if (!property.equals("coins") && !property.equals("cash") && !property.equals("description")) {
          perkLevel.getValues().put(property, section.get("levels." + level + "." + property));
        }
      }

      this.levels.add(perkLevel);
    }
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public double getCoins() {
    return this.getFirstLevel().getCoins();
  }

  @Override
  public long getCash() {
    return this.getFirstLevel().getCash();
  }

  public PerkLevel getFirstLevel() {
    return this.levels.get(0);
  }

  public PerkLevel getCurrentLevel(Profile profile) {
    return this.levels.get((int) (profile.getAbstractContainer("zCoreSkyWars", "cosmetics", CosmeticsContainer.class).getLevel(this) - 1));
  }

  public List<PerkLevel> getLevels() {
    return this.levels;
  }

  @Override
  public ItemStack getIcon(Profile profile) {
    return this.getIcon(profile, true);
  }

  public ItemStack getIcon(Profile profile, boolean select) {
    return this.getIcon(profile, true, select);
  }

  public ItemStack getIcon(Profile profile, boolean useDesc, boolean select) {
    double coins = profile.getCoins("zCoreSkyWars");
    long cash = profile.getStats("zCoreProfile", "cash");
    boolean has = this.has(profile);
    boolean canBuy = this.canBuy(profile.getPlayer());
    boolean isSelected = this.isSelectedPerk(profile);

    if (isSelected && !canBuy) {
      isSelected = false;
      profile.getAbstractContainer("zCoreSkyWars", "selected", SelectedContainer.class).setSelected(getType(), 0);
    }

    Role role = Role.getRoleByPermission(this.getPermission());
    int currentLevel = (int) profile.getAbstractContainer("zCoreSkyWars", "cosmetics", CosmeticsContainer.class).getLevel(this);
    PerkLevel perkLevel = this.levels.get(currentLevel - 1);
    String levelName = " " + (currentLevel > 3 ? currentLevel == 4 ? "IV" : "V" : StringUtils.repeat("I", currentLevel));
    String color = has ?
            Language.cosmetics$color$unlocked :
            (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) && canBuy ?
                    Language.cosmetics$color$canbuy :
                    Language.cosmetics$color$locked;
    String desc = "";

    if (useDesc) {
      desc = (has && canBuy ?
              (select ? "\n \n" + (isSelected ? Language.cosmetics$icon$has_desc$selected : Language.cosmetics$icon$has_desc$select) : Language.cosmetics$kit$icon$has_desc$start) :
              select ? "" :
                      canBuy ?
                              Language.cosmetics$kit$icon$buy_desc$start.replace("{buy_desc_status}",
                                      (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) ?
                                              Language.cosmetics$icon$buy_desc$click_to_buy :
                                              Language.cosmetics$icon$buy_desc$enough) :
                              Language.cosmetics$kit$icon$perm_desc$start.replace("{perm_desc_status}",
                                      role == null ? Language.cosmetics$icon$perm_desc$common :
                                              Language.cosmetics$icon$perm_desc$role.replace("{role}", role.getName())))
              .replaceFirst("Coins ", "Coins ")
              .replaceFirst("Cash", "Cash")
              .replace("{name}", this.name)
              .replace("{rarity}", this.getRarity().getName())
              .replace("{coins}", StringUtils.formatNumber(this.getCoins()))
              .replace("{cash}", StringUtils.formatNumber(this.getCash()));
    }

    ItemStack item = BukkitUtils.deserializeItemStack(
            this.icon.replace("{description}", perkLevel.getDescription())
                    + desc + " : nome>" + color + this.name + levelName);

    if (select && isSelected) {
      BukkitUtils.putGlowEnchantment(item);
    }

    return item;
  }
}
