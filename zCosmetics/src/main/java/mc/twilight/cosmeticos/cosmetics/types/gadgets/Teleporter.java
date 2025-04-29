package mc.twilight.cosmeticos.cosmetics.types.gadgets;

import mc.twilight.cosmeticos.cosmetics.object.GadgetCooldown;
import mc.twilight.cosmeticos.cosmetics.types.GadgetsCosmetic;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.utils.enums.EnumRarity;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;

public class Teleporter extends GadgetsCosmetic {
  
  public Teleporter() {
    super("Teleporte", EnumRarity.RARO, "ENDER_PEARL : 1 : nome>Teleporte : desc>&7Teleporte-se com uma pérola do fim.", "ENDER_PEARL : 1 : nome>&6Engenhoca: &aTeleporte");
  }
  
  @Override
  public void onClick(CUser user) {
    Player player = user.getPlayer();
    String cooldown = GadgetCooldown.getCooldown(player);
    if (!cooldown.isEmpty()) {
      player.sendMessage("§cAguarde " + cooldown + " para utilizar novamente uma Engenhoca!");
      return;
    }
    
    GadgetCooldown.setCooldown(player, 10);
    player.launchProjectile(EnderPearl.class).setPassenger(player);
  }
}
