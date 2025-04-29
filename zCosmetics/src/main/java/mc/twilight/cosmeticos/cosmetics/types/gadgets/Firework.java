package mc.twilight.cosmeticos.cosmetics.types.gadgets;

import mc.twilight.cosmeticos.cosmetics.object.GadgetCooldown;
import mc.twilight.cosmeticos.cosmetics.types.GadgetsCosmetic;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.cosmeticos.utils.FireworkUtil;
import mc.twilight.core.utils.enums.EnumRarity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class Firework extends GadgetsCosmetic {
  
  public Firework() {
    super("Decolar", EnumRarity.COMUM, "FIREWORK : 1 : nome>Decolar : desc>&7Vá até os céus com seu foguete.", "FIREWORK : 1 : nome>&6Engenhoca: &aDecolar");
  }
  
  @Override
  public void onClick(CUser user) {
    Player player = user.getPlayer();
    String cooldown = GadgetCooldown.getCooldown(player);
    if (!cooldown.isEmpty()) {
      player.sendMessage("§cAguarde " + cooldown + " para utilizar novamente uma Engenhoca!");
      return;
    }
    
    GadgetCooldown.setCooldown(player, 20);
    org.bukkit.entity.Firework firework = player.getWorld().spawn(player.getLocation(), org.bukkit.entity.Firework.class);
    FireworkMeta meta = firework.getFireworkMeta();
    meta.setPower(2);
    meta.addEffect(FireworkUtil.getRandomEffect());
    firework.setFireworkMeta(meta);
    firework.setPassenger(player);
  }
}
