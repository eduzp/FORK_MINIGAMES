package mc.twilight.cosmeticos.cosmetics.types;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.Cosmetic;
import mc.twilight.cosmeticos.cosmetics.CosmeticType;
import mc.twilight.cosmeticos.cosmetics.types.gadgets.*;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumRarity;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public abstract class GadgetsCosmetic extends Cosmetic implements Listener {
  
  protected List<ItemStack> items;
  private final ItemStack icon;
  private final ItemStack item;
  
  public GadgetsCosmetic(String name, EnumRarity rarity, String icon, String item) {
    super(name, rarity, CosmeticType.GADGET);
    this.icon = BukkitUtils.deserializeItemStack(icon);
    this.item = BukkitUtils.deserializeItemStack(item);
    Cosmetic.addCosmetic(this);
  }
  
  public static void setupGadgets() {
    new HotPotato();
    new MobGun();
    new CookieFortune();
    new Teleporter();
    new Cowboy();
    new Ghosts();
    new GoldFountain();
    new TntFountain();
    new Firework();
    new Trampoline();
  }
  
  public void registerListener() {
    Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
  }
  
  public abstract void onClick(CUser user);
  
  @Override
  public void onSelect(CUser user) {
    user.handleGadget();
  }
  
  public ItemStack getItem() {
    return this.item;
  }
  
  public ItemStack getIcon(String color, String... loreList) {
    return this.getIcon(color, Arrays.asList(loreList));
  }
  
  public ItemStack getIcon(String color, List<String> loreList) {
    ItemStack icon = this.icon.clone();
    ItemMeta meta = icon.getItemMeta();
    meta.setDisplayName(color + meta.getDisplayName());
    List<String> currentLore = meta.getLore();
    currentLore.add("");
    currentLore.add("§fRaridade: " + this.getRarity().getName());
    currentLore.addAll(loreList);
    meta.setLore(currentLore);
    icon.setItemMeta(meta);
    return icon;
  }
}
