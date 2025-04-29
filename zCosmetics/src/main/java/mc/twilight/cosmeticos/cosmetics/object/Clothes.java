package mc.twilight.cosmeticos.cosmetics.object;

import mc.twilight.cosmeticos.cosmetics.types.ClothesCosmetic;
import mc.twilight.cosmeticos.hook.player.CUser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Clothes {
  
  private Player owner;
  private ItemStack helmet;
  private ItemStack chestplate;
  private final ClothesCosmetic cosmetic;
  private ItemStack leggings;
  private ItemStack boots;
  
  public Clothes(CUser owner, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots, ClothesCosmetic cosmetic) {
    this.owner = owner.getPlayer();
    this.cosmetic = cosmetic;
    this.helmet = helmet;
    this.chestplate = chestplate;
    this.leggings = leggings;
    this.boots = boots;
    
    if (this.helmet != null) {
      this.owner.getInventory().setHelmet(helmet);
    }
    if (this.chestplate != null) {
      this.owner.getInventory().setChestplate(chestplate);
    }
    if (this.leggings != null) {
      this.owner.getInventory().setLeggings(leggings);
    }
    if (this.boots != null) {
      this.owner.getInventory().setBoots(boots);
    }
  }
  
  public void despawn() {
    if (this.helmet != null) {
      this.helmet = null;
      this.owner.getInventory().setHelmet(null);
    }
    if (this.chestplate != null) {
      this.chestplate = null;
      this.owner.getInventory().setChestplate(null);
    }
    if (this.leggings != null) {
      this.leggings = null;
      this.owner.getInventory().setLeggings(null);
    }
    if (this.boots != null) {
      this.boots = null;
      this.owner.getInventory().setBoots(null);
    }
    this.owner = null;
  }
}
