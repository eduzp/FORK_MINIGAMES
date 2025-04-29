package mc.twilight.cosmeticos.cosmetics.object;

import mc.twilight.cosmeticos.cosmetics.types.BannerCosmetic;
import mc.twilight.cosmeticos.hook.player.CUser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Banner {
  
  private Player owner;
  private ItemStack banner;
  
  public Banner(CUser owner, BannerCosmetic cosmetic) {
    this.owner = owner.getPlayer();
    this.banner = cosmetic.getIcon("§a");
    this.apply();
  }
  
  public void apply() {
    this.owner.getInventory().setHelmet(this.banner);
    this.owner.updateInventory();
  }
  
  public void destroy() {
    this.owner.getInventory().setHelmet(null);
    this.owner.updateInventory();
    this.owner = null;
    this.banner = null;
  }
}
