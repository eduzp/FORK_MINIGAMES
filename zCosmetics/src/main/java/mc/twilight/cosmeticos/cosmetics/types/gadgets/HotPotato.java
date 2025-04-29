package mc.twilight.cosmeticos.cosmetics.types.gadgets;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.object.GadgetCooldown;
import mc.twilight.cosmeticos.cosmetics.types.GadgetsCosmetic;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.cosmeticos.utils.FireworkUtil;
import mc.twilight.core.utils.enums.EnumRarity;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class HotPotato extends GadgetsCosmetic {
  
  public HotPotato() {
    super("Batata Quente", EnumRarity.RARO, "BAKED_POTATO : 1 : nome>Batata Quente : desc>&7Se divirta com esse brinquedo de\n&7batata quente.",
        "BAKED_POTATO : 1 : nome>&6Engenhoca: &aBatata Quente");
  }
  
  @Override
  public void onClick(CUser user) {
    Player player = user.getPlayer();
    String cooldown = GadgetCooldown.getCooldown(player);
    if (!cooldown.isEmpty()) {
      player.sendMessage("§cAguarde " + cooldown + " para utilizar novamente uma Engenhoca!");
      return;
    }
    
    GadgetCooldown.setCooldown(player, 35);
    new BukkitRunnable() {
      private int time = 7;
      private ItemStack oldHelmet;
      
      @Override
      public void run() {
        if (!player.isOnline() || time == 0 || user.getProfile() == null || user.getProfile().playingGame()) {
          if (player.isOnline() && user.getProfile() != null && !user.getProfile().playingGame()) {
            player.getWorld().createExplosion(player.getLocation(), 0.0F);
            FireworkUtil.playFirework(player.getLocation().clone().add(0.5, 1.7, 0.5), FireworkUtil.getBlowupRandomEffect());
            player.getInventory().setHelmet(this.oldHelmet);
            player.updateInventory();
          }
          cancel();
          return;
        }
        
        if (player.getInventory().getHelmet() == null || player.getInventory().getHelmet().getType() != Material.TNT) {
          this.oldHelmet = player.getInventory().getHelmet();
          player.getInventory().setHelmet(new ItemStack(Material.TNT));
          player.updateInventory();
        }
        
        FireworkUtil.playFirework(player.getLocation().clone().add(0.5, 1.7, 0.5), FireworkUtil.getRandomEffect());
        --time;
      }
    }.runTaskTimer(Main.getInstance(), 0, 20);
  }
}
