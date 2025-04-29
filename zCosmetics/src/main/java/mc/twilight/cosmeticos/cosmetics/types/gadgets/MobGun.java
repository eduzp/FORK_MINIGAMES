package mc.twilight.cosmeticos.cosmetics.types.gadgets;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.object.GadgetCooldown;
import mc.twilight.cosmeticos.cosmetics.types.GadgetsCosmetic;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.utils.enums.EnumRarity;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MobGun extends GadgetsCosmetic {
  
  protected Player player;
  
  public MobGun() {
    super("MobGun", EnumRarity.RARO, "BOW : 1 : nome>MobGun : desc>&7Atire mobs.", "BOW : 1 : nome>&6Engenhoca: &aMobGun");
    
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
    
    new BukkitRunnable() {
      private int time = 8;
      private int type = 0;
      private final List<Entity> entity = new ArrayList<>();

      private Listener listener = new Listener() {
        @EventHandler
        public void onEntityDamage(EntityDamageEvent evt) {
          if (!(evt.getEntity() instanceof Player)) {
            evt.setCancelled(entity.contains(evt.getEntity()));
          }
        }
      };
      
      {
        Bukkit.getPluginManager().registerEvents(listener, Main.getInstance());
      }
      
      @Override
      public void run() {
        if (time == -10 || user.getProfile() == null || user.getProfile().playingGame()) {
          entity.forEach(Entity::remove);
          entity.clear();
          HandlerList.unregisterAll(listener);
          return;
        }
        if (time > 0) {
          EnumSound.CHICKEN_EGG_POP.play(player, 1.0f, 1.0f);
          EntityType[] entityTypes = new EntityType[]{EntityType.PIG, EntityType.VILLAGER, EntityType.OCELOT, EntityType.COW,
              EntityType.CREEPER, EntityType.SPIDER, EntityType.SQUID, EntityType.CHICKEN, EntityType.SHEEP, EntityType.WOLF, EntityType.WITCH,
              EntityType.BLAZE, EntityType.HORSE, EntityType.ZOMBIE, EntityType.SKELETON, EntityType.MUSHROOM_COW, EntityType.CAVE_SPIDER,
              EntityType.PIG_ZOMBIE};
          
          EntityType entityType = entityTypes[ThreadLocalRandom.current().nextInt(type + 1)];
          Entity mob = player.getWorld().spawnEntity(player.getEyeLocation().add(0.0D, -0.5D, 0.0D), entityType);
          if (entityType == EntityType.VILLAGER) {
            ((Villager) mob).setProfession(Villager.Profession.BUTCHER);
          } else if (entityType == EntityType.SHEEP) {
            ((Sheep) mob).setColor(DyeColor.WHITE);
          } else if (entityType == EntityType.HORSE) {
            ((Horse) mob).setColor(Horse.Color.BROWN);
          }
          mob.setPassenger(null);
          mob.setVelocity(new Vector(mob.getLocation()
              .getDirection().getX() / 2.0D, 0.0D, mob.getLocation().getDirection().getZ() / 2.0D));
          entity.add(mob);
          
          type++;
        }
        --time;
      }
    }.runTaskTimer(Main.getInstance(), 0, 13);
  }
  
}
