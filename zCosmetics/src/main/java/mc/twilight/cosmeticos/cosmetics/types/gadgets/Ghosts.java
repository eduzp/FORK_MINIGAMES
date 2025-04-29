package mc.twilight.cosmeticos.cosmetics.types.gadgets;

import mc.twilight.core.Core;
import mc.twilight.cosmeticos.cosmetics.object.GadgetCooldown;
import mc.twilight.cosmeticos.cosmetics.types.GadgetsCosmetic;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumRarity;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class Ghosts extends GadgetsCosmetic {
  
  private final Map<Bat, ArmorStand> GHOSTS = new HashMap<>();
  
  public Ghosts() {
    super("Fantasmas",
        EnumRarity.RARO, "383 : 1 : nome>Fantasmas : desc>&7Divirta-se com fantasmas.", "383 : 1 : nome>&6Engenhoca: &aFantasmas");
    this.registerListener();
  }
  
  @Override
  public void onClick(CUser user) {
    Player player = user.getPlayer();
    String cooldown = GadgetCooldown.getCooldown(player);
    if (!cooldown.isEmpty()) {
      player.sendMessage("§cAguarde " + cooldown + " para utilizar novamente uma Engenhoca!");
      return;
    }
    
    GadgetCooldown.setCooldown(player, 45);
    for (int i = 0; i < 8; ++i) {
      Bat bat = player.getWorld().spawn(player.getLocation().add(0.0D, 1.0D, 0.0D), Bat.class);
      ArmorStand ghost = bat.getWorld().spawn(bat.getLocation(), ArmorStand.class);
      ghost.setSmall(true);
      ghost.setGravity(false);
      ghost.setVisible(false);
      ghost.setHelmet(BukkitUtils.deserializeItemStack("397:1 : 1"));
      ghost.setChestplate(BukkitUtils.deserializeItemStack("LEATHER_CHESTPLATE : 1"));
      bat.setPassenger(ghost);
      bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 240, 1));
      this.GHOSTS.put(bat, ghost);
    }
    
    Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), () -> {
      GHOSTS.keySet().forEach(Entity::remove);
      GHOSTS.values().forEach(ArmorStand::remove);
      GHOSTS.clear();
    }, 220L);
  }
  
  @EventHandler
  public void onEntityDamage(EntityDamageEvent evt) {
    if (!(evt.getEntity() instanceof Bat)) {
      return;
    }
    evt.setCancelled(GHOSTS.containsKey((Bat) evt.getEntity()));
  }
}
