package mc.twilight.bedwars.game.generators;

import java.util.List;

import mc.twilight.bedwars.Language;
import mc.twilight.bedwars.Main;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.utils.PlayerUtils;
import mc.twilight.core.libraries.holograms.HologramLibrary;
import mc.twilight.core.libraries.holograms.api.Hologram;
import mc.twilight.core.nms.NMS;
import mc.twilight.core.nms.interfaces.entity.IArmorStand;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class Generator {
  
  public int level, countdown;
  public String serialized;
  private Type type;
  private FloatingItem item;
  private Hologram hologram;
  
  public Generator(BedWars ignored, Type type, Location location) {
    this.level = 1;
    this.serialized = BukkitUtils.serializeLocation(location);
    this.type = type;
    this.countdown = this.type == Type.DIAMOND ? Language.options$generator$diamond$countdown_tier_1 : Language.options$generator$emerald$countdown_tier_1;
  }
  
  public void upgrade() {
    this.level++;
    if (this.type == Type.DIAMOND) {
      this.countdown = this.level == 2 ? Language.options$generator$diamond$countdown_tier_2 : Language.options$generator$diamond$countdown_tier_3;
    } else {
      this.countdown = this.level == 2 ? Language.options$generator$emerald$countdown_tier_2 : Language.options$generator$emerald$countdown_tier_3;
    }
    
    this.update();
  }
  
  public void update() {
    if (hologram != null) {
      for (int index = Language.ingame$generators$hologram.size(); index > 0; index--) {
        this.hologram.updateLine( Language.ingame$generators$hologram.size() - (index - 1) ,
            Language.ingame$generators$hologram.get(index - 1).replace("{tier}", StringUtils.repeat("I", this.level)).replace("{type}", this.type.getName()).replace("{s}", this.countdown > 1 ? "s" : "").replace("{time}", String.valueOf(this.countdown)));
      }
    }
    
    if (countdown == 0) {
      if (this.type == Type.DIAMOND) {
        this.countdown = this.level == 1 ? Language.options$generator$diamond$countdown_tier_1
            : this.level == 2 ? Language.options$generator$diamond$countdown_tier_2 : Language.options$generator$diamond$countdown_tier_3;
      } else {
        this.countdown = this.level == 1 ? Language.options$generator$emerald$countdown_tier_1
            : this.level == 2 ? Language.options$generator$emerald$countdown_tier_2 : Language.options$generator$emerald$countdown_tier_3;
      }
      if (PlayerUtils.getAmountOfItem(Material.valueOf(type.name()),
          this.getLocation()) < (4)) {
        Item i = item.getLocation().getWorld().dropItem(item.getLocation(), new ItemStack(Material.valueOf(type.name())));
        i.setPickupDelay(0);
        i.setVelocity(new Vector());
      }
      return;
    }
    
    this.countdown--;
  }
  
  public void enable() {
    if (this.item == null) {
      this.item = new FloatingItem(BukkitUtils.serializeLocation(BukkitUtils.deserializeLocation(serialized.split(", ")[0]).clone().add(0, 2.4, 0)));
    }
    
    this.hologram = HologramLibrary.createHologram(this.item.getLocation().clone().add(0, 0.6, 0));
    List<String> lines = Language.ingame$generators$hologram;
    for (int index = Language.ingame$generators$hologram.size(); index > 0; index--) {
      this.hologram.withLine(Language.ingame$generators$hologram.get(index - 1));
    }
    
    this.item.spawn(BukkitUtils.deserializeItemStack(type.name() + "_BLOCK : 1"), true);
    this.update();
  }
  
  public void reset() {
    this.level = 1;
    this.countdown = this.type == Type.DIAMOND ? Language.options$generator$diamond$countdown_tier_1 : Language.options$generator$emerald$countdown_tier_1;
    if (item != null) {
      this.item.disable();
      this.item = null;
    }
    if (this.hologram != null) {
      HologramLibrary.removeHologram(hologram);
      this.hologram = null;
    }
  }
  
  public int getTier() {
    return this.level;
  }
  
  public Type getType() {
    return this.type;
  }
  
  public Location getLocation() {
    return BukkitUtils.deserializeLocation(serialized.split(", ")[0]);
  }
  
  @Override
  public String toString() {
    return BukkitUtils.serializeLocation(getLocation()) + "; " + type.name();
  }
  
  public static class FloatingItem {
    
    private BukkitTask task;
    private String location;
    private IArmorStand armorStand;
    
    public FloatingItem(String location) {
      this.location = location;
    }
    
    public void spawn(ItemStack item, boolean big) {
      if (this.task != null) {
        return;
      }
      
      Location location =  this.getLocation();
      this.armorStand = NMS.createArmorStand(location, "", null);
      this.armorStand.getEntity().setGravity(false);
      this.armorStand.getEntity().setVisible(false);
      this.armorStand.getEntity().setBasePlate(false);
      this.armorStand.getEntity().setHelmet(item);
      this.armorStand.getEntity().setSmall(!big);
      this.armorStand.getEntity().teleport(location);
      
      this.task = new BukkitRunnable() {
        
        @Override
        public void run() {
          Location location = armorStand.getEntity().getLocation();
          
          location.setYaw((location.getYaw() - 7.5F));
          armorStand.getEntity().teleport(location);
        }
      }.runTaskTimer(Main.getInstance(), 0, 1);
    }
    
    public void disable() {
      if (this.task == null) {
        return;
      }
      
      this.task.cancel();
      this.task = null;
      this.armorStand.killEntity();
      this.armorStand = null;
    }
    
    public Location getLocation() {
      return BukkitUtils.deserializeLocation(this.location);
    }
    
    public IArmorStand getArmorStand() {
      return armorStand;
    }
  }
  
  public enum Type {
    EMERALD(Material.EMERALD),
    DIAMOND(Material.DIAMOND);
    
    private Material material;
    
    Type(Material material) {
      this.material = material;
    }
  
    private static final Type[] VALUES = values();
  
    public static Type fromName(String name) {
      for (Type mode : VALUES) {
        if (name.equalsIgnoreCase(mode.name())) {
          return mode;
        }
      }
    
      return null;
    }
    
    public String getName() {
      if (this == EMERALD) {
        return "§2§lESMERALDA";
      }
      
      return "§b§lDIAMANTE";
    }
    
    public Material getItem() {
      return material;
    }
    
    public ItemStack getBlock() {
      return new ItemStack(Material.matchMaterial(this.name() + "_BLOCK"));
    }
  }
}