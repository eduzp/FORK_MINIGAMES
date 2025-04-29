package mc.twilight.cosmeticos.cosmetics.object;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.utils.iterator.RGBIterator;
import mc.twilight.core.utils.BukkitUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class RGBArmor {
  
  private BukkitTask task;
  private RGBIterator rgbIterator;
  
  public RGBArmor(Player owner, boolean helmet, boolean chestplate, boolean leggings, boolean boots) {
    this.rgbIterator = new RGBIterator();
    this.task = new BukkitRunnable() {
      @Override
      public void run() {
        if (!owner.isOnline()) {
          RGBArmor.this.cancel();
          return;
        }
        
        String color = rgbIterator.next();
        if (helmet) {
          ItemStack h = BukkitUtils.deserializeItemStack("LEATHER_HELMET : 1 : nome>&6RGB : pintar>" + color);
          owner.getEquipment().setHelmet(h);
        }
        if (chestplate) {
          ItemStack g = BukkitUtils.deserializeItemStack("LEATHER_CHESTPLATE : 1 : nome>&6RGB : pintar>" + color);
          owner.getEquipment().setChestplate(g);
        }
        if (leggings) {
          ItemStack f = BukkitUtils.deserializeItemStack("LEATHER_LEGGINGS : 1 : nome>&6RGB : pintar>" + color);
          owner.getEquipment().setLeggings(f);
        }
        if (boots) {
          ItemStack e = BukkitUtils.deserializeItemStack("LEATHER_BOOTS : 1 : nome>&6RGB : pintar>" + color);
          owner.getEquipment().setBoots(e);
        }
        
      }
    }.runTaskTimerAsynchronously(Main.getInstance(), 0, 1);
  }
  
  public void cancel() {
    this.task.cancel();
    this.task = null;
    this.rgbIterator = null;
  }
}
