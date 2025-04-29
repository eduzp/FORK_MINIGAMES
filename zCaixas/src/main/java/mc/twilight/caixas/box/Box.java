package mc.twilight.caixas.box;

import mc.twilight.caixas.box.action.BoxContent;
import mc.twilight.caixas.box.loot.animation.BoxAnimation;
import mc.twilight.caixas.box.loot.animation.types.DefaultAnimation;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Box {
  
  private static final List<Box> CHESTS = new ArrayList<>();
  private final String id;
  private final String name;
  private final String skinValue;
  private final Class<? extends BoxAnimation> lootChestAnimation;
  
  public Box(String id, String name, String skinValue) {
    this(id, name, skinValue, DefaultAnimation.class);
  }
  
  public Box(String id, String name, String skinValue, Class<? extends BoxAnimation> menuAnimation) {
    this.id = id;
    this.name = StringUtils.formatColors(name);
    this.skinValue = skinValue;
    this.lootChestAnimation = menuAnimation;
  }
  
  public static void setupBoxes() {
    CHESTS.add(new Box("mystery_box", "&b&lCápsula Mágica",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWRlYmNmZTQxNDNhMTFlNjFkMzkzMTc5OWZiMzIyZTVhYTJhZTczMjc1YzUzYzJjNjc0MTYxNzhkMTQ5ZTE1MiJ9fX0="));
  }
  
  public static Box getById(String id) {
    return CHESTS.stream().filter(chest -> chest.getId().equals(id)).findFirst().orElse(null);
  }
  
  public static Box getFirst() {
    return CHESTS.get(0);
  }
  
  public static Collection<Box> listChests() {
    return CHESTS;
  }
  
  public String getId() {
    return this.id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public ItemStack getIcon() {
    return BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>" + skinValue);
  }
  
  public BoxContent getRandomReward() {
    return BoxContent.getRandom();
  }
  
  public BoxAnimation getAnimation() {
    try {
      return this.lootChestAnimation.newInstance();
    } catch (Exception ex) {
      throw new IllegalArgumentException("Cannot create a instance of " + this.lootChestAnimation.toString() + " LootMenuAnimation class.");
    }
  }
}